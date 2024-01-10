package com.robin.springbootbackend.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

   @Query("Select p FROM Product p")
   List<Product> findAllProducts();

   @Query("Select p FROM Product p Order By productName")
   List<Product> findAllOrderedProductsByName();

   @Query("Select p FROM Product p Order By price")
   List<Product> findAllOrderedProductsByPrice();

   @Query("Select p FROM Product p WHERE p.productName = ?1")
   Optional<Product> findProductByName(String name);

   @Query("Select p FROM Product p WHERE p.productId = ?1")
   Optional<Product> findProductById(UUID id);

   @Modifying
   @Transactional
   @Query("DELETE FROM Product p WHERE p.productId = ?1")
   void deleteProductById(UUID id);

   @Modifying
   @Transactional
   @Query("INSERT INTO Product (productName, price, description, company, image, rating, platform) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)")
   void insertProduct(String productName, double price, String description, String company, String image, int rating, String platform);

   @Modifying
   @Transactional
   @Query("UPDATE Product SET productName = ?1, price = ?2, description = ?3, company = ?4, image = ?5, rating = ?6, platform = ?7 WHERE productId = ?8")
   void updateProduct(String productName, double price, String description, String company, String image, int rating, String platform, UUID productID);
}
