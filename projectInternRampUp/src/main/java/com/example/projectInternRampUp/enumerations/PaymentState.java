package com.example.projectInternRampUp.enumerations;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentState {
    @JsonProperty("1") PAID(1),
    @JsonProperty("2") PENDING(2),
    @JsonProperty("3") DENIED(3);

    private int code;

    private PaymentState(int code) {
        this.code = code;

    }

    public int getCode() {
        return code;
    }

    public static PaymentState valueOf(int code) {
        for (PaymentState value : PaymentState.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Authorities code");
    }

}