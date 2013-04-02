package com.omo.domain;

import com.omo.repository.MenuItemRepository;
import com.omo.repository.RestaurantRepository;
import com.omo.service.MenuService;
import com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertTrue;

@RooIntegrationTest(entity = Restaurant.class, transactional = false)
public class RestaurantIntegrationTest {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RestaurantIntegrationTest.class);

    @Autowired
    MenuService menuService;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    RestaurantRepository restaurantRepository;


    @Test
    public void testGetMenuHTML() {
        int expectedMinimumSize = 15;
        List<Menu> menus = menuService.findAllMenus();
        Menu menu = menus.get(0);//just get the first menu

        List<String> html = null;
        try {
            html = menuService.getMenuAsHTML(menu.getId());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();

        }

        for (String line: html) {
            System.out.println(line);
        }
        //System.out.println("testGetMenuHTML got: " + html.toString());
        //logger.debug("testGetMenuHTML got: " + html.toString());

        assertTrue("html for a menu should be " + expectedMinimumSize + " but is " + html.size(), html.size() > expectedMinimumSize);
    }

    @Test
    public void testAddMenu() {
        Restaurant restaurant = new Restaurant();
        String restaurantName = "Test Restaurant #1" + new Date();
        restaurant.setName(restaurantName);
        restaurant.setDescription("Test Restaurant Description");
        restaurantRepository.save(restaurant);

        Menu menu = new Menu();
        menu.setName("Test Menu " + new Date());
        menu.setName("Description of test menu");
        //
        MenuItem groupItem = new MenuItem();
        groupItem.setName("Sandwiches");
        groupItem.setType(MenuItem.MenuItemTypes.MenuGroup);
        groupItem.setSortOrder(1);
        menu.getMenuItems().add(groupItem);

        MenuItem item = new MenuItem();
        item.setName("BLT");
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(2);
        groupItem.getChildMenuItems().add(item);

        item = new MenuItem();
        item.setName("Turkey");
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(3);
        groupItem.getChildMenuItems().add(item);

        item = new MenuItem();
        item.setName("Salami");
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(4);
        groupItem.getChildMenuItems().add(item);

        groupItem = new MenuItem();
        groupItem.setName("Cheese");
        groupItem.setType(MenuItem.MenuItemTypes.MenuGroup);
        groupItem.setSortOrder(2);
        menu.getMenuItems().add(groupItem);

        item = new MenuItem();
        item.setName("Provolone");
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(1);
        groupItem.getChildMenuItems().add(item);

        item = new MenuItem();
        item.setName("Cheddar");
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(2);
        groupItem.getChildMenuItems().add(item);

        item = new MenuItem();
        item.setName("Swiss");
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(3);
        groupItem.getChildMenuItems().add(item);


        //menu.setRestaurant("");
        menuService.saveMenu(menu);
    }



}
