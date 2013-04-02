package com.omo.web;

import com.omo.domain.Restaurant;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/restaurants")
@Controller
@RooWebScaffold(path = "restaurants", formBackingObject = Restaurant.class)
public class RestaurantController {
}
