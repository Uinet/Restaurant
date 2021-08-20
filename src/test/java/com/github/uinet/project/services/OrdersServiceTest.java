package com.github.uinet.project.services;

import com.github.uinet.project.domain.OrderDishes;
import com.github.uinet.project.domain.OrderStatus;
import com.github.uinet.project.domain.Orders;
import com.github.uinet.project.domain.User;
import com.github.uinet.project.repository.OrdersDishRepository;
import com.github.uinet.project.repository.OrdersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrdersServiceTest {

    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private  OrdersService ordersService;

    @Mock
    private Page<Orders> ordersPage;

    @Mock
    private Pageable pageable;

    @Mock
    private User user;

    @Mock
    private OrdersDishRepository ordersDishRepository;

    @Mock
    private OrderDishes orderDishes;

    private Orders order;

    @Before
    public void init() {
        order = Orders.builder()
                .id(1l)
                .creationDate(LocalDateTime.now())
                .orderDishes(Collections.singletonList(orderDishes))
                .status(OrderStatus.PAID)
                .build();
    }

    @Test
    public void findById() {
        when(ordersRepository.findById(order.getId())).thenReturn(Optional.of(order));
        assertEquals(order, ordersService.findById(order.getId()).orElse(null));
    }

    @Test
    public void changeStatus() {
        when(ordersRepository.getById(order.getId())).thenReturn(order);
        when(ordersRepository.save(order)).thenReturn(order);

        assertEquals(OrderStatus.CLOSED, ordersService.changeStatus(order.getId(), OrderStatus.CLOSED));
    }

    @Test
    public void save() {
        when(ordersRepository.save(order)).thenReturn(order);
        when(ordersDishRepository.saveAll(order.getOrderDishes())).thenReturn(order.getOrderDishes());
        Orders savedOrder = ordersService.save(order);

        assertEquals(order, savedOrder);
        assertArrayEquals(order.getOrderDishes().toArray(), savedOrder.getOrderDishes().toArray());
    }

    @Test
    public void findPaginated() {
        when(ordersRepository.findAll(pageable)).thenReturn(ordersPage);
        assertEquals(ordersPage, ordersService.findPaginated(pageable));
    }

    @Test
    public void findPaginatedByUser() {
        when(ordersRepository.findAllByUserId(user.getId(),pageable)).thenReturn(ordersPage);
        assertEquals(ordersPage, ordersService.findPaginatedByUser(user,pageable));
    }
}