package com.omo.service;


import com.omo.domain.*;
import com.omo.repository.MenuRepository;
import com.omo.repository.ResellerRepository;
import com.omo.repository.RestaurantRepository;
import com.omo.repository.ScheduleRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.*;


@Transactional
@Service
public class MenuServiceImpl implements MenuService {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MenuServiceImpl.class);
    private static final String INDENT = "   ";
    private static final String NEW_LINE = "\n";//"\n";
    @Autowired
    MenuRepository menuRepository;
    private ArrayList<String> html;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    ResellerRepository resellerRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    RestaurantRepository restaurantRepository;

    @Autowired
    ScheduleRepository scheduleRepository;


    /**
     * Finds menuItemOption in a menu,
     * they are usually under a group under a menu item or menu item group
     */
    public MenuItemOption findOptionInMenu(Menu menu, String optionUuid) {
        MenuItemOption menuItemResult=null;
        for (MenuItem menuItem : menu.getMenuItems()) {
            menuItemResult = findOptionInMenuItem(menuItem, optionUuid);
            if (menuItemResult != null)
                return menuItemResult;
        }
        return null;
    }


    private MenuItemOption findOptionInMenuItem(MenuItem menuItem, String optionUuid) {
        MenuItemOption result = null;
        if (menuItem.getOptions() != null)
            result = findOptionInOptions(menuItem.getOptions(), optionUuid);
        if (result == null) {
            //not in item's options, so look in item's child menu items
            for (MenuItem childMenuItem : menuItem.getChildMenuItems()) {
                result = findOptionInMenuItem(childMenuItem, optionUuid);//looks for option at section level
                if (result != null) {
                    return result;
                }
            }
        }
        return result;
    }

    private MenuItemOption findOptionInOptions(Set<MenuItemOption> options, String optionUuid) {
        MenuItemOption result = null;
        for (MenuItemOption option : options) {
            if (option.getUuid().equalsIgnoreCase(optionUuid))
                return option;
            if (option.getChildren() != null)
                result = findOptionInOptions(option.getChildren(), optionUuid);
            if (result != null)
                return result;
        }
        return result;
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
        MenuItem parentMenuItem = getMenuItemFromSet(menu.getMenuItems(), menuItemResult.getParentUuid());
        if (parentMenuItem != null && parentMenuItem.getOptions() != null)
            menuItemResult.getOptions().addAll(parentMenuItem.getOptions());
        //add menu section options
        MenuItem grandparentMenuItem = getMenuItemFromSet(menu.getMenuItems(), parentMenuItem.getParentUuid());
        if (grandparentMenuItem != null && grandparentMenuItem.getOptions() != null)
            menuItemResult.getOptions().addAll(grandparentMenuItem.getOptions());
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
        logger.debug("findOptionInMenu looking for " + optionUuid + " " + " under " + menuItem.getName());
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


    public List<Menu> getMenuByName(String menuName) {
        return menuRepository.findByName(menuName);
    }

    //recursively searchves for a menuItem within a menu
    public  MenuItem getMenuItemByUuid(Menu menu, String menuItemUUID) {
        return getMenuItemFromSet(menu.getMenuItems(), menuItemUUID);
    }

    //recursively searchves for a menuItem from a given set of menuItems
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

    public Menu updateMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public long countAllMenus() {
        return menuRepository.count();
    }

    public void deleteMenu(Menu menu) {
        menuRepository.delete(menu);
    }

    public Menu findMenu(BigInteger id) {
        Menu menu = menuRepository.findOne(id);
        menu.sortMenuItems();
        return menu;
    }

    public List<Menu> findAllMenus() {
        return menuRepository.findAll();
    }

    public List<Menu> findMenuEntries(int firstResult, int maxResults) {
        return menuRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
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
