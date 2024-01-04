package com.robin.springbootbackend.promo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robin.springbootbackend.enums.LogType;
import com.robin.springbootbackend.enums.Repo;
import com.robin.springbootbackend.enums.RouteType;
import com.robin.springbootbackend.helper.Log;
import com.robin.springbootbackend.helper.LogService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PromoService {
    private final PromoRepository promoRepository;
    private final LogService logService;

    @Autowired
    public PromoService(PromoRepository promoRepository, LogService logService){ 
        this.promoRepository = promoRepository;
        this.logService = logService;
    }

    public List<Promo> getPromos(){
        return this.promoRepository.findAll();
    }

    public Optional<Promo> getPromo(UUID promoId){
        return promoRepository.findById(promoId);
    }

    public Promo postPromo(Promo promo, UUID accountId, String ip){
        Log log = new Log(ip, accountId, LogType.COMPLETED, RouteType.POST, Repo.PROMO, null, "user created new promo");
        this.logService.LogAction(log);

        return promoRepository.save(promo);
    }

    public Promo updatePromo(UUID promoId, Promo promo, UUID accountId, String ip) {
        Promo promoObject = null;
        Optional<Promo> promoOptional = promoRepository.findById(promoId);
        if (promoOptional.isPresent()) {
            promoObject = promoOptional.get();
            promoObject.setPromoCode(promo.getPromoCode());
            promoObject.setPromoDiscount(promo.getPromoDiscount());

            Log log = new Log(ip, accountId, LogType.COMPLETED, RouteType.PUT, Repo.PROMO, null, "user updated promo with id: " + promoId);
            this.logService.LogAction(log);

            return promoRepository.save(promoObject);

        }
        else{
            Log log = new Log(ip, accountId, LogType.DENIED, RouteType.PUT, Repo.PROMO, null, "user tried to update promo with id: " + promoId);
            this.logService.LogAction(log);
            return null;
        }
    }

    public void deletePromo(UUID promoId, UUID accountId, String ip){
        boolean promoExists = promoRepository.existsById(promoId);
        if (!promoExists){
            Log log = new Log(ip, accountId, LogType.DENIED, RouteType.DELETE, Repo.PROMO, null, "user tried to delete promo with id: " + promoId);
            this.logService.LogAction(log);
        }

        Log log = new Log(ip, accountId, LogType.COMPLETED, RouteType.DELETE, Repo.PROMO, null, "user deleted promo with id: " + promoId);
        this.logService.LogAction(log);

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
