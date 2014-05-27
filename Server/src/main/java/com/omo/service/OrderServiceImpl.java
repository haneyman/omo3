package com.omo.service;


import com.omo.domain.ApplicationUser;
import com.omo.domain.MenuItem;
import com.omo.domain.Order;
import com.omo.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    public List<Order> getOrdersByUser(ApplicationUser user) {
        //List<Order> orders = orderRepository.findAll();
        PageRequest request =
                new PageRequest(0, 100, org.springframework.data.domain.Sort.Direction.DESC, "orderDate");
        Page<Order> orders = orderRepository.findAll(request);

        //System.out.println("getOrdersByUser: " + user+ ", there are "+ orders.size() + " total orders.");
        List<Order> userOrders = new ArrayList<Order>();
        for (Order order : orders){
            //System.out.println("order user: " + user);
            if (order.getUser() != null && order.getUser().getId().equals(user.getId()))  {
                //System.out.println("order user: " + user.getId());
                userOrders.add(order);
            }
        }

        return userOrders;
    }

    public void notifyOrder(Order order) {
        StringBuffer body = new StringBuffer();
//        body.append("<html><body>");
        body.append("<h3>Menu<i>Breeze</i></h3><hr/>");
        body.append("<div style='color:#7aba7b'>Yummy, you've just ordered food from MenuBreeze!</div>");
        body.append("<br/>");
        body.append("<br/>");
        //body.append("<b>Who's got your food:" + order.);//TODO:Need reseller added to order
        body.append("<b>Order Date: </b>" + order.getOrderDate());
        body.append("<br/><b>Status: " + order.getStatus() + "</b>");
        body.append("<br/>");
        body.append("<b>Menu: </b>" + order.getMenu().getName());
//        body.append("<br/>" + order.getMenu().getRestaurant().getName());
        body.append("<div style='margin-left:20px;'>");
        for (MenuItem item : order.getMenuItems()) {
            //body.append("<div><b></b>" + item.internalNotes  + "</div>");
            body.append("      <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + item.getName() + "</div>");
        }
        body.append("   <div><b>Notes:</b>" + order.getNotes() + "</div>");
        body.append("   <div><b>Total:</b>" + order.getTotalPretax() + "</div>");
        body.append("</div>");
        body.append("");
        body.append("<br/>");
        body.append("<br/>");
        body.append("<div><i>Thanks for your business and enjoy your food!</div>");

        String from = "support@menubreeze.com";
        String to = order.getUser().getEmail();
        if (to != null && to.length() > 0) {
            try {
                EmailViaSES.sendEmail("MenuBreeze - Order Confirmed", body.toString(), from, to);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




/*

    private void getMimeMessage(final String recipient, final String from, final String message, final String subject)  {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {

                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(recipient));
                mimeMessage.setFrom(new InternetAddress(from));
                mimeMessage.setText(message);
            }
        };

        try {
            this.mailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
*/





}
