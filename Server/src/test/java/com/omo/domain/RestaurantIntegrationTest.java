package com.omo.domain;

import com.omo.repository.MenuItemRepository;
import com.omo.repository.RestaurantRepository;
import com.omo.service.MenuService;
import com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.junit.Test;
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

    private static final String TEST_RESTAURANT_1 = "Test Restaurant 1";
    private static final String TEST_RESTAURANT_2 = "Test Restaurant 2";
    private static final String TEST_RESTAURANT_3 = "Test Restaurant 3";
    private static final String MENU1 = "Menu for " + TEST_RESTAURANT_1;

    @Test
    public void testGetMenuHTML() {
        int expectedMinimumSize = 6;
        Menu menu1 = menuService.getMenuByName(MENU1).get(0);

        String html = null;
        html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n";
        html += getHeader();
        html += "<body data-spy=\"scroll\" data-target=\".bs-docs-sidebar\">";
        html += getBodyTop();
        try {
            html += menuService.getMenuAsHTML(menu1.getId());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        html += getBodyBottom();
        html += "</body>";
        html += "</html>";

        System.out.println(html);

        Writer writer = null;
        File outFile = new File("D:\\Projects\\omo3\\Server\\src\\main\\webapp\\testMenu.html");

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile)));
            writer.write(html);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {writer.close();} catch (Exception ex) {}
        }
        //System.out.println("testGetMenuHTML got: " + html.toString());
        //logger.debug("testGetMenuHTML got: " + html.toString());

        assertTrue("html for a menu should be " + expectedMinimumSize + " but is " + html.length(), html.length() > expectedMinimumSize);
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
    public void testAddMenuBentolinos() {
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
        menuItem.addChildMenuItem("Croissants","",69,MenuItem.MenuItemTypes.MenuItem, 1.00f);
        menuItem.addChildMenuItem("Focaccia Rolls","",70,MenuItem.MenuItemTypes.MenuItem, 1.00f);
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
        menuItem.getChildMenuItems().add(menuItem2);
        sectionMenuItem.getChildMenuItems().add(menuItem);








        //menu.setRestaurant("");
        menuService.saveMenu(menu);

        assertTrue("Menu " + MENU1 + " not found! ", menuService.getMenuByName(MENU1).get(0) != null);


    }

    public String getHeader() {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream("D:\\Projects\\omo3\\Server\\src\\main\\webapp\\testHeader.html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = null;
            try {
                bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
    /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        }
        finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public String getBodyTop() {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream("D:\\Projects\\omo3\\Server\\src\\main\\webapp\\testBodyTop.html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = null;
            try {
                bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
    /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        }
        finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public String getBodyBottom() {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream("D:\\Projects\\omo3\\Server\\src\\main\\webapp\\testBodyBottom.html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = null;
            try {
                bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
    /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        }
        finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
