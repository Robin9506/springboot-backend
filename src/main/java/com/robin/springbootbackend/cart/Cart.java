package com.robin.springbootbackend.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robin.springbootbackend.product.Product;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;
@Entity
@Table
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private UUID cartId;
    @Column(name = "account_id")
    private UUID accountId;

    @OneToMany
    @JoinTable(name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public Cart() {
    }

    public Cart(UUID cartId, UUID accountId){
        this.cartId = cartId;
        this.accountId = accountId;
    }

    @JsonProperty("cart_id")
    public UUID getCartId() {
        return cartId;
    }

    @JsonProperty("_cart_id")
    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    @JsonProperty("account_id")
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
