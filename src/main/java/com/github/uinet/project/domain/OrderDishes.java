package com.github.uinet.project.domain;

import javax.persistence.*;

@Entity
@Table
public class OrderDishes {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Orders order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Dish dish;

    @Column
    private int quantities;

    @Transient
    public Dish getDish() {
        return dish;
    }

    @Transient
    public Double getTotalPrice() {
        return getDish().getPrice() * quantities;
    }
}
