package com.robin.springbootbackend.promo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @JsonProperty(value = "id", index = 1)
    public UUID getPromoId() {
        return promo_id;
    }

    public void setPromoId(UUID promo_id) {
        this.promo_id = promo_id;
    }

    @JsonProperty(value = "code", index = 2)
    public String getPromoCode() {
        return promoCode;
    }

    @JsonProperty("_code")
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    @JsonProperty("discount")
    public int getPromoDiscount() {
        return promoDiscount;
    }

    @JsonProperty("_discount")
    public void setPromoDiscount(int promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

}
