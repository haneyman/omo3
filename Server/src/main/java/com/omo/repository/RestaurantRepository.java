package com.omo.repository;

import com.omo.domain.Restaurant;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Restaurant.class)
public interface RestaurantRepository  extends PagingAndSortingRepository<Restaurant, BigInteger> {

    List<com.omo.domain.Restaurant> findAll();
    List<com.omo.domain.Restaurant> findByName(String name);
}
