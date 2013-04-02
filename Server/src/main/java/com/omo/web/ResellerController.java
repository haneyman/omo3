package com.omo.web;

import com.omo.domain.Reseller;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/resellers")
@Controller
@RooWebScaffold(path = "resellers", formBackingObject = Reseller.class)
public class ResellerController {
}
