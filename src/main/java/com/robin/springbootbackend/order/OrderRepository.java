package com.robin.springbootbackend.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("Select oa from Order oa")
    public List<Order> getAllOrders();

    @Query("Select oa from Order oa Where accountId = ?1")
    public List<Order> getOwnOrders(UUID accountId);
}
