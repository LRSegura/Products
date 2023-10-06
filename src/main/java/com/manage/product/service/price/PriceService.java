package com.manage.product.service.price;

import com.manage.product.api.exception.ApplicationBusinessException;
import com.manage.product.api.rest.customer.JsonCustomer;
import com.manage.product.api.rest.model.CrudRestOperations;
import com.manage.product.api.rest.model.JsonData;
import com.manage.product.api.rest.price.JsonPrice;
import com.manage.product.api.util.UtilClass;
import com.manage.product.model.customer.Customer;
import com.manage.product.model.price.Price;
import com.manage.product.model.product.Product;
import com.manage.product.repository.price.PriceRepository;
import com.manage.product.repository.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PriceService implements CrudRestOperations<JsonPrice> {

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;

    public PriceService(PriceRepository priceRepository, ProductRepository productRepository) {
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<? extends JsonData> restGet() {
        return priceRepository.findAll().stream().map(price -> new JsonPrice(price.getId(),
                price.getStartValue(), price.getEndValue(), price.getPrice(), price.getProduct().getId())).toList();
    }

    @Override
    public void restSave(JsonPrice json) {
        UtilClass.requireNonNull(json.startValue(), "Price start value cant be null");
        UtilClass.requireNonNull(json.endValue(), "Price end value cant be null");
        UtilClass.requireNonNull(json.price(), "Price value cant be null");
        UtilClass.requireNonNull(json.idProduct(), "Product cant be null");
        Product product = getProduct(json.idProduct());

        priceRepository.findCrossRange(json.startValue(), json.endValue())
                .ifPresent(price -> {
                    throw new ApplicationBusinessException("Your values are crossed. Already exists a price range " +
                            "that start in " + price.getStartValue()
                            + " and end in "+ price.getEndValue());
                });

        Price price = new Price();
        price.setStartValue(json.startValue());
        price.setEndValue(json.endValue());
        price.setPrice(json.price());
        price.setProduct(product);
        priceRepository.save(price);
    }

    @Override
    public void restUpdate(JsonPrice json) {
        UtilClass.requireNonNull(json.id(), "Price Id cant be null");

        Price price = getPrice(json.id());

        if (Objects.nonNull(json.startValue())) {
            price.setStartValue(json.startValue());
        }
        if (Objects.nonNull(json.endValue())) {
            price.setEndValue(json.endValue());
        }
        if (Objects.nonNull(json.price())) {
            price.setPrice(json.price());
        }
        if (Objects.nonNull(json.idProduct())) {
            price.setProduct(getProduct(json.idProduct()));
        }
        priceRepository.save(price);
    }

    private Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(()-> {
            String errorMessage = "Product not found. Id " + id;
            return new ApplicationBusinessException(errorMessage);
        });
    }
    private Price getPrice(Long id){
        return priceRepository.findById(id).orElseThrow(()-> {
            String errorMessage = "price not found. Id " + id;
            return new ApplicationBusinessException(errorMessage);
        });
    }

    @Override
    public void restDelete(Long id) {
        UtilClass.requireNonNull(id, "Price Id cant be null");
        //TODO make validation
        priceRepository.deleteById(id);
    }


}
