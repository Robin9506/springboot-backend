package com.robin.springbootbackend.product;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    public List<Product> getProducts(){
        return List.of(
                new Product("Mario Kart", 49.99, "Dit is Mario Kart"),
                new Product("Mario Party", 39.99, "Dit is Mario Party")
        );
    }
}
