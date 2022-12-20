package com.example.projectInternRampUp.entities;

import com.example.projectInternRampUp.dtos.AddressDto;
import com.example.projectInternRampUp.dtos.CustomerDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "tb_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull
    private Instant instant;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @ManyToOne
    @NotNull
    private Address deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull
    private Customer client; //There can be only one instance of client

    @OneToMany(mappedBy = "id.order")
    //@NotEmpty
    private Set<OrderItem> items = new HashSet<>();

    private Boolean isActive = true;

    public Order() {

    }

    public Order(Integer id, Instant instant, Payment payment, Address deliveryAddress, Customer client, Boolean isActive) {
        this.id = id;
        this.instant = instant;
        this.payment = payment;
        this.deliveryAddress = deliveryAddress;
        this.client = client;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public AddressDto getDeliveryAddress() {
        AddressDto addressDto = new AddressDto(deliveryAddress);
        return addressDto;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public CustomerDto getClient() {
        CustomerDto customerDto = new CustomerDto(client);
        return customerDto;
    }

    public void setClient(Customer client) {
        this.client = client;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String userAssociated() {
        return client.getUser().getEmail();
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, instant);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        return Objects.equals(client, other.client) && Objects.equals(instant, other.instant);
    }


}