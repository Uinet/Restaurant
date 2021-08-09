package com.github.uinet.project.repository;

import com.github.uinet.project.domain.OrderDishes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersDishRepository extends JpaRepository<OrderDishes, Long> {
}
