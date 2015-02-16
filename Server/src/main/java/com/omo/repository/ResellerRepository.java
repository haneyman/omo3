package com.omo.repository;

import com.omo.domain.Reseller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResellerRepository extends PagingAndSortingRepository<Reseller, BigInteger> {

    List<com.omo.domain.Reseller> findAll();
    Reseller findOneByName(String name);
}
