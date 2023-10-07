package com.manage.product.service.customer;

import com.manage.product.api.exception.ApplicationBusinessException;
import com.manage.product.api.rest.customer.JsonCustomer;
import com.manage.product.api.rest.model.CrudRestOperations;
import com.manage.product.api.rest.model.JsonData;
import com.manage.product.api.util.UtilClass;
import com.manage.product.model.customer.Customer;
import com.manage.product.repository.customer.CustomerRepository;
import com.manage.product.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService extends AbstractService implements CrudRestOperations<JsonCustomer> {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<? extends JsonData> restGet() {
        return customerRepository.findAll().stream().map(Customer::getJsonCustomer).toList();
    }

    @Override
    public void restSave(JsonCustomer json) {
        UtilClass.requireNonNull(json.name(), "Customer name cant be null");
        UtilClass.requireNonNull(json.discount(), "Customer discount cant be null");
        UtilClass.requireNonNull(json.discountValue(), "Customer discount value cant be null");
        UtilClass.requireNonBlankString(json.name(), "Customer name cant be empty");
        if (customerRepository.existsCustomerByName(json.name())) {
            throw new ApplicationBusinessException("Duplicated customer name");
        }
        Customer customer = new Customer();
        customer.setName(json.name());
        customer.setDiscount(json.discount());
        customer.setDiscountValue(json.discountValue());
        customerRepository.save(customer);
    }

    @Override
    public void restUpdate(JsonCustomer json) {
        UtilClass.requireNonNull(json.id(), "Customer Id cant be null");
        Customer customer = getEntity(Customer.class, json.id());
        if (Objects.nonNull(json.name()) && !json.name().isEmpty()) {
            customer.setName(json.name());
        }
        if (Objects.nonNull(json.discount())) {
            customer.setDiscount(json.discount());
        }
        if (Objects.nonNull(json.discountValue())) {
            customer.setDiscountValue(json.discountValue());
        }
        customerRepository.save(customer);
    }

    @Override
    public void restDelete(Long id) {
        UtilClass.requireNonNull(id, "Customer Id cant be null");
        //TODO make validation
        customerRepository.deleteById(id);
    }


}
