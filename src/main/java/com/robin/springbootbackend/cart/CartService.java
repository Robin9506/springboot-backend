package com.robin.springbootbackend.cart;

import com.robin.springbootbackend.enums.LogType;
import com.robin.springbootbackend.enums.Repo;
import com.robin.springbootbackend.enums.RouteType;
import com.robin.springbootbackend.helper.FileHelper;
import com.robin.springbootbackend.helper.Log;
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

    private final FileHelper fileHelper;

    @Autowired
    public CartService(CartRepository cartRepository, ProductService productService, FileHelper fileHelper){
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.fileHelper = fileHelper;
    }

    public Optional<Cart> getOwnCart(UUID accountId){
        Optional<Cart> cartOptional = cartRepository.findCartByAccountId(accountId);
        if (cartOptional.isEmpty()){
            System.out.println("cart is NULL");
            createCart(accountId);
        }
        else {
            Cart cart = cartOptional.get();
            if (cart.getProducts() != null){
                for (Product product : cart.getProducts()){
                    String imageString = fileHelper.encodeFile(product.getImage());
                    product.setImage(imageString);
                }
            }

            return Optional.of(cart);
        }

        return Optional.empty();
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

        if(product == null) return;

        Optional<Cart> cartOptional = getOwnCart(accountId);
        if (cartOptional.isPresent()){
            Cart cart = cartOptional.get();
            System.out.println(product.getName());
            cart.getProducts().remove(product);
        }
        
    }

    @Transactional
    public void addItemToCart(UUID accountId, UUID productId){
        Product product = productService.getProductForCart(productId);

        if(product == null) return;

        Optional<Cart> cartOptional = getOwnCart(accountId);
        if (cartOptional.isPresent()){
            Cart cart = cartOptional.get();
            System.out.println(product.getName());
            cart.getProducts().add(product);
        }      
    }
}
