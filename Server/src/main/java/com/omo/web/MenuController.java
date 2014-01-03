package com.omo.web;

import com.omo.domain.Menu;
import com.omo.domain.Reseller;
import com.omo.domain.Restaurant;
import com.omo.domain.Schedule;
import com.omo.repository.ResellerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigInteger;

@RequestMapping("/menus")
@Controller
@RooWebScaffold(path = "menus", formBackingObject = Menu.class)
public class MenuController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(MenuController.class);

    @RequestMapping(value = "showMenu/{id}", produces = "text/html")
    public String showMenu(@PathVariable("id") BigInteger id, Model uiModel) {
        logger.debug("showMenu for:" + id);
        //http://localhost:8080/omo/menus/showMenu/25228012736225668066740962972
        //http://localhost:8080/omo/menus/showMenu/25218716663578156317953998846   Haneymain
/*
        BigInteger bi = new BigInteger("512c0357ff3acce65673455d");
        id = bi;//temp
        uiModel.addAttribute("menu", menuService.findMenu(id));
*/
        try {
            uiModel.addAttribute("menuHTML", menuService.getMenuAsHTML(id));
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }

        return "public/menu";
    }

    @RequestMapping(value = "viewMenu", method = RequestMethod.POST, produces = "text/html")
    public String viewMenu(Model uiModel, HttpServletRequest httpServletRequest) throws Exception {
        logger.debug("viewMenu post");
        //display menu to display based on reseller, restaurant and day
        String reseller = httpServletRequest.getParameter("reseller");
        String restaurant = httpServletRequest.getParameter("restaurant");

        logger.debug("   viewMenu post reseller is:" + reseller);
        Menu menu = menuService.findTodaysMenu(reseller, restaurant);
        //uiModel.asMap().clear();
        //menuService.saveMenu(menu);
        return "redirect:/menus/showMenu/" + encodeUrlPathSegment(menu.getId().toString(), httpServletRequest);
    }

}
