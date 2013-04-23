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
        menu.getMenuItems().add(new MenuItem("Sandwiches","",1,MenuItem.MenuItemTypes.MenuGroup, 6.00f));
        menu.getMenuItems().add(new MenuItem("BLT","",2,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Tuna Salad","",3,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Corn Beef","",4,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Turkey","",5,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Oen Roasted Turkey","",6,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Roast Beef","",7,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Pastrami","",8,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Meatloaf","",9,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Ham","",10,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Chicken Salad","",11,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Crab Salad","",12,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Salami","",13,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Egg Salad","",14,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Vegetarian","",15,MenuItem.MenuItemTypes.MenuItem, 6.00f));

        menu.getMenuItems().add(new MenuItem("Cheese","",20,MenuItem.MenuItemTypes.MenuGroup, .80f));
        menu.getMenuItems().add(new MenuItem("Provolone","",21,MenuItem.MenuItemTypes.MenuItem, .80f));
        menu.getMenuItems().add(new MenuItem("Cheddar","",22,MenuItem.MenuItemTypes.MenuItem, .80f));
        menu.getMenuItems().add(new MenuItem("Swiss","",23,MenuItem.MenuItemTypes.MenuItem, .80f));
        menu.getMenuItems().add(new MenuItem("American","",24,MenuItem.MenuItemTypes.MenuItem, .80f));
        menu.getMenuItems().add(new MenuItem("Jack","",25,MenuItem.MenuItemTypes.MenuItem, .80f));

        menu.getMenuItems().add(new MenuItem("Hot Sandwiches","",30,MenuItem.MenuItemTypes.MenuGroup, 7.95f));
        menu.getMenuItems().add(new MenuItem("Chicken Parmesan W/Provolone","",31,MenuItem.MenuItemTypes.MenuItem, 7.95f));
        menu.getMenuItems().add(new MenuItem("Chicken Pesto W/Jack","",32,MenuItem.MenuItemTypes.MenuItem, 7.95f));
        menu.getMenuItems().add(new MenuItem("French Dip W/Au Jus","",33,MenuItem.MenuItemTypes.MenuItem, 7.95f));

        menu.getMenuItems().add(new MenuItem("Sandwich Extras","",40,MenuItem.MenuItemTypes.MenuGroup, 0.00f));
        menu.getMenuItems().add(new MenuItem("Cranberry","",41,MenuItem.MenuItemTypes.MenuItem, 0.75f));
        menu.getMenuItems().add(new MenuItem("Avocado","",42,MenuItem.MenuItemTypes.MenuItem, 1.00f));
        menu.getMenuItems().add(new MenuItem("Bacon","",43,MenuItem.MenuItemTypes.MenuItem, 0.30f));

        menu.getMenuItems().add(new MenuItem("Combos","",50,MenuItem.MenuItemTypes.MenuGroup, 7.25f));
        menu.getMenuItems().add(new MenuItem("1/2 Sand + Soup","",51,MenuItem.MenuItemTypes.MenuItem, 7.25f));
        menu.getMenuItems().add(new MenuItem("1/2 Sand + Salad","",52,MenuItem.MenuItemTypes.MenuItem, 7.25f));
        menu.getMenuItems().add(new MenuItem("Soup + Salad","",53,MenuItem.MenuItemTypes.MenuItem, 7.25f));

        menu.getMenuItems().add(new MenuItem("Bread","",60,MenuItem.MenuItemTypes.MenuGroup, 0.00f));
        menu.getMenuItems().add(new MenuItem("Sour French Bread","",61,MenuItem.MenuItemTypes.MenuItem, 0.00f));
        menu.getMenuItems().add(new MenuItem("Dutch Crunch Roll","",62,MenuItem.MenuItemTypes.MenuItem, 0.00f));
        menu.getMenuItems().add(new MenuItem("Wheat Roll","",63,MenuItem.MenuItemTypes.MenuItem, 0.00f));
        menu.getMenuItems().add(new MenuItem("Sliced Sourdough","",64,MenuItem.MenuItemTypes.MenuItem, 0.00f));
        menu.getMenuItems().add(new MenuItem("Honey White","",65,MenuItem.MenuItemTypes.MenuItem, 0.00f));
        menu.getMenuItems().add(new MenuItem("Dark Rye","",66,MenuItem.MenuItemTypes.MenuItem, 0.00f));
        menu.getMenuItems().add(new MenuItem("Soft Roll","",67,MenuItem.MenuItemTypes.MenuItem, 0.00f));
        menu.getMenuItems().add(new MenuItem("Multi Grain","",68,MenuItem.MenuItemTypes.MenuItem, 0.00f));
        menu.getMenuItems().add(new MenuItem("Croissants","",69,MenuItem.MenuItemTypes.MenuItem, 1.00f));
        menu.getMenuItems().add(new MenuItem("Focaccia Rolls","",70,MenuItem.MenuItemTypes.MenuItem, 1.00f));

        menu.getMenuItems().add(new MenuItem("Soup","",80,MenuItem.MenuItemTypes.MenuGroup, 0.00f));
        menu.getMenuItems().add(new MenuItem("12 Oz","",81,MenuItem.MenuItemTypes.MenuItem, 3.95f));
        menu.getMenuItems().add(new MenuItem("16 Oz","",82,MenuItem.MenuItemTypes.MenuItem, 5.00f));

        menu.getMenuItems().add(new MenuItem("Monday","",90,MenuItem.MenuItemTypes.MenuGroup, 0.00f));
        menu.getMenuItems().add(new MenuItem("Cream Potato","",91,MenuItem.MenuItemTypes.MenuItem, 0.00f));
        menu.getMenuItems().add(new MenuItem("TuesDay","",92,MenuItem.MenuItemTypes.MenuGroup, 0.00f));
        menu.getMenuItems().add(new MenuItem("Minestrone","",93,MenuItem.MenuItemTypes.MenuItem, 0.00f));
        
        menu.getMenuItems().add(new MenuItem("Hot Food","",100,MenuItem.MenuItemTypes.MenuGroup, 5.50f));
        menu.getMenuItems().add(new MenuItem("Beef Lasagna","",101,MenuItem.MenuItemTypes.MenuItem, 5.50f));
        
        menu.getMenuItems().add(new MenuItem("Deli Salads","",110,MenuItem.MenuItemTypes.MenuGroup, 3.50f));
        menu.getMenuItems().add(new MenuItem("Macaroni","",111,MenuItem.MenuItemTypes.MenuGroup, 3.50f));
        menu.getMenuItems().add(new MenuItem("Fresh Fruit","",112,MenuItem.MenuItemTypes.MenuItem, 3.50f));
        menu.getMenuItems().add(new MenuItem("Cucumber Tomato","",113,MenuItem.MenuItemTypes.MenuItem, 3.50f));
        menu.getMenuItems().add(new MenuItem("Red Potato","",114,MenuItem.MenuItemTypes.MenuItem, 3.50f));
        menu.getMenuItems().add(new MenuItem("Mixed Beans","",115,MenuItem.MenuItemTypes.MenuItem, 3.50f));
        menu.getMenuItems().add(new MenuItem("Cold Tuna with Pasta Shells","",116,MenuItem.MenuItemTypes.MenuItem, 3.50f));
        menu.getMenuItems().add(new MenuItem("Cajun Bean","",117,MenuItem.MenuItemTypes.MenuItem, 3.50f));
        menu.getMenuItems().add(new MenuItem("Pretzel Jell-O","",118,MenuItem.MenuItemTypes.MenuItem, 3.50f));
        menu.getMenuItems().add(new MenuItem("Chicken Salad","",119,MenuItem.MenuItemTypes.MenuItem, 3.85f));
        menu.getMenuItems().add(new MenuItem("Tuna Salad","",120,MenuItem.MenuItemTypes.MenuItem, 3.85f));
        
        menu.getMenuItems().add(new MenuItem("Green Salads","",130,MenuItem.MenuItemTypes.MenuGroup, 0.00f));
        menu.getMenuItems().add(new MenuItem("Dressing on the side","",131,MenuItem.MenuItemTypes.MenuItem, 0.00f));
        menu.getMenuItems().add(new MenuItem("Caesar Salad","",132,MenuItem.MenuItemTypes.MenuItem, 5.50f));
        menu.getMenuItems().add(new MenuItem("Garden Salad","",133,MenuItem.MenuItemTypes.MenuItem, 5.50f));
        menu.getMenuItems().add(new MenuItem("Chicken Caesar Salad","",134,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Garden Salad w/Chicken","",135,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Pasta primavera w/chicken","",136,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Taco Salad","",137,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Oriental Salad","",138,MenuItem.MenuItemTypes.MenuItem, 6.00f));
        menu.getMenuItems().add(new MenuItem("Caesar","",139,MenuItem.MenuItemTypes.MenuItem, 0.50f));
        menu.getMenuItems().add(new MenuItem("Ranch","",140,MenuItem.MenuItemTypes.MenuItem, 0.50f));
        menu.getMenuItems().add(new MenuItem("Oriental","",141,MenuItem.MenuItemTypes.MenuItem, 0.50f));
        menu.getMenuItems().add(new MenuItem("Italian","",142,MenuItem.MenuItemTypes.MenuItem, 0.50f));
        menu.getMenuItems().add(new MenuItem("Balsamic Vinegar","",143,MenuItem.MenuItemTypes.MenuItem, 0.50f));


        




        //menu.setRestaurant("");
        menuService.saveMenu(menu);

        assertTrue("Menu " + MENU1 + " not found! ", menuService.getMenuByName(MENU1).get(0) != null);


    }



}
