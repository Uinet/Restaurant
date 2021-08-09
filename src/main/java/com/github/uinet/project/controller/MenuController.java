package com.github.uinet.project.controller;

import com.github.uinet.project.domain.*;
import com.github.uinet.project.services.DishService;
import com.github.uinet.project.services.OrdersService;
import com.github.uinet.project.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class MenuController {

    @Autowired
    DishService dishService;

    @Autowired
    OrdersService ordersService;

    @GetMapping("/menu")
    public String menuPage(@RequestParam(value = "category", required = false) DishesCategory category,
                           Model model,
                           HttpServletRequest httpServletRequest){
        if(category != null){
            model.addAttribute("dishes", dishService.findAllByCategory(category));
        }
        else {
            model.addAttribute("dishes", dishService.findAllDish());
        }

        model.addAttribute("order", Utils.getOrderFromSession(httpServletRequest));
        return "menu";
    }

    @PostMapping("/menu/addToCart")
    public String menuPage(@RequestParam(value = "dishId", required = false) long dishId,
                           HttpServletRequest httpServletRequest){
        Optional<Dish> dish = dishService.findDishById(dishId);
        if(!dish.isPresent()){
            return "redirect:/403";
        }

        Orders orders = Utils.getOrderFromSession(httpServletRequest);
        OrderDishes orderDishes = new OrderDishes();
        orderDishes.setOrder(orders);
        orderDishes.setDish(dish.get());
        orderDishes.setQuantities(1);
        orders.getOrderDishes().add(orderDishes);
        return "redirect:/menu";
    }

    @PostMapping("/menu/buy")
    public String buy(HttpServletRequest httpServletRequest, Authentication authUser){
        Orders orders = Utils.getOrderFromSession(httpServletRequest);
        orders.setUser((User) authUser.getPrincipal());
        orders.setStatus(OrderStatus.NEW);
        if(orders.getOrderDishes().size() != 0){
            ordersService.save(orders);
        }
        Utils.removeOrderFromSession(httpServletRequest);
        return "redirect:/menu";
    }
}
