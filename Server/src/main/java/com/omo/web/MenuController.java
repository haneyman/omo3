package com.omo.web;

import com.omo.domain.Menu;
import com.omo.domain.MenuItem;
import com.omo.domain.OrderItem;
import com.omo.domain.Restaurant;
import com.omo.repository.*;
import com.omo.service.MenuService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

@RequestMapping("/menus")
@Controller
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
    @Autowired
    MenuService menuService;
    @Autowired
    MenuItemRepository menuItemRepository;

    //used by function orderItem(item) for the order item dialog in menu.jsp, to confirm the item and select options.
    @RequestMapping(value="getMenuItemForOrder/{menuId}/{itemUuid}", method = RequestMethod.GET)
    public @ResponseBody OrderItem getMenuItemForOrderInJSON(@PathVariable BigInteger menuId, @PathVariable String itemUuid) throws Exception {
        Menu menu = menuRepository.findOne(menuId);
        MenuItem menuItem = menuService.getMenuItemWithOptions(menuId, itemUuid);
        MenuItem menuGroup = menuService.getMenuItemByUuid(menu, menuItem.getParentUuid());
        MenuItem menuSection = menuService.getMenuItemByUuid(menu, menuGroup.getParentUuid());

        OrderItem orderItem = new OrderItem(1, menu, menuSection, menuGroup, menuItem, "");
        return orderItem;

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
    public String showMenuNormal(@PathVariable("id") BigInteger id, Model uiModel) throws Exception  {
        return showMenu(id, null, uiModel);
    }
    @RequestMapping(value = "showMenu/{id}/{debug}", produces = "text/html")
    public String showMenuNormal(@PathVariable("id") BigInteger id, @PathVariable("debug") String debug, Model uiModel) throws Exception  {
        return showMenu(id, debug, uiModel);
    }


    private String showMenu(BigInteger id, String debug, Model uiModel) throws Exception {
        logger.debug("showMenu for:" + id);
        Menu menu = menuService.findMenu(id);
        //TODO: Need to sort ALL the menu items before adding as attribute
        uiModel.addAttribute("menu", menu);
        String debugMode = System.getProperty("debug");
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

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Menu menu, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, menu);
            return "menus/create";
        }
        uiModel.asMap().clear();
        menuService.saveMenu(menu);
        return "redirect:/menus/" + encodeUrlPathSegment(menu.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Menu());
        return "menus/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        uiModel.addAttribute("menu", menuService.findMenu(id));
        uiModel.addAttribute("itemId", id);
        return "menus/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("menus", menuService.findMenuEntries(firstResult, sizeNo));
            float nrOfPages = (float) menuService.countAllMenus() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("menus", menuService.findAllMenus());
        }
        return "menus/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Menu menu, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, menu);
            return "menus/update";
        }
        uiModel.asMap().clear();
        menuService.updateMenu(menu);
        return "redirect:/menus/" + encodeUrlPathSegment(menu.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, menuService.findMenu(id));
        return "menus/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Menu menu = menuService.findMenu(id);
        menuService.deleteMenu(menu);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/menus";
    }

    void populateEditForm(Model uiModel, Menu menu) {
        uiModel.addAttribute("menu", menu);
        uiModel.addAttribute("menuitems", menuItemRepository.findAll());
        uiModel.addAttribute("restaurants", restaurantRepository.findAll());
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
