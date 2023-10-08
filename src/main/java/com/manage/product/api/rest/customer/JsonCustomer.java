package com.manage.product.api.rest.customer;

import com.manage.product.api.rest.model.JsonData;

import java.math.BigDecimal;

/**
 * Used to represent customer as Json
 * @author Luis
 */
public record JsonCustomer(Long id, String registerDate, String updateDate, String name, Boolean discount,
                           BigDecimal discountValue) implements JsonData {
}
