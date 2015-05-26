package com.omo.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.math.BigInteger;
import java.util.*;

@Persistent
public class Menu {
    @Id
    private BigInteger id;

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getDescription() {
        return this.description;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public ORDER_CONTACT_TYPES getOrderContactType() {
        return this.orderContactType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MenuItem> getMenuItems() {
        return this.menuItems;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderContact() {
        return this.orderContact;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getBlurb() {
        return this.blurb;
    }

    public void setOrderContactType(ORDER_CONTACT_TYPES orderContactType) {
        this.orderContactType = orderContactType;
    }

    public void setOrderContact(String orderContact) {
        this.orderContact = orderContact;
    }

    public enum ORDER_CONTACT_TYPES {EMAIL, PHONE};

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    //don't not work - @OrderBy("sortOrder ASC")
    private Set<MenuItem> menuItems = new HashSet<MenuItem>();

    @DBRef
    private Restaurant restaurant;

    private String blurb;
    private String address;
    private ORDER_CONTACT_TYPES orderContactType;
    private String orderContact;

//public int () { return menuItems.size()};


    class MenuItemComparator implements Comparator<MenuItem> {
        public int compare(MenuItem mi1, MenuItem mi2) {
            return mi1.getSortOrder().compareTo(mi2.getSortOrder());
        }
    }

    public void sortMenuItems() {
        setMenuItems(sortMenuItems(getMenuItems()));
        sortMenuItemChildren(getMenuItems());
    }

    //recursively sorts given menu item list's children
    private void sortMenuItemChildren(Set<MenuItem> menuItems) {
        for (MenuItem menuItem: getMenuItems()) {
            if (menuItem.getChildMenuItems() != null) {
                menuItem.setChildMenuItems(sortMenuItems(menuItem.getChildMenuItems()));
            }
        }
    }

    private Set<MenuItem> sortMenuItems(Set<MenuItem> items) {
        List<MenuItem> miList = new LinkedList<MenuItem>(items);
        Collections.sort(miList, new MenuItemComparator());
        Set<MenuItem> sortedItems = new LinkedHashSet<MenuItem>(miList);  //convert back to set - not sure necessary but what the hell
        return sortedItems;
    }

}
