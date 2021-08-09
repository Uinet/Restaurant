package com.github.uinet.project.domain;

import lombok.Getter;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="orders")
@Getter
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

    @Column(name = "total")
    private double totalSum;
}
