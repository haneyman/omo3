package com.omo.service;

import org.springframework.roo.addon.layers.service.RooService;

import java.math.BigInteger;
import java.util.ArrayList;

@RooService(domainTypes = { com.omo.domain.Menu.class })
public interface MenuService {

    public ArrayList<String> getMenuAsHTML(BigInteger menuId) throws Exception;
}
