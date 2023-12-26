package com.robin.springbootbackend.cart;

import com.robin.springbootbackend.product.Product;
import com.robin.springbootbackend.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository){
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
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
        cartRepository.createCartById(accountId);
    }

    @Transactional
    public void removeItemFromCart(UUID accountId, UUID productId){
        System.out.println(productId);
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()){
            Product product = productOptional.get();

            Optional<Cart> cartOptional = getOwnCart(accountId);
            if (cartOptional.isPresent()){
                Cart cart = cartOptional.get();
                System.out.println(product.getName());
                cart.getProducts().remove(product);
            }
        }
    }

    @Transactional
    public void addItemToCart(UUID accountId, UUID productId){
        System.out.println(productId);
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            Optional<Cart> cartOptional = getOwnCart(accountId);
            if (cartOptional.isPresent()){
                Cart cart = cartOptional.get();
                System.out.println(product.getName());
                cart.getProducts().add(product);
            }
        }
    }
}
