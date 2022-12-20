package com.example.projectInternRampUp.dtos;

import com.example.projectInternRampUp.entities.Characteristic;
import com.example.projectInternRampUp.entities.ProductOffering;
import com.example.projectInternRampUp.entities.TimePeriod;
import com.example.projectInternRampUp.enumerations.POState;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
public class ProductOfferingCharacteristicDto implements Serializable {
    //This DTO is used for methods in class "ProductOfferingService" to pass all the data required for a request and what to recover from a get order #id request.
    //The characteristic in the class name is meant to convey that in it's service class it allows for the creation of a characteristic or utilization of an existing one.
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private TimePeriod validFor;

    private POState postate;

    private Boolean sellIndicator;
    @JsonProperty(access = WRITE_ONLY)
    private List<Characteristic> characteristics;

    public ProductOfferingCharacteristicDto() {
        super();
    }

    public ProductOfferingCharacteristicDto(ProductOffering obj) {
        this.id = obj.getId();
        this.name = obj.getName();
        this.validFor = obj.getValidFor();
        this.postate = obj.getPOState();
        this.sellIndicator = obj.getSellIndicator();
    }
}