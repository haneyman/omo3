package com.omo.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@RooJavaBean
@RooToString
@RooMongoEntity
public class Menu {
    public enum ORDER_CONTACT_TYPES {EMAIL, PHONE};

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<MenuItem> menuItems = new HashSet<MenuItem>();

    @DBRef
    private Restaurant restaurant;

    private String blurb;
    private String address;
    private ORDER_CONTACT_TYPES orderContactType;
    private String orderContact;



}
