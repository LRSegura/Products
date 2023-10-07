package com.manage.product.service.product;

import com.manage.product.api.exception.ApplicationBusinessException;
import com.manage.product.api.rest.model.CrudRestOperations;
import com.manage.product.api.rest.model.JsonData;
import com.manage.product.api.rest.product.JsonProduct;
import com.manage.product.api.util.UtilClass;
import com.manage.product.model.product.Product;
import com.manage.product.repository.product.ProductRepository;
import com.manage.product.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProductService extends AbstractService implements CrudRestOperations<JsonProduct> {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<? extends JsonData> restGet() {
        return productRepository.findAll().stream().map(product -> new JsonProduct(product.getId(), product.getName(),
                product.getDescription(), product.getDefaultPrice())).toList();
    }

    @Override
    public void restSave(JsonProduct json) {
        UtilClass.requireNonNull(json.name(), "Product name cant be null");
        UtilClass.requireNonNull(json.description(), "Product description cant be null");
        UtilClass.requireNonNull(json.defaultPrice(), "Product price cant be null");
        UtilClass.requireNonBlankString(json.name(), "Product name cant be empty");
        UtilClass.requireNonBlankString(json.description(), "Product description cant be empty");
        if (productRepository.existsProductByName(json.name())) {
            throw new ApplicationBusinessException("Duplicated product name");
        }
        Product product = new Product();
        product.setName(json.name());
        product.setDescription(json.description());
        product.setDefaultPrice(json.defaultPrice());
        productRepository.save(product);
    }

    @Override
    public void restUpdate(JsonProduct json) {
        UtilClass.requireNonNull(json.id(), "Product Id cant be null");

        Product product = getEntity(Product.class, json.id());

        if (Objects.nonNull(json.name()) && !json.name().isEmpty()) {
            product.setName(json.name());
        }
        if (Objects.nonNull(json.description()) && !json.description().isEmpty()) {
            product.setDescription(json.description());
        }
        if (Objects.nonNull(json.defaultPrice())) {
            product.setDefaultPrice(json.defaultPrice());
        }
        productRepository.save(product);
    }

    @Override
    public void restDelete(Long id) {
        UtilClass.requireNonNull(id, "Product Id cant be null");
        //TODO make validation
        productRepository.deleteById(id);
    }


}
