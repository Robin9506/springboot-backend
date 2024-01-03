package com.robin.springbootbackend.order;

import com.robin.springbootbackend.cart.Cart;
import com.robin.springbootbackend.cart.CartService;
import com.robin.springbootbackend.product.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartService cartService){
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    @Transactional
    public void postNewOrder(UUID accountId){
        Optional<Cart> cartOptional = cartService.getOwnCart(accountId);
        if (cartOptional.isPresent()){
            Cart cart = cartOptional.get();

            if(cart.getProducts().size() > 0){
                Order order = new Order(accountId);
                order.setProducts(new ArrayList<Product>(cart.getProducts()));

                this.orderRepository.save(order);
                this.cartService.removeCart(accountId);
            }

        }
        else return;


    }

    public Optional<List<Order>> getAllOrders(){
        return orderRepository.getAllOrders();
    }
}
