package com.manage.product.model.product;

import com.manage.product.model.AbstractEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity(name = "Product")
@AttributeOverride(name = "id", column = @Column(name = "Id_Product"))
public class Product extends AbstractEntity {

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "Default_Price", nullable = false)
    private BigDecimal defaultPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(defaultPrice, product.defaultPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, defaultPrice);
    }
}
