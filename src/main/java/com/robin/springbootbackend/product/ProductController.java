package com.robin.springbootbackend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:4200",
        "http://triplergames.com",
        "https://triplergames.com"}, maxAge = 3600)
@RequestMapping(path = "api/v1/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void postProduct(@RequestBody Product product, Authentication authentication, HttpServletRequest request) throws IOException{
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        productService.addProduct(product, accountId, request.getRemoteAddr());
    }

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping(path = "/ordered")
    public List<Product> getOrderedProducts(){
        return productService.getOrderedProductsByName();
    }

    @GetMapping(path = "/ordered/price")
    public List<Product> getOrderedProductsByPrice(){
        return productService.getOrderedProductsByPrice();
    }

    @GetMapping(path = "{productId}")
    public Product getProduct(@PathVariable("productId") UUID productId){
        return productService.getProduct(productId);
    }

    @PutMapping(path = "{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateProduct(@PathVariable("productId") UUID productId,
                              @RequestBody Product product, Authentication authentication, HttpServletRequest request){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        productService.updateProduct(productId, product, accountId, request.getRemoteAddr());
    }

    @DeleteMapping(path = "{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable("productId") UUID productId, Authentication authentication, HttpServletRequest request){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        productService.deleteProduct(productId, accountId, request.getRemoteAddr());
    }

}
