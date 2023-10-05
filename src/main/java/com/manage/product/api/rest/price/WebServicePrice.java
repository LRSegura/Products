package com.manage.product.api.rest.price;


import com.manage.product.api.rest.model.CrudRestOperations;
import com.manage.product.api.rest.model.CrudWebService;
import com.manage.product.service.price.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/price")
@Slf4j
public class WebServicePrice extends CrudWebService<JsonPrice> {
    private final PriceService priceService;

    public WebServicePrice(PriceService priceService) {
        this.priceService = priceService;
    }

    @Override
    public CrudRestOperations<JsonPrice> getCrudRestOperations() {
        return priceService;
    }

    @Override
    protected String getNameEntityService() {
        return "Price";
    }
}
