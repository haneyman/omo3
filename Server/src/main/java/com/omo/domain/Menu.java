package com.omo.domain;

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

    @OneToOne
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<MenuItem> menuItems = new HashSet<MenuItem>();

    private String blurb;
    private String address;
    private ORDER_CONTACT_TYPES orderContactType;
    private String orderContact;
}
