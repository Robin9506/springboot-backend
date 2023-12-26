package com.robin.springbootbackend.product;

import com.robin.springbootbackend.cart.CartService;
import com.robin.springbootbackend.order.Order;
import com.robin.springbootbackend.order.OrderService;
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
    private final CartService cartService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService){
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @PostMapping
    public void postOrder(Authentication authentication){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        orderService.postNewOrder(accountId);
        this.cartService.removeCart(accountId);
    }
    @GetMapping
    public Optional<List<Order>> getOrders(){
        return orderService.getAllOrders();
    }


}
