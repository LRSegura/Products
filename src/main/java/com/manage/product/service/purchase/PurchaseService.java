package com.manage.product.service.purchase;

import com.manage.product.api.exception.ApplicationBusinessException;
import com.manage.product.api.rest.model.CrudRestOperations;
import com.manage.product.api.rest.model.JsonData;
import com.manage.product.api.rest.purchase.JsonPurchase;
import com.manage.product.api.util.UtilClass;
import com.manage.product.model.customer.Customer;
import com.manage.product.model.price.Price;
import com.manage.product.model.product.Product;
import com.manage.product.model.purchase.Purchase;
import com.manage.product.repository.price.PriceRepository;
import com.manage.product.repository.purchase.PurchaseRepository;
import com.manage.product.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PurchaseService extends AbstractService implements CrudRestOperations<JsonPurchase> {


    private final PriceRepository priceRepository;

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PriceRepository priceRepository, PurchaseRepository purchaseRepository) {
        this.priceRepository = priceRepository;
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

        Product product = getEntity(Product.class, json.idProduct());
        BigDecimal priceValue =
                priceRepository.findPriceByValue(json.quantity()).map(Price::getPrice).orElse(product.getDefaultPrice());
        Customer customer = getEntity(Customer.class,json.idCustomer());
        if(customer.getDiscount()){
            BigDecimal discount = customer.getDiscountValue().divide(BigDecimal.valueOf(100), 2,
                    RoundingMode.CEILING).multiply(priceValue);
            priceValue = priceValue.subtract(discount);
        }
        BigDecimal total = priceValue.multiply(BigDecimal.valueOf(json.quantity()));

        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setCustomer(customer);
        purchase.setQuantity(json.quantity());
        purchase.setTotal(total);
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
            purchase.setProduct(getEntity(Product.class,json.idProduct()));
        }
        if (Objects.nonNull(json.idCustomer())) {
            purchase.setCustomer(getEntity(Customer.class,json.idCustomer()));
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
}
