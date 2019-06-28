package com.sam.demo.convert;

import com.google.common.collect.Lists;
import com.sam.demo.VO.InnerVO;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sam
 * @date 2019/6/19 11:17
 */
public abstract class  AbstractConvertTemplate<T> implements ConvertTemplate<T> {

    public <T> List<T> convertBeanToTemplate(Object source){
        List<T> beanList = new ArrayList<>();
        try {
            Field[] fields = source.getClass().getDeclaredFields();
            T bean = null;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                InnerVO annotation = field.getAnnotation(InnerVO.class);
                if (null != annotation) {
                    if (field.getType() == List.class) {
                        field.setAccessible(true);
                        List<Object> value = null;
                        value = (List<Object>) field.get(source);
                        field.setAccessible(false);
                        beanList.addAll(convertBeansToTemplate(value));
                    } else {
                        field.setAccessible(true);
                        Object value = field.get(source);
                        field.setAccessible(false);
                        beanList.addAll(convertBeanToTemplate(value));
                    }
                } else {
                    bean = swapBean(source, field);
                }
                if (null != bean) {
                    beanList.add(bean);
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    private <T> List<T> convertBeansToTemplate(List<Object> beans) {
        if (null == beans || beans.isEmpty()) {
            return Lists.newArrayList();
        }
        List<T> beanList = new ArrayList<>();
        for (Object bean : beans) {
            beanList.addAll(convertBeanToTemplate(bean));
        }
        return beanList;
    }


    abstract <T> T swapBean(Object source, Field field);


}
