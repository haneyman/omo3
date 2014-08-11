package com.omo.service;


import com.omo.domain.*;
import com.omo.repository.ResellerRepository;
import com.omo.repository.RestaurantRepository;
import com.omo.repository.ScheduleRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.text.DateFormat;
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


    /**
     * Finds menuItemOption in a menu,
     * they are usually under a group under a menu item or menu item group
     */
    public MenuItemOption findMenuItemOption(Menu menu, String optionUuid) {
        MenuItemOption option;
        for (MenuItem menuItemSection : menu.getMenuItems()) {
            option = findMenuItemOption(menuItemSection, optionUuid);//looks for option at section level
            if (option != null) {
                return option;
            }
        }
        return null;
    }

    /**
     * Finds menu item and loads all options into the menu item (even the options from parents)
     *
     */
    @Override
    public MenuItem getMenuItemWithOptions(BigInteger menuId, String menuItemUuid) throws Exception {
        Menu menu = menuRepository.findOne(menuId);
        MenuItem menuItemResult = null;
        //OrderItem orderItem = null;
        //find the menu item for the menu
        for (MenuItem menuItemSection : menu.getMenuItems()) {
            for (MenuItem menuItemGroup : menuItemSection.getChildMenuItems())  {
                for (MenuItem menuItem : menuItemGroup.getChildMenuItems())  {
                    if (menuItem.getUuid().equalsIgnoreCase(menuItemUuid)) {
                        menuItemResult = menuItem;
                        break;
                    }
                }
            }
        }
        if (menuItemResult == null)
            throw new Exception("menu item not found in MenuServiceImpl for menu " + menuId + "  item id "  + menuItemUuid);
        //add menu group options
        if (menuItemResult.getParent() != null && menuItemResult.getParent().getOptions() != null)
            menuItemResult.getOptions().addAll(menuItemResult.getParent().getOptions());
        //add menu section options
        if (menuItemResult.getParent().getParent() != null && menuItemResult.getParent().getParent().getOptions() != null)
            menuItemResult.getOptions().addAll(menuItemResult.getParent().getParent().getOptions());
        //
        return menuItemResult;
    }

    /**
     * Finds menu item options by recursively searching a given menu item, usually called by above
     *
     * @param menuItem
     * @param optionUuid
     * @return
     */
    private MenuItemOption findMenuItemOption(MenuItem menuItem, String optionUuid) {
        logger.debug("findMenuItemOption looking for " + optionUuid + " " + " under " + menuItem.getName());
        for (MenuItemOption option : menuItem.getOptions()) {
            //initial option is usually a group like "Bread"
            if (option.getType().equals(MenuItemOption.MenuItemOptionTypes.Group)) {
                for (MenuItemOption optionChild : option.getChildren()) {
                    if (optionChild.getUuid().equalsIgnoreCase(optionUuid))
                        return optionChild;
                }
            } else {
                if (option.getUuid().equalsIgnoreCase(optionUuid))
                    return option;
            }
        }
        //if here didn't find it, look in children
        MenuItemOption result = null;
        for (MenuItem menuItemChild : menuItem.getChildMenuItems()) {
            result = findMenuItemOption(menuItemChild, optionUuid);
            if (result != null)
                return result;
        }

        return null;
    }
/*

    public String getMenuAsHTML(Menu menu) throws Exception {
        logger.debug("getMenuAsHTML - loading html for menu " + menu.getName() +  "...");
        html = new ArrayList<String>();
        html.add(NEW_LINE + "<!-- begin generated menu html -->" + NEW_LINE);
        html.add("<div class=\"container divMenu\">" + NEW_LINE);
        html.add("    <div class=\"row divMenuRow\">" + NEW_LINE);
        html.add("    <input type=\"hidden\" name=\"menuId\" value=\"" + menu.getId() + "\">" + NEW_LINE);
        loadMenuItems(menu.getMenuItems(), 1);
        html.add("    </div> <!-- divMenuRow -->" + NEW_LINE);
        html.add("</div> <!-- divMenu -->" + NEW_LINE);
        html.add("<!-- end generated menu html -->" + NEW_LINE);
        logger.debug("Loading html for menu complete. Lines:" + html.size());
        logger.debug("getMenuAsHTML done");
        return htmlAsString();
    }

*/
/*
    public String getMenu(Menu menu) throws Exception {
        logger.debug("getMenuAsHTML - loading html for menu " + menu.getName() +  "...");
        html = new ArrayList<String>();
        html.add(NEW_LINE + "<!-- begin generated menu html -->" + NEW_LINE);
        html.add("<div class=\"container divMenu\">" + NEW_LINE);
        html.add("    <div class=\"row divMenuRow\">" + NEW_LINE);
        html.add("    <input type=\"hidden\" name=\"menuId\" value=\"" + menu.getId() + "\">" + NEW_LINE);
        loadMenuItems(menu.getMenuItems(), 1);
        html.add("    </div> <!-- divMenuRow -->" + NEW_LINE);
        html.add("</div> <!-- divMenu -->" + NEW_LINE);
        html.add("<!-- end generated menu html -->" + NEW_LINE);
        logger.debug("Loading html for menu complete. Lines:" + html.size());
        logger.debug("getMenuAsHTML done");
        return htmlAsString();
    }
*/
/*

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
        addToHTML(INDENT + "<div class=\"divMenuGroup\">",level);
        addToHTML(INDENT + INDENT + "<div class=\"col-xs-6 row divMenuGroupRow\">",level);
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
        String name =  menuItem.getName().replaceAll(" ", "_").replaceAll("/","_");
        addToHTML(INDENT + "<div class=\"checkbox\">",level);
        addToHTML(INDENT + "    <label>",level);
        //addToHTML(INDENT + "        <input type=\"checkbox\" "  + checked + " name=\"" + MenuItem.MENUITEM_LABEL + "_" + menuItem.getUuid() + "\" value=\"" + name + "\">", level);
        addToHTML(INDENT + "    </label>",level);
        addToHTML(INDENT + "    <div class=\"divNamePrice\">",level);
        addToHTML(INDENT + "        <div class=\"menuItemName\">" + menuItem.getName() + " </div>",level);
        String priceOutput;
        for (MenuItemOption option : menuItem.getOptions()) {
            priceOutput = myFormatter.format(option.getPrice());
            if (option.getPrice() > 0) {
                addToHTML(INDENT + "        <div class=\"menuItemPrice\">$" + priceOutput + "</div>", level);
            } else {
                addToHTML(INDENT + "        <div class=\"menuItemPrice\"> -----" + */
/*"&nbsp;" +*//*
 "</div>", level);
            }
        }
        addToHTML(INDENT + "    </div>", level);
        addToHTML(INDENT + "</div><div style=\"clear:both\"></div>",level);
    }
*/
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
/*


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

*/

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

    //this logic depends on schedules to be in order by reseller
    public String whenAndWhereOffered(Menu menu) throws Exception {
        //HashMap<String, >
        StringBuffer results = new StringBuffer();
        List<Schedule> schedules = scheduleRepository.findAll();
        String curReseller = "yomama";
        String days = "";
        results.append("This menu is offered by ");
        for (Schedule sched : schedules) {
            if (menu.getId().compareTo(sched.getMenu().getId()) == 0) {
                if (curReseller.equals(sched.getReseller().getName())) {
                    if (days.length()==0) {
                        results.append(" " + sched.getDayOfWeekName() );
                        days = sched.getDayOfWeekName();
                    } else {
                        results.append(", " + sched.getDayOfWeekName() );
                        days += sched.getDayOfWeekName();
                    }
                } else {
                    results.append(sched.getReseller().getName() + " on " + sched.getDayOfWeekName());
                    curReseller = sched.getReseller().getName();
                    days = sched.getDayOfWeekName();
                }
            }
        }
        results.append(", you must order by 10:00 AM");
        return results.toString();
    }

    public Boolean isMenuOrderable(Menu menu) throws Exception {
        Boolean result = false;
        //check if correct day
        List<Menu> todaysMenus = findTodaysMenus();
        for (Menu todayMenu: todaysMenus) {
            if (menu.getId().compareTo(todayMenu.getId()) == 0)
                result = true;
        }
        //check time is before 10 am
        LocalDateTime now = new LocalDateTime();
        if (result) {
            DateTime nowPacific = now.toDateTime(DateTimeZone.forID("US/Pacific"));
            LocalTime deadline = new LocalTime(10, 00);//limit to 10:00 am
//            LocalTime deadline = new LocalTime(23, 00);//no limit
            if (nowPacific.toLocalTime().isBefore(deadline))
                result = true;
            else
                result = false;
        }


        return result;
    }

    public List<Menu> findTodaysMenusForReseller(String resellerName) throws Exception {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        logger.debug("menusForReseller starting for reseller: " + resellerName + " for day:" + day);
        List<Menu> menus = new ArrayList<Menu>();
        Reseller reseller = resellerRepository.findOneByName(resellerName);
        if (reseller == null)
            throw new Exception("reseller not found in menusForReseller.  That shouldn't happen.");
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule sched : schedules) {
//            logger.debug("    " + sched.getReseller().getName() + " day:" + sched.getDayOfWeek() );
            if (sched.getDayOfWeek() == day) {
                if (sched.getReseller().getId().equals(reseller.getId())) {
                    menus.add(sched.getMenu());
                }
            }
        }
        return menus;
    }

    public List<Menu> findTodaysMenus() throws Exception {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        List<Menu> menus = new ArrayList<Menu>();
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule sched : schedules) {
            if (sched.getDayOfWeek() == day) {
                menus.add(sched.getMenu());
            }
        }
        return menus;
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
