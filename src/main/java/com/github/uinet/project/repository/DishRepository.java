package com.github.uinet.project.repository;

import com.github.uinet.project.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish,Long> {
    Optional<Dish> findById(Long id);
}