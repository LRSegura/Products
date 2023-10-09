package com.manage.product;

import com.manage.product.model.customer.Customer;
import com.manage.product.model.price.Price;
import com.manage.product.model.product.Product;
import com.manage.product.repository.customer.CustomerRepository;
import com.manage.product.repository.price.PriceRepository;
import com.manage.product.repository.product.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LoadData implements ApplicationRunner {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;

    public LoadData(CustomerRepository customerRepository, ProductRepository productRepository, PriceRepository priceRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Customer customer1 = new Customer();
        customer1.setName("Luis");
        customer1.setDiscount(true);
        customer1.setDiscountValue(BigDecimal.valueOf(20));
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Maria");
        customer2.setDiscount(true);
        customer2.setDiscountValue(BigDecimal.valueOf(30));
        customerRepository.save(customer2);

        Product product1 = new Product();
        product1.setName("Book");
        product1.setDescription("This is a book");
        product1.setDefaultPrice(BigDecimal.valueOf(300));
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Pencil");
        product2.setDescription("This is a pencil");
        product2.setDefaultPrice(BigDecimal.valueOf(50));
        productRepository.save(product2);

        Price price1 = new Price();
        price1.setStartValue(10);
        price1.setEndValue(20);
        price1.setPrice(BigDecimal.valueOf(250));
        price1.setProduct(product1);
        priceRepository.save(price1);

        Price price2 = new Price();
        price2.setStartValue(21);
        price2.setEndValue(30);
        price2.setPrice(BigDecimal.valueOf(220));
        price2.setProduct(product1);
        priceRepository.save(price2);

        Price price3 = new Price();
        price3.setStartValue(10);
        price3.setEndValue(20);
        price3.setPrice(BigDecimal.valueOf(40));
        price3.setProduct(product2);
        priceRepository.save(price3);

        Price price4 = new Price();
        price4.setStartValue(21);
        price4.setEndValue(30);
        price4.setPrice(BigDecimal.valueOf(50));
        price4.setProduct(product2);
        priceRepository.save(price4);
    }
}
