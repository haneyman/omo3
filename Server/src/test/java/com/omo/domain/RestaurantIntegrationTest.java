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

    private static final String TEST_RESTAURANT_1 = "Test Restaurant 1";
    private static final String TEST_RESTAURANT_2 = "Test Restaurant 2";
    private static final String TEST_RESTAURANT_3 = "Test Restaurant 3";
    private static final String MENU1 = "Menu for " + TEST_RESTAURANT_1;

    @Test
    public void testGetMenuHTML() {
        int expectedMinimumSize = 6;
        Menu menu1 = menuService.getMenuByName(MENU1).get(0);

        List<String> html = null;
        try {
            html = menuService.getMenuAsHTML(menu1.getId());
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
    public void testAddTestRestaurants() {
        addRestaurant(TEST_RESTAURANT_1);
        addRestaurant(TEST_RESTAURANT_2);
        addRestaurant(TEST_RESTAURANT_3);

        assertTrue("Restaurant 1 not found.", restaurantRepository.findByName(TEST_RESTAURANT_1).size() > 0);
        assertTrue("Restaurant 2 not found.", restaurantRepository.findByName(TEST_RESTAURANT_2).size() > 0);
        assertTrue("Restaurant 3 not found.", restaurantRepository.findByName(TEST_RESTAURANT_3).size() > 0);
    }

    private Restaurant addRestaurant(String name) {
        List<Restaurant> restaurants = restaurantRepository.findByName(name);
        if (restaurants != null && restaurants.size() > 0) {
            Restaurant restaurant = restaurants.get(0);
            restaurantRepository.delete(restaurant);
        }

        Restaurant restaurant = new Restaurant();
        //String restaurantName = name + new Date();
        restaurant.setName(name);
        restaurant.setDescription(name + " Description created " + new Date());
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    @Test
    public void testAddMenu1() {
        testAddTestRestaurants();
        Restaurant restaurant1 = restaurantRepository.findByName(TEST_RESTAURANT_1).get(0);

        List<Menu> menus = menuService.getMenuByName(MENU1);
        if (menus != null && menus.size() > 0) {
            Menu menu = menus.get(0);
            menuService.deleteMenu(menu);
        }

        Menu menu = new Menu();
        menu.setName(MENU1);
        menu.setDescription("Description of menu: " + MENU1);
        menu.setRestaurant(restaurant1);
        //
        MenuItem groupItem = new MenuItem();
        groupItem.setName("Sandwiches");
        groupItem.setType(MenuItem.MenuItemTypes.MenuGroup);
        groupItem.setSortOrder(1);
        menu.getMenuItems().add(groupItem);

        MenuItem item = new MenuItem();
        item.setName("BLT");
        item.setPrice(2.50f);
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(2);
        groupItem.getChildMenuItems().add(item);

        item = new MenuItem();
        item.setName("Turkey");
        item.setPrice(2.50f);
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(3);
        groupItem.getChildMenuItems().add(item);

        item = new MenuItem();
        item.setName("Salami");
        item.setPrice(2.75f);
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(4);
        groupItem.getChildMenuItems().add(item);

        groupItem = new MenuItem();
        groupItem.setName("Cheese");
        item.setPrice(.50f);
        groupItem.setType(MenuItem.MenuItemTypes.MenuGroup);
        groupItem.setSortOrder(2);
        menu.getMenuItems().add(groupItem);

        item = new MenuItem();
        item.setName("Provolone");
        item.setPrice(.50f);
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(1);
        groupItem.getChildMenuItems().add(item);

        item = new MenuItem();
        item.setName("Cheddar");
        item.setPrice(.50f);
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(2);
        groupItem.getChildMenuItems().add(item);

        item = new MenuItem();
        item.setName("Swiss");
        item.setPrice(.50f);
        item.setType(MenuItem.MenuItemTypes.MenuItem);
        item.setSortOrder(3);
        groupItem.getChildMenuItems().add(item);


        //menu.setRestaurant("");
        menuService.saveMenu(menu);

        assertTrue("Menu " + MENU1 + " not found! ", menuService.getMenuByName(MENU1).get(0) != null);


    }



}
