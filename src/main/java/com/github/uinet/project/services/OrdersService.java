package com.github.uinet.project.services;

import com.github.uinet.project.domain.Orders;
import com.github.uinet.project.domain.User;
import com.github.uinet.project.repository.OrdersDishRepository;
import com.github.uinet.project.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrdersDishRepository ordersDishRepository;

    public List<Orders> findOrdersByUser(User user){
        return ordersRepository.findByUserId(user.getId());
    }

    public Page<Orders> findPaginated(Pageable pageable) {
        return ordersRepository.findAll(pageable);
    }

    @Transactional
    public void save(Orders orders){
        ordersDishRepository.saveAll(ordersRepository.save(orders).getOrderDishes()
                .stream()
                .peek(orderDishes -> orderDishes.setOrder(ordersRepository.save(orders)))
                .collect(Collectors.toList()));
    }
}
