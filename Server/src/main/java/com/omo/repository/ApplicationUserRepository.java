package com.omo.repository;

import com.omo.domain.ApplicationUser;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

import java.util.List;

@RooMongoRepository(domainType = ApplicationUser.class)
public interface ApplicationUserRepository {

    List<com.omo.domain.ApplicationUser> findAll();
    ApplicationUser findOneByEmail(String email);
}
