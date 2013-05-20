// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.omo.web;

import com.omo.domain.Order;
import com.omo.repository.MenuItemRepository;
import com.omo.service.MenuService;
import com.omo.service.OrderService;
import com.omo.web.OrderController;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect OrderController_Roo_Controller {
    
    @Autowired
    OrderService OrderController.orderService;
    
    @Autowired
    MenuService OrderController.menuService;
    
    @Autowired
    MenuItemRepository OrderController.menuItemRepository;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String OrderController.create(@Valid Order order, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, order);
            return "orders/create";
        }
        uiModel.asMap().clear();
        orderService.saveOrder(order);
        return "redirect:/orders/" + encodeUrlPathSegment(order.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String OrderController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Order());
        return "orders/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String OrderController.show(@PathVariable("id") BigInteger id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("order", orderService.findOrder(id));
        uiModel.addAttribute("itemId", id);
        return "orders/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String OrderController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("orders", orderService.findOrderEntries(firstResult, sizeNo));
            float nrOfPages = (float) orderService.countAllOrders() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("orders", orderService.findAllOrders());
        }
        addDateTimeFormatPatterns(uiModel);
        return "orders/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String OrderController.update(@Valid Order order, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, order);
            return "orders/update";
        }
        uiModel.asMap().clear();
        orderService.updateOrder(order);
        return "redirect:/orders/" + encodeUrlPathSegment(order.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String OrderController.updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, orderService.findOrder(id));
        return "orders/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String OrderController.delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Order order = orderService.findOrder(id);
        orderService.deleteOrder(order);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/orders";
    }
    
    void OrderController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("order_orderdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void OrderController.populateEditForm(Model uiModel, Order order) {
        uiModel.addAttribute("order", order);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("menus", menuService.findAllMenus());
        uiModel.addAttribute("menuitems", menuItemRepository.findAll());
    }
    
    String OrderController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
