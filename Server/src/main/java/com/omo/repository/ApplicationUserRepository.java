package com.omo.repository;

import com.omo.domain.ApplicationUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, BigInteger>{

    List<com.omo.domain.ApplicationUser> findAll();
    ApplicationUser findOneByEmail(String email);
}
