package com.github.uinet.project.services;

import com.github.uinet.project.domain.Dish;
import com.github.uinet.project.domain.DishesCategory;
import com.github.uinet.project.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class DishService {

    @Autowired
    DishRepository dishRepository;

    public Optional<Dish> findDishById(Long id){
        return dishRepository.findById(id);
    }

    public List<Dish> findAllDish(){
        return dishRepository.findAll();
    }

    public List<Dish> findAllByCategory(DishesCategory category){
        return dishRepository.findAllByCategory(category);
    }

    public List<Dish> findAllByOrderByNameAsc(){
        return dishRepository.findAllByOrderByNameAsc();
    }

    public List<Dish> findAllByOrderByNameDesc(){
        return dishRepository.findAllByOrderByNameDesc();
    }

    public List<Dish> findAllByOrderByCategoryAsc(){
        return dishRepository.findAllByOrderByCategoryAsc();
    }

    public List<Dish> findAllByOrderByPriceAsc(){
        return dishRepository.findAllByOrderByPriceAsc();
    }

    public List<Dish> findAllByOrderByCategoryDesc(){
        return dishRepository.findAllByOrderByCategoryDesc();
    }

    public List<Dish> findAllByOrderByPriceDesc(){
        return dishRepository.findAllByOrderByPriceDesc();
    }
}
