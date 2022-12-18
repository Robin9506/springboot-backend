package com.robin.springbootbackend.product;

import com.fasterxml.jackson.annotation.JsonProperty;
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


    private UUID productId;

    private String productName;
    private double price;
    private String description;

    private String company;

    private String imageLink;

    private int rating;

    private String platform;

    public Product() {
    }

    public Product(String name,
                   double price,
                   String description,
                   String company,
                   String imageLink,
                   int rating,
                   String platform) {
        this.productName = name;
        this.price = price;
        this.description = description;
        this.company = company;
        this.imageLink = imageLink;
        this.rating = rating;
        this.platform = platform;
    }


    @JsonProperty(value = "product_id", index = 1)
    public UUID getId() {
        return productId;
    }

    public void setId(UUID id) {
        this.productId = id;
    }

    @JsonProperty(value = "product_name", index = 2)
    public String getName() {
        return productName;
    }

    public void setName(String name) {
        this.productName = name;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
