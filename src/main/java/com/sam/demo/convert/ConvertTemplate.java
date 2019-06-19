package com.sam.demo.convert;

import java.util.List;

/**
 * @author sam
 * @date 2019/6/19 11:17
 */
public interface ConvertTemplate<T> {

    <T> List<T> convertBeanToTemplate(Object source);

}
