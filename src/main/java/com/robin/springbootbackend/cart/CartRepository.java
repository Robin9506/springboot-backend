package com.robin.springbootbackend.cart;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Query("Select c FROM Cart c WHERE c.accountId = ?1")
    public Optional<Cart> findCartByAccountId(UUID AccountId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Cart (accountId) VALUES (?1)")
    public void createCartById(UUID AccountId);

    @Modifying
    @Query(value = "DELETE FROM Cart WHERE accountId = ?1")
    public void removeCartByAccountId(UUID AccountId);
}