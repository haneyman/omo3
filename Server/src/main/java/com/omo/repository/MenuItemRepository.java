package com.omo.repository;

import com.omo.domain.MenuItem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

import java.math.BigInteger;
import java.util.List;

@RooMongoRepository(domainType = MenuItem.class)
public interface MenuItemRepository  extends PagingAndSortingRepository<MenuItem, BigInteger> {

    List<MenuItem> findAll();
}
