package com.robin.springbootbackend.promo;

import com.robin.springbootbackend.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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

}
