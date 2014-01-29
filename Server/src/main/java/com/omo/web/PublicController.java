package com.omo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.omo.domain.Reseller;
import com.omo.repository.ResellerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;
import java.util.List;

@RequestMapping("/public/**")
@Controller
public class PublicController {

    private static org.apache.log4j.Logger logger = Logger.getLogger(PublicController.class);
    @Autowired
    ResellerRepository resellerRepository;

    @RequestMapping(value = "start", produces = "text/html")
    public String start(Model uiModel) {
        logger.debug("PublicController start...");

        List<Reseller> resellers = resellerRepository.findAll();
        logger.debug("   reseller count:" + resellers.size());
        uiModel.addAttribute("resellers", resellers);


        return "public/start";
    }
}
