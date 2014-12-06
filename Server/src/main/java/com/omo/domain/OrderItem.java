package com.omo.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
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

//    private String description;

    @DBRef
    private Menu menu;

    private Float total;
    private Float totalOptions;
    private Float price;

    private MenuItem group;
    private MenuItem section;
    private MenuItem menuItem;//actual Menu.menuItem with options attached

    private String note;

    //@OneToMany(cascade = CascadeType.ALL)
    //private Set<MenuItemOption> options = new HashSet<MenuItemOption>();


    public OrderItem(Integer quantity, Menu menu, MenuItem section, MenuItem group, MenuItem menuItem, String note) {
        this.quantity = quantity;
        this.section = section;
        this.group = group;
        this.menuItem = menuItem;
        this.note = note;
        this.menu = menu;
    }
/*

    public OrderItem(Integer quantity, MenuItem menuItem, String note) {
        this.quantity = quantity;
//        this.description = description;
        this.menuItem = menuItem;
//        this.group = group;
//        this.section = section;
        this.note = note;

    }
*/
}
