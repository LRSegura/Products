package com.manage.product.service.purchase;


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
    public List<? extends JsonData> restGetAll() {
        return purchaseRepository.findAll().stream().map(Purchase::geJsonPurchase).toList();
    }

    @Override
    public JsonPurchase restGet(Long id) {
        return ((Purchase)getEntity(Purchase.class, id)).geJsonPurchase();
    }

    @Override
    public void restSave(JsonPurchase json) {
        UtilClass.requireNonNull(json.idProduct(), "Product Id cant be null");
        UtilClass.requireNonNull(json.idCustomer(), "Customer Id cant be null");
        UtilClass.requireNonNull(json.quantity(), "Quantity cant be null");

        Product product = getEntity(Product.class, json.idProduct());
        Customer customer = getEntity(Customer.class,json.idCustomer());
        BigDecimal total = getTotal(json);

        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setCustomer(customer);
        purchase.setQuantity(json.quantity());
        purchase.setTotal(total);
        purchaseRepository.save(purchase);
    }

    private BigDecimal getTotal(JsonPurchase json){
        Product product = getEntity(Product.class, json.idProduct());
        BigDecimal priceValue =
                priceRepository.findPriceByValue(json.quantity(), product.getId()).map(Price::getPrice).orElse(product.getDefaultPrice());
        Customer customer = getEntity(Customer.class,json.idCustomer());
        if(customer.getDiscount()){
            BigDecimal discount = customer.getDiscountValue().divide(BigDecimal.valueOf(100), 2,
                    RoundingMode.CEILING).multiply(priceValue);
            priceValue = priceValue.subtract(discount);
        }
        return priceValue.multiply(BigDecimal.valueOf(json.quantity()));
    }
    @Override
    public void restUpdate(JsonPurchase json) {
        UtilClass.requireNonNull(json.id(), "Purchase Id cant be null");

        Purchase purchase = getEntity(Purchase.class, json.id());
        boolean isPriceChanged = false;
        if (Objects.nonNull(json.idProduct())) {
            purchase.setProduct(getEntity(Product.class,json.idProduct()));
            isPriceChanged = true;
        }
        if (Objects.nonNull(json.idCustomer())) {
            purchase.setCustomer(getEntity(Customer.class,json.idCustomer()));
            isPriceChanged = true;
        }
        if (Objects.nonNull(json.quantity())) {
            purchase.setQuantity(json.quantity());
            isPriceChanged = true;
        }
        if(isPriceChanged){
            BigDecimal total = getTotal(json);
            purchase.setTotal(total);
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
