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

   public void addProduct(Product product) throws IOException {
       Optional<Product> productOptional = productRepository.findProductByName(product.getName());
       if (productOptional.isPresent()){
           throw new IllegalStateException("Product With Name: " + product.getName() + " already exists");
       }

       if(product.getImage() == null){ return;}
       FileHelper fileHelper = new FileHelper();
       String base64File = fileHelper.encodeFile("src/main/java/com/robin/springbootbackend/product/cat.jpeg");

       byte[] bytes = fileHelper.decodeFile(base64File);
       if(!fileHelper.checkFileHex(bytes)){
            return;
       }

    //    productRepository.save(product);
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
