package com.sam.demo.enums;

import javax.persistence.AttributeConverter;

public class SexEnumConvert implements AttributeConverter<SexEnum, String> {

    @Override
    public String convertToDatabaseColumn(SexEnum sex) {
        return sex.getValue();
    }

    @Override
    public SexEnum convertToEntityAttribute(String dbData) {
        return SexEnum.parseValue(dbData);
    }

}
