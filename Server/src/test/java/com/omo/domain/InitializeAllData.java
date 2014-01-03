package com.omo.domain;

import com.omo.repository.ResellerRepository;
import com.omo.repository.ScheduleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
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
        RestaurantIntegrationTest restaurantIntegrationTest = new RestaurantIntegrationTest();
        //restaurant
        Restaurant restaurant = restaurantIntegrationTest.addRestaurant("Bentolinos");
        restaurantIntegrationTest.testAddMenuForBentolinos();

        //reseller
        String resellerName = "El Cafecito";
        Reseller reseller = resellerRepository.findOneByName(resellerName);
        if (reseller != null)
            resellerRepository.delete(reseller);

        reseller = new Reseller();
        reseller.setAddress("111 Main St.");
        reseller.setName(resellerName);
        resellerRepository.save(reseller);

        //menu
        Menu menu = restaurantIntegrationTest.createBentolinosMenu();

        //schedule
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule: schedules) {
            scheduleRepository.delete(schedule);
        }

        //Days of week start from 1 which is Sunday, 0 is all days
        Schedule schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurant);
        schedule.setDayOfWeek(1);
        schedule.setMenu(menu);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurant);
        schedule.setDayOfWeek(2);
        schedule.setMenu(menu);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurant);
        schedule.setDayOfWeek(3);
        schedule.setMenu(menu);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurant);
        schedule.setDayOfWeek(4);
        schedule.setMenu(menu);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurant);
        schedule.setDayOfWeek(5);
        schedule.setMenu(menu);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurant);
        schedule.setDayOfWeek(6);
        schedule.setMenu(menu);
        scheduleRepository.save(schedule);
        schedule = new Schedule();
        schedule.setReseller(reseller);
        schedule.setRestaurant(restaurant);
        schedule.setDayOfWeek(7);
        schedule.setMenu(menu);
        scheduleRepository.save(schedule);
    }

}
