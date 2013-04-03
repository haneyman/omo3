package com.omo.repository;

import com.omo.domain.Menu;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Menu.class)
public interface MenuRepository  extends PagingAndSortingRepository<Menu, BigInteger> {

    List<com.omo.domain.Menu> findAll();
    List<com.omo.domain.Menu> findByName(String menuName);
}
