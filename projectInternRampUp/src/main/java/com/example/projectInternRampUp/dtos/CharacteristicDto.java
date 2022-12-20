package com.example.projectInternRampUp.dtos;

import com.example.projectInternRampUp.entities.Characteristic;
import com.example.projectInternRampUp.entities.TimePeriod;
import com.example.projectInternRampUp.enumerations.CharacteristicType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CharacteristicDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private TimePeriod validFor;
    private CharacteristicType characteristicType;
    private String valueType;

    public CharacteristicDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CharacteristicDto(Characteristic obj) {
        super();
        this.id = obj.getId();
        this.name = obj.getName();
        this.validFor = obj.getValidFor();
        this.characteristicType = obj.getCharacteristicType();
        this.valueType = obj.getValueType();
    }
}