package com.example.projectInternRampUp.dtos;

import com.example.projectInternRampUp.entities.Address;
import com.example.projectInternRampUp.entities.BankSlipPayment;
import com.example.projectInternRampUp.entities.CreditCartPayment;
import com.example.projectInternRampUp.entities.Customer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class OrderDtoFindId implements Serializable {
    private static final long serialVersionUID = 1L;
    //This DTO is used for methods in class "OrderService" to pass all the data required for a request and what to recover from a get order #id request.

    private Integer id;

    private Instant instant;

    private BankSlipPayment bankSlipPayment;

    private CreditCartPayment creditCardPayment;

    private Address deliveryAddress;

    private Customer client;

    private List<ProductOfferingDto> products;
}