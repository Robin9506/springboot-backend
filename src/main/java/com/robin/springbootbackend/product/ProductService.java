package com.robin.springbootbackend.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robin.springbootbackend.enums.LogType;
import com.robin.springbootbackend.enums.Repo;
import com.robin.springbootbackend.enums.RouteType;
import com.robin.springbootbackend.helper.FileHelper;
import com.robin.springbootbackend.helper.Log;
import com.robin.springbootbackend.helper.LogService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public List<Product> getProducts(){
        List<Product> productList = productRepository.findAllProducts();
        if (productList.size() != 0){
            for (Product product : productList){
                String imageString = fileHelper.encodeFile(product.getImage());

                product.setImage(imageString);
            }
        }

        return productList;
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
       productRepository.insertProduct(product.getName(), product.getPrice(), product.getDescription(), product.getCompany(), product.getImage(), product.getRating(), product.getPlatform());
   }

    public void deleteProduct(UUID productId, UUID accountId, String ip) {
        Optional<Product> productOptional = productRepository.findProductById(productId);
        if (!productOptional.isPresent()){
            Log log = new Log(ip, accountId, LogType.DENIED, RouteType.DELETE, Repo.PRODUCT, null, "user tried to delete product with id: " + productId);
            this.logService.LogAction(log);
            throw new IllegalStateException("Product with ID: " + productId + " Does not exists");
        }

        Log log = new Log(ip, accountId, LogType.COMPLETED, RouteType.DELETE, Repo.PRODUCT, null, "user deleted product with id: " + productId);
        this.logService.LogAction(log);
        productRepository.deleteProductById(productId);
    }

    @Transactional
    public void updateProduct(UUID productId, Product product, UUID accountId, String ip) {
        Product currentProduct = null;

        Optional<Product> productOptional = productRepository.findProductById(productId);
        if (productOptional.isPresent()){
            currentProduct = productOptional.get();
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setDescription(product.getDescription());
            currentProduct.setCompany(product.getCompany());
            currentProduct.setRating(product.getRating());
            currentProduct.setPlatform(product.getPlatform());

            if(product.getImage() == null){ return;}
            byte[] bytes = fileHelper.decodeFile(product.getImage());
            if(!fileHelper.checkFileHex(bytes)){
                    Log log = new Log(ip, accountId, LogType.DENIED, RouteType.POST, Repo.PRODUCT, null, "user tried to update product with file that is not png/jpeg");
                    this.logService.LogAction(log);
                    return;
            }

            String imageLink = fileHelper.convertBase64ToFile(bytes);

            currentProduct.setImage(imageLink);

            Log log = new Log(ip, accountId, LogType.COMPLETED, RouteType.PUT, Repo.PRODUCT, null, "user " + accountId +" updated product with id: " + productId);
            this.logService.LogAction(log);

            productRepository.updateProduct(currentProduct.getName(), currentProduct.getPrice(), currentProduct.getDescription(), currentProduct.getCompany(), currentProduct.getImage(), currentProduct.getRating(), currentProduct.getPlatform(), productId);

        }else{
            Log log = new Log(ip, accountId, LogType.DENIED, RouteType.PUT, Repo.PRODUCT, null, "user " + accountId +" tried to update product with id: " + productId);
            this.logService.LogAction(log);
            return;
        }
    }
}
