package com.manage.product.model.purchase;

import com.manage.product.api.rest.purchase.JsonPurchase;
import com.manage.product.model.AbstractEntity;
import com.manage.product.model.customer.Customer;
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
@Entity(name = "Purchase")
@AttributeOverride(name = "id", column = @Column(name = "Id_Purchase"))
public class Purchase extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "Id_Product", nullable = false)
    private Product product;

    @Column(name = "Total", nullable = false)
    private BigDecimal total;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "Id_Customer", nullable = false)
    private Customer customer;

    public JsonPurchase geJsonPurchase(){
        return new JsonPurchase(getId(),getRegisterDateFormat(), getUpdateDateFormat(), getProduct().getId(),
                getQuantity(),getTotal(),
                getCustomer().getId(), getProduct().getJsonProduct(), getCustomer().getJsonCustomer());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Purchase purchase)) return false;
        if (!super.equals(object)) return false;
        return Objects.equals(product, purchase.product) && Objects.equals(total, purchase.total) && Objects.equals(quantity, purchase.quantity) && Objects.equals(customer, purchase.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), product, total, quantity, customer);
    }
}
