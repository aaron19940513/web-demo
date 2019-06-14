package com.sam.demo.util;


import com.sam.demo.VO.BlockHeader;
import com.sam.demo.VO.BlockInfo;

import com.sam.demo.VO.RuleVO;
import com.sam.demo.VO.rule.RequiredRuleVO;
import com.sam.demo.enums.RuleTypeEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Bean convert utils.
 */
public class BeanConvertUtils {


    public static <T> List<T> convert(Object object, Class clazz) throws Exception {
        List<T> beanList = new ArrayList<>();

        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            T bean = swapBean(object, field, clazz);
            beanList.add(bean);
        }
        return beanList;
    }

    public static <T> T swapBean(Object object, Field field, Class clazz) throws Exception {
        if (object == null) {
            return null;
        }
        T bean = (T) clazz.newInstance();
        swapHead(object, bean);
        swapField(field, bean);
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
        Rule annotation = field.getAnnotation(Rule.class);
        if (null != annotation) {
            convertAnnotation2RuleField(annotation, bean);
        }
    }

    private static <T> void convertAnnotation2RuleField(Rule annotation, T bean) throws Exception {
        List<RuleVO> rules = new ArrayList<>();
        RuleTypeEnum[] ruleTypes = annotation.rules();
        boolean required = annotation.required();
        int max = annotation.max();
        int min = annotation.min();
        RuleVO rule = null;
        for (RuleTypeEnum ruleType : ruleTypes) {
            if (ruleType.getRule() == RequiredRuleVO.class) {
                rule =  RequiredRuleVO.builder().build();
            }
            if (null != rule) {
                rules.add(rule);
            }

        }
        RuleVO[] rulesArray = rules.toArray(new RuleVO[rules.size()]);
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (StringUtils.equals("rules", propertyName)) {
                BeanUtils.copyProperty(bean, propertyName, rules.toArray(new RuleVO[rules.size()]));
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


}
