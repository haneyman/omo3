package com.omo.web;

import com.omo.domain.Order;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RequestMapping("/orders")
@Controller
@RooWebScaffold(path = "orders", formBackingObject = Order.class)
public class OrderController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(MenuController.class);

    @RequestMapping(value = "publicCreate", method = RequestMethod.POST, produces = "text/html")
    public String createOrder(Model uiModel, HttpServletRequest httpServletRequest) {
        logger.debug("createOrder");
/*
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, order);
            return "orders/create";
        }
*/
        Enumeration enumeration = httpServletRequest.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = (String) enumeration.nextElement();
            logger.debug("Parameter = " + parameterName);
        }
        uiModel.asMap().clear();
        //orderService.saveOrder(order);
        return "public/confirmOrder";
//        return "redirect:/orders/" + encodeUrlPathSegment(order.getId().toString(), httpServletRequest);
    }


}
