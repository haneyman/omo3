// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.omo.domain;

import com.omo.domain.OrderItem;

privileged aspect OrderItem_Roo_JavaBean {
    
    public Integer OrderItem.getQuantity() {
        return this.quantity;
    }
    
    public void OrderItem.setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String OrderItem.getDescription() {
        return this.description;
    }
    
    public void OrderItem.setDescription(String description) {
        this.description = description;
    }
    
}
