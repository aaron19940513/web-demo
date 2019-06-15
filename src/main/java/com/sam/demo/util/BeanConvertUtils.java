package com.sam.demo.util;


import com.google.common.collect.Lists;
import com.sam.demo.VO.BlockHeader;
import com.sam.demo.VO.BlockInfo;

import com.sam.demo.VO.InnerVO;
import com.sam.demo.VO.rule.RuleVO;
import com.sam.demo.VO.rule.RequiredRuleVO;
import com.sam.demo.enums.RuleTypeEnum;
import com.sam.demo.exception.BaseException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * The type Bean convert utils.
 */
public class BeanConvertUtils {


    public static <T> List<T> convertBlock(Object source, Class clazz) throws Exception {
        List<T> beanList = new ArrayList<>();

        Field[] fields = source.getClass().getDeclaredFields();
        T bean = null;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Annotation annotation = field.getAnnotation(InnerVO.class);
            if (null != annotation) {
                field.setAccessible(true);
                Object value = field.get(source);
                field.setAccessible(false);
                beanList.addAll(convertBlock(value, clazz));
            } else if (field.getType() == List.class) {
                field.setAccessible(true);
                List<Object> value = (List<Object>) field.get(source);
                field.setAccessible(false);
                beanList.addAll(convertBlock4ListBean(value, clazz));
            } else {
                bean = swapBean(source, field, clazz);
            }
            beanList.add(bean);
        }
        return beanList;
    }

    private static <T> List<T> convertBlock4ListBean(List<Object> beans, Class clazz) throws Exception {
        if(null==beans || beans.isEmpty()){
            return Lists.newArrayList();
        }
        List<T> beanList = new ArrayList<>();
        for (Object bean : beans) {
            beanList.addAll(convertBlock(bean, clazz));
        }
        return beanList;
    }

    public static <T> T swapBean(Object source, Field field, Class clazz) throws Exception {
        if (source == null) {
            return null;
        }
        T bean = (T) clazz.newInstance();
        swapHead(source, bean);
        swapField(field, bean);
        swapValue(source,field, bean);
        swapRules(field, bean);
        return bean;
    }



    private static <T> void swapHead(Object object, T bean) throws Exception {
        BlockHeader annotation = object.getClass().getAnnotation(BlockHeader.class);
        if (null != annotation) {
            convertAnnotation2Field(annotation, bean);
        }
    }

    private static <T> void swapField(Field field, T bean) throws Exception {
        BlockInfo annotation = field.getAnnotation(BlockInfo.class);
        if (null != annotation) {
            convertAnnotation2Field(annotation, bean);
        }
    }

    private static <T> void swapRules(Field field, T bean) throws Exception {
        List<RuleVO> rules = new ArrayList<>();
        NotEmpty notEmptyAnnotation = field.getAnnotation(NotEmpty.class);
        if (null != notEmptyAnnotation) {
            rules.add(RequiredRuleVO.builder().build());
        }
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (StringUtils.equals("rules", propertyName)) {
                BeanUtils.copyProperty(bean, propertyName, rules);
            }
        }
    }

    private static <T> void convertAnnotation2Field(Annotation annotation, T bean) throws Exception {
        Method[] methods = annotation.getClass().getDeclaredMethods();
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (propertyName.equals("class")) {
                continue;
            }
            for (Method method : methods) {
                if (StringUtils.equals(method.getName(), propertyName)) {
                    method.setAccessible(true);
                    Object value = method.invoke(annotation);
                    method.setAccessible(false);
                    BeanUtils.copyProperty(bean, propertyName, value);
                }
            }
        }
    }

    private static <T> void swapValue(Object source, Field field, T bean) throws Exception {
        Field[] targetFields = bean.getClass().getDeclaredFields();
        for (Field targetField : targetFields) {
            if (StringUtils.equals("value", targetField.getName())) {
                field.setAccessible(true);
                Object value = field.get(source);
                field.setAccessible(false);
                BeanUtils.copyProperty(bean, targetField.getName(), value);
            }
        }
    }


}
