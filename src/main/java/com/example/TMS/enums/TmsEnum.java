package com.example.TMS.enums;

public enum TmsEnum {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");
    private String value;

    TmsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}