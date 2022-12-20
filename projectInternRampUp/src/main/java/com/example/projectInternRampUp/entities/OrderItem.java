package com.example.projectInternRampUp.entities;

import com.example.projectInternRampUp.entities.pk.OrderItemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_orderitem")

public class OrderItem implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();
    private Double unitPrice;
    private Double discount;
    private Integer quantity;
    private Double totalPrice;

    public OrderItem() {

    }

    public OrderItem(Order order, ProductOffering productOffering, Double unitPrice, Double discount, Integer quantity) {
        super();
        id.setOrder(order);
        id.setProductOffering(productOffering);
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice(unitPrice, discount, quantity);
    }

    @JsonIgnore
    public Order getOrder() {
        return id.getOrder();
    }

    public void setOrder(Order order) {
        id.setOrder(order);
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ProductOffering getProductOffering() {
        return id.getProductOffering();
    }

    public void setProductOffering(ProductOffering productOffering) {
        id.setProductOffering(productOffering);
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private Double calculateTotalPrice(Double unitPrice, Double discount, Integer quantity) {
        return quantity * unitPrice * (1 - discount / 100);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItem other = (OrderItem) obj;
        return Objects.equals(id, other.id);
    }
}