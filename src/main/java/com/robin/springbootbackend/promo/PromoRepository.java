package com.robin.springbootbackend.promo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PromoRepository extends JpaRepository<Promo, UUID> {


}
