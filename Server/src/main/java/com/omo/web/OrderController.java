package com.omo.web;

import com.omo.domain.Menu;
import com.omo.domain.MenuItem;
import com.omo.domain.Order;
import com.omo.repository.MenuRepository;
import com.omo.service.MenuServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Enumeration;

@RequestMapping("/orders")
@Controller
@RooWebScaffold(path = "orders", formBackingObject = Order.class)
public class OrderController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(MenuController.class);

    @Autowired
    MenuRepository menuRepository;

    @RequestMapping(value = "confirmOrder/{id}", produces = "text/html")
    public String confirmOrder(@PathVariable("id") BigInteger id, Model uiModel, HttpServletRequest httpServletRequest) throws Exception {
        logger.debug("confirmOrder " + id);
        Order order = orderService.findOrder(id);
        if (order != null) {
            uiModel.addAttribute("order", order);
        } else
            throw new Exception("Order not found:" + id);
        uiModel.addAttribute("id", id);
        return "public/confirmOrder";
    }


    @RequestMapping(value = "confirmOrder", method = RequestMethod.POST, produces = "text/html")
    public String confirmOrderPost(Model uiModel, HttpServletRequest request) throws Exception {
        logger.debug("confirmOrderPost for order ");
        String orderid = request.getParameter("orderId");
        Order order = orderService.findOrder(new BigInteger(orderid));
        if (order == null)
            throw new Exception("Could not retrieve order: " + order);
        logger.debug("   order found: " + order.getId() + " , status set to OPEN");
        order.setStatus(Order.ORDER_STATUS.OPEN);
        orderService.updateOrder(order);
        return "public/myOrders";
    }


    @RequestMapping(value = "myOrders", produces = "text/html")
    public String myOrders(Order order, Model uiModel) throws Exception {
        logger.debug("myOrders " );
        return "public/myOrders";
    }


    @RequestMapping(value = "submitOrder", method = RequestMethod.POST, produces = "text/html")
    public String createOrder(Model uiModel, HttpServletRequest httpServletRequest) throws Exception {
        logger.debug("createOrder");
/*
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, order);
            return "orders/create";
        }
*/
        String menuId="";
        String menuItemUUID;
        MenuItem menuItem;
        MenuItem menuItemParent;
        Order order = new Order();
        Menu menu=null;
        Float total=0f;
        //first find the menu - by looping through parameters
        Enumeration enumeration = httpServletRequest.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = (String) enumeration.nextElement();
            if (parameterName.equalsIgnoreCase("menuId")) {
                menuId = httpServletRequest.getParameter(parameterName);
                menu = menuRepository.findOne(new BigInteger(menuId));
                if (menu == null)
                    throw new Exception("Could not retrieve menu: " + menu);
                order.setMenu(menu);
                logger.debug("Menu id parm found, menu set to: " + menu.getId());
            }
        }
        //now find the selected menuItems
        enumeration = httpServletRequest.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = (String) enumeration.nextElement();
//            logger.debug("Parameter = " + parameterName);
            if (parameterName.contains(MenuItem.MENUITEM_LABEL)) {
                menuItemUUID = parameterName.substring(MenuItem.MENUITEM_LABEL.length() + 1);
//                logger.debug("   Order has menu item: " + menuItemUUID + " retrieving...");
                menuItem = MenuServiceImpl.getMenuItemFromSet(menu.getMenuItems(), menuItemUUID);
                menuItemParent = MenuServiceImpl.getMenuItemFromSet(menu.getMenuItems(), menuItem.getParentUuid());
                if (menuItem == null)
                    throw new Exception("   order could not find menuItem UUID:" + menuItemUUID);
                if (menuItemParent == null)
                    throw new Exception("   order could not find menuItem parent:" );
                else {
                    menuItem.setInternalNotes(menuItemParent.getName());
                }
                total += menuItem.getPrice();
                order.getMenuItems().add(menuItem);
                logger.debug("   Menu Item added to new order " );
            }
        }
        order.setStatus(Order.ORDER_STATUS.INIT);
        order.setNotes(httpServletRequest.getParameter("notes"));
        order.setTotalPretax(total);

        uiModel.asMap().clear();
        orderService.saveOrder(order);
        //pass the order id to confirmOrder?
        logger.debug("New order added with " + order.getMenuItems().size() + " items." );
//        return "public/confirmOrder";
        return "redirect:/orders/confirmOrder/" + encodeUrlPathSegment(order.getId().toString(), httpServletRequest);

//        return "redirect:/orders/" + encodeUrlPathSegment(order.getId().toString(), httpServletRequest);
    }


}
