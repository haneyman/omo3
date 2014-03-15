package com.omo.web;

import com.omo.domain.Menu;
import com.omo.domain.Reseller;
import com.omo.domain.Restaurant;
import com.omo.domain.Schedule;
import com.omo.repository.MenuRepository;
import com.omo.repository.ResellerRepository;
import com.omo.repository.RestaurantRepository;
import com.omo.repository.ScheduleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequestMapping("/menus")
@Controller
@RooWebScaffold(path = "menus", formBackingObject = Menu.class)
public class MenuController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(MenuController.class);

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    ResellerRepository resellerRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @RequestMapping(value = "listMenuPublic", produces = "text/html")
    public String listMenusPublic(Model uiModel) {
        uiModel.addAttribute("menus", menuService.findAllMenus());
        uiModel.addAttribute("schedules", scheduleRepository.findAll());
        return "public/menus";
    }

    @RequestMapping(value = "showMenuByRestaurant/{id}", produces = "text/html")
    public String showMenuByRestaurant(@PathVariable("id") BigInteger id, Model uiModel) {
        Restaurant restaurant = restaurantRepository.findOne(id);
        List<Menu> menus = menuRepository.findAll();
        Menu menuShow = null;
        for (Menu menu : menus) {
            if (menu.getRestaurant().getName().equals(restaurant.getName())) {
                menuShow = menu;
                 break;
            }
        }
        uiModel.addAttribute("menu", menuShow);
        //uiModel.addAttribute("itemId", menuShow.getId());
        return "redirect:/menus/showMenu/"+menuShow.getId();
    }

    @RequestMapping(value = "showMenu/{id}", produces = "text/html")
    public String showMenu(@PathVariable("id") BigInteger id, Model uiModel) throws Exception  {
        logger.debug("showMenu for:" + id);
        Menu menu = menuService.findMenu(id);
        uiModel.addAttribute("menu", menu);
        uiModel.addAttribute("canOrder", menuService.isMenuForToday(menu));
        uiModel.addAttribute("offered", menuService.whenAndWhereOffered(menu));

        try {
            uiModel.addAttribute("menuHTML", menuService.getMenuAsHTML(menu));
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
        String menuName = httpServletRequest.getParameter("menu");

        logger.debug("   viewMenu post reseller is:" + reseller + "  menu:" + menuName);
        //Menu menu = menuService.findTodaysMenu(reseller, restaurant);
        Menu menu = menuRepository.findOneByName(menuName);
        //uiModel.asMap().clear();
        //menuService.saveMenu(menu);
        return "redirect:/menus/showMenu/" + encodeUrlPathSegment(menu.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "byResellerToday/{resllerName:.+}")
    public @ResponseBody List<Menu> menusForReseller(@PathVariable("resllerName") String resellerName) throws Exception {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        logger.debug("menusForReseller starting for reseller: " + resellerName + " for day:" + day);
        List<Menu> menus = menuService.findTodaysMenusForReseller(resellerName);
        logger.debug("menusForReseller found " + menus.size() +  " menus. ");
        logger.debug("   returning:" + menus);
        return menus;
    }

}
