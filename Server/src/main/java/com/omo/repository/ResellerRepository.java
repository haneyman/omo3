package com.omo.repository;

import com.omo.domain.Reseller;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Reseller.class)
public interface ResellerRepository {

    List<com.omo.domain.Reseller> findAll();
}
