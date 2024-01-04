package com.robin.springbootbackend.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robin.springbootbackend.account.Account;
import com.robin.springbootbackend.enums.LogType;
import com.robin.springbootbackend.enums.Repo;
import com.robin.springbootbackend.enums.RouteType;
import com.robin.springbootbackend.helper.FileHelper;
import com.robin.springbootbackend.helper.Log;
import com.robin.springbootbackend.helper.LogService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.swing.text.html.Option;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final LogService logService;
    private final FileHelper fileHelper;

    @Autowired
    public ProductService(ProductRepository productRepository, LogService logService, FileHelper fileHelper){
        this.productRepository = productRepository;
        this.logService = logService;
        this.fileHelper = fileHelper;

    }
    public List<Optional<Product>> getProducts(){
        return productRepository.findAllProducts();
    }

    public Product getProduct(UUID productId){
        Optional<Product> productOptional = productRepository.findProductById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            String imageString = fileHelper.encodeFile(product.getImage());
            
            product.setImage(imageString);

            return product;
        }

        return null;
    }

   public void addProduct(Product product, UUID accountId, String ip) {
       Optional<Product> productOptional = productRepository.findProductByName(product.getName());
       if (productOptional.isPresent()){
            Log log = new Log(ip, accountId, LogType.DENIED, RouteType.POST, Repo.PRODUCT, null, "user tried to create product with name: " + product.getName());
            this.logService.LogAction(log);
           throw new IllegalStateException("Product With Name: " + product.getName() + " already exists");
       }

       if(product.getImage() == null){ return;}
       byte[] bytes = fileHelper.decodeFile(product.getImage());
       if(!fileHelper.checkFileHex(bytes)){
            Log log = new Log(ip, accountId, LogType.DENIED, RouteType.POST, Repo.PRODUCT, null, "user tried to create product with file that is not png/jpeg");
            this.logService.LogAction(log);
            return;
       }

       String imageLink = fileHelper.convertBase64ToFile(bytes);

       product.setImage(imageLink);

       Log log = new Log(ip, accountId, LogType.COMPLETED, RouteType.POST, Repo.PRODUCT, null, "user created product with name: " + product.getName());
       this.logService.LogAction(log);
       productRepository.save(product);
   }

    public void deleteProduct(UUID productId, UUID accountId, String ip) {
        Optional<Product> productOptional = productRepository.findProductById(productId);
        if (!productOptional.isPresent()){
            Log log = new Log(ip, accountId, LogType.DENIED, RouteType.POST, Repo.PRODUCT, null, "user tried to delete product with id: " + productId);
            this.logService.LogAction(log);
            throw new IllegalStateException("Product with ID: " + productId + " Does not exists");
        }

        Log log = new Log(ip, accountId, LogType.COMPLETED, RouteType.POST, Repo.PRODUCT, null, "user deleted product with id: " + productId);
        this.logService.LogAction(log);
        productRepository.deleteProductById(productId);
    }

    @Transactional
    public Product updateProduct(UUID productId, Product product, UUID accountId, String ip) {
        Product currentProduct = null;

        Optional<Product> productOptional = productRepository.findProductById(productId);
        if (productOptional.isPresent()){
            currentProduct = productOptional.get();
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setDescription(product.getDescription());
            currentProduct.setCompany(product.getCompany());
            currentProduct.setImage(product.getImage());
            currentProduct.setRating(product.getRating());
            currentProduct.setPlatform(product.getPlatform());

            Log log = new Log(ip, accountId, LogType.COMPLETED, RouteType.PUT, Repo.PRODUCT, null, "user " + accountId +" updated product with id: " + productId);
            this.logService.LogAction(log);

            return productRepository.save(currentProduct);

        }else{
            Log log = new Log(ip, accountId, LogType.DENIED, RouteType.PUT, Repo.PRODUCT, null, "user " + accountId +" tried to update product with id: " + productId);
            this.logService.LogAction(log);
            return null;
        }
    }
}
