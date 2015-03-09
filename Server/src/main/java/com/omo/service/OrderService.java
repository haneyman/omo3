package com.omo.service;

import com.omo.domain.ApplicationUser;
import com.omo.domain.Order;

import java.math.BigInteger;
import java.util.List;

public interface OrderService {
    //public List<Order> getOrdersByUser(ApplicationUser user);
    List<Order> getOrdersByUser(ApplicationUser user) ;
    void notifyOrder(Order order) ;
    public long countAllOrders() ;

    public void deleteOrder(Order order) ;

    public Order findOrder(BigInteger id) ;

    public List<Order> findAllOrders() ;

    public List<Order> findOrderEntries(int firstResult, int maxResults) ;

    public void saveOrder(Order order);

    public Order updateOrder(Order order) ;

    public Order getTodaysOrderByUser(ApplicationUser user) ;


    public void deleteOrderItem(Order order, String orderItemId) ;
}
