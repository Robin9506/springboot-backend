package com.robin.springbootbackend;

import com.robin.springbootbackend.product.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class SpringBootBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBackendApplication.class, args);
	}

	@GetMapping("/")
	public List<Product> hello(){
		return List.of(
				new Product("Mario Kart", 49.99, "Dit is Mario Kart")
		);
	}

}
