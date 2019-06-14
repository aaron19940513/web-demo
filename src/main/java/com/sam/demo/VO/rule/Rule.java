package com.sam.demo.VO.rule;




import com.sam.demo.enums.RuleTypeEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Rule {

    RuleTypeEnum[] rules();

    boolean required() default true;

    int max() default 0;

    int min() default 0;
}
