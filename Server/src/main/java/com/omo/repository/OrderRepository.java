package com.omo.repository;

import com.omo.domain.ApplicationUser;
import com.omo.domain.Order;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.cglib.core.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Sort;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Order.class)
public interface OrderRepository {

    List<Order> findAll();

}
