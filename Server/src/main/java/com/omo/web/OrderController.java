package com.omo.web;

import com.omo.domain.*;
import com.omo.repository.MenuRepository;
import com.omo.repository.OrderRepository;
import com.omo.service.MenuService;
import com.omo.service.MenuServiceImpl;
import com.omo.service.OrderService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.*;

@RequestMapping("/orders")
@Controller
public class OrderController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(MenuController.class);

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MenuService menuService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;


    @RequestMapping(value = "reorder/{id}", produces = "text/html")
    public String reorderOrder(@PathVariable("id") BigInteger id, Model uiModel, HttpServletRequest httpServletRequest)
            throws Exception {
        logger.debug("reOrder " + id);
        Order order = orderService.findOrder(id);
        Order newOrder = order.copy();

        uiModel.addAttribute("order", newOrder);
        orderService.saveOrder(newOrder);
        uiModel.addAttribute("id", newOrder.getId());
        return "public/confirmOrder";
    }

/*

    @RequestMapping(value = "confirmOrder/{id}", produces = "text/html")
    public String confirmOrder(@PathVariable("id") BigInteger id, Model uiModel, HttpServletRequest httpServletRequest)
            throws Exception {
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
    public String confirmOrderPost(Model uiModel, HttpServletRequest request, HttpSession session) throws Exception {
        logger.debug("confirmOrderPost for order ");
        String orderid = request.getParameter("orderId");
        Order order = orderService.findOrder(new BigInteger(orderid));
        if (order == null)
            throw new Exception("Could not retrieve order: " + order);
        logger.debug("   order found: " + order.getId() + " , status set to OPEN");
        order.setStatus(Order.ORDER_STATUS.OPEN);
        orderService.updateOrder(order);
        try {
            orderService.notifyOrder(order);
        } catch (Exception e) {
            //do nada
        }
        uiModel.addAttribute("order", order);
//        return "orders/myOrders";
        return myOrders(uiModel, session);
    }


*/



    @RequestMapping(value = "myOrders", produces = "text/html")
    public String myOrders(Model uiModel, HttpSession session) throws Exception {
        logger.debug("myOrders " );
        ApplicationUser user = (ApplicationUser) session.getAttribute("applicationUser");
        //List<Order> orders = orderRepository.findAll();
        //List<Order> orders = orderRepository.findOrdersByUser(user);
        List<Order> orders = orderService.getOrdersByUser(user);
        uiModel.addAttribute("orders", orders);
        return "public/myOrders";
    }


    @RequestMapping(value = "orders/{filter}", produces = "text/html")
    public String allOrders(@PathVariable("filter") String filter, Model uiModel) throws Exception {
        logger.debug("allOrders with filter " + filter );
        List<Order> filteredOrders = new ArrayList<Order>();
        PageRequest request =
                new PageRequest(0, 100, org.springframework.data.domain.Sort.Direction.DESC, "orderDate");
        Page<Order> orders = orderRepository.findAll(request);

        if (filter.equalsIgnoreCase("all")) {
            uiModel.addAttribute("orders", orders.getContent());
        } else {
            for (Order order: orders.getContent()) {
                if (filter.equalsIgnoreCase("today")) {
                    if (DateUtils.isSameDay(order.getOrderDate(), new Date())) {
                        if (order.getStatus() == Order.ORDER_STATUS.OPEN) {
                            filteredOrders.add(order);
                        }
                    }
                } else if (filter.equalsIgnoreCase("open")) {
                    if (order.getStatus() == Order.ORDER_STATUS.OPEN)
                        filteredOrders.add(order);
                }
            }
            uiModel.addAttribute("orders", filteredOrders);
            uiModel.addAttribute("filter", filter);
        }
        return "public/orders";
    }

    /*
    Called when they hit "add to order" in dialog in menu
     */
    @RequestMapping(value = "submitOrderItem", method = RequestMethod.POST, produces = "text/html")
    public String submitOrderItem(Model uiModel, HttpServletRequest httpServletRequest, HttpSession session) throws Exception {
        logger.debug("orderItem");
        String menuId="";
        String menuItemUUID;
        MenuItem menuItem=null;
        MenuItem menuItemParent;
        Menu menu=null;
        Float total=0f;
        //get the current order or create one
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            order = new Order();
        }


        String note  = httpServletRequest.getParameter("dialogOrderItemNote");
        String checkout = httpServletRequest.getParameter("dialogOrderItemCheckout");
        menuItemUUID = httpServletRequest.getParameter("dialogOrderItemItemUuid");
        Integer menuItemQuantity = Integer.parseInt(httpServletRequest.getParameter("dialogOrderItemQuantity"));

        //get the menu
        menuId = httpServletRequest.getParameter("dialogOrderItemMenuId");
        menu = menuRepository.findOne(new BigInteger(menuId));
        if (menu == null)
            throw new Exception("Could not retrieve menu: " + menu);
        order.setMenu(menu);
        logger.debug("Menu id parm found, menu set to: " + menu.getId());

        //get the menu item and its options
        menuItem = MenuServiceImpl.getMenuItemFromSet(menu.getMenuItems(), menuItemUUID);
        //menuItemParent = MenuServiceImpl.getMenuItemFromSet(menu.getMenuItems(), menuItem.getParentUuid());
        if (menuItem == null)
            throw new Exception("   order could not find menuItem UUID:" + menuItemUUID);
        //now loop through the parameters searching for options, then find the option in the menu, add to order
        //refer to menu.jsp function orderItem(item)
        //it contains options with names of "OPTION:" + option.uuid
        Enumeration enumeration = httpServletRequest.getParameterNames();
        menuItem.getOptions().clear();
        MenuItemOption option;
        MenuItemOption optionGroup;
        String menuItemOptionUuid;
        String menuItemOptionGroupUuid;
        //for options paramName is group, value is option
        while (enumeration.hasMoreElements()) {
            String parameterName = (String) enumeration.nextElement();
            if (parameterName.toUpperCase().contains("OPTION_")) {
                //using the name not value?                menuItemOptionUuid = httpServletRequest.getParameter(parameterName).substring(6);
                menuItemOptionGroupUuid = parameterName.substring(7);
                optionGroup = menuService.findOptionInMenu(menu, menuItemOptionGroupUuid);
                //optionGroup.getChildren().clear();//clear out options and only put in selected option
                MenuItemOption newOptionGroup = new MenuItemOption(optionGroup.getType(), optionGroup.getPrice(), optionGroup.getDescription());
                newOptionGroup.setUuid(optionGroup.getUuid());
                menuItemOptionUuid = httpServletRequest.getParameter(parameterName);
                option = menuService.findOptionInMenu(menu, menuItemOptionUuid);
                newOptionGroup.getChildren().add(option);
                menuItem.getOptions().add(newOptionGroup);
            }
        }

        //orderItem. order.setStatus(Order.ORDER_STATUS.INIT);
        MenuItem menuItemGroup = menuService.getMenuItemByUuid(menu, menuItem.getParentUuid());
        MenuItem menuItemSection = menuService.getMenuItemByUuid(menu, menuItemGroup.getParentUuid());
        OrderItem orderItem = new OrderItem(menuItemQuantity, menu, menuItemSection, menuItemGroup, menuItem, note);
        orderItem.setId(BigInteger.valueOf(order.getOrderItems().size()+1)); //give it a sequential id for UI reference in hrefs
        order.getOrderItems().add(orderItem);
        //order.setTotalPretax(total);
//TODO: new solution for combos        adjustForComboHack(order);
        ApplicationUser user = (ApplicationUser) session.getAttribute("applicationUser");
        order.setUser(user);
        order.calculateTotals();
        order.setStatus(Order.ORDER_STATUS.OPEN);//immediately placed on order in open status - no init bs
        session.setAttribute("order", order);
        uiModel.asMap().clear();
        orderService.saveOrder(order);
        logger.debug("Order updated with " + order.getMenu().getMenuItems().size() + " items." );
/*
        if (checkout.equalsIgnoreCase("checkout"))
            //checkout???
            return "redirect:/orders/confirmOrder" + encodeUrlPathSegment(order.getId().toString(), httpServletRequest);
        else
            //??????? back to menu?
*/
            return "redirect:/menus/showMenu/" + encodeUrlPathSegment(order.getMenu().getId().toString(), httpServletRequest);
    }

    /*
    Called when they hit "add to order" in dialog in menu
     */
    @RequestMapping(value="getOrderJSON", method = RequestMethod.GET)
    public @ResponseBody Order getOrderAsJSON(HttpSession session) throws Exception {
        logger.debug("getOrderAsJSON");
        //grab order from session
        Order order = (Order) session.getAttribute("order");
        //if not there than look in db
        if (order == null) {
            ApplicationUser user = (ApplicationUser) session.getAttribute("applicationUser");
            if (user == null)
                return null;
            //get the current order or create one
            logger.debug("There was no order in session.  Checking db...");
            order = orderService.getTodaysOrderByUser(user);
            //order = new Order();
        }

        return order;
    }

    @RequestMapping(value="deleteOrderItem/{orderItemId}", method = RequestMethod.GET)
    public @ResponseBody Order deleteOrderItemAJAX(@PathVariable("orderItemId") String orderItemId, HttpSession session) throws Exception {
        logger.debug("deleting order item");
        //grab order from session, not being in session is not feasible i believe
        Order order = (Order) session.getAttribute("order");
        orderService.deleteOrderItem(order, orderItemId);
        return order;
    }
/*

    @RequestMapping(value = "submitOrder", method = RequestMethod.POST, produces = "text/html")
    public String createOrder(Model uiModel, HttpServletRequest httpServletRequest, HttpSession session) throws Exception {
        logger.debug("createOrder");
*/
/*
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, order);
            return "orders/create";
        }
*//*

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
                //menuItemParent = MenuServiceImpl.getMenuItemFromSet(menu.getMenuItems(), menuItem.getParentUuid());
                if (menuItem == null)
                    throw new Exception("   order could not find menuItem UUID:" + menuItemUUID);
//                if (menuItemParent == null)
//                    throw new Exception("   order could not find menuItem parent:" );
//                else {
//                    menuItem.setInternalNotes(menuItemParent.getName());
//                }
//                total += menuItem.getPrice();
                order.getMenuItems().add(menuItem);



                logger.debug("   Menu Item added to new order " );
            }
        }
        order.setStatus(Order.ORDER_STATUS.INIT);
        order.setNotes(httpServletRequest.getParameter("notes"));
        order.setTotalPretax(total);
        adjustForComboHack(order);
        ApplicationUser user = (ApplicationUser) session.getAttribute("applicationUser");
        order.setUser(user);

        uiModel.asMap().clear();
        orderService.saveOrder(order);
        //pass the order id to confirmOrder?
        logger.debug("New order added with " + order.getMenuItems().size() + " items." );
//        return "public/confirmOrder";
        return "redirect:/orders/confirmOrder/" + encodeUrlPathSegment(order.getId().toString(), httpServletRequest);

//        return "redirect:/orders/" + encodeUrlPathSegment(order.getId().toString(), httpServletRequest);
    }

*/
    //looks at name and parent (in internalNotes) and if its a combo, finds combo items and zeros out their price
    //its a hack but it works for now...
/*
    private void adjustForComboHack (Order order) {
        //HACK to adjust for 1/2 sand 1/2 soup
        boolean containsComboHalfSand = false;
        boolean containsComboSoup = false;
        boolean containsComboSalad = false;
        for (MenuItem item : order.getMenuItems()) {
            if (item.getInternalNotes().contains("Combo")  && item.getName().contains("1/2 Sand")) {  //internalNotes contain the parent name
                containsComboHalfSand = true;
            }
            if (item.getInternalNotes().contains("Combo")  && item.getName().contains("Soup")) {
                containsComboSoup = true;
            }
            if (item.getInternalNotes().contains("Combo")  && item.getName().contains("Salad")) {
                containsComboSalad = true;
                break;
            }
        }

        if (containsComboHalfSand || containsComboSalad || containsComboSoup) {
            for (MenuItem item2 : order.getMenuItems()) {
                if (containsComboHalfSand && item2.getInternalNotes().contains("Sandwich")) {
//                    order.setTotalPretax(order.getTotalPretax() - item2.getPrice()); //credit back item so only combo is charged
                    containsComboHalfSand = false;//so they don't get any more credits
//                    item2.setPrice(0f);
                } else if (containsComboSalad && item2.getInternalNotes().contains("Salad")) {
//                    order.setTotalPretax(order.getTotalPretax() - item2.getPrice()); //credit back item so only combo is charged
                    containsComboSalad = false;//so they don't get any more credits
//                    item2.setPrice(0f);
                } else if (containsComboSoup && item2.getInternalNotes().contains("Soup")) {
//                    order.setTotalPretax(order.getTotalPretax() - item2.getPrice()); //credit back item so only combo is charged
                    containsComboSoup = false;//so they don't get any more credits
//                    item2.setPrice(0f);
                }
            }
        }

    }

*/
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
