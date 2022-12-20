package com.example.projectInternRampUp.entities;

import com.example.projectInternRampUp.enumerations.PaymentState;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer paymentState;

    @JsonIgnore
    @OneToOne
    @MapsId
    private Order order;

    public Payment() {

    }

    public Payment(Integer id, PaymentState paymentState, Order order) {
        super();
        this.id = id;
        setPaymentState(paymentState);
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPaymentState(PaymentState authority) {
        if (authority != null) {
            this.paymentState = authority.getCode();
        }
    }


    public PaymentState getPaymentState() {
        return PaymentState.valueOf(paymentState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentState);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Payment other = (Payment) obj;
        return paymentState == other.paymentState;
    }


}