package com.sam.demo.helper.excel;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.*;

/**
 * Spec field value style
 *
 * @author Rakim
 * @date 2018 /11/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface SpecFieldValueStyle {
    /**
     * special field value
     *
     * @return if filed is reference type then  return {@link JSONObject#toString()} ,otherwise return {@link Object#toString()}
     */
    String specFiledValue() default "defaultSpecFieldValue";

    /**
     * special data background color
     *
     * @return color key  refer to {@link IndexedColors}
     */
    String color() default "";

    /**
     * special data format string
     *
     * @return data format string  refer to  {@link BuiltinFormats#_formats}
     */
    String dataFormat() default "";

}
