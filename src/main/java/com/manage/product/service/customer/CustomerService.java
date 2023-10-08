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

    /**
     * Represent the business logic to get all customers.
     */
    @Override
    public List<? extends JsonData> restGetAll() {
        return customerRepository.findAll().stream().map(Customer::getJsonCustomer).toList();
    }

    /**
     * Represent the business logic to get a customer by id.
     */
    @Override
    public JsonCustomer restGet(Long id) {
        return ((Customer)getEntity(Customer.class, id)).getJsonCustomer();
    }

    /**
     * Represent the business logic to save a customer. All the required values are validated before save the entity.
     * If a required value is not present, then a {@link ApplicationBusinessException} will be thrown and the error
     * message will be returned as a Json by the web service.
     */
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

    /**
     * Represent the business logic to update a customer.
     */
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

    /**
     * Represent the business logic to delete a customer.
     */
    @Override
    public void restDelete(Long id) {
        UtilClass.requireNonNull(id, "Customer Id cant be null");
        customerRepository.deleteById(id);
    }


}
