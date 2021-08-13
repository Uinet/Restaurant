package com.github.uinet.project.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderDishes> orderDishes = new ArrayList<>();

    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Transient
    public Double getTotalPrice() {
        return orderDishes.stream().mapToDouble(OrderDishes::getTotalPrice).sum();
    }
}
