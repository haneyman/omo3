package com.omo.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@RooJavaBean
@RooToString
@RooMongoEntity
public class OrderItem {

    private Integer quantity;

    private String description;

    private MenuItem group;

    private MenuItem section;

    private MenuItem menuItem;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<MenuItemOption> options = new HashSet<MenuItemOption>();

}
