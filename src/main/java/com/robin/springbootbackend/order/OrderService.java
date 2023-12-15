package com.robin.springbootbackend.order;

import com.robin.springbootbackend.product.Product;
import com.robin.springbootbackend.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;

    }

    public List<Order> getOrdersByUser(){
        return orderRepository.findAll();
    }
}
