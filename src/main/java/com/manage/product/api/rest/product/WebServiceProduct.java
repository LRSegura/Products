package com.manage.product.api.rest.product;


import com.manage.product.api.rest.model.CrudRestOperations;
import com.manage.product.api.rest.model.CrudWebService;
import com.manage.product.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class WebServiceProduct extends CrudWebService<JsonProduct> {
    private final ProductService productService;

    public WebServiceProduct(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public CrudRestOperations<JsonProduct> getCrudRestOperations() {
        return productService;
    }

    @Override
    protected String getNameEntityService() {
        return "Product";
    }


}
