package com.robin.springbootbackend.order;

import com.robin.springbootbackend.cart.Cart;
import com.robin.springbootbackend.cart.CartService;
import com.robin.springbootbackend.helper.FileHelper;
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

    private final FileHelper fileHelper;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartService cartService, FileHelper fileHelper){
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.fileHelper = fileHelper;
    }

    @Transactional
    public Order postNewOrder(UUID accountId){
        Optional<Cart> cartOptional = cartService.getOwnCart(accountId, true);
        if (cartOptional.isPresent()){
            Cart cart = cartOptional.get();

            if(cart.getProducts().size() > 0){
                Order order = new Order(accountId);
                order.setProducts(new ArrayList<Product>(cart.getProducts()));

                this.orderRepository.save(order);
                this.cartService.removeCart(accountId);

                return order;
            }

        }
        return null;
    }

    public List<Order> getOwnOrders(UUID accountId) {
        return orderRepository.getOwnOrders(accountId);
    }

    public List<Order> getAllOrders(){
        return orderRepository.getAllOrders();
    }
}
