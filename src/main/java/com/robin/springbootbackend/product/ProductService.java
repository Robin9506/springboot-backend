package com.robin.springbootbackend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
