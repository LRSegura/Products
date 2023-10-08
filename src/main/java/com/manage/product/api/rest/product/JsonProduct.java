package com.manage.product.api.rest.product;



import com.manage.product.api.rest.model.JsonData;

import java.math.BigDecimal;

/**
 * Used to represent product as Json
 * @author Luis
 */
public record JsonProduct(Long id, String registerDate, String updateDate, String name, String description,
                          BigDecimal defaultPrice) implements JsonData {
}
