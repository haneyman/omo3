package com.omo.domain;

import javax.persistence.ManyToOne;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity
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

    public String getDayOfWeekName() {
        return namesOfDays[dayOfWeek - 1];
    }
}
