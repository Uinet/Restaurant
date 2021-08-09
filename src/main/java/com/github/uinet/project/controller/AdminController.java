package com.github.uinet.project.controller;

import com.github.uinet.project.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    OrdersService ordersService;

    @GetMapping("/orders")
    public String getOrderPage(Model model){
        model.addAttribute("orders", ordersService.findAll());
        return "admin/orders";
    }
}
