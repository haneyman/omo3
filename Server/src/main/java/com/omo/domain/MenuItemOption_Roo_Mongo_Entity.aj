// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.omo.domain;

import com.omo.domain.MenuItemOption;
import java.math.BigInteger;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

privileged aspect MenuItemOption_Roo_Mongo_Entity {
    
    declare @type: MenuItemOption: @Persistent;
    
    @Id
    private BigInteger MenuItemOption.id;
    
    public BigInteger MenuItemOption.getId() {
        return this.id;
    }
    
    public void MenuItemOption.setId(BigInteger id) {
        this.id = id;
    }
    
}
