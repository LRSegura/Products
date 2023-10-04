package com.manage.product.api.rest.product;



import com.manage.product.api.rest.model.JsonData;

import java.math.BigDecimal;

public record JsonProduct(Long id, String name, String description, BigDecimal defaultPrice) implements JsonData {
}
