package com.robin.springbootbackend.cart;

import com.robin.springbootbackend.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(path = "api/v1/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping(path = "/own/{productId}")
    public void addItemFromCart(Authentication authentication, @PathVariable("productId") UUID productId){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        cartService.addItemToCart(accountId, productId);
    }

    @GetMapping(path = "/own")
    public Optional<Cart> getOwnCart(Authentication authentication){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        return cartService.getOwnCart(accountId);
    }

    @DeleteMapping(path = "/own/{productId}")
    public void removeItemFromCart(Authentication authentication, @PathVariable("productId") UUID productId){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        cartService.removeItemFromCart(accountId, productId);
    }


}
