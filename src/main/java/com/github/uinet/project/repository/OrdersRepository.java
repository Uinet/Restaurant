package com.github.uinet.project.repository;

import com.github.uinet.project.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
    Optional<Orders> findById(Long id);
}
