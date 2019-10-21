package com.sam.demo.enums;


import java.io.Serializable;

public enum SexEnum implements Serializable {
    MALE("male"),
    FEMALE("female");

    private String value;

    public String getValue() {
        return value;

    }

    private SexEnum(String value) {
        this.value = value;

    }

    public static SexEnum parseValue(String value) {
        for (SexEnum s : SexEnum.values()) {
            if (s.value.equalsIgnoreCase(value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;

    }

}
