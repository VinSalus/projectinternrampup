package com.example.projectInternRampUp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductOfferingDto implements Serializable {
    //This Dto handles what informations are displayed on a get all productOffering request.
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Double unitPrice;

    private Double discount;

    private Integer quantity;

    private Double totalPrice;

}