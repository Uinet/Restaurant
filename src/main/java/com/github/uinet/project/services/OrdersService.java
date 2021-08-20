package com.github.uinet.project.services;

import com.github.uinet.project.domain.OrderStatus;
import com.github.uinet.project.domain.Orders;
import com.github.uinet.project.domain.User;
import com.github.uinet.project.repository.OrdersDishRepository;
import com.github.uinet.project.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrdersDishRepository ordersDishRepository;

    public Page<Orders> findPaginated(Pageable pageable) {
        return ordersRepository.findAll(pageable);
    }

    public Page<Orders> findPaginatedByUser(User user, Pageable pageable){
        return ordersRepository.findAllByUserId(user.getId(),pageable);
    }

    public Optional<Orders> findById(Long id){
        return ordersRepository.findById(id);
    }

    @Transactional
    public OrderStatus changeStatus(Long id, OrderStatus status){
        Orders order = ordersRepository.getById(id);
        order.setStatus(status);
        ordersRepository.save(order);
        return order.getStatus();
    }

    @Transactional
    public Orders save(Orders orders){
        Orders order = ordersRepository.save(orders);
        ordersDishRepository.saveAll(order.getOrderDishes()
                .stream()
                .peek(orderDishes -> orderDishes.setOrder(order))
                .collect(Collectors.toList()));
        return order;
    }
}
