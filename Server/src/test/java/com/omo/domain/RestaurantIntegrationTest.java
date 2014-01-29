package com.omo.domain;

import com.omo.repository.MenuItemRepository;
import com.omo.repository.ResellerRepository;
import com.omo.repository.RestaurantRepository;
import com.omo.repository.ScheduleRepository;
import com.omo.service.MenuService;
import com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.junit.Test;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertTrue;

@RooIntegrationTest(entity = Restaurant.class, transactional = false)
public class RestaurantIntegrationTest {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RestaurantIntegrationTest.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    MenuService menuService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    MenuItemRepository menuItemRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    RestaurantRepository restaurantRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ResellerRepository resellerRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ScheduleRepository scheduleRepository;

    private static final String BENTOLINOS = "Bentolinos";
    private static final String ELCAFECITO = "El Cafecito";

    private static final String TEST_RESTAURANT_1 = "Test Restaurant 1";
    private static final String TEST_RESTAURANT_2 = "Test Restaurant 2";
    private static final String TEST_RESTAURANT_3 = "Test Restaurant 3";
    private static final String MENU1 = "Menu for " + TEST_RESTAURANT_1;

    @Test
    public void LoadAllData(){
        deleteAllMenus();
        deleteAllRestaurants();
        deleteAllSchedules();

        testAddTestRestaurants();
        Menu menu = createBentolinosMenu();

        testScheduleMenu(menu);
    }



    @Test
    public void testAddTestRestaurants() {
        addRestaurant(BENTOLINOS);
//        addRestaurant(TEST_RESTAURANT_1);
//        addRestaurant(TEST_RESTAURANT_2);
//        addRestaurant(TEST_RESTAURANT_3);

        assertTrue("Restaurant Bentolinos not found.", restaurantRepository.findByName(BENTOLINOS).size() > 0);
//        assertTrue("Restaurant 1 not found.", restaurantRepository.findByName(TEST_RESTAURANT_1).size() > 0);
//        assertTrue("Restaurant 2 not found.", restaurantRepository.findByName(TEST_RESTAURANT_2).size() > 0);
//        assertTrue("Restaurant 3 not found.", restaurantRepository.findByName(TEST_RESTAURANT_3).size() > 0);
    }


    //****************************************************************************************************************

    public Restaurant addRestaurant(String name) {
        Restaurant restaurant = new Restaurant();
        Reseller resller = resellerRepository.findOneByName(ELCAFECITO);
        //String restaurantName = name + new Date();
        restaurant.setName(name);
        restaurant.setDescription(name + " Description created " + new Date());
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    public void deleteAllSchedules() {
        List<Schedule>schedules = scheduleRepository.findAll();
        for (Schedule schedule: schedules) {
            scheduleRepository.delete(schedule);
        }
    }

    public void deleteAllMenus() {
        List<Menu> menus = menuService.findAllMenus();
        for (Menu menu:menus) {
            menuService.deleteMenu(menu);
        }
    }

    public void deleteAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant:restaurants) {
            restaurantRepository.delete(restaurant);
        }
    }



    public void testScheduleMenu(Menu menu) {
        Restaurant restaurant = restaurantRepository.findOneByName(BENTOLINOS);
        Reseller reseller = resellerRepository.findOneByName(ELCAFECITO);
        createNewSchedule(reseller, restaurant, menu, 1);
        createNewSchedule(reseller, restaurant, menu, 2);
        createNewSchedule(reseller, restaurant, menu, 3);
        createNewSchedule(reseller, restaurant, menu, 4);
        createNewSchedule(reseller, restaurant, menu, 5);
        createNewSchedule(reseller, restaurant, menu, 6);
        createNewSchedule(reseller, restaurant, menu, 7);
    }


    public Schedule createNewSchedule(Reseller reseller, Restaurant restaurant, Menu menu, Integer day){
        Schedule schedule = new Schedule();
        schedule.setMenu(menu);
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurant);
        schedule.setDayOfWeek(day);
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Test
    public void testAddMenuForBentolinos() {
        Menu menu = createBentolinosMenu();
        assertTrue("Menu is null", menu != null);
    }

    public Menu createBentolinosMenu() {
        //testAddTestRestaurants();
        String restaurantName = "Bentolinos";
        Restaurant restaurant1 = restaurantRepository.findByName(restaurantName).get(0);
        String menuName = "Menu for " + restaurant1.getName();
        String menuDescription = "Description for menu for " + restaurant1.getName();

        Menu menu = new Menu();
        menu.setName(menuName);
        menu.setDescription(menuDescription);
        menu.setRestaurant(restaurant1);
        //
        MenuItem sectionMenuItem = new MenuItem("Sandwiches","",1,MenuItem.MenuItemTypes.MenuSection, 0f, null);
        menu.getMenuItems().add(sectionMenuItem);

        MenuItem menuItem = new MenuItem("Sandwiches","Made with Mayo, Mustard, Lettuce, Tomato & Pickles",1,MenuItem.MenuItemTypes.MenuGroup, 6.00f, sectionMenuItem.getUuid());
        menuItem.addChildMenuItem("BLT","",2,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Tuna Salad","",3,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Corn Beef","",4,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Turkey","",5,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Oven Roasted Turkey","",6,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Roast Beef","",7,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Pastrami","",8,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Meatloaf","",9,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Ham","",10,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Chicken Salad","",11,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Crab Salad","",12,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Salami","",13,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Egg Salad","",14,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Vegetarian","",15,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section


        menuItem = new MenuItem("Cheese","",20,MenuItem.MenuItemTypes.MenuGroup, .80f, sectionMenuItem.getUuid());
        menuItem.addChildMenuItem("Provolone","",21,MenuItem.MenuItemTypes.MenuItem, .80f);
        menuItem.addChildMenuItem("Cheddar","",22,MenuItem.MenuItemTypes.MenuItem, .80f);
        menuItem.addChildMenuItem("Swiss","",23,MenuItem.MenuItemTypes.MenuItem, .80f);
        menuItem.addChildMenuItem("American","",24,MenuItem.MenuItemTypes.MenuItem, .80f);
        menuItem.addChildMenuItem("Jack","",25,MenuItem.MenuItemTypes.MenuItem, .80f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Hot Sandwiches","",40,MenuItem.MenuItemTypes.MenuGroup, 7.95f, sectionMenuItem.getUuid());
        menuItem.addChildMenuItem("Chicken Parmesan W/Provolone","",31,MenuItem.MenuItemTypes.MenuItem, 7.95f);
        menuItem.addChildMenuItem("Chicken Pesto W/Jack","",32,MenuItem.MenuItemTypes.MenuItem, 7.95f);
        menuItem.addChildMenuItem("French Dip W/Au Jus","",33,MenuItem.MenuItemTypes.MenuItem, 7.95f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Sandwich Extras","",30,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem.getUuid());
        menuItem.addChildMenuItem("Cranberry","",41,MenuItem.MenuItemTypes.MenuItem, 0.75f);
        menuItem.addChildMenuItem("Avocado","",42,MenuItem.MenuItemTypes.MenuItem, 1.00f);
        menuItem.addChildMenuItem("Bacon","",43,MenuItem.MenuItemTypes.MenuItem, 0.30f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Combos","",50,MenuItem.MenuItemTypes.MenuGroup, 7.25f, sectionMenuItem.getUuid());
        menuItem.addChildMenuItem("1/2 Sand + Soup","",51,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("1/2 Sand + Salad","",52,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Soup + Salad","",53,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Bread","",25,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem.getUuid());
        menuItem.addChildMenuItem("Sour French Bread","",61,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Dutch Crunch Roll","",62,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Wheat Roll","",63,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Sliced Sourdough","",64,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Honey White","",65,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Dark Rye","",66,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Soft Roll","",67,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Multi Grain","",68,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Croissant","",69,MenuItem.MenuItemTypes.MenuItem, 1.00f);
        menuItem.addChildMenuItem("Focaccia Roll","",70,MenuItem.MenuItemTypes.MenuItem, 1.00f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section


        sectionMenuItem = new MenuItem("SOUP","",80,MenuItem.MenuItemTypes.MenuSection, 0f, null);
        menu.getMenuItems().add(sectionMenuItem);
        menuItem = new MenuItem("Soup Size","",1,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem.getUuid());
        menuItem.addChildMenuItem("12 Oz","",1,MenuItem.MenuItemTypes.MenuItem, 3.95f);
        menuItem.addChildMenuItem("16 Oz","",2,MenuItem.MenuItemTypes.MenuItem, 5.00f);
        sectionMenuItem.getChildMenuItems().add(menuItem);

        menuItem = new MenuItem("Soup Flavor","",2,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem.getUuid());
        menuItem.addChildMenuItem("Cream Potato (Mondays)","",1,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Minestrone (Tuesdays)","",2,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        sectionMenuItem.getChildMenuItems().add(menuItem);

        sectionMenuItem = new MenuItem("HOT FOOD","",90,MenuItem.MenuItemTypes.MenuSection, 0f, sectionMenuItem.getUuid());
        sectionMenuItem.addChildMenuItem("Beef Lasagna","",1,MenuItem.MenuItemTypes.MenuItem, 5.50f);
        menu.getMenuItems().add(sectionMenuItem);

        sectionMenuItem = new MenuItem("SALADS","",110,MenuItem.MenuItemTypes.MenuSection, 0f, null);
        menu.getMenuItems().add(sectionMenuItem);
        menuItem = new MenuItem("Deli Salads","",110,MenuItem.MenuItemTypes.MenuGroup, 3.50f, sectionMenuItem.getUuid());
        menuItem.addChildMenuItem("Macaroni","",111,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Fresh Fruit","",112,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Cucumber Tomato","",113,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Red Potato","",114,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Mixed Beans","",115,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Cold Tuna with Pasta Shells","",116,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Cajun Bean","",117,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Pretzel Jell-O","",118,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Chicken Salad","",119,MenuItem.MenuItemTypes.MenuItem, 3.85f);
        menuItem.addChildMenuItem("Tuna Salad","",120,MenuItem.MenuItemTypes.MenuItem, 3.85f);
        sectionMenuItem.getChildMenuItems().add(menuItem);

        menuItem = new MenuItem("Green Salads","",130,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem.getUuid());
        menuItem.addChildMenuItem("Dressing on the side","",131,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Caesar Salad","",132,MenuItem.MenuItemTypes.MenuItem, 5.50f);
        menuItem.addChildMenuItem("Garden Salad","",133,MenuItem.MenuItemTypes.MenuItem, 5.50f);
        menuItem.addChildMenuItem("Chicken Caesar Salad","",134,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Garden Salad w/Chicken","",135,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Pasta primavera w/chicken","",136,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Taco Salad","",137,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Oriental Salad","",138,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        MenuItem menuItem2 = new MenuItem("Dressing","",150,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem.getUuid());
        menuItem2.addChildMenuItem("Caesar","",1,MenuItem.MenuItemTypes.MenuItem, 0.50f);
        menuItem2.addChildMenuItem("Ranch","",2,MenuItem.MenuItemTypes.MenuItem, 0.50f);
        menuItem2.addChildMenuItem("Oriental","",3,MenuItem.MenuItemTypes.MenuItem, 0.50f);
        menuItem2.addChildMenuItem("Italian","",4,MenuItem.MenuItemTypes.MenuItem, 0.50f);
        menuItem2.addChildMenuItem("Balsamic Vinegar","",5,MenuItem.MenuItemTypes.MenuItem, 0.50f);
        sectionMenuItem.getChildMenuItems().add(menuItem2);
        sectionMenuItem.getChildMenuItems().add(menuItem);


        //menu.setRestaurant("");
        menuService.saveMenu(menu);

        assertTrue("Menu " + MENU1 + " not found! ", menuService.getMenuByName(menuName).get(0) != null);

        return menu;

    }

}
