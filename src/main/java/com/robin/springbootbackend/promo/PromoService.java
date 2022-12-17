package com.robin.springbootbackend.promo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PromoService {
    private final PromoRepository promoRepository;

    @Autowired
    public PromoService(PromoRepository promoRepository){ this.promoRepository = promoRepository;}

    public List<Promo> getPromos(){
        return this.promoRepository.findAll();
    }

    public Optional<Promo> getPromo(UUID promoId){
        return promoRepository.findById(promoId);
    }


}
