package com.robin.springbootbackend.promo;

import com.robin.springbootbackend.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(path = "api/v1/promo")
public class PromoController {
    private final PromoService promoService;

    @Autowired
    public PromoController(PromoService promoService){
        this.promoService = promoService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Promo> getPromos(){
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
    public Promo postPromo(@RequestBody Promo promo){
        return promoService.postPromo(promo);
    }

    @PutMapping(path = "{promoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateProduct(@PathVariable("promoId") UUID promoId,
                              @RequestBody Promo promo){
        promoService.updatePromo(promoId, promo);
    }

    @DeleteMapping(path = "{promoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable("promoId") UUID promoId){
        promoService.deletePromo(promoId);
    }
}
