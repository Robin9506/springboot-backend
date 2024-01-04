package com.robin.springbootbackend.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

   @Query("Select p FROM Product p")
   List<Optional<Product>> findAllProducts();

   @Query("Select p FROM Product p WHERE p.productName = ?1")
   Optional<Product> findProductByName(String name);

   @Query("Select p FROM Product p WHERE p.productId = ?1")
   Optional<Product> findProductById(UUID id);

   @Query("DELETE FROM Product p WHERE p.productId = ?1")
   Optional<Product> deleteProductById(UUID id);
}
