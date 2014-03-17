package com.omo.web;

import com.omo.domain.ApplicationUser;
import com.omo.service.EmailViaSES;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RequestMapping("/applicationusers")
@Controller
@RooWebScaffold(path = "applicationusers", formBackingObject = ApplicationUser.class)
public class ApplicationUserController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(ApplicationUserController.class);

    @RequestMapping(value = "registerUser", method = RequestMethod.POST, produces = "text/html")
    public String registerUser(ApplicationUser appUser, Model uiModel, HttpServletRequest request) throws Exception {
        logger.debug("registerUser for " + appUser.getEmail());
        //String orderid = request.getParameter("orderId");
        logger.debug("email:" + appUser.getEmail());
        appUser.setIsAdmin(false);
        applicationUserRepository.save(appUser);
        HttpSession session = request.getSession();
        session.setAttribute("applicationUser", appUser);
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
        EmailViaSES.sendEmail("Password reminder", passwordMsg, "omo@markhaney.net", newEmail);

        return "redirect:/public/start";

    }

    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/html")
    public String login(@RequestParam(value="email") String email, @RequestParam(value="password") String password,
                        Model uiModel, HttpServletRequest request) throws Exception {
        logger.debug("login for order ");
        //String orderid = request.getParameter("orderId");
        logger.debug("email:" + email);
        ApplicationUser appUser = applicationUserRepository.findOneByEmail(email);
        if (appUser == null){
            logger.debug("login unsuccessful");
            return "redirect:/public/start";
        } else {
            logger.debug("login successful");
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


}
