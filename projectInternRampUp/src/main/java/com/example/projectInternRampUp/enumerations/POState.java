package com.example.projectInternRampUp.enumerations;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum POState {
    @JsonProperty("1") ACTIVE(1),
    @JsonProperty("2") DEFINITION(2),
    @JsonProperty("3") TECHNICAL(3);

    private int code;

    private POState(int code) {
        this.code = code;

    }

    public int getCode() {
        return code;
    }

    public static POState valueOf(int code) {
        for (POState value : POState.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid POState code");
    }

}