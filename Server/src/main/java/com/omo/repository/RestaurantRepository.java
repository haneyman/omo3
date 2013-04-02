package com.omo.repository;

import com.omo.domain.Restaurant;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Restaurant.class)
public interface RestaurantRepository {

    List<com.omo.domain.Restaurant> findAll();
}
