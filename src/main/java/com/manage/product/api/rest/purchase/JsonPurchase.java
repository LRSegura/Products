package com.manage.product.api.rest.purchase;



import com.manage.product.api.rest.customer.JsonCustomer;
import com.manage.product.api.rest.model.JsonData;
import com.manage.product.api.rest.product.JsonProduct;

import java.math.BigDecimal;

/**
 * Used to represent purchase as Json
 * @author Luis
 */
public record JsonPurchase(Long id, String registerDate, String updateDate, Long idProduct, Integer quantity,
                           BigDecimal total, Long idCustomer, JsonProduct product, JsonCustomer customer) implements JsonData {
}
