package com.omo.service;

import com.omo.domain.Menu;
import org.springframework.roo.addon.layers.service.RooService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RooService(domainTypes = { com.omo.domain.Menu.class })
public interface MenuService {

    public String getMenuAsHTML(BigInteger menuId) throws Exception;
    public List<Menu> getMenuByName(String menuName);
    public Menu findTodaysMenu(String resellerName, String restaurantName) throws Exception;
}
