package com.github.uinet.project.services;

import com.github.uinet.project.domain.Dish;
import com.github.uinet.project.domain.DishesCategory;
import com.github.uinet.project.repository.DishRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private  DishService dishService;

    @Mock
    private Page<Dish> dishPage;

    @Mock
    private Pageable pageable;

    private Dish dish;

    @Before
    public void init(){
        dish = Dish.builder()
                .id(1l)
                .category(DishesCategory.DRINKS)
                .name("TestDish")
                .price(100)
                .build();
    }

    @Test
    public void findDishById() {
        when(dishRepository.findById(dish.getId())).thenReturn(Optional.of(dish));

        assertEquals(dish, dishService.findDishById(dish.getId()).orElse(null));
    }

    @Test
    public void findAllByCategory() {
        when(dishRepository.findAllByCategory(pageable, DishesCategory.DRINKS)).thenReturn(dishPage);
        assertEquals(dishPage, dishService.findAllByCategory(pageable, DishesCategory.DRINKS));
    }

    @Test
    public void findAllDish() {
        when(dishRepository.findAll(pageable)).thenReturn(dishPage);
        assertEquals(dishPage, dishService.findAllDish(pageable));
    }
}