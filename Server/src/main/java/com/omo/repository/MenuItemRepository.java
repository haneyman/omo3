package com.omo.repository;

import com.omo.domain.MenuItem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface MenuItemRepository  extends PagingAndSortingRepository<MenuItem, BigInteger> {

    List<MenuItem> findAll();
    List<MenuItem> findByUuid(String uuid);
}
