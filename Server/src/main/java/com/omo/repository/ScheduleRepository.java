package com.omo.repository;

import com.omo.domain.Schedule;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, BigInteger> {

    List<com.omo.domain.Schedule> findAll();
}
