package com.robin.springbootbackend.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;

    }
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public void addNewProduct(Product product) {
        Optional<Product> productOptional = productRepository.findProductByName(product.getName());
        if (productOptional.isPresent()){
            throw new IllegalStateException("Product With Name: " + product.getName() + " already exists");
        }
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        boolean productExists = productRepository.existsById(productId);
        if (!productExists){
            throw new IllegalStateException("Product with ID: " + productId + " Does not exists");
        }
        productRepository.deleteById(productId);
    }

    @Transactional
    public void updateProduct(Long productId, String name, Double price, String description) {
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new IllegalStateException("product not found"));
        System.out.println(name);


        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(product.getName(), name)){
            product.setName(name);
        }

        if (price != null &&
                price > 0 &&
                !Objects.equals(product.getPrice(), price)){
            product.setPrice(price);
        }

        product.setDescription(description);
    }
}
