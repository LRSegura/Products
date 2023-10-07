package com.manage.product.api.rest.price;



import com.manage.product.api.rest.model.JsonData;
import com.manage.product.api.rest.product.JsonProduct;

import java.math.BigDecimal;

public record JsonPrice(Long id, String registerDate, String updateDate, Integer startValue, Integer endValue,
                        BigDecimal price, Long idProduct,
                        JsonProduct product) implements JsonData {
}
