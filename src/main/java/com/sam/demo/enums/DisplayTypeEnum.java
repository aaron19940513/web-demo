package com.sam.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DisplayTypeEnum {

    INPUT("input"),

    SELECTED("selected"),

    CHECKBOX("checkbox");

    private String value;
}
