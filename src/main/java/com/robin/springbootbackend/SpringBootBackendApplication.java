package com.robin.springbootbackend;

import com.robin.springbootbackend.auth.RsaKeyProperties;
import com.robin.springbootbackend.product.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class SpringBootBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBackendApplication.class, args);
	}

}
