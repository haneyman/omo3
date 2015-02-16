package com.omo.service;

import com.omo.domain.Menu;
import com.omo.domain.MenuItem;
import com.omo.domain.MenuItemOption;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface MenuService {

//    public String getMenuAsHTML(Menu menu) throws Exception;
    public List<Menu> getMenuByName(String menuName);
    public Menu findTodaysMenu(String resellerName, String restaurantName) throws Exception;
    public List<Menu> findTodaysMenusForReseller(String resellerName) throws Exception ;
    public Boolean isMenuOrderable(Menu menu) throws Exception ;
    public String whenAndWhereOffered(Menu menu) throws Exception ;
    public MenuItemOption findOptionInMenu(Menu menu, String optionUuid) ;
    public MenuItem getMenuItemByUuid(Menu menu, String menuItemUUID) ;
    public List<Menu> findAllMenus() ;
    public Menu findMenu(BigInteger id) ;
    public Menu updateMenu(Menu menu);
    public long countAllMenus();
    public void deleteMenu(Menu menu);
    public List<Menu> findMenuEntries(int firstResult, int maxResults);
    public void saveMenu(Menu menu);




    MenuItem getMenuItemWithOptions(BigInteger menuId, String itemUuid) throws Exception;
}
