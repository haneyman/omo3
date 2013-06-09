package com.omo.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
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
    private String parentUuid;
    private Integer sortOrder;
    private MenuItemTypes type;
    private float price;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<MenuItem> childMenuItems = new HashSet<MenuItem>();
    private String internalNotes;//a hack way of storing parent descriptive text

    public MenuItem(String inName, String inDescription, Integer inSortOrder, MenuItemTypes inType, Float inPrice, String parent) {
        name = inName;
        description = inDescription;
        sortOrder = inSortOrder;
        type = inType;
        price = inPrice;
        uuid = UUID.randomUUID().toString();
        parentUuid = parent;


    }

    public MenuItem addChildMenuItem(String name, String desc, Integer sortOrder, MenuItemTypes menuItemType, Float price) {
        MenuItem child = new MenuItem(name, desc, sortOrder, menuItemType, price, this.getUuid());
        this.getChildMenuItems().add(child);
        return child;
    }

    public MenuItem() {
    }

}
