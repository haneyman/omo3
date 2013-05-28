// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.omo.web;

import com.omo.domain.Menu;
import com.omo.domain.MenuItem;
import com.omo.domain.Order;
import com.omo.domain.OrderItem;
import com.omo.domain.Reseller;
import com.omo.domain.Restaurant;
import com.omo.domain.Schedule;
import com.omo.repository.MenuItemRepository;
import com.omo.repository.ResellerRepository;
import com.omo.repository.RestaurantRepository;
import com.omo.repository.ScheduleRepository;
import com.omo.service.MenuService;
import com.omo.service.OrderService;
import com.omo.web.ApplicationConversionServiceFactoryBean;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    @Autowired
    MenuService ApplicationConversionServiceFactoryBean.menuService;
    
    @Autowired
    MenuItemRepository ApplicationConversionServiceFactoryBean.menuItemRepository;
    
    @Autowired
    OrderService ApplicationConversionServiceFactoryBean.orderService;
    
    @Autowired
    ResellerRepository ApplicationConversionServiceFactoryBean.resellerRepository;
    
    @Autowired
    RestaurantRepository ApplicationConversionServiceFactoryBean.restaurantRepository;
    
    @Autowired
    ScheduleRepository ApplicationConversionServiceFactoryBean.scheduleRepository;
    
    public Converter<Menu, String> ApplicationConversionServiceFactoryBean.getMenuToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.omo.domain.Menu, java.lang.String>() {
            public String convert(Menu menu) {
                return new StringBuilder().append(menu.getName()).append(' ').append(menu.getDescription()).append(' ').append(menu.getBlurb()).append(' ').append(menu.getAddress()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Menu> ApplicationConversionServiceFactoryBean.getIdToMenuConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.omo.domain.Menu>() {
            public com.omo.domain.Menu convert(java.math.BigInteger id) {
                return menuService.findMenu(id);
            }
        };
    }
    
    public Converter<String, Menu> ApplicationConversionServiceFactoryBean.getStringToMenuConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.omo.domain.Menu>() {
            public com.omo.domain.Menu convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Menu.class);
            }
        };
    }
    
    public Converter<MenuItem, String> ApplicationConversionServiceFactoryBean.getMenuItemToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.omo.domain.MenuItem, java.lang.String>() {
            public String convert(MenuItem menuItem) {
                return new StringBuilder().append(menuItem.getName()).append(' ').append(menuItem.getDescription()).append(' ').append(menuItem.getUuid()).append(' ').append(menuItem.getSortOrder()).toString();
            }
        };
    }
    
    public Converter<BigInteger, MenuItem> ApplicationConversionServiceFactoryBean.getIdToMenuItemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.omo.domain.MenuItem>() {
            public com.omo.domain.MenuItem convert(java.math.BigInteger id) {
                return menuItemRepository.findOne(id);
            }
        };
    }
    
    public Converter<String, MenuItem> ApplicationConversionServiceFactoryBean.getStringToMenuItemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.omo.domain.MenuItem>() {
            public com.omo.domain.MenuItem convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), MenuItem.class);
            }
        };
    }
    
    public Converter<Order, String> ApplicationConversionServiceFactoryBean.getOrderToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.omo.domain.Order, java.lang.String>() {
            public String convert(Order order) {
                return new StringBuilder().append(order.getOrderDate()).append(' ').append(order.getStatus()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Order> ApplicationConversionServiceFactoryBean.getIdToOrderConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.omo.domain.Order>() {
            public com.omo.domain.Order convert(java.math.BigInteger id) {
                return orderService.findOrder(id);
            }
        };
    }
    
    public Converter<String, Order> ApplicationConversionServiceFactoryBean.getStringToOrderConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.omo.domain.Order>() {
            public com.omo.domain.Order convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Order.class);
            }
        };
    }
    
    public Converter<OrderItem, String> ApplicationConversionServiceFactoryBean.getOrderItemToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.omo.domain.OrderItem, java.lang.String>() {
            public String convert(OrderItem orderItem) {
                return new StringBuilder().append(orderItem.getQuantity()).append(' ').append(orderItem.getDescription()).toString();
            }
        };
    }
    
    public Converter<String, OrderItem> ApplicationConversionServiceFactoryBean.getStringToOrderItemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.omo.domain.OrderItem>() {
            public com.omo.domain.OrderItem convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), OrderItem.class);
            }
        };
    }
    
    public Converter<Reseller, String> ApplicationConversionServiceFactoryBean.getResellerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.omo.domain.Reseller, java.lang.String>() {
            public String convert(Reseller reseller) {
                return new StringBuilder().append(reseller.getName()).append(' ').append(reseller.getAddress()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Reseller> ApplicationConversionServiceFactoryBean.getIdToResellerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.omo.domain.Reseller>() {
            public com.omo.domain.Reseller convert(java.math.BigInteger id) {
                return resellerRepository.findOne(id);
            }
        };
    }
    
    public Converter<String, Reseller> ApplicationConversionServiceFactoryBean.getStringToResellerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.omo.domain.Reseller>() {
            public com.omo.domain.Reseller convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Reseller.class);
            }
        };
    }
    
    public Converter<Restaurant, String> ApplicationConversionServiceFactoryBean.getRestaurantToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.omo.domain.Restaurant, java.lang.String>() {
            public String convert(Restaurant restaurant) {
                return new StringBuilder().append(restaurant.getName()).append(' ').append(restaurant.getDescription()).append(' ').append(restaurant.getAddress()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Restaurant> ApplicationConversionServiceFactoryBean.getIdToRestaurantConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.omo.domain.Restaurant>() {
            public com.omo.domain.Restaurant convert(java.math.BigInteger id) {
                return restaurantRepository.findOne(id);
            }
        };
    }
    
    public Converter<String, Restaurant> ApplicationConversionServiceFactoryBean.getStringToRestaurantConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.omo.domain.Restaurant>() {
            public com.omo.domain.Restaurant convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Restaurant.class);
            }
        };
    }
    
    public Converter<Schedule, String> ApplicationConversionServiceFactoryBean.getScheduleToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.omo.domain.Schedule, java.lang.String>() {
            public String convert(Schedule schedule) {
                return new StringBuilder().append(schedule.getDayOfWeek()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Schedule> ApplicationConversionServiceFactoryBean.getIdToScheduleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.omo.domain.Schedule>() {
            public com.omo.domain.Schedule convert(java.math.BigInteger id) {
                return scheduleRepository.findOne(id);
            }
        };
    }
    
    public Converter<String, Schedule> ApplicationConversionServiceFactoryBean.getStringToScheduleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.omo.domain.Schedule>() {
            public com.omo.domain.Schedule convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Schedule.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getMenuToStringConverter());
        registry.addConverter(getIdToMenuConverter());
        registry.addConverter(getStringToMenuConverter());
        registry.addConverter(getMenuItemToStringConverter());
        registry.addConverter(getIdToMenuItemConverter());
        registry.addConverter(getStringToMenuItemConverter());
        registry.addConverter(getOrderToStringConverter());
        registry.addConverter(getIdToOrderConverter());
        registry.addConverter(getStringToOrderConverter());
        registry.addConverter(getOrderItemToStringConverter());
        registry.addConverter(getStringToOrderItemConverter());
        registry.addConverter(getResellerToStringConverter());
        registry.addConverter(getIdToResellerConverter());
        registry.addConverter(getStringToResellerConverter());
        registry.addConverter(getRestaurantToStringConverter());
        registry.addConverter(getIdToRestaurantConverter());
        registry.addConverter(getStringToRestaurantConverter());
        registry.addConverter(getScheduleToStringConverter());
        registry.addConverter(getIdToScheduleConverter());
        registry.addConverter(getStringToScheduleConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
