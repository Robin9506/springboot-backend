package com.robin.springbootbackend.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProductConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            ProductRepository productRepository){
        return args -> {
                    Product marioKart = new Product("Mario Kart", 49.99, "Dit is Mario Kart");
                    Product marioParty = new Product("Mario Party", 39.99, "Dit is Mario Party");

            productRepository.saveAll(
                    List.of(marioKart, marioParty)
            );
        };
    }
}
