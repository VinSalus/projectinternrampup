package com.example.projectInternRampUp.enumerations;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CustomerType {
    @JsonProperty("1") LEGAL_PERSON(1),
    @JsonProperty("2") NATURAL_PERSON(2),
    @JsonProperty("3") TECHNICAL(3);

    private int code;

    private CustomerType(int code) {
        this.code = code;

    }

    public int getCode() {
        return code;
    }

    public static CustomerType valueOf(Integer code) {
        if (code == null) {
            return null;
        }
        for (CustomerType value : CustomerType.values()) {

            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Authorities code");
    }

}

