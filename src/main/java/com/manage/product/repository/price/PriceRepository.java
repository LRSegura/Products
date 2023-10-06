package com.manage.product.repository.price;

import com.manage.product.model.price.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("SELECT p FROM Price p WHERE ?1 between p.startValue and p.endValue or ?2 between p.startValue " +
            "and p.endValue ")
    List<Price> findCrossRange(Integer startValue, Integer endValue);

    @Query("SELECT p FROM Price p WHERE ?1 between p.startValue and p.endValue ")
    Optional<Price> findPriceByValue(Integer value);
}
