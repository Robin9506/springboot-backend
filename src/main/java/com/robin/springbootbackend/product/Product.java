package com.robin.springbootbackend.product;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class Product {
    @Id
    @SequenceGenerator(
            name= "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )

    private UUID product_id;
    private String product_name;
    private double price;
    private String description;

    public Product() {
    }

    public Product(String name, double price, String description) {
        this.product_name = name;
        this.price = price;
        this.description = description;
    }

    public UUID getId() {
        return product_id;
    }

    public void setId(UUID id) {
        this.product_id = id;
    }

    public String getName() {
        return product_name;
    }

    public void setName(String name) {
        this.product_name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + product_id +
                ", name='" + product_name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
