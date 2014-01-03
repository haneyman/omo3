package com.omo.web;

import com.omo.domain.ApplicationUser;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        applicationUserRepository.save(appUser);
        HttpSession session = request.getSession();
        session.setAttribute("applicationUser", appUser);
        return "public/index";
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
            return "public/index";
        } else {
            logger.debug("login successful");
        }
        HttpSession session = request.getSession();
        session.setAttribute("applicationUser", appUser);
        return "public/index";
    }


    @RequestMapping(value = "logout", produces = "text/html")
    public String logout(Model uiModel, HttpServletRequest request) throws Exception {
        logger.debug("logout for order ");
        HttpSession session = request.getSession();
        session.setAttribute("applicationUser", null);
        return "public/index";
    }


}
