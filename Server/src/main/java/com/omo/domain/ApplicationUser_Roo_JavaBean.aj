// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.omo.domain;

import com.omo.domain.ApplicationUser;
import java.util.Date;

privileged aspect ApplicationUser_Roo_JavaBean {
    
    public String ApplicationUser.getEmail() {
        return this.email;
    }
    
    public void ApplicationUser.setEmail(String email) {
        this.email = email;
    }
    
    public String ApplicationUser.getPassword() {
        return this.password;
    }
    
    public void ApplicationUser.setPassword(String password) {
        this.password = password;
    }
    
    public String ApplicationUser.getNameFirst() {
        return this.nameFirst;
    }
    
    public void ApplicationUser.setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }
    
    public String ApplicationUser.getNameLast() {
        return this.nameLast;
    }
    
    public void ApplicationUser.setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }
    
    public String ApplicationUser.getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public void ApplicationUser.setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public Date ApplicationUser.getDateAdded() {
        return this.dateAdded;
    }
    
    public void ApplicationUser.setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
    
    public Date ApplicationUser.getDateLastLogin() {
        return this.dateLastLogin;
    }
    
    public void ApplicationUser.setDateLastLogin(Date dateLastLogin) {
        this.dateLastLogin = dateLastLogin;
    }
    
}