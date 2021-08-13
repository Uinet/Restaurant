package com.github.uinet.project.controller;

import com.github.uinet.project.domain.OrderStatus;
import com.github.uinet.project.domain.Orders;
import com.github.uinet.project.domain.User;
import com.github.uinet.project.exception.UserException;
import com.github.uinet.project.services.OrdersService;
import com.github.uinet.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class UserController {

    @Autowired
    OrdersService ordersService;

    @Autowired
    UserService userService;

    @GetMapping("/myorders")
    public String getOrderPage(@RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> pageSize,
                               @RequestParam(value = "error", required = false) String error,
                               Authentication authUser,
                               Model model){

        Page<Orders> ordersPage = ordersService.findPaginatedByUser((User) authUser.getPrincipal(),
                PageRequest.of(page.orElse(0), pageSize.orElse(5)));

        model.addAttribute("orderPages", ordersPage);
        model.addAttribute("moneyError", error != null);
        model.addAttribute("pageNumbers", IntStream.range(0,ordersPage.getTotalPages())
                .boxed()
                .collect(Collectors.toList()));
        return "/myorders";
    }

    @PostMapping("/myorders/pay")
    public RedirectView payOrder(@RequestParam("orderId") Optional<Orders> order,
                                 Authentication authUser,
                                 RedirectAttributes redirectAttributes){

        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("../myorders");

        order.ifPresent(orders -> {
            try {
                userService.buyOrder(((User)authUser.getPrincipal()).getId(), orders);
            } catch (UserException e) {
                e.printStackTrace();
                redirectAttributes.addAttribute("error", "notEnoughMoney");
            }
        });

        return redirectView;
    }
}
