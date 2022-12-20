package com.example.projectInternRampUp.entities;

import com.example.projectInternRampUp.dtos.ProductOfferingCharacteristicDto;
import com.example.projectInternRampUp.enumerations.CharacteristicType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_characteristic")
public class Characteristic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    @ManyToOne
    @NotNull
    private TimePeriod validFor;
    @NotNull
    private Integer characteristicType;
    @NotNull
    @NotEmpty
    @NotBlank
    private String valueType;
    @ManyToMany(mappedBy = "characteristics")
    private Set<ProductOffering> producOfferings = new HashSet<>();
    private Boolean isActive = true;

    public Characteristic() {

    }

    public Characteristic(Integer id, String name, TimePeriod validFor, CharacteristicType characteristicType, String valueType, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.validFor = validFor;
        setCharacteristicType(characteristicType);
        this.valueType = valueType;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimePeriod getValidFor() {
        return validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public void setCharacteristicType(CharacteristicType characteristicType) {
        if (characteristicType != null) {
            this.characteristicType = characteristicType.getCode();
        }
    }

    public CharacteristicType getCharacteristicType() {
        return CharacteristicType.valueOf(characteristicType);
    }

    public List<ProductOfferingCharacteristicDto> getProductOffering() {
        List<ProductOfferingCharacteristicDto> productOfferingDto = producOfferings.stream().map(obj -> new ProductOfferingCharacteristicDto(obj)).collect(Collectors.toList());
        return productOfferingDto;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public int hashCode() {
        return Objects.hash(characteristicType, validFor, valueType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Characteristic other = (Characteristic) obj;
        return characteristicType == other.characteristicType && Objects.equals(validFor, other.validFor)
                && Objects.equals(valueType, other.valueType);
    }


}