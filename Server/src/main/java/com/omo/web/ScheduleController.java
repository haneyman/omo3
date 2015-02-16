package com.omo.web;

import com.omo.domain.Schedule;
import com.omo.repository.ResellerRepository;
import com.omo.repository.RestaurantRepository;
import com.omo.repository.ScheduleRepository;
import com.omo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

@RequestMapping("/schedules")
@Controller
public class ScheduleController {
    @Autowired
    MenuService menuService;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ResellerRepository resellerRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Schedule());
        return "schedules/create";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Schedule schedule = scheduleRepository.findOne(id);
        scheduleRepository.delete(schedule);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/schedules";
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Schedule schedule, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, schedule);
            return "schedules/create";
        }
        uiModel.asMap().clear();
        scheduleRepository.save(schedule);
        return "redirect:/schedules/" + encodeUrlPathSegment(schedule.getId().toString(), httpServletRequest);
    }

    void populateEditForm(Model uiModel, Schedule schedule) {
        uiModel.addAttribute("schedule", schedule);
        uiModel.addAttribute("menus", menuService.findAllMenus());
        uiModel.addAttribute("resellers", resellerRepository.findAll());
        uiModel.addAttribute("restaurants", restaurantRepository.findAll());
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, scheduleRepository.findOne(id));
        return "schedules/update";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        uiModel.addAttribute("schedule", scheduleRepository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "schedules/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("schedules", scheduleRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) scheduleRepository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("schedules", scheduleRepository.findAll());
        }
        return "schedules/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Schedule schedule, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, schedule);
            return "schedules/update";
        }
        uiModel.asMap().clear();
        scheduleRepository.save(schedule);
        return "redirect:/schedules/" + encodeUrlPathSegment(schedule.getId().toString(), httpServletRequest);
    }
}
