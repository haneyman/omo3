package com.omo.web;

import com.omo.domain.OrderItem;
import com.omo.repository.MenuItemRepository;
import com.omo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RequestMapping("/orderitems")
@Controller
public class OrderItemController {
    @Autowired
    MenuService menuService;
    @Autowired
    MenuItemRepository menuItemRepository;

    void populateEditForm(Model uiModel, OrderItem orderItem) {
        uiModel.addAttribute("orderItem", orderItem);
        uiModel.addAttribute("menus", menuService.findAllMenus());
        uiModel.addAttribute("menuitems", menuItemRepository.findAll());
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
