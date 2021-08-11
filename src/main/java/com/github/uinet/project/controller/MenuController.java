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
    public String addToCart(@RequestParam(value = "dishId") long dishId,
                           HttpServletRequest httpServletRequest){
        Optional<Dish> dish = dishService.findDishById(dishId);
        if(!dish.isPresent()){
            return "redirect:/403";
        }

        Orders orders = Utils.getOrderFromSession(httpServletRequest);
        Optional<OrderDishes> orderDish = orders.getOrderDishes().stream()
                .filter(orderDishes -> orderDishes.getDish().getId() == dishId)
                .findFirst();
        if(orderDish.isPresent()){
            orderDish.get().incrementQuantities();
        }else {
            orders.getOrderDishes().add(OrderDishes.builder()
                    .dish(dish.get())
                    .order(orders)
                    .quantities(1)
                    .build());
        }
        return "redirect:/menu";
    }

    @PostMapping("/menu/removeFromCart")
    public String removeFromCart(@RequestParam(value = "dishId") long dishId,
                                 HttpServletRequest httpServletRequest){
        Orders orders = Utils.getOrderFromSession(httpServletRequest);
        orders.getOrderDishes().removeIf(orderDishes -> orderDishes.getDish().getId() == dishId);
        return "redirect:/menu";
    }

    @PostMapping("/menu/increase")
    public String increaseDishQuantities(@RequestParam(value = "dishId") long dishId,
                                         HttpServletRequest httpServletRequest){
        Orders orders = Utils.getOrderFromSession(httpServletRequest);
        orders.getOrderDishes().stream()
                .filter(orderDishes -> orderDishes.getDish().getId() == dishId)
                .findFirst().ifPresent(OrderDishes::incrementQuantities);
        return "redirect:/menu";
    }

    @PostMapping("/menu/reduce")
    public String reduceDishQuantities(@RequestParam(value = "dishId") long dishId,
                                         HttpServletRequest httpServletRequest){
        Orders orders = Utils.getOrderFromSession(httpServletRequest);
        Optional<OrderDishes> orderDish = orders.getOrderDishes().stream()
                .filter(orderDishes -> orderDishes.getDish().getId() == dishId)
                .findFirst();

        if(orderDish.isPresent()){
            if(orderDish.get().getQuantities() == 1){
                removeFromCart(dishId, httpServletRequest);
            }
            else{
                orderDish.get().decrementQuantities();
            }
        }
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
