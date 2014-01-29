package com.omo.service;


import com.omo.domain.ApplicationUser;
import com.omo.domain.Order;
import com.omo.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    public List<Order> getOrdersByUser(ApplicationUser user) {
        List<Order> orders = orderRepository.findAll();
        System.out.println("getOrdersByUser: " + user+ ", there are "+ orders.size() + " total orders.");
        List<Order> userOrders = new ArrayList<Order>();
        for (Order order : orders){
            //System.out.println("order user: " + user);
            if (order.getUser() != null && order.getUser().getId().equals(user.getId()))  {
                System.out.println("order user: " + user.getId());
                userOrders.add(order);
            }
        }

        return userOrders;
    }
}
