package com.github.uinet.project.repository;

import com.github.uinet.project.domain.Dish;
import com.github.uinet.project.domain.DishesCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish,Long> {
    Optional<Dish> findById(Long id);
    List<Dish> findAllByCategory(DishesCategory dishesCategory);
}
