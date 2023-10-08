package com.manage.product.api.rest.purchase;


import com.manage.product.api.rest.model.CrudRestOperations;
import com.manage.product.api.rest.model.CrudWebService;
import com.manage.product.api.rest.product.JsonProduct;
import com.manage.product.service.purchase.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Web service class for purchase
 * @author Luis
 */
@RestController
@RequestMapping("/api/purchase")
@Slf4j
public class WebServicePurchase extends CrudWebService<JsonPurchase> {
    private final PurchaseService purchaseService;

    public WebServicePurchase(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Override
    public CrudRestOperations<JsonPurchase> getCrudRestOperations() {
        return purchaseService;
    }

    @Override
    protected String getNameEntityService() {
        return "Purchase";
    }


}
