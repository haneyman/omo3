package com.omo.domain;

import javax.persistence.ManyToOne;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity
public class Schedule {

    private String dayOfWeek;

    @ManyToOne
    private Reseller reseller;

    @ManyToOne
    private Restaurant restaurant;
}
