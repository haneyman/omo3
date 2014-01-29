package com.omo.repository;

import com.omo.domain.ApplicationUser;
import com.omo.domain.Order;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Order.class)
public interface OrderRepository {

    List<Order> findAll();
    List<Order> findOrdersByUser(ApplicationUser user);

}
