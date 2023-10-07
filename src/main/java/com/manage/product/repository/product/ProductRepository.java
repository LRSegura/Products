package com.manage.product.repository.product;

import com.manage.product.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsProductByName(String name);
    List<Product> findAllByOrderByRegisterDateDesc();
}
