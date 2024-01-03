package com.robin.springbootbackend.cart;

import com.robin.springbootbackend.product.Product;
import com.robin.springbootbackend.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, ProductService productService){
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public Optional<Cart> getOwnCart(UUID accountId){
        Optional<Cart> cart = cartRepository.findCartByAccountId(accountId);
        if (cart.isEmpty()){
            System.out.println("cart is NULL");
            createCart(accountId);
        }
        else {
            System.out.println("cart is owned");
        }

        return cart;
    }

    public void createCart(UUID accountId){
        Optional<Cart> cart = cartRepository.findCartByAccountId(accountId);
        if (cart.isEmpty()) {
            cartRepository.createCartById(accountId);
        }

    }

    @Transactional
    public void removeCart(UUID accountId){
        cartRepository.removeCartByAccountId(accountId);
    }

    @Transactional
    public void removeItemFromCart(UUID accountId, UUID productId){
        System.out.println(productId);
        Product product = productService.getProduct(productId);

        Optional<Cart> cartOptional = getOwnCart(accountId);
        if (cartOptional.isPresent()){
            Cart cart = cartOptional.get();
            System.out.println(product.getName());
            cart.getProducts().remove(product);
        }
        
    }

    @Transactional
    public void addItemToCart(UUID accountId, UUID productId){
        System.out.println(productId);
        Product product = productService.getProduct(productId);

        Optional<Cart> cartOptional = getOwnCart(accountId);
        if (cartOptional.isPresent()){
            Cart cart = cartOptional.get();
            System.out.println(product.getName());
            cart.getProducts().add(product);
        }      
    }
}
