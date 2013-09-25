package com.omo.web;

import com.omo.domain.ApplicationUser;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


@RequestMapping("/applicationusers")
@Controller
@RooWebScaffold(path = "applicationusers", formBackingObject = ApplicationUser.class)
public class ApplicationUserController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(ApplicationUserController.class);

    @RequestMapping(value = "registerUser", method = RequestMethod.POST, produces = "text/html")
    public String confirmOrderPost(ApplicationUser appUser, Model uiModel, HttpServletRequest request, sess) throws Exception {
        logger.debug("confirmOrderPost for order ");
        //String orderid = request.getParameter("orderId");
        logger.debug("email:" + appUser.getEmail());
        applicationUserRepository.save(appUser);
        HttpSession session = request.getSession();
        session.setAttribute("applicationUser", appUser);
        return "public/index";
    }


}
