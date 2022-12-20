package com.example.projectInternRampUp.entities;

import com.example.projectInternRampUp.enumerations.AddressType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TB_ADDRESS")
@Getter
@Setter
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String street;
    @NotNull
    private Integer houseNumber;
    @NotNull
    @NotEmpty
    @NotBlank
    private String neighborhood;
    private String complement;
    @NotNull
    private Integer zipCode;
    @NotNull
    private Integer addressType;
    @NotNull
    @NotEmpty
    @NotBlank
    private String country;
    @ManyToOne
    @JoinColumn
    private Customer client;

    public Address() {

    }
    public Address(Integer id, String street, Integer houseNumber, String neighborhood, String complement,
                   Integer zipCode, AddressType addressType, String country, Customer client) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.zipCode = zipCode;
        setAddressType(addressType);
        this.country = country;
        this.client = client;
    }

    public void setAddressType(AddressType addressType) {
        if (addressType != null) {
            this.addressType = addressType.getCode();
        }
    }

    public AddressType AddressType() {
        return AddressType.valueOf(addressType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressType, client);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        return addressType == other.addressType && Objects.equals(client, other.client);
    }

}