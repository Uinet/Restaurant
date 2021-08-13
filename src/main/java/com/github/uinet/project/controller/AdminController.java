package com.github.uinet.project.controller;

import com.github.uinet.project.domain.OrderStatus;
import com.github.uinet.project.domain.Orders;
import com.github.uinet.project.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

        Page<Orders> ordersPage = ordersService.findPaginated(PageRequest.of(page.orElse(0),
                pageSize.orElse(5),
                Sort.by(Sort.Direction.DESC, "id")));

        model.addAttribute("orderPages", ordersPage)
                .addAttribute("pageNumbers", IntStream.range(0,ordersPage.getTotalPages())
                        .boxed()
                        .collect(Collectors.toList()))
                .addAttribute("currentPage", page.orElse(0));

        return "admin/orders";
    }

    @PostMapping("/orders/cook")
    public String cookDishes(@RequestParam("orderId") Optional<Long> orderId){
        orderId.ifPresent(id-> ordersService.changeStatus(id, OrderStatus.COOKED));
        return "redirect:/admin/orders";
    }

    @PostMapping("/orders/deliver")
    public String deliverOrder(@RequestParam("orderId") Optional<Long> orderId){
        orderId.ifPresent(id-> ordersService.changeStatus(id, OrderStatus.DELIVERED));
        return "redirect:/admin/orders";
    }
    @PostMapping("/orders/complete")
    public String completeOrder(@RequestParam("orderId") Optional<Long> orderId){
        orderId.ifPresent(id-> ordersService.changeStatus(id, OrderStatus.CLOSED));
        return "redirect:/admin/orders";
    }

    @PostMapping("/orders/cancel")
    public String cancelOrder(@RequestParam("orderId") Optional<Long> orderId){
        orderId.ifPresent(id-> ordersService.changeStatus(id, OrderStatus.CANCELED));
        return "redirect:/admin/orders";
    }
}
