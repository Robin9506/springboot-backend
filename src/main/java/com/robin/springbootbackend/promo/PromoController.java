package com.robin.springbootbackend.promo;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Promo> getPromos(){
        return promoService.getPromos();
    }

    @GetMapping(path = "{promoId}")
    public Optional<Promo> getPromo(@PathVariable("promoId") UUID promoId){
        return promoService.getPromo(promoId);
    }

    @PostMapping
    public int getPromoByCode(@RequestBody Promo promo){
        return promoService.getPromoByCode(promo);
    }
}
