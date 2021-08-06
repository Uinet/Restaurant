package com.github.uinet.project.repository;

import com.github.uinet.project.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish,Long> {
    List<Dish> findByUserId(Long userId);
}
