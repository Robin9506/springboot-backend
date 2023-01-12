package com.robin.springbootbackend.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<Product> getProduct(UUID productId){
        return productRepository.findById(productId);
    }

//    public void addProduct(Product product) {
//        Optional<Product> productOptional = productRepository.findProductByName(product.getName());
//        if (productOptional.isPresent()){
//            throw new IllegalStateException("Product With Name: " + product.getName() + " already exists");
//        }
//        productRepository.save(product);
//    }

    public void postProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(UUID productId) {
        boolean productExists = productRepository.existsById(productId);
        if (!productExists){
            throw new IllegalStateException("Product with ID: " + productId + " Does not exists");
        }
        productRepository.deleteById(productId);
    }

    @Transactional
    public Product updateProduct(UUID productId, Product product) {
        return productRepository.findById(productId)
                .map(updatedProduct -> {
                    updatedProduct.setName(product.getName());
                    updatedProduct.setPrice(product.getPrice());
                    updatedProduct.setDescription(product.getDescription());
                    updatedProduct.setCompany(product.getCompany());
                    updatedProduct.setImageLink(product.getImageLink());
                    updatedProduct.setRating(product.getRating());
                    updatedProduct.setPlatform(product.getPlatform());
                    return productRepository.save(updatedProduct);
                })
                .orElseThrow(() -> new IllegalStateException("product not found"));

    }
}
