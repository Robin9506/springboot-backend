package com.robin.springbootbackend.promo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.UUID;

@Entity
@Table
public class Promo {
    @Id
    private UUID promo_id;
    private String promo_code;
    private int promo_discount;
    private Date promo_end_time;

    public Promo(){}
    public Promo(String promoCode, int promoDiscount, Date promoEndTime){
        this.promo_code = promoCode;
        this.promo_discount = promoDiscount;
        this.promo_end_time = promoEndTime;
    }

    @JsonProperty(value = "promo_id", index = 1)
    public UUID getPromoId() {
        return promo_id;
    }

    public void setPromoId(UUID promo_id) {
        this.promo_id = promo_id;
    }

    @JsonProperty(value = "product_code", index = 2)
    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public int getPromo_discount() {
        return promo_discount;
    }

    public void setPromo_discount(int promo_discount) {
        this.promo_discount = promo_discount;
    }

    public Date getPromo_end_time() {
        return promo_end_time;
    }

    public void setPromo_end_time(Date promo_end_time) {
        this.promo_end_time = promo_end_time;
    }
}
