package com.robin.springbootbackend.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

   @Query("Select p FROM Product p WHERE p.productName = ?1")
   Optional<Product> findProductByName(String name);
//    @Query("Select p FROM Product p WHERE p.product_id = ?1")
//    Optional<Product> findProductByID(UUID id);
}
