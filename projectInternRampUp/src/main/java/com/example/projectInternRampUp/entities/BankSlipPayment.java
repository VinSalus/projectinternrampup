package com.example.projectInternRampUp.entities;

import com.example.projectInternRampUp.enumerations.PaymentState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "tb_bankslip_payment")
@Getter
@Setter
public class BankSlipPayment extends Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant startDate;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant dueDate;

    public BankSlipPayment() {

    }

    public BankSlipPayment(Integer id, PaymentState paymentState, Order order, Instant startDate, Instant dueDate) {
        super(id, paymentState, order);
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
}