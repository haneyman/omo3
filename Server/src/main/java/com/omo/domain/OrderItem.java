package com.omo.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Persistent
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
    @Id
    private BigInteger id;

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

    public Float getTotal() {
        return this.total;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return this.note;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public void setSection(MenuItem section) {
        this.section = section;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public MenuItem getSection() {
        return this.section;
    }

    public MenuItem getMenuItem() {
        return this.menuItem;
    }

    public MenuItem getGroup() {
        return this.group;
    }

    public void setGroup(MenuItem group) {
        this.group = group;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Float getTotalOptions() {
        return this.totalOptions;
    }

    public void setTotalOptions(Float totalOptions) {
        this.totalOptions = totalOptions;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
