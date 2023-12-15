package com.robin.springbootbackend.order;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;
}
