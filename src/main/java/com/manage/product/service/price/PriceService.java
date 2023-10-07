package com.manage.product.service.price;

import com.manage.product.api.exception.ApplicationBusinessException;
import com.manage.product.api.rest.model.CrudRestOperations;
import com.manage.product.api.rest.model.JsonData;
import com.manage.product.api.rest.price.JsonPrice;
import com.manage.product.api.util.UtilClass;
import com.manage.product.model.price.Price;
import com.manage.product.model.product.Product;
import com.manage.product.repository.price.PriceRepository;
import com.manage.product.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PriceService  extends AbstractService implements CrudRestOperations<JsonPrice> {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public List<? extends JsonData> restGetAll() {
        return priceRepository.findAll().stream().map(Price::getJsonPrice).toList();
    }

    @Override
    public JsonPrice restGet(Long id) {
        return ((Price)getEntity(Price.class, id)).getJsonPrice();
    }

    @Override
    public void restSave(JsonPrice json) {
        UtilClass.requireNonNull(json.startValue(), "Price start value cant be null");
        UtilClass.requireNonNull(json.endValue(), "Price end value cant be null");
        UtilClass.requireNonNull(json.price(), "Price value cant be null");
        UtilClass.requireNonNull(json.idProduct(), "Product cant be null");

        Integer startValue = json.startValue();
        Integer endValue = json.endValue();

        if(startValue.compareTo(endValue) == 0){
            throw new ApplicationBusinessException("Start_value must be not equal to end_value");
        }

        if(startValue.compareTo(endValue) > 0){
            throw new ApplicationBusinessException("Start_value must be less than end_value");
        }

        List<Price> prices = priceRepository.findCrossRange(json.startValue(), json.endValue(), json.idProduct());
        if(!prices.isEmpty()){
            StringBuilder error = new StringBuilder();
            for (Price priceSaved: prices) {
                error.append("Your values are crossed. Already exists a price range that start in ")
                        .append(priceSaved.getStartValue()).append(" and end in ").append(priceSaved.getEndValue()).append(". ");
            }
            throw new ApplicationBusinessException(error);
        }

        Price price = new Price();
        price.setStartValue(json.startValue());
        price.setEndValue(json.endValue());
        price.setPrice(json.price());
        price.setProduct(getEntity(Product.class, json.idProduct()));
        priceRepository.save(price);
    }

    @Override
    public void restUpdate(JsonPrice json) {
        UtilClass.requireNonNull(json.id(), "Price Id cant be null");

        Price price = getEntity(Price.class, json.id());

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
            price.setProduct(getEntity(Product.class, json.idProduct()));
        }
        priceRepository.save(price);
    }

    @Override
    public void restDelete(Long id) {
        UtilClass.requireNonNull(id, "Price Id cant be null");
        //TODO make validation
        priceRepository.deleteById(id);
    }


}
