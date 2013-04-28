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
public class MenuItem {
    public enum MenuItemTypes {MenuGroup, MenuItem, MenuSection, MenuBreak;}   // Arrays.asList(Menu.MenuItemTypes.values())

    private String name;

    private String description;
    private Integer sortOrder;
    private MenuItemTypes type;
    private float price;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<MenuItem> childMenuItems = new HashSet<MenuItem>();


    public MenuItem(String inName, String inDescription, Integer inSortOrder, MenuItemTypes inType, Float inPrice) {
        name = inName;
        description = inDescription;
        sortOrder = inSortOrder;
        type = inType;
        price = inPrice;
    }

    public MenuItem() {
    }
}
