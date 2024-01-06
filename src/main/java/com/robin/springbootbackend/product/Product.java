package com.robin.springbootbackend.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID productId;

    private String productName;
    private double price;
    private String description;

    private String company;

    @Column(name = "imageLink")
    private String image;

    private int rating;

    private String platform;

    public Product() {
    }

    public Product(String name,
                   double price,
                   String description,
                   String company,
                   String image,
                   int rating,
                   String platform) {
        this.productName = name;
        this.price = price;
        this.description = description;
        this.company = company;
        this.image = image;
        this.rating = rating;
        this.platform = platform;
    }


    @JsonProperty(value = "id", index = 1)
    public UUID getId() {
        return productId;
    }

    @JsonProperty(value = "_id", index = 1)
    public void setId(UUID id) {
        this.productId = id;
    }

    @JsonProperty(value = "name", index = 2)
    public String getName() {
        return productName;
    }

    @JsonProperty(value = "_name")
    public void setName(String name) {
        this.productName = name;
    }

    @JsonProperty(value = "price")
    public double getPrice() {
        return price;
    }

    @JsonProperty(value = "_price")
    public void setPrice(double price) {
        this.price = price;
    }

    @JsonProperty(value = "description")
    public String getDescription() {
        return description;
    }

    @JsonProperty(value = "_description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty(value = "company")
    public String getCompany() {
        return company;
    }

    @JsonProperty(value = "_company")
    public void setCompany(String company) {
        this.company = company;
    }

    @JsonProperty(value = "imageLink")
    public String getImage() {
        return image;
    }

    @JsonProperty(value = "_imageLink")
    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty(value = "rating")
    public int getRating() {
        return rating;
    }

    @JsonProperty(value = "_rating")
    public void setRating(int rating) {
        this.rating = rating;
    }

    @JsonProperty(value = "platform")
    public String getPlatform() {
        return platform;
    }

    @JsonProperty(value = "_platform")
    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
