package com.robin.springbootbackend.promo;

import com.robin.springbootbackend.product.Product;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://triplergames.com"}, maxAge = 3600)
@RequestMapping(path = "api/v1/promo")
public class PromoController {
    private final PromoService promoService;

    @Autowired
    public PromoController(PromoService promoService){
        this.promoService = promoService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Optional<Promo>> getPromos(){
        return promoService.getPromos();
    }

    @GetMapping(path = "{promoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Optional<Promo> getPromo(@PathVariable("promoId") UUID promoId){
        return promoService.getPromo(promoId);
    }

    @PostMapping
    public int getPromoByCode(@RequestBody Promo promo){
        return promoService.getPromoByCode(promo);
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Promo postPromo(@RequestBody Promo promo, Authentication authentication, HttpServletRequest request){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        return promoService.postPromo(promo, accountId, request.getRemoteAddr());
    }

    @PutMapping(path = "{promoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateProduct(@PathVariable("promoId") UUID promoId,
                              @RequestBody Promo promo, Authentication authentication, HttpServletRequest request){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        promoService.updatePromo(promoId, promo, accountId, request.getRemoteAddr());
    }

    @DeleteMapping(path = "{promoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable("promoId") UUID promoId, Authentication authentication, HttpServletRequest request){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        promoService.deletePromo(promoId, accountId, request.getRemoteAddr());
    }
}
