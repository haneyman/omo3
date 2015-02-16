package com.omo.web;

import com.omo.domain.*;
import com.omo.repository.*;
import com.omo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import java.math.BigInteger;

/**
 * A central place to register application converters and formatters. 
 */
@Configurable
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

    @Autowired
    MenuService menuService;
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    MenuItemRepository menuItemRepository;
    @Autowired
    ResellerRepository resellerRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

    public Converter<String, Menu> getStringToMenuConverter() {
        return new Converter<String, Menu>() {
            public Menu convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Menu.class);
            }
        };
    }

    public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getApplicationUserToStringConverter());
        registry.addConverter(getIdToApplicationUserConverter());
        registry.addConverter(getStringToApplicationUserConverter());
        registry.addConverter(getMenuToStringConverter());
        registry.addConverter(getIdToMenuConverter());
        registry.addConverter(getStringToMenuConverter());
        registry.addConverter(getMenuItemToStringConverter());
        registry.addConverter(getIdToMenuItemConverter());
        registry.addConverter(getStringToMenuItemConverter());
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

    public Converter<Schedule, String> getScheduleToStringConverter() {
        return new Converter<Schedule, String>() {
            public String convert(Schedule schedule) {
                return new StringBuilder().append(schedule.getDayOfWeek()).toString();
            }
        };
    }

    public Converter<BigInteger, Schedule> getIdToScheduleConverter() {
        return new Converter<BigInteger, Schedule>() {
            public Schedule convert(BigInteger id) {
                return scheduleRepository.findOne(id);
            }
        };
    }

    public Converter<String, Schedule> getStringToScheduleConverter() {
        return new Converter<String, Schedule>() {
            public Schedule convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Schedule.class);
            }
        };
    }

    public Converter<BigInteger, MenuItem> getIdToMenuItemConverter() {
        return new Converter<BigInteger, MenuItem>() {
            public MenuItem convert(BigInteger id) {
                return menuItemRepository.findOne(id);
            }
        };
    }

    public Converter<Reseller, String> getResellerToStringConverter() {
        return new Converter<Reseller, String>() {
            public String convert(Reseller reseller) {
                return new StringBuilder().append(reseller.getName()).append(' ').append(reseller.getAddress()).toString();
            }
        };
    }

    public Converter<Restaurant, String> getRestaurantToStringConverter() {
        return new Converter<Restaurant, String>() {
            public String convert(Restaurant restaurant) {
                return new StringBuilder().append(restaurant.getName()).append(' ').append(restaurant.getDescription()).append(' ').append(restaurant.getAddress()).toString();
            }
        };
    }

    public Converter<String, Restaurant> getStringToRestaurantConverter() {
        return new Converter<String, Restaurant>() {
            public Restaurant convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Restaurant.class);
            }
        };
    }

    public Converter<String, ApplicationUser> getStringToApplicationUserConverter() {
        return new Converter<String, ApplicationUser>() {
            public ApplicationUser convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), ApplicationUser.class);
            }
        };
    }

    public Converter<String, MenuItem> getStringToMenuItemConverter() {
        return new Converter<String, MenuItem>() {
            public MenuItem convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), MenuItem.class);
            }
        };
    }

    public Converter<String, Reseller> getStringToResellerConverter() {
        return new Converter<String, Reseller>() {
            public Reseller convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Reseller.class);
            }
        };
    }

    public Converter<OrderItem, String> getOrderItemToStringConverter() {
        return new Converter<OrderItem, String>() {
            public String convert(OrderItem orderItem) {
                return new StringBuilder().append(orderItem.getQuantity()).append(' ').append(orderItem.getTotal()).append(' ').append(orderItem.getTotalOptions()).append(' ').append(orderItem.getPrice()).toString();
            }
        };
    }

    public Converter<BigInteger, Reseller> getIdToResellerConverter() {
        return new Converter<BigInteger, Reseller>() {
            public Reseller convert(BigInteger id) {
                return resellerRepository.findOne(id);
            }
        };
    }

    public Converter<Menu, String> getMenuToStringConverter() {
        return new Converter<Menu, String>() {
            public String convert(Menu menu) {
                return new StringBuilder().append(menu.getName()).append(' ').append(menu.getDescription()).append(' ').append(menu.getBlurb()).append(' ').append(menu.getAddress()).toString();
            }
        };
    }

    public Converter<ApplicationUser, String> getApplicationUserToStringConverter() {
        return new Converter<ApplicationUser, String>() {
            public String convert(ApplicationUser applicationUser) {
                return new StringBuilder().append(applicationUser.getEmail()).append(' ').append(applicationUser.getPassword()).append(' ').append(applicationUser.getNameFirst()).append(' ').append(applicationUser.getNameLast()).toString();
            }
        };
    }

    public Converter<String, OrderItem> getStringToOrderItemConverter() {
        return new Converter<String, OrderItem>() {
            public OrderItem convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), OrderItem.class);
            }
        };
    }

    public Converter<MenuItem, String> getMenuItemToStringConverter() {
        return new Converter<MenuItem, String>() {
            public String convert(MenuItem menuItem) {
                return new StringBuilder().append(menuItem.getName()).append(' ').append(menuItem.getDescription()).append(' ').append(menuItem.getUuid()).append(' ').append(menuItem.getParentUuid()).toString();
            }
        };
    }

    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }

    public Converter<BigInteger, Restaurant> getIdToRestaurantConverter() {
        return new Converter<BigInteger, Restaurant>() {
            public Restaurant convert(BigInteger id) {
                return restaurantRepository.findOne(id);
            }
        };
    }

    public Converter<BigInteger, Menu> getIdToMenuConverter() {
        return new Converter<BigInteger, Menu>() {
            public Menu convert(BigInteger id) {
                return menuService.findMenu(id);
            }
        };
    }

    public Converter<BigInteger, ApplicationUser> getIdToApplicationUserConverter() {
        return new Converter<BigInteger, ApplicationUser>() {
            public ApplicationUser convert(BigInteger id) {
                return applicationUserRepository.findOne(id);
            }
        };
    }
}
