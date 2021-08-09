package com.github.uinet.project.services;

import com.github.uinet.project.domain.OrderDishes;
import com.github.uinet.project.domain.Orders;
import com.github.uinet.project.domain.User;
import com.github.uinet.project.repository.OrdersDishRepository;
import com.github.uinet.project.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrdersDishRepository ordersDishRepository;

    public List<Orders> findOrdersByUser(User user){
        return ordersRepository.findByUserId(user.getId());
    }

    public void save(Orders orders){
        ordersRepository.save(orders);
    }
}
