package com.example.projectInternRampUp.dtos;

import com.example.projectInternRampUp.entities.Address;
import com.example.projectInternRampUp.entities.User;
import com.example.projectInternRampUp.enumerations.CustomerType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
public class CustomerPostDto implements Serializable {
    //This DTO is used for methods in class "CustomerService" to pass all the data required for a request and what to recover from a get customer #id request.
    //The other Dto related to Customer which is "CustomerDto" handles what informations are displayed on requests.
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String customerName;

    private String documentNumber;

    private String customerStatus;
    private Integer customerType;

    private String creditScore;

    @JsonProperty(access = WRITE_ONLY)
    private String password;

    private User user;
    private Set<OrderDtoFindId> orders = new HashSet<>();

    private Set<Address> addresses = new HashSet<>();

    public CustomerType getCustomerType() {
        return CustomerType.valueOf(customerType);
    }

    public void setCustomerType(CustomerType customerType) {
        if (customerType != null) {
            this.customerType = customerType.getCode();
        }
    }

    public Set<OrderDtoFindId> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderDtoFindId> orders) {
        this.orders = orders;
    }
}