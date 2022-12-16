package com.robin.springbootbackend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

//    @PostMapping
//    public void postProduct(@RequestBody Product product){
//        productService.addProduct(product);
//    }

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping(path = "{productId}")
    public Optional<Product> getProduct(@PathVariable("productId") UUID productId){
        return productService.getProduct(productId);
    }

    @PutMapping(path = "{productId}")
    public void updateProduct(@PathVariable("productId") Long productId,
                              @RequestBody Product product){
        productService.updateProduct(
                productId,
                product.getName(),
                product.getPrice(),
                product.getDescription());
    }

    @DeleteMapping(path = "{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProduct(productId);
    }

}
