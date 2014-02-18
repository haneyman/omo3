package com.omo.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@RooJavaBean
@RooToString
@RooMongoEntity
public class ApplicationUser {

    private String email;

    private String password;

    private String nameFirst;

    private String nameLast;

    private String phoneNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateAdded;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateLastLogin;

    private Boolean isAdmin;
}
