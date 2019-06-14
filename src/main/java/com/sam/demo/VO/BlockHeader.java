package com.sam.demo.VO;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface BlockHeader {
    String blockKey() default  "";

    String blockTitle() default  "";
}
