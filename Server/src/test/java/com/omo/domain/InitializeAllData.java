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

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ScheduleRepository scheduleRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ResellerRepository resellerRepository;

    @Test
    public void initializeAllTest() {
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
        RestaurantIntegrationTest restaurantIntegrationTest = new RestaurantIntegrationTest();
        restaurantIntegrationTest.deleteAllRestaurants();
        Restaurant restaurantBentolinos     = restaurantIntegrationTest.addRestaurant("Bentolinos");
        Restaurant restaurantElMolino       = restaurantIntegrationTest.addRestaurant("El Molino");
        Restaurant restaurantASweetAffair   = restaurantIntegrationTest.addRestaurant("A Sweet Affair");
        Restaurant restaurantKinders        = restaurantIntegrationTest.addRestaurant("Kinders");


        //menus --------------------------------------------------------------------------------------------------------
        restaurantIntegrationTest.deleteAllMenus();
        Menu menuBentolinos = restaurantIntegrationTest.createMenuBentolinos();
        Menu menuElMolino = restaurantIntegrationTest.createMenuElMolino(restaurantElMolino);
        Menu menuKinders = restaurantIntegrationTest.createMenuKinders(restaurantKinders);


        //schedule -----------------------------------------------------------------------------------------------------
        restaurantIntegrationTest.deleteAllSchedules();

        //Days of week start from 1 which is Sunday, 0 is all days
        Schedule schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantBentolinos);
        schedule.setDayOfWeek(1);
        schedule.setMenu(menuBentolinos);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantKinders);
        schedule.setDayOfWeek(2);
        schedule.setMenu(menuKinders);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantBentolinos);
        schedule.setDayOfWeek(3);
        schedule.setMenu(menuBentolinos);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantBentolinos);
        schedule.setDayOfWeek(4);
        schedule.setMenu(menuBentolinos);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantBentolinos);
        schedule.setDayOfWeek(5);
        schedule.setMenu(menuBentolinos);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantBentolinos);
        schedule.setDayOfWeek(6);
        schedule.setMenu(menuBentolinos);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurantBentolinos);
        schedule.setDayOfWeek(7);
        schedule.setMenu(menuBentolinos);
        scheduleRepository.save(schedule);
    }

}
