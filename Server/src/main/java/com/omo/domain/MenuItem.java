package com.omo.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Persistent
public class MenuItem {

    @Id
    private BigInteger id;

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public void setType(MenuItemTypes type) {
        this.type = type;
    }

    public Set<MenuItemOption> getOptions() {
        return this.options;
    }

    public void setOptions(Set<MenuItemOption> options) {
        this.options = options;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Set<MenuItem> getChildMenuItems() {
        return this.childMenuItems;
    }

    public String getParentUuid() {
        return this.parentUuid;
    }

    public void setChildMenuItems(Set<MenuItem> childMenuItems) {
        this.childMenuItems = childMenuItems;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public String getInternalNotes() {
        return this.internalNotes;
    }

    public void setInternalNotes(String internalNotes) {
        this.internalNotes = internalNotes;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public MenuItemTypes getType() {
        return this.type;
    }

    public BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

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
