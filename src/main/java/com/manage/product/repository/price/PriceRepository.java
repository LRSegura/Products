package com.manage.product.repository.price;

import com.manage.product.model.price.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("SELECT p FROM Price p WHERE (p.endValue >= ?1 and p.startValue <= ?1) or (p.endValue >= ?2 " +
            "and p.startValue <= ?2)")
    Optional<Price> findCrossRange(Integer startValue, Integer endValue);
}
