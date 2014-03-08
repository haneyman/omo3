package com.omo.service;


import com.omo.domain.*;
import com.omo.repository.ResellerRepository;
import com.omo.repository.RestaurantRepository;
import com.omo.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;


public class MenuServiceImpl implements MenuService {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MenuServiceImpl.class);
    private static final String INDENT = "   ";
    private static final String NEW_LINE = "\n";//"\n";
    private ArrayList<String> html;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    ResellerRepository resellerRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    RestaurantRepository restaurantRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    ScheduleRepository scheduleRepository;


    public String getMenuAsHTML(BigInteger menuId) throws Exception {
        logger.debug("getMenuAsHTML - loading html for menu " + menuId +  "...");
        html = new ArrayList<String>();
        html.add(NEW_LINE + "<!-- begin generated menu html -->" + NEW_LINE);
        html.add("<div class=\"container divMenu\">" + NEW_LINE);
        html.add("    <div class=\"row divMenuRow\">" + NEW_LINE);
        Menu menu = findMenu(menuId);
        html.add("    <input type=\"hidden\" name=\"menuId\" value=\"" + menu.getId() + "\">" + NEW_LINE);
        loadMenuItems(menu.getMenuItems(), 1);
        html.add("    </div> <!-- divMenuRow -->" + NEW_LINE);
        html.add("</div> <!-- divMenu -->" + NEW_LINE);
        html.add("<!-- end generated menu html -->" + NEW_LINE);
        logger.debug("Loading html for menu complete. Lines:" + html.size());
        logger.debug("getMenuAsHTML done");
        return htmlAsString();
    }

    private void loadMenuItems(Set<MenuItem> menuItems, int level) throws Exception {
        List<MenuItem> menuItemsList = new ArrayList(menuItems);
        Collections.sort(menuItemsList, new Comparator<MenuItem>() {
            public int compare(MenuItem mi1, MenuItem mi2) {
                return (mi1.getSortOrder() > mi2.getSortOrder() ? 1 : (mi1.getSortOrder()==mi2.getSortOrder() ? 0 : -1));
            }
        });

        for (MenuItem menuItem: menuItemsList) {
            if (menuItem.getType().equals(MenuItem.MenuItemTypes.MenuSection)) {
                loadMenuSection(menuItem, level);
            } else if (menuItem.getType().equals(MenuItem.MenuItemTypes.MenuGroup)) {
                loadMenuGroup(menuItem, level);
            } else if (menuItem.getType().equals(MenuItem.MenuItemTypes.MenuItem)) {
                loadMenuItem(menuItem, level);
            } else
                throw new Exception("Invalid Menu Item Type: \"" + menuItem.getType() + "\"");
        }
    }

    private void loadMenuSection(MenuItem menuItem, int level) throws Exception {
        level++;
//        addToHTML("<div class=\"row\">",level);
        addToHTML(INDENT + "<div class=\"divMenuSection row\">",level);
        addToHTML(INDENT + "<h3>" + menuItem.getName() + "</h3>",level);

        if (menuItem.getChildMenuItems().size() > 0) {
            loadMenuItems(menuItem.getChildMenuItems(), level + 1);
        }
        addToHTML(INDENT + "</div> <!-- divMenuSection -->",level);
//        addToHTML("</div>",level);
    }

    private void loadMenuGroup(MenuItem menuItem, int level) throws Exception {
        level++;
//        addToHTML("<div class=\"row\">",level);
        addToHTML(INDENT + "<div class=\"col-xs-6 divMenuGroup\">",level);
        addToHTML(INDENT + INDENT + "<div class=\"row divMenuGroupRow\">",level);
        addToHTML(INDENT + INDENT + INDENT + "<h4 class=\"menuGroupTitle\">" + menuItem.getName() + "</h4>", level);
        addToHTML(INDENT + INDENT + INDENT + "<p><span style='padding:10px;'>" + menuItem.getDescription() + "</span></p>",level);
        addToHTML(INDENT + INDENT + INDENT + "<div class=\"divItems\">",level);
        loadMenuItems(menuItem.getChildMenuItems(), level + 2);
        addToHTML(INDENT + INDENT + INDENT + "</div> <!-- divItems -->", level);
        addToHTML(INDENT + INDENT + "</div> <!-- divMenuGroupRow -->",level);
        addToHTML(INDENT + "</div> <!-- divMenuGroup -->",level);
        addToHTML("<br/>",level);
//        addToHTML("</div>",level);
    }

    private void loadMenuItem(MenuItem menuItem, int level) {
        level++;
        boolean checked = false;
        DecimalFormat myFormatter = new DecimalFormat("###.00");
        String priceOutput = myFormatter.format(menuItem.getPrice());
        String name =  menuItem.getName().replaceAll(" ", "_").replaceAll("/","_");
        addToHTML(INDENT + "<div class=\"checkbox\">",level);
        addToHTML(INDENT + "    <label>",level);
        addToHTML(INDENT + "        <input type=\"checkbox\" "  + checked + " name=\"" + MenuItem.MENUITEM_LABEL + "_" + menuItem.getUuid() + "\" value=\"" + name + "\">", level);
        addToHTML(INDENT + "    </label>",level);
        addToHTML(INDENT + "    <div class=\"divNamePrice\">",level);
        addToHTML(INDENT + "        <div class=\"menuItemName\">" + menuItem.getName() + " </div>",level);
        if (menuItem.getPrice() > 0)
            addToHTML(INDENT + "        <div class=\"menuItemPrice\">$" + priceOutput + "</div>",level);
        else
            addToHTML(INDENT + "        <div class=\"menuItemPrice\"> -----" + /*"&nbsp;" +*/ "</div>",level);
        addToHTML(INDENT + "    </div>", level);
        addToHTML(INDENT + "</div><div style=\"clear:both\"></div>",level);
    }
/*

    private void loadMenuItem(MenuItem menuItem, int level) {
        level++;
        boolean checked = false;
        DecimalFormat myFormatter = new DecimalFormat("###.00");
        String priceOutput = myFormatter.format(menuItem.getPrice());
        String name =  menuItem.getName().replaceAll(" ", "_").replaceAll("/","_");
        addToHTML(INDENT + "<label>",level);
        addToHTML(INDENT + "    <div class=\"divCheckbox\">",level);
//        String group =
        addToHTML(INDENT + "        <input type=\"checkbox\" "  + checked + " name=\"" + MenuItem.MENUITEM_LABEL + "_" + menuItem.getUuid() + "\" value=\"" + name + "\">", level);
        addToHTML(INDENT + "    </div>",level);
        addToHTML(INDENT + "    <div class=\"divNamePrice\">",level);
        addToHTML(INDENT + "        <div class=\"menuItemName\">" + menuItem.getName() + " </div>",level);
        if (menuItem.getPrice() > 0)
            addToHTML(INDENT + "        <div class=\"menuItemPrice\">$" + priceOutput + "</div>",level);
        else
            addToHTML(INDENT + "        <div class=\"menuItemPrice\"> -----" + */
/*"&nbsp;" +*//*
 "</div>",level);
        addToHTML(INDENT + "    </div>", level);
        addToHTML(INDENT + "</label>",level);
    }
*/


    private void addToHTML(String htmlLine, int level) {
        String totalIndention = "";
        if (level > 0)
            totalIndention = String.format(String.format("%%0%dd", level), 0).replace("0", INDENT);//adds level+1 number of indents to total indents
        html.add(totalIndention + htmlLine + NEW_LINE);
    }

    private String htmlAsString() {
        StringBuilder sb = new StringBuilder();
        for (String line: html) {
            sb.append(line);
        }
        return sb.toString();
    }


    public List<Menu> getMenuByName(String menuName) {
        return menuRepository.findByName(menuName);
    }


    //
    public static MenuItem getMenuItemFromSet(Set<MenuItem> menuItemSet, String menuItemUUID) {
        //logger.debug("getmenuitemfromset looking for " + menuItemUUID);
        MenuItem result = null;
        for (MenuItem menuItem: menuItemSet) {
            //logger.debug("getMenuItemFromSet checking " + menuItem.getUuid() + "  " + menuItem.getName());
            if (menuItem.getChildMenuItems().size() > 0) {
                result = getMenuItemFromSet(menuItem.getChildMenuItems(), menuItemUUID);
            }
            if (menuItem.getUuid().equalsIgnoreCase(menuItemUUID)) {
                //logger.debug(" found it!");
                result = menuItem;
            }
            if (result != null)
                return result;
        }
        return null;
    }

    public Menu findTodaysMenu(String resellerName, String restaurantName) throws Exception {
        logger.debug("findMenu for reseller:" + resellerName + " restaurant:" + restaurantName);
        Reseller reseller = resellerRepository.findOneByName(resellerName);
        if (reseller == null)
            throw new Exception("Reseller not found in resellers:" + reseller);
        Restaurant restaurant = restaurantRepository.findOneByName(restaurantName);
        if (restaurant == null)
            throw new Exception("Restaurant not found in restaurants:" + restaurantName);
        Calendar calendar = Calendar.getInstance();
        Integer day = calendar.get(Calendar.DAY_OF_WEEK);
        Schedule scheduleFound = null;

        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule : schedules) {
            if (schedule.getReseller().getId().equals(reseller.getId())
                && schedule.getRestaurant().getId().equals(restaurant.getId())) {
                if (schedule.getDayOfWeek() == 0) {
                    scheduleFound = schedule;
                    break;
                } else if (schedule.getDayOfWeek() == day) {
                    scheduleFound = schedule;
                    break;
                }
            }
        }
        if (scheduleFound == null)
            throw new Exception("Schedule not found for day:" + day + ", reseller:" + resellerName + ", restaurant:" +restaurantName);

        Menu menu = scheduleFound.getMenu();
        logger.debug("   findMenu found a menu: " + menu.getName());

        return menu;
    }


/*
    private static int countGrandchildren(MenuItem menuItem, HashMap<String, MenuItem> menuItems) {
        int count=0;
        MenuItem child;
        for (MenuItem item: menuItem.getChildMenuItems()) {
            child = employees.get(childId);
            count += child.getChildren().size();
        }
        return count;
    }
*/



}
