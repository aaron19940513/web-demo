package com.sam.demo.helper.excel;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.*;

/**
 * Column style
 *
 * @author Rakim
 * @date 2018 /11/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ColumnStyle {

    /**
     * Data background color
     *
     * @return color key  refer to {@link IndexedColors}
     */
    String color() default "";

    /**
     * Data format string
     *
     * @return data format string  refer to  {@link BuiltinFormats#_formats}
     */
    String dataFormat() default "";

    /**
     * Header background color
     *
     * @return color key refer to {@link IndexedColors}
     */
    String headerColor() default "";


    /**
     * Set specific file value cell style
     *
     * @return SpecFieldValueStyle[] spec field value style [ ]
     */
    SpecFieldValueStyle[] specFieldValueStyle() default {};


}
