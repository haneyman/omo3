package com.omo.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity
public class Order {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date orderDate;
    private ORDER_STATUS status;
    @DBRef
    private Menu menu;
    private Float totalPretax;
    private String notes;
    @DBRef
    private ApplicationUser user;
    @OneToMany(cascade = CascadeType.ALL)
    private java.util.Set<OrderItem> orderItems = new HashSet<OrderItem>();


/*  Moved under Menu
    @OneToMany(cascade = CascadeType.ALL)
    private java.util.Set<MenuItem> menuItems = new HashSet<MenuItem>();
*/

    public Order() {
        setOrderDate(new Date());
    }

    // update order and orderitems with totals
    //
    // for each orderitem find price in item or group or section, then add up totals for options
    public void calculateTotals() {
        Float orderTotal = 0f;
        for (OrderItem orderItem : this.orderItems) {
            if (orderItem.getMenuItem().getPrice() != null && orderItem.getMenuItem().getPrice() > 0) {
                orderItem.setPrice(orderItem.getMenuItem().getPrice());
            } else if (orderItem.getGroup().getPrice() != null && orderItem.getGroup().getPrice() > 0) {
                orderItem.setPrice(orderItem.getGroup().getPrice());
            } else if (orderItem.getSection().getPrice() != null && orderItem.getSection().getPrice() > 0) {
                orderItem.setPrice(orderItem.getSection().getPrice());
            } else
                orderItem.setPrice(0f);
            orderItem.setTotalOptions(getOptionTotal(orderItem.getMenuItem()));
            orderItem.setTotal(orderItem.getPrice() + orderItem.getTotalOptions() );
            orderTotal += orderItem.getTotal();
        }
        setTotalPretax(orderTotal);
    }

    public Float getOptionTotal(MenuItem menuItem) {
        Float total = 0f;
        for (MenuItemOption option : menuItem.getOptions()) {
            if (option.getType() == MenuItemOption.MenuItemOptionTypes.Group) {
                for (MenuItemOption optionChild : option.getChildren()) {
                    total += optionChild.getPrice();
                }
            }
        }
        return total;
    }

    public enum  ORDER_STATUS {INIT, OPEN, CANCELLED, CLOSED};

    public boolean isToday() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(new Date()).equals(fmt.format(orderDate));
    }

    public Order copy() {
        Order newOrder = new Order();
        newOrder.setOrderDate(new Date());
        newOrder.setStatus(ORDER_STATUS.OPEN);
        newOrder.setMenu(getMenu());
        newOrder.totalPretax = 0f;
        newOrder.setNotes(notes);
        newOrder.setUser(user);
        //newOrder.getMenuItems().addAll(getMenuItems());

        return newOrder;
    }

}
