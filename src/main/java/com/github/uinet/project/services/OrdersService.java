package com.github.uinet.project.services;

import com.github.uinet.project.domain.OrderStatus;
import com.github.uinet.project.domain.Orders;
import com.github.uinet.project.domain.User;
import com.github.uinet.project.repository.OrdersDishRepository;
import com.github.uinet.project.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    public Page<Orders> findPaginatedByUser(User user, Pageable pageable){
        List<Orders> orders = ordersRepository.findByUserId(user.getId());
        int startItem = pageable.getPageNumber() * pageable.getPageSize();
        return new PageImpl<Orders>(orders.size() < startItem ? Collections.emptyList()
                : orders.subList(startItem, Math.min(startItem + pageable.getPageSize(), orders.size())),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),
                orders.size());
    }

    public Optional<Orders> findById(Long id){
        return ordersRepository.findById(id);
    }

    @Transactional
    public void changeStatus(Long id, OrderStatus status){
        Orders order = ordersRepository.getById(id);
        order.setStatus(status);
        ordersRepository.save(order);
    }

    @Transactional
    public void save(Orders orders){
        ordersDishRepository.saveAll(ordersRepository.save(orders).getOrderDishes()
                .stream()
                .peek(orderDishes -> orderDishes.setOrder(ordersRepository.save(orders)))
                .collect(Collectors.toList()));
    }
}
