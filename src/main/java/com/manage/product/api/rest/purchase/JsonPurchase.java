package com.manage.product.api.rest.purchase;



import com.manage.product.api.rest.model.JsonData;

import java.math.BigDecimal;

public record JsonPurchase(Long id, Long idProduct, Integer quantity, BigDecimal total, Long idCustomer) implements JsonData {
}
