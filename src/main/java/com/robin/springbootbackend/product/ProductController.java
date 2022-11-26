package com.robin.springbootbackend.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    @GetMapping
    public List<Product> hello(){
        return List.of(
                new Product("Mario Kart", 49.99, "Dit is Mario Kart")
        );
    }
}
