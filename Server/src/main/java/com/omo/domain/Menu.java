package com.omo.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

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
    private Set<MenuItem> menuItems = new HashSet<MenuItem>();

    @DBRef
    private Restaurant restaurant;

    private String blurb;
    private String address;
    private ORDER_CONTACT_TYPES orderContactType;
    private String orderContact;

//public int () { return menuItems.size()};

}
