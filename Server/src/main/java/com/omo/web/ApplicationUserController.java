package com.omo.web;

import com.omo.domain.ApplicationUser;
import com.omo.repository.ApplicationUserRepository;
import com.omo.service.EmailViaSES;
import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;


@RequestMapping("/applicationusers")
@Controller
public class ApplicationUserController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(ApplicationUserController.class);
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @RequestMapping(value = "registerUser", method = RequestMethod.POST, produces = "text/html")
    public String registerUser(ApplicationUser appUser, Model uiModel, HttpServletRequest request) throws Exception {
        logger.debug("registerUser for " + appUser.getEmail());
        if (appUser != null
        && appUser.getEmail().trim().length() > 0
        && appUser.getPassword().trim().length() > 0 ) {
            appUser.setIsAdmin(false);
            applicationUserRepository.save(appUser);
            HttpSession session = request.getSession();
            session.setAttribute("applicationUser", appUser);
        }

        String returnView = request.getParameter("returnView");
        if (returnView == null || returnView.length() == 0)
            return "redirect:/public/start";
        else
            return "redirect:" + returnView;
    }

    @RequestMapping(value = "remind/{email}", produces = "text/html")
    public String remind(@PathVariable("email") String email) throws Exception {
        logger.debug("remind for " + email);
        String newEmail = email.replaceAll("&#46;", ".");//hack, because period is not coming through
        logger.debug("   new email is " + newEmail);
        ApplicationUser appUser = applicationUserRepository.findOneByEmail(newEmail);
        String passwordMsg = "";
        if (appUser == null){
            passwordMsg = "Sorry, we don't have that email on record.  Try registering again.";
        } else {
            passwordMsg = "You requested your password.  Password: " + appUser.getPassword();
        }
        EmailViaSES.sendEmail("Password reminder", passwordMsg, "support@menubreeze.com", newEmail);

        return "redirect:/public/start";

    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }


    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/html")
    public String login(@RequestParam(value="email") String email, @RequestParam(value="password") String password,
                        Model uiModel, HttpServletRequest request) throws Exception {
        logger.debug("login for email:" + email);
        ApplicationUser appUser = applicationUserRepository.findOneByEmail(email);
        if (appUser == null){
            logger.debug("login unsuccessful, unknown user");
            return "redirect:/public/start";
        } else {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(appUser.getPassword().getBytes());
            String digestString = convertByteArrayToHexString(digest);
            if (digestString.equals(password))
                logger.debug("login successful");
            else {
                logger.debug("Incorrect password");
                return "redirect:/public/start";//TODO:this should be a login page??
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("applicationUser", appUser);
        String returnView = request.getParameter("returnView");
        if (returnView == null || returnView.length() == 0)
            return "redirect:/public/start";
        else
            return "redirect:" + returnView;
    }


    @RequestMapping(value = "logout", produces = "text/html")
    public String logout(Model uiModel, HttpServletRequest request) throws Exception {
        logger.debug("logout  ");
        HttpSession session = request.getSession();
        session.setAttribute("applicationUser", null);
        return "redirect:/public/start";
    }


    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid ApplicationUser applicationUser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, applicationUser);
            return "applicationusers/create";
        }
        uiModel.asMap().clear();
        applicationUserRepository.save(applicationUser);
        return "redirect:/applicationusers/" + encodeUrlPathSegment(applicationUser.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new ApplicationUser());
        return "applicationusers/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("applicationuser", applicationUserRepository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "applicationusers/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("applicationusers", applicationUserRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) applicationUserRepository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("applicationusers", applicationUserRepository.findAll());
        }
        addDateTimeFormatPatterns(uiModel);
        return "applicationusers/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid ApplicationUser applicationUser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, applicationUser);
            return "applicationusers/update";
        }
        uiModel.asMap().clear();
        applicationUserRepository.save(applicationUser);
        return "redirect:/applicationusers/" + encodeUrlPathSegment(applicationUser.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, applicationUserRepository.findOne(id));
        return "applicationusers/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ApplicationUser applicationUser = applicationUserRepository.findOne(id);
        applicationUserRepository.delete(applicationUser);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/applicationusers";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("applicationUser_dateadded_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("applicationUser_datelastlogin_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, ApplicationUser applicationUser) {
        uiModel.addAttribute("applicationUser", applicationUser);
        addDateTimeFormatPatterns(uiModel);
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
