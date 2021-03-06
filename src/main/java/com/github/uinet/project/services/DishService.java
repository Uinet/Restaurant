package com.github.uinet.project.services;

import com.github.uinet.project.domain.Dish;
import com.github.uinet.project.domain.DishesCategory;
import com.github.uinet.project.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DishService {

    @Autowired
    DishRepository dishRepository;

    public Optional<Dish> findDishById(Long id){
        return dishRepository.findById(id);
    }

    public Page<Dish> findAllByCategory( Pageable pageable, DishesCategory category){
        return dishRepository.findAllByCategory(pageable, category);
    }

    public Page<Dish> findAllDish(Pageable pageable){
        return dishRepository.findAll(pageable);
    }
}
