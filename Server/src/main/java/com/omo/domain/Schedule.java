package com.omo.domain;

import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigInteger;

@Persistent
public class Schedule {
    String[] namesOfDays =  {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

    private Integer dayOfWeek;

    @DBRef
    @ManyToOne
    private Reseller reseller;

    @DBRef
    @ManyToOne
    private Restaurant restaurant;

    @DBRef
    @ManyToOne
    private Menu menu;
    @Id
    private BigInteger id;

    public String getDayOfWeekName() {
        return namesOfDays[dayOfWeek - 1];
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Reseller getReseller() {
        return this.reseller;
    }

    public String[] getNamesOfDays() {
        return this.namesOfDays;
    }

    public void setNamesOfDays(String[] namesOfDays) {
        this.namesOfDays = namesOfDays;
    }

    public void setReseller(Reseller reseller) {
        this.reseller = reseller;
    }

    public Integer getDayOfWeek() {
        return this.dayOfWeek;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
