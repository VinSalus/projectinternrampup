package com.example.projectInternRampUp.enumerations;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Authorities {
    @JsonProperty("1") ADMIN(1),
    @JsonProperty("2") OPERATOR(2);

    private int code;

    private Authorities(int code) {
        this.code = code;

    }

    public int getCode() {
        return code;
    }

    public static Authorities valueOf(Integer code) {
        if (code == null) {
            return null;
        }
        for (Authorities value : Authorities.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Authorities code");
    }

}