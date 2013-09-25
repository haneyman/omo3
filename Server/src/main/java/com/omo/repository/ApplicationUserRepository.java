package com.omo.repository;

import com.omo.domain.ApplicationUser;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = ApplicationUser.class)
public interface ApplicationUserRepository {

    List<com.omo.domain.ApplicationUser> findAll();
}
