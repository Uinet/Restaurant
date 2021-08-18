package com.github.uinet.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="dishes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column
    private String img;
}
