package com.example.projectInternRampUp.dtos;

import com.example.projectInternRampUp.entities.Address;
import com.example.projectInternRampUp.enumerations.AddressType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AddressDto implements Serializable {
    //This Dto handles what informations are displayed when address is part of a request.

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String street;
    private Integer houseNumber;
    private String neighborhood;
    private String complement;
    private Integer zipCode;
    private AddressType addressType;
    private String country;

    public AddressDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AddressDto(Address obj) {
        super();
        this.id = obj.getId();
        this.street = obj.getStreet();
        this.houseNumber = obj.getHouseNumber();
        this.neighborhood = obj.getNeighborhood();
        this.complement = obj.getComplement();
        this.zipCode = obj.getZipCode();
        this.addressType = obj.AddressType();
        this.country = obj.getCountry();
    }
}