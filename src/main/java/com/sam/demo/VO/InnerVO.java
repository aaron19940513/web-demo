package com.sam.demo.VO;

import com.sam.demo.enums.DisplayTypeEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface InnerVO {

}
