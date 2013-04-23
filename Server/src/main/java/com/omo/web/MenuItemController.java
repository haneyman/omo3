package com.omo.web;

import com.omo.domain.MenuItem;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/menuitems")
@Controller
@RooWebScaffold(path = "menuitems", formBackingObject = MenuItem.class)
public class MenuItemController {
    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new MenuItem());
        return "menuitems/create";
    }
}
