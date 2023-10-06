package com.manage.product.service.purchase;

import com.manage.product.api.exception.ApplicationBusinessException;
import com.manage.product.api.rest.model.CrudRestOperations;
import com.manage.product.api.rest.model.JsonData;
import com.manage.product.api.rest.product.JsonProduct;
import com.manage.product.api.rest.purchase.JsonPurchase;
import com.manage.product.api.util.UtilClass;
import com.manage.product.model.customer.Customer;
import com.manage.product.model.product.Product;
import com.manage.product.model.purchase.Purchase;
import com.manage.product.repository.customer.CustomerRepository;
import com.manage.product.repository.price.PriceRepository;
import com.manage.product.repository.product.ProductRepository;
import com.manage.product.repository.purchase.PurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PurchaseService implements CrudRestOperations<JsonPurchase> {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    public PurchaseService(ProductRepository productRepository, PriceRepository priceRepository, CustomerRepository customerRepository,
                           PurchaseRepository purchaseRepository) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public List<? extends JsonData> restGet() {
        return purchaseRepository.findAll().stream().map(purchase -> new JsonPurchase(purchase.getId(),
                purchase.getProduct().getId(),
                purchase.getQuantity(), purchase.getTotal(), purchase.getCustomer().getId())).toList();
    }

    @Override
    public void restSave(JsonPurchase json) {
        UtilClass.requireNonNull(json.idProduct(), "Product Id cant be null");
        UtilClass.requireNonNull(json.idCustomer(), "Customer Id cant be null");
        UtilClass.requireNonNull(json.quantity(), "Quantity cant be null");

        Purchase purchase = new Purchase();
        purchase.setProduct(getProduct(json.idProduct()));
        purchase.setCustomer(getCustomer(json.idCustomer()));
        purchase.setQuantity(json.quantity());
        purchase.setTotal(BigDecimal.ONE);
        purchaseRepository.save(purchase);
    }

    @Override
    public void restUpdate(JsonPurchase json) {
        UtilClass.requireNonNull(json.id(), "Purchase Id cant be null");

        Purchase purchase = purchaseRepository.findById(json.id()).orElseThrow(() -> {
            String errorMessage = "Purchase not found. Id " + json.id();
            return new ApplicationBusinessException(errorMessage);
        });
        if (Objects.nonNull(json.idProduct())) {
            purchase.setProduct(getProduct(json.idProduct()));
        }
        if (Objects.nonNull(json.idCustomer())) {
            purchase.setCustomer(getCustomer(json.idCustomer()));
        }
        if (Objects.nonNull(json.quantity())) {
            purchase.setQuantity(json.quantity());
        }
        purchaseRepository.save(purchase);
    }

    @Override
    public void restDelete(Long id) {
        UtilClass.requireNonNull(id, "Purchase Id cant be null");
        //TODO make validation
        purchaseRepository.deleteById(id);
    }
    private Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(()-> {
            String errorMessage = "Product not found. Id " + id;
            return new ApplicationBusinessException(errorMessage);
        });
    }

    private Customer getCustomer(Long id){
        return customerRepository.findById(id).orElseThrow(()-> {
            String errorMessage = "Customer not found. Id " + id;
            return new ApplicationBusinessException(errorMessage);
        });
    }

}
