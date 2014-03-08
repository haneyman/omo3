package com.omo.service;


import com.omo.domain.ApplicationUser;
import com.omo.domain.Order;
import com.omo.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    public List<Order> getOrdersByUser(ApplicationUser user) {
        //List<Order> orders = orderRepository.findAll();
        PageRequest request =
                new PageRequest(0, 100, org.springframework.data.domain.Sort.Direction.DESC, "orderDate");
        Page<Order> orders = orderRepository.findAll(request);

        //System.out.println("getOrdersByUser: " + user+ ", there are "+ orders.size() + " total orders.");
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
