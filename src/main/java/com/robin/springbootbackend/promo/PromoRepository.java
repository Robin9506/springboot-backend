package com.robin.springbootbackend.promo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PromoRepository extends JpaRepository<Promo, UUID> {

    @Query("Select p FROM Promo p WHERE p.promoCode = ?1")
    public Optional<Promo> getPromoByCode(String code);

}
