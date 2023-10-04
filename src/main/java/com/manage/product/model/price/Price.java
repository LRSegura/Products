package com.manage.product.model.price;

import com.manage.product.model.AbstractEntity;
import com.manage.product.model.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity(name = "Price")
@AttributeOverride(name = "id", column = @Column(name = "Id_Price"))
public class Price extends AbstractEntity {

    @Column(name = "Start_Value", nullable = false)
    private Integer startValue;

    @Column(name = "End_Value")
    private Integer endValue;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "Id_Product", nullable = false)
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price price1)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(startValue, price1.startValue) && Objects.equals(endValue, price1.endValue) && Objects.equals(price, price1.price) && Objects.equals(product, price1.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startValue, endValue, price, product);
    }
}
