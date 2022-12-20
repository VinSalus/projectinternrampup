package com.example.projectInternRampUp.enumerations;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AddressType {
    @JsonProperty("1") HOME_ADDRESS(1),
    @JsonProperty("2") BUSINESS_ADDRESS(2),
    @JsonProperty("3") SHIPPING_ADDRESS(3);

    private int code;

    private AddressType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AddressType valueOf(int code) {
        for (AddressType value : AddressType.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid AddressType code");
    }
}
