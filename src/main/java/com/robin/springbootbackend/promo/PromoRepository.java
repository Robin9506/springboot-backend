package com.robin.springbootbackend.promo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PromoRepository extends JpaRepository<Promo, UUID> {

    @Query("Select p FROM Promo p WHERE p.promoCode = ?1")
    public Optional<Promo> getPromoByCode(String code);

    @Query("Select p FROM Promo p WHERE p.promoId = ?1")
    public Optional<Promo> getPromoById(UUID id);

    @Query("Select p FROM Promo p")
    public List<Optional<Promo>> getAllPromos();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Promo WHERE promoId = ?1")
    public void deletePromoFromId(UUID promoId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Promo (promoCode, promoDiscount) VALUES (?1, ?2)")
    public void insertPromo(String promoCode, int promoDiscount);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Promo SET promoCode = ?1, promoDiscount = ?2 WHERE promoId = ?3")
    public void updatePromo(String promoCode, int promoDiscount, UUID promoID);

}
