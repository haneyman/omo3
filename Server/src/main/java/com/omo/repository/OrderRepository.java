package com.omo.repository;

import com.omo.domain.ApplicationUser;
import com.omo.domain.Order;

import java.awt.print.Pageable;
import java.math.BigInteger;
import java.util.List;

import org.springframework.cglib.core.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, BigInteger> {

    List<Order>  findAll();
    List<Order>  findByUser(ApplicationUser user);
}
