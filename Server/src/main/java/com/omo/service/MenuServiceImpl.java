package com.omo.service;


import com.omo.domain.Menu;
import com.omo.domain.MenuItem;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;


public class MenuServiceImpl implements MenuService {
    private static final String INDENT = "   ";
    private static final String NEW_LINE = "\n";//"\n";
    private ArrayList<String> html;

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MenuServiceImpl.class);

    public String getMenuAsHTML(BigInteger menuId) throws Exception {
        logger.debug("getMenuAsHTML - loading html for menu " + menuId +  "...");
        html = new ArrayList<String>();
        html.add(NEW_LINE + "<!-- begin generated menu html -->" + NEW_LINE);
        //addHeader();
        //addToHTML("<body>");
//        html.add("<LINK href=\"bootstrap/css/bootstrap.css\" rel=\"stylesheet\" type=\"text/css\">" + NEW_LINE);
        html.add("<div class=\"container divMenu\">" + NEW_LINE);
//        html.add("  <div class=\"row-fluid\">" + NEW_LINE);
//        html.add("    <div class=\"span18\">" + NEW_LINE);
        Menu menu = findMenu(menuId);
//        html.add("      <ul class=\"listMenu menulist\" id='menu_" + menuId + "'>" + NEW_LINE);
        loadMenuItems(menu.getMenuItems(), 2);
//        html.add("      </ul> <!-- end of listMenu -->" + NEW_LINE);
//        html.add("    </div>" + NEW_LINE);
//        html.add("  </div>" + NEW_LINE);
        html.add("</div>" + NEW_LINE);
        html.add("<!-- end generated menu html -->" + NEW_LINE);
        logger.debug("Loading html for menu complete. Lines:" + html.size());
        return htmlAsString();
    }
    
    public class menuItemComparator implements Comparator<MenuItem> {
        @Override
        public int compare(MenuItem o1, MenuItem o2) {
            return (o1.getSortOrder()>o2.getSortOrder() ? -1 : (o1.getSortOrder()==o2.getSortOrder() ? 0 : 1));
        }
    }

    private void loadMenuItems(Set<MenuItem> menuItems, int level) throws Exception {
        List<MenuItem> menuItemsList = new ArrayList(menuItems);
        Collections.sort(menuItemsList, new Comparator<MenuItem>() {
            public int compare(MenuItem mi1, MenuItem mi2) {
                return (mi1.getSortOrder() > mi2.getSortOrder() ? 1 : (mi1.getSortOrder()==mi2.getSortOrder() ? 0 : -1));
            }
        });

        for (MenuItem menuItem: menuItems) {
            if (menuItem.getType().equals(MenuItem.MenuItemTypes.MenuGroup)) {
                loadMenuGroup(menuItem, level);
            } else if (menuItem.getType().equals(MenuItem.MenuItemTypes.MenuItem)) {
                loadMenuItem(menuItem, level);
            } else
                throw new Exception("Invalid Menu Item Type: \"" + menuItem.getType() + "\"");
        }
    }
        //logger.debug("   loadMenuItem...");

    private void loadMenuGroup(MenuItem menuItem, int level) throws Exception {
        level++;
//        addToHTML("<ul class=\" menuGroup\" id='menuGroup_" + menuItem.getName() + "'>", level);
        addToHTML("<div class=\"row\">",level);
        addToHTML(INDENT + "<div class=\"span8\">",level);
        addToHTML(INDENT + INDENT + "<h4 class=\"menuGroupTitle\">" + menuItem.getName().replaceAll(" ", "_").replaceAll("/","_") + "</h4>", level);
        addToHTML(INDENT + INDENT + "<p>additional menuGroup text</p>",level);
        addToHTML(INDENT + INDENT + "<div class=\"row\">",level);
        loadMenuItems(menuItem.getChildMenuItems(), level+1);
        addToHTML(INDENT + INDENT + "</div>",level);
        addToHTML(INDENT + "</div>",level);
        addToHTML("</div>",level);
//        addToHTML("</ul>",level);
    }

    private void loadMenuItem(MenuItem menuItem, int level) {
        level++;
        boolean checked = false;
        DecimalFormat myFormatter = new DecimalFormat("###.00");
        String priceOutput = myFormatter.format(menuItem.getPrice());
        String name =  menuItem.getName().replaceAll(" ", "_").replaceAll("/","_");
/*
        addToHTML("<li class=\"nodeLevel" + level + " menuListItem"  + "\"><input type=\"checkbox\" "  + checked
                + " name=\"menuitem_" + menuItem.getName().replaceAll(" ", "_").replaceAll("/","_") + "\" value=\"" + menuItem.getName() + "\">"
                + "<label>" + menuItem.getName() + " </label>"
                + "<span class=\"spanMenuItemPrice\">$" + priceOutput + "</span></li>", level);
*/
        addToHTML(INDENT + "<label>",level);
        addToHTML(INDENT + "    <div class=\"divCheckbox\">",level);
        addToHTML(INDENT + "        <input type=\"checkbox\" "  + checked + " name=\"menuitem_" + name + "\" value=\"" + name + "\">", level);
        addToHTML(INDENT + "    </div>",level);
        addToHTML(INDENT + "    <div class=\"divNamePrice\">",level);
        addToHTML(INDENT + "        <div class=\"menuItemName\">" + menuItem.getName() + " </div>",level);
        addToHTML(INDENT + "        <div class=\"menuItemPrice\">$" + priceOutput + "</div>",level);
        addToHTML(INDENT + "    </div>", level);
        addToHTML(INDENT + "</label>",level);
    }


    private void addToHTML(String htmlLine, int level) {
        String totalIndention = "";
        if (level > 0)
            totalIndention = String.format(String.format("%%0%dd", level), 0).replace("0", INDENT);//adds level+1 number of indents to total indents
        html.add(totalIndention + htmlLine + NEW_LINE);
    }

    private String htmlAsString() {
        StringBuilder sb = new StringBuilder();
        for (String line: html) {
            sb.append(line);
        }
        return sb.toString();
    }


    public List<Menu> getMenuByName(String menuName) {
        return menuRepository.findByName(menuName);
    }

    public void deleteMenu(Menu menu) {
        menuRepository.delete(menu);
    }

/*
    private static int countGrandchildren(MenuItem menuItem, HashMap<String, MenuItem> menuItems) {
        int count=0;
        MenuItem child;
        for (MenuItem item: menuItem.getChildMenuItems()) {
            child = employees.get(childId);
            count += child.getChildren().size();
        }
        return count;
    }
*/



}
