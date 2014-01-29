package com.omo.service;

import com.omo.domain.ApplicationUser;
import com.omo.domain.Order;
import org.springframework.roo.addon.layers.service.RooService;

import java.util.List;

@RooService(domainTypes = { com.omo.domain.Order.class })
public interface OrderService {
    //public List<Order> getOrdersByUser(ApplicationUser user);
    List<Order> getOrdersByUser(ApplicationUser user) ;

}
