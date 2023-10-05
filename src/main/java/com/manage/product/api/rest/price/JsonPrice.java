package com.manage.product.api.rest.price;



import com.manage.product.api.rest.model.JsonData;

import java.math.BigDecimal;

public record JsonPrice(Long id, Integer startValue, Integer endValue, BigDecimal price, Long idProduct) implements JsonData {
}
