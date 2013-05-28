package com.omo.domain;

import java.util.Date;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity
public class Order {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date orderDate;
    private ORDER_STATUS status;
    @DBRef
    private Menu menu;

    @OneToMany(cascade = CascadeType.ALL)
    private java.util.Set<MenuItem> menuItems = new HashSet<MenuItem>();

    public Order() {
        setOrderDate(new Date());
    }

    public enum  ORDER_STATUS {INIT, OPEN, CANCELLED, CLOSED};

}
