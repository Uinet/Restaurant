package com.github.uinet.project.controller;

import com.github.uinet.project.domain.Orders;
import com.github.uinet.project.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    OrdersService ordersService;

    @GetMapping("/orders")
    public String getOrderPage(@RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> pageSize,
                               Model model){
        Page<Orders> ordersPage = ordersService.findPaginated(PageRequest.of(page.orElse(0), pageSize.orElse(5)));
        model.addAttribute("orderPages", ordersPage);
        model.addAttribute("pageNumbers", IntStream.range(0,ordersPage.getTotalPages())
                .boxed()
                .collect(Collectors.toList()));
        return "admin/orders";
    }
}
