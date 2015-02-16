package com.omo.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Persistent
public class MenuItemOption {
    @Id
    private BigInteger id;

    public String getUuid() {
        return this.uuid;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setChildren(Set<MenuItemOption> children) {
        this.children = children;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public MenuItemOptionTypes getType() {
        return this.type;
    }

    public void setType(MenuItemOptionTypes type) {
        this.type = type;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Set<MenuItemOption> getChildren() {
        return this.children;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public enum MenuItemOptionTypes {Group, Item}   // Arrays.asList(Menu.MenuItemTypes.values())

    private String description;

    private Float price;

    private MenuItemOptionTypes type;

    private String uuid;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<MenuItemOption> children = new HashSet<MenuItemOption>();


    //************

    public MenuItemOption(MenuItemOptionTypes type, Float price, String description) {
        this.description = description;
        this.price = price;
        this.type = type;
        this.uuid = UUID.randomUUID().toString();
    }

    public void addChild(MenuItemOptionTypes type, Float price, String description) {
        if (children == null) {
            children = new HashSet<MenuItemOption>();
        }
        children.add(new MenuItemOption(type, price, description));
    }
}
