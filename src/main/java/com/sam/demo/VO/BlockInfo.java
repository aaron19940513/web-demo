package com.sam.demo.VO;

import com.sam.demo.enums.DisplayTypeEnum;
import org.apache.tomcat.util.digester.Rules;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface BlockInfo {
    String key() default "";
    String title() default "";
    String value() default  "";
    DisplayTypeEnum displayType() default DisplayTypeEnum.INPUT;
    boolean hidden() default false;
    boolean editable() default true;

}
