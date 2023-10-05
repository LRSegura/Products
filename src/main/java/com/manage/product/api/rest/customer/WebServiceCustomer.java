package com.manage.product.api.rest.customer;


import com.manage.product.api.rest.model.CrudRestOperations;
import com.manage.product.api.rest.model.CrudWebService;
import com.manage.product.service.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@Slf4j
public class WebServiceCustomer extends CrudWebService<JsonCustomer> {
    private final CustomerService customerService;

    public WebServiceCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public CrudRestOperations<JsonCustomer> getCrudRestOperations() {
        return customerService;
    }

    @Override
    protected String getNameEntityService() {
        return "Customer";
    }
}
