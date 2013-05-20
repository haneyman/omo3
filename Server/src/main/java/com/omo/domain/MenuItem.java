package com.omo.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RooJavaBean
@RooToString
@RooMongoEntity
public class MenuItem {
    public enum MenuItemTypes {MenuGroup, MenuItem, MenuSection, MenuBreak;}   // Arrays.asList(Menu.MenuItemTypes.values())
    public static final String MENUITEM_LABEL = "menuitem_";
    private String name;

    private String description;
    private String uuid;
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
        uuid = UUID.randomUUID().toString();
    }

    public MenuItem() {
    }
}
