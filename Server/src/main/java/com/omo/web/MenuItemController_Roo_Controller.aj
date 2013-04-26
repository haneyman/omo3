// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.omo.web;

import com.omo.domain.MenuItem;
import com.omo.repository.MenuItemRepository;
import com.omo.web.MenuItemController;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect MenuItemController_Roo_Controller {
    
    @Autowired
    MenuItemRepository MenuItemController.menuItemRepository;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String MenuItemController.create(@Valid MenuItem menuItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, menuItem);
            return "menuitems/create";
        }
        uiModel.asMap().clear();
        menuItemRepository.save(menuItem);
        return "redirect:/menuitems/" + encodeUrlPathSegment(menuItem.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String MenuItemController.show(@PathVariable("id") BigInteger id, Model uiModel) {
        uiModel.addAttribute("menuitem", menuItemRepository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "menuitems/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String MenuItemController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("menuitems", menuItemRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) menuItemRepository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("menuitems", menuItemRepository.findAll());
        }
        return "menuitems/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String MenuItemController.update(@Valid MenuItem menuItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, menuItem);
            return "menuitems/update";
        }
        uiModel.asMap().clear();
        menuItemRepository.save(menuItem);
        return "redirect:/menuitems/" + encodeUrlPathSegment(menuItem.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String MenuItemController.updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, menuItemRepository.findOne(id));
        return "menuitems/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String MenuItemController.delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        MenuItem menuItem = menuItemRepository.findOne(id);
        menuItemRepository.delete(menuItem);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/menuitems";
    }
    
    void MenuItemController.populateEditForm(Model uiModel, MenuItem menuItem) {
        uiModel.addAttribute("menuItem", menuItem);
        uiModel.addAttribute("menuitems", menuItemRepository.findAll());
    }
    
    String MenuItemController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
