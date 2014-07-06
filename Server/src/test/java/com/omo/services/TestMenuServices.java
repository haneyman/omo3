package com.omo.services;

    import com.omo.domain.Menu;
    import com.omo.repository.MenuRepository;
    import com.omo.repository.ScheduleRepository;
    import com.omo.service.*;
    import org.junit.Before;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.test.context.ContextConfiguration;
    import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
    import org.springframework.test.context.web.WebAppConfiguration;
    import org.springframework.web.context.WebApplicationContext;

    import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class TestMenuServices {
    //private MockMvc mockMvc;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MenuService menuService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        //this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void checkMenuIsForToday() throws Exception {
        List<Menu> menus = menuRepository.findAll();

        for (Menu menu : menus) {
            Boolean result = menuService.isMenuOrderable(menu);
            System.out.println("Menu: " + menu.getName() + ":" + result);
        }
    }




}

