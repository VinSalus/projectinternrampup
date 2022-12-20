package com.example.projectInternRampUp.entities.pk;

import com.example.projectInternRampUp.entities.Order;
import com.example.projectInternRampUp.entities.ProductOffering;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderItemPK implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productOffering_id")
    private ProductOffering productOffering;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductOffering getProductOffering() {
        return productOffering;
    }

    public void setProductOffering(ProductOffering productOffering) {
        this.productOffering = productOffering;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, productOffering); //both products and orders identify the product
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItemPK other = (OrderItemPK) obj;
        return Objects.equals(order, other.order)
                && Objects.equals(productOffering, other.productOffering);
    }


}