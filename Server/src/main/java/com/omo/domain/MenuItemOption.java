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
public class MenuItemOption {
    public enum MenuItemOptionTypes {Group, Item}   // Arrays.asList(Menu.MenuItemTypes.values())

    private String description;

    private Float price;

    private MenuItemOptionTypes type;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<MenuItemOption> children = new HashSet<MenuItemOption>();

    //************

    public MenuItemOption(MenuItemOptionTypes type, Float price, String description) {
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public void addChild(MenuItemOptionTypes type, Float price, String description) {
        if (children == null) {
            children = new HashSet<MenuItemOption>();
        }
        children.add(new MenuItemOption(type, price, description));
    }
}
