package com.github.uinet.project.services;

import com.github.uinet.project.entity.Dish;
import com.github.uinet.project.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DishService {

    @Autowired
    DishRepository dishRepository;

    public Optional<Dish> findDishById(Long id){
        return dishRepository.findById(id);
    }
}
