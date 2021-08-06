package com.github.uinet.project.repository;

import com.github.uinet.project.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
    List<Orders> findByUserId(Long userId);
}
