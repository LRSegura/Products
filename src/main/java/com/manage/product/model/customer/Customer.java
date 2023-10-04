package com.manage.product.model.customer;

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
@Entity(name = "Customer")
@AttributeOverride(name = "id", column = @Column(name = "Id_Customer"))
public class Customer extends AbstractEntity {

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Column(name = "Discount", nullable = false)
    private Boolean discount;

    @Column(name = "Discount_Value", nullable = false)
    private BigDecimal discountValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, customer.name) && Objects.equals(discount, customer.discount) && Objects.equals(discountValue, customer.discountValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, discount, discountValue);
    }
}
