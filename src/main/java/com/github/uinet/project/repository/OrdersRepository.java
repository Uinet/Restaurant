package com.github.uinet.project.repository;

import com.github.uinet.project.domain.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long>{
    Page<Orders> findAllByUserId(Long userId, Pageable pageable);
}
