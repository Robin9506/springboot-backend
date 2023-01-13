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

    public Promo postPromo(Promo promo){
        return promoRepository.save(promo);
    }

    public Promo updatePromo(UUID promoId, Promo promo) {
        return promoRepository.findById(promoId)
                .map(updatedPromo -> {
                    updatedPromo.setPromoCode(promo.getPromoCode());
                    updatedPromo.setPromoDiscount(promo.getPromoDiscount());

                    return promoRepository.save(updatedPromo);
                })
                .orElseThrow(() -> new IllegalStateException("promo not found"));

    }

    public void deletePromo(UUID promoId){
        promoRepository.deleteById(promoId);
    }
    public int getPromoByCode(Promo promo){
        Optional<Promo> promoObject = promoRepository.getPromoByCode(promo.getPromoCode());
        if(promoObject.isEmpty()){
            return 0;
        }
        return promoObject.orElseThrow().getPromoDiscount();
    }


}
