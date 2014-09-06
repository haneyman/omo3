package com.omo.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
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

    public enum MenuItemTypes {MenuGroup, MenuItem, MenuSection}   // Arrays.asList(Menu.MenuItemTypes.values())
    public static final String MENUITEM_LABEL = "menuitem ";
    private String name;
    private String description;
    private String uuid;
//    @DBRef   get a system error when this is used, "cannot create a reference to an object with a null id"
//    private MenuItem parent;
    private String parentUuid;
    private Integer sortOrder;
    private MenuItemTypes type;
    private Float price;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<MenuItem> childMenuItems = new HashSet<MenuItem>();
    @OneToMany(cascade = CascadeType.ALL)
    private Set<MenuItemOption> options = new HashSet<MenuItemOption>();
    private String internalNotes;//a hack way of storing parent descriptive text

    //legacy constructor for 1 price only
    public MenuItem(String inName, String inDescription, Integer inSortOrder, MenuItemTypes inType, Float inPrice, MenuItem inParent) {
        name = inName;
        description = inDescription;
        sortOrder = inSortOrder;
        type = inType;
        uuid = UUID.randomUUID().toString();
        if (inParent != null)
            parentUuid = inParent.getUuid();
        this.price = inPrice;
        //addOption(inPrice, "");
    }

    public MenuItem(String inName, String inDescription, Integer inSortOrder, MenuItemTypes inType /*, MenuItem parent*/) {
        this.name = inName;
        this.description = inDescription;
        this.sortOrder = inSortOrder;
        this.type = inType;
        this.uuid = UUID.randomUUID().toString();
    }

    //legacy method, for adding a single price
    public MenuItem addChildMenuItem(String name, String desc, Integer sortOrder, MenuItemTypes menuItemType, Float price) {
        MenuItem child = new MenuItem(name, desc, sortOrder, menuItemType, price, this);
        child.setParentUuid(this.uuid);
        this.getChildMenuItems().add(child);
        return child;
    }

    public MenuItem addChildMenuItem(String name, String desc, Integer sortOrder, MenuItemTypes menuItemType) {
        MenuItem child = new MenuItem(name, desc, sortOrder, menuItemType/*, this*/);
        child.setParentUuid(this.uuid);
        this.getChildMenuItems().add(child);
        return child;
    }

    public MenuItemOption addOption(MenuItemOption.MenuItemOptionTypes type, Float price, String description) {
        MenuItemOption option = new MenuItemOption(type, price, description);
        if (options == null) {
            options = new HashSet<MenuItemOption>();
        }
        options.add(option);
        return option;
    }

    public MenuItem() {
    }

}
