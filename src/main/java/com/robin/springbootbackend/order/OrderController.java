package com.robin.springbootbackend.order;

import com.robin.springbootbackend.cart.Cart;
import com.robin.springbootbackend.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(path = "api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public void postOrder(Authentication authentication){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        orderService.postNewOrder(accountId);
    }

    @GetMapping
    public Optional<List<Order>> getOrders(){
        return orderService.getAllOrders();
    }


}
