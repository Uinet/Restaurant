package com.github.uinet.project.controller;

import com.github.uinet.project.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @Autowired
    DishService dishService;

    @GetMapping("/menu")
    public String menuPage(Model model){
        model.addAttribute("dishes", dishService.findAllDish());
        return "menu";
    }
}
