package com.omo.repository;

import com.omo.domain.Menu;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Menu.class)
public interface MenuRepository {

    List<com.omo.domain.Menu> findAll();
}
