package com.omo.domain;

import com.omo.repository.ResellerRepository;
import com.omo.repository.ScheduleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Configurable
public class InitializeAllData {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(InitializeAllData.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ScheduleRepository scheduleRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ResellerRepository resellerRepository;

    @Test
    public void initializeAllTest() {
        logger.info("Initializing data...");
        logger.info("   resellers...");
        //reseller -----------------------------------------------------------------------------------------------------
        String resellerName = "El Cafecito";
        Reseller reseller = resellerRepository.findOneByName(resellerName);
        if (reseller != null)
            resellerRepository.delete(reseller);

        reseller = new Reseller();
        reseller.setAddress("1400 Treat Blvd, Walnut Creek, CA");
        reseller.setName(resellerName);
        resellerRepository.save(reseller);

        //restaurants --------------------------------------------------------------------------------------------------
        logger.info("   restaurants...");
        RestaurantIntegrationTest restaurantIntegrationTest = new RestaurantIntegrationTest();
        restaurantIntegrationTest.deleteAllRestaurants();
        Restaurant restaurantBentolinos     = restaurantIntegrationTest.addRestaurant("Bentolinos", "old fashioned family operated catering business and deli");
        Restaurant restaurantElMolino       = restaurantIntegrationTest.addRestaurant("El Molino", "Mexcan Food Catering, Food To Go, and Tortilla Factory.");
        Restaurant restaurantASweetAffair   = restaurantIntegrationTest.addRestaurant("A Sweet Affair","Bakery, Sandwiches, Salads, Soups");
        Restaurant restaurantKinders        = restaurantIntegrationTest.addRestaurant("Kinders", "A tradition of great flavor.  A fresh taste for today.");
        Restaurant restaurantQuiznos        = restaurantIntegrationTest.addRestaurant("Quiznos", "mmmm...TOASTY!");


        //menus --------------------------------------------------------------------------------------------------------
        logger.info("   menus...");
        restaurantIntegrationTest.deleteAllMenus();
        Menu menuBentolinos = restaurantIntegrationTest.createMenuBentolinos();
        Menu menuElMolino = restaurantIntegrationTest.createMenuElMolino(restaurantElMolino);
        Menu menuKinders = restaurantIntegrationTest.createMenuKinders(restaurantKinders);
        Menu menuASweetAffair = restaurantIntegrationTest.createMenuSweetAffair(restaurantASweetAffair);
        Menu menuQuiznos = restaurantIntegrationTest.createMenuQuiznos(restaurantQuiznos);


        //schedule -----------------------------------------------------------------------------------------------------
        logger.info("   schedules...");
        restaurantIntegrationTest.deleteAllSchedules();

        //Days of week start from 1 which is Sunday, 0 is all days
        Schedule schedule = new Schedule();
        //Sunday
/*
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantBentolinos);
        schedule.setDayOfWeek(1);
        schedule.setMenu(menuBentolinos);
        scheduleRepository.save(schedule);
*/

        //Monday
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantBentolinos);
        schedule.setDayOfWeek(2);
        schedule.setMenu(menuBentolinos);
        scheduleRepository.save(schedule);

        //Tuesday
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantBentolinos);
        schedule.setDayOfWeek(3);
        schedule.setMenu(menuBentolinos);
        scheduleRepository.save(schedule);

        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantQuiznos);
        schedule.setDayOfWeek(3);
        schedule.setMenu(menuQuiznos);
        scheduleRepository.save(schedule);

        //Wednesday
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantElMolino);
        schedule.setDayOfWeek(4);
        schedule.setMenu(menuElMolino);
        scheduleRepository.save(schedule);

        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantASweetAffair);
        schedule.setDayOfWeek(4);
        schedule.setMenu(menuASweetAffair);
        scheduleRepository.save(schedule);

        //Thursday
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantASweetAffair);
        schedule.setDayOfWeek(5);
        schedule.setMenu(menuASweetAffair);
        scheduleRepository.save(schedule);

        //Friday
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantKinders);
        schedule.setDayOfWeek(6);
        schedule.setMenu(menuKinders);
        scheduleRepository.save(schedule);

        //Saturday
        //schedule = new Schedule();
        //schedule.setReseller(reseller);
        //schedule.setRestaurant(restaurantBentolinos);
        //schedule.setDayOfWeek(7);
        //schedule.setMenu(menuBentolinos);
        //scheduleRepository.save(schedule);

        //Sunday
//        schedule = new Schedule();
//        schedule.setReseller(reseller);
//        schedule.setRestaurant(restaurantQuiznos);
//        schedule.setDayOfWeek(7);
//        schedule.setMenu(menuQuiznos);
//        scheduleRepository.save(schedule);
    }

}
