package com.example.projectInternRampUp.entities;

import com.example.projectInternRampUp.enumerations.PaymentState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_creditcard_payment")
@Getter
@Setter
public class CreditCartPayment extends Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer installments;

    public CreditCartPayment() {

    }

    public CreditCartPayment(Integer id, PaymentState paymentState, Order order, Integer installments) {
        super(id, paymentState, order);
        this.installments = installments;
    }
}