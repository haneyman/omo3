package com.omo.web;

import com.omo.domain.Menu;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
