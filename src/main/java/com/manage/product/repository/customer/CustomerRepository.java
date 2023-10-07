package com.manage.product.repository.customer;

import com.manage.product.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByName(String name);
    List<Customer> findAllByOrderByRegisterDateDesc();
}
