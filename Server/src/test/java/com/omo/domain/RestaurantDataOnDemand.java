package com.omo.domain;

import com.omo.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Component
@Configurable
public class RestaurantDataOnDemand {
    @Autowired
    RestaurantRepository restaurantRepository;
    private Random rnd = new SecureRandom();
    private List<Restaurant> data;

    public void init() {
        int from = 0;
        int to = 10;
        data = restaurantRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Restaurant' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<Restaurant>();
        for (int i = 0; i < 10; i++) {
            Restaurant obj = getNewTransientRestaurant(i);
            try {
                restaurantRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            data.add(obj);
        }
    }

    public Restaurant getNewTransientRestaurant(int index) {
        Restaurant obj = new Restaurant();
        setAddress(obj, index);
        setDescription(obj, index);
        setName(obj, index);
        return obj;
    }

    public void setAddress(Restaurant obj, int index) {
        String address = "address_" + index;
        obj.setAddress(address);
    }

    public void setDescription(Restaurant obj, int index) {
        String description = "description_" + index;
        obj.setDescription(description);
    }

    public Restaurant getRandomRestaurant() {
        init();
        Restaurant obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return restaurantRepository.findOne(id);
    }

    public void setName(Restaurant obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

    public Restaurant getSpecificRestaurant(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Restaurant obj = data.get(index);
        BigInteger id = obj.getId();
        return restaurantRepository.findOne(id);
    }

    public boolean modifyRestaurant(Restaurant obj) {
        return false;
    }
}
