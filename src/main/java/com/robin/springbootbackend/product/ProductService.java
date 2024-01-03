package com.robin.springbootbackend.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robin.springbootbackend.helper.FileHelper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final FileHelper fileHelper;

    @Autowired
    public ProductService(ProductRepository productRepository, FileHelper fileHelper){
        this.productRepository = productRepository;
        this.fileHelper = fileHelper;

    }
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(UUID productId){
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            String imageString = fileHelper.encodeFile(product.getImage());
            
            product.setImage(imageString);

            return product;
        }

        return null;
    }

   public void addProduct(Product product) {
       Optional<Product> productOptional = productRepository.findProductByName(product.getName());
       if (productOptional.isPresent()){
           throw new IllegalStateException("Product With Name: " + product.getName() + " already exists");
       }

       if(product.getImage() == null){ return;}
       byte[] bytes = fileHelper.decodeFile(product.getImage());
       if(!fileHelper.checkFileHex(bytes)){
            return;
       }

       String imageLink = fileHelper.convertBase64ToFile(bytes);

       product.setImage(imageLink);
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
                    updatedProduct.setImage(product.getImage());
                    updatedProduct.setRating(product.getRating());
                    updatedProduct.setPlatform(product.getPlatform());
                    return productRepository.save(updatedProduct);
                })
                .orElseThrow(() -> new IllegalStateException("product not found"));

    }
}
