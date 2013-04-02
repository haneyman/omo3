package com.omo.repository;

import com.omo.domain.Schedule;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Schedule.class)
public interface ScheduleRepository {

    List<com.omo.domain.Schedule> findAll();
}
