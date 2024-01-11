package com.robin.springbootbackend.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.robin.springbootbackend.product.Product;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_account")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "account_id")
    private UUID accountId;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public Order() {
    }
    public Order(UUID accountId){
        this.accountId = accountId;
    }

    @JsonProperty("id")
    public UUID getOrderId() {
        return orderId;
    }

    @JsonProperty("id")
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    @JsonIgnore
    public UUID getAccountId() {
        return accountId;
    }

    @JsonProperty("_account_id")
    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("products")
    public List<Product> getProducts() {
        return products;
    }

    @JsonProperty("_products")
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
