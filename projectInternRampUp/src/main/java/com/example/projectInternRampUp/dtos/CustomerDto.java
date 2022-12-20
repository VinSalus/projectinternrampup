package com.example.projectInternRampUp.dtos;

import com.example.projectInternRampUp.entities.Customer;
import com.example.projectInternRampUp.enumerations.CustomerType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
public class CustomerDto implements Serializable {

    //This Dto handles what informations are displayed on a get all customers request.
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String customerName;
    private String documentNumber;
    private String customerStatus;
    private CustomerType customerType;
    private String creditScore;
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    public CustomerDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CustomerDto(Customer obj) {
        super();
        this.id = obj.getId();
        this.customerName = obj.getCustomerName();
        this.documentNumber = obj.getDocumentNumber();
        this.customerStatus = obj.getCustomerStatus();
        this.customerType = obj.getCustomerType();
        this.creditScore = obj.getCreditScore();
        this.password = obj.getPassword();
    }
}