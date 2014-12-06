package com.omo.domain;

import com.omo.repository.MenuItemRepository;
import com.omo.repository.ResellerRepository;
import com.omo.repository.RestaurantRepository;
import com.omo.repository.ScheduleRepository;
import com.omo.service.MenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

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

/*
    @Test
    public void LoadAllData(){
        deleteAllMenus();
        deleteAllRestaurants();
        deleteAllSchedules();

        testAddTestRestaurants();
        Menu menu = createMenuBentolinos();

        testScheduleMenu(menu);
    }
*/



    @Test
    public void testAddTestRestaurants() {
//        addRestaurant(BENTOLINOS);
//        addRestaurant(TEST_RESTAURANT_1);
//        addRestaurant(TEST_RESTAURANT_2);
//        addRestaurant(TEST_RESTAURANT_3);

//        assertTrue("Restaurant Bentolinos not found.", restaurantRepository.findByName(BENTOLINOS).size() > 0);
//        assertTrue("Restaurant 1 not found.", restaurantRepository.findByName(TEST_RESTAURANT_1).size() > 0);
//        assertTrue("Restaurant 2 not found.", restaurantRepository.findByName(TEST_RESTAURANT_2).size() > 0);
//        assertTrue("Restaurant 3 not found.", restaurantRepository.findByName(TEST_RESTAURANT_3).size() > 0);
    }


    //****************************************************************************************************************

    public Restaurant addRestaurant(String name, String description) {
        Restaurant restaurant = new Restaurant();
        Reseller resller = resellerRepository.findOneByName(name);
        //String restaurantName = name + new Date();
        restaurant.setName(name);
        restaurant.setDescription(description);
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
        Menu menu = createMenuBentolinos();
        assertTrue("Menu is null", menu != null);
    }

    public Menu createMenuBentolinos() {
        //testAddTestRestaurants();
        String restaurantName = "Bentolinos";
        Restaurant restaurant1 = restaurantRepository.findByName(restaurantName).get(0);
        String menuName = "Menu for " + restaurant1.getName();
        String menuDescription = "High Quality family operated Deli";

        Menu menu = new Menu();
        menu.setName(menuName);
        menu.setDescription(menuDescription);
        menu.setRestaurant(restaurant1);
        //
        MenuItem sectionMenuItem = new MenuItem("Sandwiches","",1,MenuItem.MenuItemTypes.MenuSection, 0f, null);
        menu.getMenuItems().add(sectionMenuItem);

        MenuItem menuItem = new MenuItem("Sandwiches","Made with Mayo, Mustard, Lettuce, Tomato & Pickles",1,MenuItem.MenuItemTypes.MenuGroup, 6.00f, sectionMenuItem);
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


        menuItem = new MenuItem("Cheese","",20,MenuItem.MenuItemTypes.MenuGroup, .80f, sectionMenuItem);
        menuItem.addChildMenuItem("Provolone","",21,MenuItem.MenuItemTypes.MenuItem, .80f);
        menuItem.addChildMenuItem("Cheddar","",22,MenuItem.MenuItemTypes.MenuItem, .80f);
        menuItem.addChildMenuItem("Swiss","",23,MenuItem.MenuItemTypes.MenuItem, .80f);
        menuItem.addChildMenuItem("American","",24,MenuItem.MenuItemTypes.MenuItem, .80f);
        menuItem.addChildMenuItem("Jack","",25,MenuItem.MenuItemTypes.MenuItem, .80f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Hot Sandwiches","",40,MenuItem.MenuItemTypes.MenuGroup, 7.95f, sectionMenuItem);
        menuItem.addChildMenuItem("Chicken Parmesan W/Provolone","",31,MenuItem.MenuItemTypes.MenuItem, 7.95f);
        menuItem.addChildMenuItem("Chicken Pesto W/Jack","",32,MenuItem.MenuItemTypes.MenuItem, 7.95f);
        menuItem.addChildMenuItem("French Dip W/Au Jus","",33,MenuItem.MenuItemTypes.MenuItem, 7.95f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Sandwich Extras","",30,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem);
        menuItem.addChildMenuItem("Cranberry","",41,MenuItem.MenuItemTypes.MenuItem, 0.75f);
        menuItem.addChildMenuItem("Avocado","",42,MenuItem.MenuItemTypes.MenuItem, 1.00f);
        menuItem.addChildMenuItem("Bacon","",43,MenuItem.MenuItemTypes.MenuItem, 0.40f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Combos","",50,MenuItem.MenuItemTypes.MenuGroup, 7.25f, sectionMenuItem);
        menuItem.addChildMenuItem("1/2 Sand + Soup","",51,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("1/2 Sand + Salad","",52,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Soup + Salad","",53,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Bread","",25,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem);
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
        menuItem = new MenuItem("Soup Size","",1,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem);
        menuItem.addChildMenuItem("12 Oz","",1,MenuItem.MenuItemTypes.MenuItem, 3.95f);
        menuItem.addChildMenuItem("16 Oz","",2,MenuItem.MenuItemTypes.MenuItem, 5.00f);
        sectionMenuItem.getChildMenuItems().add(menuItem);

        menuItem = new MenuItem("Soup Flavor","",2,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem);
        menuItem.addChildMenuItem("Cream Potato (Mondays)","",1,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Minestrone (Tuesdays)","",2,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        sectionMenuItem.getChildMenuItems().add(menuItem);

        sectionMenuItem = new MenuItem("HOT FOOD","",90,MenuItem.MenuItemTypes.MenuSection, 0f, sectionMenuItem);
        sectionMenuItem.addChildMenuItem("Beef Lasagna","",1,MenuItem.MenuItemTypes.MenuItem, 5.75f);
        menu.getMenuItems().add(sectionMenuItem);

        sectionMenuItem = new MenuItem("SALADS","",110,MenuItem.MenuItemTypes.MenuSection, 0f, null);
        menu.getMenuItems().add(sectionMenuItem);
        menuItem = new MenuItem("Deli Salads","",110,MenuItem.MenuItemTypes.MenuGroup, 3.50f, sectionMenuItem);
        menuItem.addChildMenuItem("Macaroni","",111,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Fresh Fruit","",112,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Cucumber Tomato","",113,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Red Potato","",114,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Mixed Beans","",115,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Cold Tuna with Pasta Shells","",116,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Cajun Bean","",117,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Pretzel Jell-O","",118,MenuItem.MenuItemTypes.MenuItem, 3.50f);
        menuItem.addChildMenuItem("Chicken Salad","",119,MenuItem.MenuItemTypes.MenuItem, 4.50f);
        menuItem.addChildMenuItem("Tuna Salad","",120,MenuItem.MenuItemTypes.MenuItem, 4.50f);
        sectionMenuItem.getChildMenuItems().add(menuItem);

        menuItem = new MenuItem("Green Salads","",130,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem);
        menuItem.addChildMenuItem("Dressing on the side","",131,MenuItem.MenuItemTypes.MenuItem, 0.00f);
        menuItem.addChildMenuItem("Caesar Salad","",132,MenuItem.MenuItemTypes.MenuItem, 5.50f);
        menuItem.addChildMenuItem("Garden Salad","",133,MenuItem.MenuItemTypes.MenuItem, 5.50f);
        menuItem.addChildMenuItem("Chicken Caesar Salad","",134,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Garden Salad w/Chicken","",135,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Pasta primavera w/chicken","",136,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Taco Salad","",137,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        menuItem.addChildMenuItem("Oriental Salad","",138,MenuItem.MenuItemTypes.MenuItem, 6.00f);
        MenuItem menuItem2 = new MenuItem("Dressing","",150,MenuItem.MenuItemTypes.MenuGroup, 0.00f, sectionMenuItem);
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


    public Menu createMenuElMolino(Restaurant restaurant) {
        //testAddTestRestaurants();
        String restaurantName = "El Molino";
        String menuName = "Menu for " + restaurant.getName();
        String menuDescription = "Mexican Food";

        Menu menu = new Menu();
        menu.setName(menuName);
        menu.setDescription(menuDescription);
        menu.setRestaurant(restaurant);
        //
        MenuItem sectionMenuItem = new MenuItem("","",1,MenuItem.MenuItemTypes.MenuSection, 0f, null);
        menu.getMenuItems().add(sectionMenuItem);

        MenuItem menuItem = new MenuItem("Taco Salad","Served with Pico de Gallo, Sour Cream, Guacamole, Romaine Lettuce, Beans & Rice in a Flour Bowl."
                ,1,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Chicken","",1,MenuItem.MenuItemTypes.MenuItem, 8.50f);
        menuItem.addChildMenuItem("Beef","",2,MenuItem.MenuItemTypes.MenuItem, 8.50f);
        menuItem.addChildMenuItem("Carnitas","",3,MenuItem.MenuItemTypes.MenuItem, 8.50f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Nachos","Jack Cheese, Beans, Lettuce, Guacamole, Pico De Gallo, Sour Cream",
                2,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Steak","",1,MenuItem.MenuItemTypes.MenuItem, 7.50f);
        menuItem.addChildMenuItem("Chicken","",2,MenuItem.MenuItemTypes.MenuItem, 7.50f);
        menuItem.addChildMenuItem("Carnitas","",3,MenuItem.MenuItemTypes.MenuItem, 7.50f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Soft Tacos","Served with Chopped Onions, Cilantro, Salsa, Beans & Rice",
                4,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Steak (Asada)","",1,MenuItem.MenuItemTypes.MenuItem, 7.50f);
        menuItem.addChildMenuItem("Fried Pork (Carnitas","",2,MenuItem.MenuItemTypes.MenuItem, 7.50f);
        menuItem.addChildMenuItem("Pork Stomach (Buche)","",3,MenuItem.MenuItemTypes.MenuItem, 7.50f);
        menuItem.addChildMenuItem("Chicken (Pollo)","",4,MenuItem.MenuItemTypes.MenuItem, 7.50f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Burrito","Served with Onions, Guacamole, Sour Cream, Cheese, Tomatoes, Beans, Rice, Salsa & Cilantro",
                5,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Steak (Asada)","",1,MenuItem.MenuItemTypes.MenuItem, 6.75f);
        menuItem.addChildMenuItem("Chicken (Pollo)","",2,MenuItem.MenuItemTypes.MenuItem, 6.75f);
        menuItem.addChildMenuItem("Marinated Pork (Alpastor)","",3,MenuItem.MenuItemTypes.MenuItem, 6.75f);
        menuItem.addChildMenuItem("Fried Pork (Carnitas)","",4,MenuItem.MenuItemTypes.MenuItem, 6.75f);
        menuItem.addChildMenuItem("Pork Stomach (Buche)","",5,MenuItem.MenuItemTypes.MenuItem, 6.75f);
        menuItem.addChildMenuItem("Pork Skin (Cueritos)","",6,MenuItem.MenuItemTypes.MenuItem, 6.75f);
        menuItem.addChildMenuItem("Beef Head (Cabeza)","",7,MenuItem.MenuItemTypes.MenuItem, 6.75f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Soup 32oz","Chicken Based Soup with Tortilla Chips, diced Avocado & Cheese",
                6,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Chicken Tortilla Soup","",1,MenuItem.MenuItemTypes.MenuItem, 5.95f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Super Quesadilla","",
                7,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Steak","",1,MenuItem.MenuItemTypes.MenuItem, 7.50f);
        menuItem.addChildMenuItem("Ham","",1,MenuItem.MenuItemTypes.MenuItem, 7.50f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Chile Rellenos","Pasilla Pepper Stuffed with Cheese.  Served with Beans & Rice",
                8,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Corn Tortilla","",1,MenuItem.MenuItemTypes.MenuItem, 8.99f);
        menuItem.addChildMenuItem("Flour Tortilla","",2,MenuItem.MenuItemTypes.MenuItem, 8.99f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Sopes","Served with 2 Corn Shells, Sour Cream, Lettuce, Salsa, Beans & Rice",
                9,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Beef (Asada)","",1,MenuItem.MenuItemTypes.MenuItem, 8.50f);
        menuItem.addChildMenuItem("Pork Stomach (Buche) & Carnitas","",2,MenuItem.MenuItemTypes.MenuItem, 8.50f);
        menuItem.addChildMenuItem("Cuerito (Pork Skin)","",3,MenuItem.MenuItemTypes.MenuItem, 8.50f);
        menuItem.addChildMenuItem("Chicken (Pollo)","",4,MenuItem.MenuItemTypes.MenuItem, 8.50f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("2 Papusas (Salvadorian food)","Served with Cabbage Salad  Tomato Sauce (Salsa)",
            10,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Cheese","",2,MenuItem.MenuItemTypes.MenuItem, 5.95f);
        menuItem.addChildMenuItem("Re-Fried Beans","",3,MenuItem.MenuItemTypes.MenuItem, 5.95f);
        menuItem.addChildMenuItem("Pork","",4,MenuItem.MenuItemTypes.MenuItem, 5.95f);
        menuItem.addChildMenuItem("Mix all together","",5,MenuItem.MenuItemTypes.MenuItem, 5.95f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Tortas","Lettuce, Tomato, Onion, Jalapeno, Guacamole & Mayo.",
                10,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Tortas ","",1,MenuItem.MenuItemTypes.MenuItem, 5.99f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Other Items","",
                11,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Taco","",2,MenuItem.MenuItemTypes.MenuItem, 2.95f);
        menuItem.addChildMenuItem("Sope","",3,MenuItem.MenuItemTypes.MenuItem, 3.25f);
        menuItem.addChildMenuItem("Bionico - fruit salad with yogurt and coconuts","",1,MenuItem.MenuItemTypes.MenuItem, 4.00f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        //menu.setRestaurant("");
        menuService.saveMenu(menu);

        assertTrue("Menu " + MENU1 + " not found! ", menuService.getMenuByName(menuName).get(0) != null);

        return menu;

    }


    public Menu createMenuKinders(Restaurant restaurant) {
        //testAddTestRestaurants();
        String restaurantName = "Kinder's";
        String menuName = "Menu for " + restaurant.getName();
        String menuDescription = "Meat Deli BBQ";

        Menu menu = new Menu();
        menu.setName(menuName);
        menu.setDescription(menuDescription);
        menu.setRestaurant(restaurant);
        //
        MenuItem sectionMenuItem = new MenuItem("","",1,MenuItem.MenuItemTypes.MenuSection, 0f, null);
        menu.getMenuItems().add(sectionMenuItem);

        MenuItem menuItem = new MenuItem("Hot Sandwiches","(Excluding BBQ Beef) Include Mayo, Lettuce, Tomato & Mild BBQ Sauce."
                ,1,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Kinder's Famous Ball Tip Sandwich","",1,MenuItem.MenuItemTypes.MenuItem, 9.30f);
        menuItem.addChildMenuItem("Kinder's BBQ Beef Sandwich","",2,MenuItem.MenuItemTypes.MenuItem, 9.01f);
        menuItem.addChildMenuItem("Louisiana Style Hot Link Sandwich","",3,MenuItem.MenuItemTypes.MenuItem, 9.01f);
        menuItem.addChildMenuItem("Grilled Chicken Breast Sandwich","",4,MenuItem.MenuItemTypes.MenuItem, 9.01f);
        menuItem.addChildMenuItem("California Chicken Club Sandwich - everything a Hot Sandwich has plus Bacon, Cheese and Avocado","",5,MenuItem.MenuItemTypes.MenuItem, 10.10f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Deli Sandwiches","",
                2,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Roasted Turkey Sandwich","",1,MenuItem.MenuItemTypes.MenuItem, 7.92f);
        menuItem.addChildMenuItem("Black Forest Ham Sandwich","",2,MenuItem.MenuItemTypes.MenuItem, 7.92f);
        menuItem.addChildMenuItem("Roast Beef Sandwich","",3,MenuItem.MenuItemTypes.MenuItem, 7.92f);
        menuItem.addChildMenuItem("Italian Dry Salami Sandwich","",4,MenuItem.MenuItemTypes.MenuItem, 7.92f);
        menuItem.addChildMenuItem("Combination Sandwich (Roast beef, turkey, dry salami, ham, cheese)","",5,MenuItem.MenuItemTypes.MenuItem, 8.70f);
        menuItem.addChildMenuItem("Vegetarian Sandwich (Cream Cheese, Crusshed Walnuts, Spinach Leaves, Bell Peppers, Cucumbers, Lettuce, and Tomato","",6,MenuItem.MenuItemTypes.MenuItem, 7.92f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Type of Bread for Sandwiches","Please choose a bread for your sandwich.",
                3,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Wheat","",1,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Soft Roll","",2,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Wheat Roll","",3,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Sourdough Roll","",4,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Sliced Sourdough","",5,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Dutch Crunch Roll","",6,MenuItem.MenuItemTypes.MenuItem, 0f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section


        menuItem = new MenuItem("Cheese for Sandwiches","",
                4,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("Jack","",1,MenuItem.MenuItemTypes.MenuItem, 0.75f);
        menuItem.addChildMenuItem("Cheddar","",2,MenuItem.MenuItemTypes.MenuItem, 0.75f);
        menuItem.addChildMenuItem("Provolone","",3,MenuItem.MenuItemTypes.MenuItem, 0.75f);
        menuItem.addChildMenuItem("Swiss","",4,MenuItem.MenuItemTypes.MenuItem, 0.75f);
        menuItem.addChildMenuItem("American","",5,MenuItem.MenuItemTypes.MenuItem, 0.75f);
        menuItem.addChildMenuItem("Avocado","",10,MenuItem.MenuItemTypes.MenuItem, 1.00f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Salads","",
                5,MenuItem.MenuItemTypes.MenuGroup, .00f, sectionMenuItem);
        menuItem.addChildMenuItem("BBQ Chicken Salad - Iceberg Lettuce, Carrots, Cabbage, Tomato, Cucumber, Shredded Cheddar, Avocado, BBQ Ranch Dressing","",1,MenuItem.MenuItemTypes.MenuItem, 8.46f);
        menuItem.addChildMenuItem("Chicken Caesar Salad - Romain Lettuce, Parmesan Cheese, Croutons, And Caesar Dressing","",2,MenuItem.MenuItemTypes.MenuItem, 8.46f);
        menuItem.addChildMenuItem("Cobb Salad - Iceberg Lettuce, Egg, Tomato, Avocado, ?Blue Cheese Crubles, Bacon Bits, and Blue Cheese Dressing","",3,MenuItem.MenuItemTypes.MenuItem, 8.46f);
        menuItem.addChildMenuItem("Tossed Green Salad - Iceberg Lettuce, Carrots, Cabbage, Tomato, Cucumber, and Shredded Cheddar Cheese with Ranch Dressing","",4,MenuItem.MenuItemTypes.MenuItem, 5.95f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        //menu.setRestaurant("");
        menuService.saveMenu(menu);

        assertTrue("Menu " + MENU1 + " not found! ", menuService.getMenuByName(menuName).get(0) != null);

        return menu;

    }

    public Menu createMenuSweetAffair(Restaurant restaurant) {
        //testAddTestRestaurants();
        String restaurantName = "A Sweet Affair";
        Restaurant restaurant1 = restaurantRepository.findByName(restaurantName).get(0);
        String menuName = restaurant1.getName() + " Manu";
        String menuDescription = "Sandwiches And Salads" + restaurant1.getName();

        Menu menu = new Menu();
        menu.setName(menuName);
        menu.setDescription(menuDescription);
        menu.setRestaurant(restaurant1);
        //
        MenuItem sectionMenuItem = new MenuItem("","",1,MenuItem.MenuItemTypes.MenuSection, 0f, null);
        menu.getMenuItems().add(sectionMenuItem);

        MenuItem menuItem = new MenuItem("Sandwiches","",1,MenuItem.MenuItemTypes.MenuGroup, 6.00f, sectionMenuItem);
        menuItem.addChildMenuItem("Roasted Turkey - Cream Cheese, Avocado, Cranberry Sauce, Sunflower Seeds, Swiss Cheese Sprouts, Lettuce, Tomato","",2,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Turkey & Ham - Mayo, Mustard, Tomato, Swiss Cheese, Sprouts, Lettuce","",2,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Roast Beef - Mayo, Mustard, Swiss Cheese, Horseradish, Sprouts, Lettuce","",2,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Ham - Mayo, Mustard, Sprouts, Lettuce Swiss Cheese","",2,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Italian - Mayo, Salami, Hot Peppers, Bell Peppers, Mortadella, Ham, Swiss Cheese, Lettuce","",5,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Salami - Mayo, Mustard, Tomato, Red Onion, Swiss Cheese, Lettuce","",6,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Caprese - Fresh Mozzarella, tomato, fresh basil, and pesto (served on ciabatta bread)","",8,MenuItem.MenuItemTypes.MenuItem, 8.25f);
        menuItem.addChildMenuItem("Vegeterian - Cream Cheese, Avocado, Sprouts, Cranberry Sauce, Sun flower Seeds, Lettuce, Cucumber, Bell Peppers, Swiss Cheese","",9,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Cheese Lover - Mayo, Mustard, Tomato, Red Onion, Swiss Cheese, Probolone, American Cheese, Sprouts, Lettuce","",10,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Chicken Salad - Cream Cheese, Avocado, Cashews, Sprouts, Lettuce","",12,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Tuna Salad - Mayo, Cashews, Pickles, Sprouts, Lettuce, Red Onions","",12,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Crab Salad - Cream Cheese, Sprouts, Lettuce","",13,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Hot Sandwiches","",2,MenuItem.MenuItemTypes.MenuGroup, 6.00f, sectionMenuItem);
        menuItem.addChildMenuItem("Hot Pastrami - Mayo, mustard, Lettuce, Red Onion, Provolone","",1,MenuItem.MenuItemTypes.MenuItem, 8.25f);
        menuItem.addChildMenuItem("Hot Bar-B-Que - Beef & Chicken (Best on French Roll)","",2,MenuItem.MenuItemTypes.MenuItem, 8.25f);
        menuItem.addChildMenuItem("Meatball Hot - Meatballs, marinara sauce, bell peppers, red onions, provolone cheese","",3,MenuItem.MenuItemTypes.MenuItem, 8.25f);
        menuItem.addChildMenuItem("The Club - Turkey, ham, bacon, mayo, avocado, tomato, lettuce","",16,MenuItem.MenuItemTypes.MenuItem, 8.25f);
        menuItem.addChildMenuItem("The Club - Turkey, ham, bacon, mayo, avocado, tomato, lettuce","",4,MenuItem.MenuItemTypes.MenuItem, 8.25f);
        menuItem.addChildMenuItem("Tri Tip Sandwich","",17,MenuItem.MenuItemTypes.MenuItem, 8.25f);
        menuItem.addChildMenuItem("BLT-A - Bacon, lettuce, tomato, avocado, mayo (served on ciabatta)","",19,MenuItem.MenuItemTypes.MenuItem, 8.25f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section


        menuItem = new MenuItem("Choice of Bread","",2,MenuItem.MenuItemTypes.MenuGroup, 7.95f, sectionMenuItem);
        menuItem.addChildMenuItem("Multi Grain Roll","",1,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Multi Grain","",2,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("French Roll","",3,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Whole Wheat","",4,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Butter Crust","",5,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Sour Dough Roll","",6,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Whole Wheat Roll","",7,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Honey Corn","",8,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Sesame Herb","",9,MenuItem.MenuItemTypes.MenuItem, 0f);
        menuItem.addChildMenuItem("Rye","",10,MenuItem.MenuItemTypes.MenuItem, 0f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Combos","",3,MenuItem.MenuItemTypes.MenuGroup, 7.25f, sectionMenuItem);
        menuItem.addChildMenuItem("1/2 Sand + Soup","",1,MenuItem.MenuItemTypes.MenuItem, 7.75f);
        menuItem.addChildMenuItem("1/2 Sand + Caesar Salad","",2,MenuItem.MenuItemTypes.MenuItem, 7.75f);
        menuItem.addChildMenuItem("1/2 Sand + Garden Salad","",3,MenuItem.MenuItemTypes.MenuItem, 7.75f);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Soup","",4,MenuItem.MenuItemTypes.MenuGroup, 7.25f, sectionMenuItem);
        menuItem.addChildMenuItem("Large Soup","",1,MenuItem.MenuItemTypes.MenuItem, 4.50f);
        sectionMenuItem.getChildMenuItems().add(menuItem);

        menuItem = new MenuItem("Salads","",5,MenuItem.MenuItemTypes.MenuGroup, 3.50f, sectionMenuItem);
        menuItem.addChildMenuItem("Garden Salad - Cucumbers, Bell Peppers, Red Onions, Raisins, Sunflower Seeds","", 1,MenuItem.MenuItemTypes.MenuItem, 6.75f);
        menuItem.addChildMenuItem("Caesar Salad - Crisp Romain Lettuce Tossed with Creamy Caesar Dressing, Parmesan Cheese, Croutons","", 2,MenuItem.MenuItemTypes.MenuItem, 6.75f);
        menuItem.addChildMenuItem("Greek Salad - Crisp Romaine Lettuce, red Onion, Cucumbers, Black Olives, Hot pepppers, Tomatoes, feta cheese, marinated artichokes","", 3,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Chinese Chicken Salad - Chineses Noodles, Chicken, Sasame Seeds, Oriantal Dressing","", 4,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Chef Salad - Egg, Onion, Tomato, Ham, Turkey, Swiss Cheese, American Cheese","", 5,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Cobb Salad - Cucumbers, Tomato, Egg, Bacon, Blue Cheese, Chicken","", 6,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("House Salad - Gorgonzola Cheese, Walnuts, Cranberry, Tangerine, Won Ton Noodles, Homemade Italian Dressing","", 7,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Southwestern - Black Beans, Cheddar Cheese, Corn, Onion, Tortilla Strips, Salsa, Romaine Lettuce with Chipotle Ranch Dressing","", 8,MenuItem.MenuItemTypes.MenuItem, 7.25f);
        menuItem.addChildMenuItem("Add Chicken","", 9,MenuItem.MenuItemTypes.MenuItem, 2.00f);
        sectionMenuItem.getChildMenuItems().add(menuItem);

        menuItem = new MenuItem("Dressing","",6,MenuItem.MenuItemTypes.MenuGroup, 0f, sectionMenuItem);
        menuItem.addChildMenuItem("Ranch","", 2,MenuItem.MenuItemTypes.MenuItem, .50f);
        menuItem.addChildMenuItem("Blue Cheese","", 3,MenuItem.MenuItemTypes.MenuItem, .50f);
        menuItem.addChildMenuItem("Italian","", 4,MenuItem.MenuItemTypes.MenuItem, .50f);
        menuItem.addChildMenuItem("FF Honey Mustard","", 5,MenuItem.MenuItemTypes.MenuItem, .50f);
        sectionMenuItem.getChildMenuItems().add(menuItem);

        menuItem = new MenuItem("Cookie","",7,MenuItem.MenuItemTypes.MenuGroup, 3.50f, sectionMenuItem);
        menuItem.addChildMenuItem("White Chip","", 1,MenuItem.MenuItemTypes.MenuItem, 1.50f);
        menuItem.addChildMenuItem("Chocolate Chip","", 2,MenuItem.MenuItemTypes.MenuItem, 1.50f);
        menuItem.addChildMenuItem("Chocolate Chip and Nuts","", 3,MenuItem.MenuItemTypes.MenuItem, 1.50f);
        menuItem.addChildMenuItem("Oatmeal","", 4,MenuItem.MenuItemTypes.MenuItem, 1.50f);
        menuItem.addChildMenuItem("Oatmeal Raisin","", 5,MenuItem.MenuItemTypes.MenuItem, 1.50f);
        menuItem.addChildMenuItem("Peanut Butter Chip","", 6,MenuItem.MenuItemTypes.MenuItem, 1.50f);
        menuItem.addChildMenuItem("Macaroons","", 7,MenuItem.MenuItemTypes.MenuItem, 1.50f);
        menuItem.addChildMenuItem("Rasberry Filled","", 8,MenuItem.MenuItemTypes.MenuItem, 1.50f);
        menuItem.addChildMenuItem("Peanut Butter","", 9,MenuItem.MenuItemTypes.MenuItem, 1.50f);
        menuItem.addChildMenuItem("Mexican Wedding","", 10,MenuItem.MenuItemTypes.MenuItem, 1.50f);
        menuItem.addChildMenuItem("Snicker Doodle","", 11,MenuItem.MenuItemTypes.MenuItem, 1.50f);
        sectionMenuItem.getChildMenuItems().add(menuItem);

        menuService.saveMenu(menu);

        assertTrue("Menu " + MENU1 + " not found! ", menuService.getMenuByName(menuName).get(0) != null);

        return menu;


    }

    public Menu createMenuQuiznos(Restaurant restaurant) {
        logger.info("Creating menu for Quiznos...");
        //testAddTestRestaurants();
        String restaurantName = "Quiznos";
        Restaurant restaurant1 = restaurantRepository.findByName(restaurantName).get(0);
        String menuName = restaurant1.getName() + " Menu";
        String menuDescription = "mmmm....TOASTY" + restaurant1.getName();

        Menu menu = new Menu();
        menu.setName(menuName);
        menu.setDescription(menuDescription);
        menu.setRestaurant(restaurant1);
        //
        MenuItem sectionMenuItem = new MenuItem("Subs","",1,MenuItem.MenuItemTypes.MenuSection, 0f, null);
        menu.getMenuItems().add(sectionMenuItem);

        final String SIZE_SMALL = "Sm";
        final String SIZE_REGULAR = "Rg 8\"";
        final String SIZE_LARGE = "Lg 11\"";
        final String SIZE_REGULAR_SOUP = "Reg 10oz";
        final String SIZE_LARGE_SOUP = "Lg 16oz";
        final String SIZE_WRAP = "Wrap";
        MenuItem menuItem = new MenuItem("Steak Subs","",1,MenuItem.MenuItemTypes.MenuGroup, 0f, sectionMenuItem);

        MenuItemOption groupOption = menuItem.addOption(MenuItemOption.MenuItemOptionTypes.Group, null, "Size");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 7.99F, SIZE_REGULAR);
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 9.99F, SIZE_LARGE);

        groupOption = menuItem.addOption(MenuItemOption.MenuItemOptionTypes.Group, null, "Bread");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 0F, "White");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 0F, "Wheat");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 0F, "Rosemary Parmesan");


        menuItem.addChildMenuItem("Black Angus Steak", "With mozzarella & mild cheddar, sautéed mushrooms & onions, honey bourbon mustard & zesty grille sauce",1,MenuItem.MenuItemTypes.MenuItem);

        menuItem.addChildMenuItem("Double Swiss Prime Rib","with sautéed onions & mayo",2,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Peppercorn Prime Rib","with mozzarella, sautéed onions & peppercorn sauce",3,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Roast Beef & Horseradish","Black Angus roast beef, mild cheddar, sautéed onions, lettuce & tomatoes",4,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Spicy Prime Rib","with mild cheddar, fresh Q-salasa & chipotle mayo",5,MenuItem.MenuItemTypes.MenuItem);

        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section


        menuItem = new MenuItem("Chicken Subs","",1,MenuItem.MenuItemTypes.MenuGroup, 0f, sectionMenuItem);
        groupOption = menuItem.addOption(MenuItemOption.MenuItemOptionTypes.Group, null, "Size");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 7.99F, SIZE_REGULAR);
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 9.99F, SIZE_LARGE);

        groupOption = menuItem.addOption(MenuItemOption.MenuItemOptionTypes.Group, null, "Bread");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 0F, "White");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 0F, "Wheat");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 0F, "Rosemary Parmesan");

        menuItem.addChildMenuItem("Baja Chicken","with bacon, mild cheddar cheese, onions, BBQ sauce, & chipotle mayo",1,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Chicken Carbonara","with bacon, mozzarella cheese, sautéed mushrooms & parmesan alfredo sauce",2,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Honey Bourbon Chicken","with seasonal greens, tomatoes, onions, honey bourbon mustard & zesty grille sauce",3,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Honey Mustard Chicken","with bacon, swiss cheese, lettuce, tomatoes, onions & honey mustard sauce",4,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Mesquite Chicken","with bacon, mild cheddar cheese, lettuce, tomatoes, onions & ranch dressing",5,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Pesto Caesar Chicken","with mozzarella, parmesan, romano & asiago cheese, seasonal greens, tomatoes, basil pesto & peppercorn caesar sauce",5,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Spicy Chicken","with mild cheddar, fresh Q-salasa & chipotle mayo",5,MenuItem.MenuItemTypes.MenuItem);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Classic Subs","",1,MenuItem.MenuItemTypes.MenuGroup, 0f, sectionMenuItem);
        groupOption = menuItem.addOption(MenuItemOption.MenuItemOptionTypes.Group, null, "Size");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 7.49F, SIZE_REGULAR);
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 9.49F, SIZE_LARGE);

        groupOption = menuItem.addOption(MenuItemOption.MenuItemOptionTypes.Group, null, "Bread");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 0F, "White");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 0F, "Wheat");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 0F, "Rosemary Parmesan");

        menuItem.addChildMenuItem("Classic Italian","Pepperoni, salami, capicola, ham, mozzarella, lettuce, black olives, tomatoes, onions & red wine vinaigrette dressing",1,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Ham & Swiss","with lettuce, tomatoes, red onins & mayo",2,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Honey Bacon Club","with turkey breast, ham, bacon, Swiss, lettuce, tomatoes, onions & honey-french dressing",3,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Italian Meatball","with double mozzarella, marinara sauce",4,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Oven Roast Turkey","with lettuce, tomatoes, red onins & mayo",5,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Spicy Monterey","with turkey breast, ham, mozzarella, pickles, lettuce, tomatoes, mayo & 4 pepper chili sauce",5,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("The Traditional","with roast beef, turkey breast, ham, cheddar, lettuce, black olives, tomatoes, onions & ranch dressing",6,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Tuna","with cheddar, lettuce, tomatoes & mayo",7,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Turkey Bacon Guacamole","with mozzarella, lettuce, tomatoes, onions & ranch dressing ",8,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Turkey Lite","with lettuce, tomatoes, onions, cucumbers & fat-free balsamic vinaigrette dressing",9,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Turkey Ranch & Swiss","with lettuce, tomatoes, onions",10,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Ultimate Turkey Club", "with bacon, cheddar, lettuce, tomatoes & mayo",11,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Veggie Guacamole","with cheddar, mozzarella, lettuce, black olives, tomatoes, onions, mushrooms & red wine vinaigrette dressing",12,MenuItem.MenuItemTypes.MenuItem);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        sectionMenuItem = new MenuItem("Other Tasty Items","",1,MenuItem.MenuItemTypes.MenuSection, 0f, null);
        menu.getMenuItems().add(sectionMenuItem);

        menuItem = new MenuItem("Toasty Pastas","",1,MenuItem.MenuItemTypes.MenuGroup, 6.00f, sectionMenuItem);
        menuItem.addChildMenuItem("Mac & Cheese","with romano, parmesan, provolone & fontina cheeses, breadcrumbs. $6.49",1,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Mac & Cheese with Bacon","with romano, parmesan, provolone & fontina cheeses, breadcrumbs. $6.99",2,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Meatball Marinara","with mozzarella, 3-cheese blend. ",3,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Chicken Pesto","with 3-cheese blend, balsamic tomatoes. ",4,MenuItem.MenuItemTypes.MenuItem);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Salads & Wraps","",1,MenuItem.MenuItemTypes.MenuGroup, 6.00f, sectionMenuItem);
        groupOption = menuItem.addOption(MenuItemOption.MenuItemOptionTypes.Group, null, "Size");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 4.99f, SIZE_SMALL);
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 7.49f, SIZE_LARGE);

        groupOption = menuItem.addOption(MenuItemOption.MenuItemOptionTypes.Group, null, "Salad Type");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 0f, "Salad");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 0f, "Wrap");

        menuItem.addChildMenuItem("Black & Blue Steak ","with Black Angus steak, blue cheese crumbles, onions, tomatoes, fat-free balsamic vinaigrette dressing",1,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Caesar Chicken","with 3-cheese blend, tomatoes, peppercorn caesar dressing",2,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Cobb","with chicken, bacon, hard-boiled egg, blue cheese crumbles, tomatoes, ranch dressing",3,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Harvest Chicken","with Honey-dijon chicken salad, apples, grapes, dried cranberries, cucumbers, tomatoes, pumpkin seeds, acai vinaigrette dressing",4,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Honey Mustard Chicken","with bacon, cheddar, tomatoes, honey mustard dressing\n",5,MenuItem.MenuItemTypes.MenuItem);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Grilled Flatbreads","",1,MenuItem.MenuItemTypes.MenuGroup, 6.00f, sectionMenuItem);
        menuItem.addChildMenuItem("Basil Pesto Chicken","with mozzarella, seasonal greens, tomatoes, basil pesto",1,MenuItem.MenuItemTypes.MenuItem, 5.49f);
        menuItem.addChildMenuItem("Chicken Bacon Ranch","with Swiss, seasonal greens, tomatoes",2,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Greek Chicken","with mozzarella, cucumbers, black olives, banana peppers, seasonal greens, tomatoes, Tzatziki dressing",3,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Honey Bourbon Chicken","with seasonal greens, tomatoes, onions, honey bourbon mustard & zesty grille sauce",4,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Sonoma Turkey","with cheddar, seasonal greens, tomatoes, chipotle mayo",5,MenuItem.MenuItemTypes.MenuItem);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section

        menuItem = new MenuItem("Soups of the Day","",1,MenuItem.MenuItemTypes.MenuGroup, 6.00f, sectionMenuItem);
        groupOption = menuItem.addOption(MenuItemOption.MenuItemOptionTypes.Group, null, "Size");
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 3.99F, SIZE_REGULAR_SOUP);
        groupOption.addChild(MenuItemOption.MenuItemOptionTypes.Item, 4.99F, SIZE_LARGE_SOUP);
        menuItem.addChildMenuItem("Brocolli Cheese","",1,MenuItem.MenuItemTypes.MenuItem, 5.49f);
        menuItem.addChildMenuItem("Chicken Noodle","",2,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Chile","",3,MenuItem.MenuItemTypes.MenuItem);
        menuItem.addChildMenuItem("Chicken Tortilla","",4,MenuItem.MenuItemTypes.MenuItem);
        sectionMenuItem.getChildMenuItems().add(menuItem);//add group to section



        menuService.saveMenu(menu);

        assertTrue("Menu " + MENU1 + " not found! ", menuService.getMenuByName(menuName).get(0) != null);

        return menu;


    }

}
