package com.github.uinet.project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="dishes")
@Setter
@Getter
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private double price;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private DishesCategory category;

    @Column(nullable = false)
    private String description;
}
