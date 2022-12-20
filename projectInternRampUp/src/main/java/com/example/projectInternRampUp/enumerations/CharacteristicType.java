package com.example.projectInternRampUp.enumerations;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CharacteristicType {
    @JsonProperty("1") USER(1),
    @JsonProperty("2") INTERNAL(2),
    @JsonProperty("3") TECHNICAL(3);

    private int code;

    private CharacteristicType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CharacteristicType valueOf(int code) {
        for (CharacteristicType value : CharacteristicType.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid CharacteristicType code");
    }

}