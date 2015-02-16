package com.omo.domain;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;

@Persistent
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
    @Id
    private BigInteger id;


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

    public Menu getMenu() {
        return this.menu;
    }

    public ORDER_STATUS getStatus() {
        return this.status;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(ORDER_STATUS status) {
        this.status = status;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Float getTotalPretax() {
        return this.totalPretax;
    }

    public void setTotalPretax(Float totalPretax) {
        this.totalPretax = totalPretax;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ApplicationUser getUser() {
        return this.user;
    }

    public Set<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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
