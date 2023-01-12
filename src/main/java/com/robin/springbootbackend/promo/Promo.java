package com.robin.springbootbackend.promo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table
public class Promo {
    @Id
    @Column(name = "promo_id")
    private UUID promo_id;
    @Column(name = "promo_code")
    private String promoCode;
    @Column(name = "promo_discount")
    private int promoDiscount;

    public Promo(){}
    public Promo(String promoCode, int promoDiscount){
        this.promoCode = promoCode;
        this.promoDiscount = promoDiscount;
    }

    @JsonProperty(value = "promo_id", index = 1)
    public UUID getPromoId() {
        return promo_id;
    }

    public void setPromoId(UUID promo_id) {
        this.promo_id = promo_id;
    }

    @JsonProperty(value = "promo_code", index = 2)
    public String getPromoCode() {
        return promoCode;
    }

    @JsonProperty("_code")
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public int getPromoDiscount() {
        return promoDiscount;
    }

    @JsonProperty("_discount")
    public void setPromoDiscount(int promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

}
