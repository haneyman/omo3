package com.omo.web;

import com.omo.domain.Menu;
import com.omo.domain.MenuItem;
import com.omo.domain.OrderItem;
import com.omo.domain.Restaurant;
import com.omo.repository.MenuRepository;
import com.omo.repository.ResellerRepository;
import com.omo.repository.RestaurantRepository;
import com.omo.repository.ScheduleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
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

    //used by function orderItem(item) for the order item dialog in menu.jsp, to confirm the item and select options.
    @RequestMapping(value="getMenuItemForOrder/{menuId}/{itemUuid}", method = RequestMethod.GET)
    public @ResponseBody MenuItem getMenuItemInJSON(@PathVariable BigInteger menuId, @PathVariable String itemUuid) throws Exception {
        MenuItem menuItem = menuService.getMenuItemWithOptions(menuId, itemUuid);
        return menuItem;

    }

/*
    //inject all the menu item info AND options for section, group, item into a single OrderItem object
    //used by the order item dialog, to confirm the item and select options.
    @RequestMapping(value="getMenuItemForOrder/{menuId}/{itemUuid}", method = RequestMethod.GET)
    public @ResponseBody OrderItem getOrderItemInJSON(@PathVariable BigInteger menuId, @PathVariable String itemUuid) {
        Menu menu = menuRepository.findOne(menuId);

        OrderItem orderItem = null;
        //find the menu item for the menu
        for (MenuItem menuItemSection : menu.getMenuItems()) {
            for (MenuItem menuItemGroup : menuItemSection.getChildMenuItems())  {
                for (MenuItem menuItem : menuItemGroup.getChildMenuItems())  {
                    if (menuItem.getUuid().equalsIgnoreCase(itemUuid)) {
                        orderItem = new OrderItem();
                        orderItem.setMenuItem(menuItem);
                        //orderItem.setGroup(menuItemGroup);
                        //orderItem.setSection(menuItemSection);
                        if (menuItemSection.getOptions() != null) {
                            orderItem.getOptions().addAll(menuItemSection.getOptions());
                        }
                        if (menuItemGroup.getOptions().size() > 0) {
                            orderItem.getOptions().addAll(menuItemGroup.getOptions());
                        }
                        break;
                    }
                }
            }
        }

        return orderItem;

    }

*/

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
        //TODO: Need to sort ALL the menu items before adding as attribute
        uiModel.addAttribute("menu", menu);
        String debugMode = System.getProperty("debug.mode");
        if (debugMode != null && debugMode.equalsIgnoreCase("true"))
            uiModel.addAttribute("canOrder", true);//
        else
            uiModel.addAttribute("canOrder", menuService.isMenuOrderable(menu));
        uiModel.addAttribute("offered", menuService.whenAndWhereOffered(menu));

/*
        try {
            uiModel.addAttribute("menuHTML", menuService.getMenuAsHTML(menu));
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
*/

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
