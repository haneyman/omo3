// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.omo.domain;

import com.omo.domain.Reseller;
import com.omo.domain.Restaurant;
import com.omo.domain.Schedule;

privileged aspect Schedule_Roo_JavaBean {
    
    public String Schedule.getDayOfWeek() {
        return this.dayOfWeek;
    }
    
    public void Schedule.setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public Reseller Schedule.getReseller() {
        return this.reseller;
    }
    
    public void Schedule.setReseller(Reseller reseller) {
        this.reseller = reseller;
    }
    
    public Restaurant Schedule.getRestaurant() {
        return this.restaurant;
    }
    
    public void Schedule.setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
}
