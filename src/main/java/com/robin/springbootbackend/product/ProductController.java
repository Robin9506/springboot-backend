package com.robin.springbootbackend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(path = "api/v1/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void postProduct(@RequestBody Product product) throws IOException{
        productService.addProduct(product);
    }

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping(path = "{productId}")
    public Product getProduct(@PathVariable("productId") UUID productId){
        return productService.getProduct(productId);
    }

    @PutMapping(path = "{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateProduct(@PathVariable("productId") UUID productId,
                              @RequestBody Product product){
        productService.updateProduct(productId, product);
    }

    @DeleteMapping(path = "{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable("productId") UUID productId){
        productService.deleteProduct(productId);
    }

}
