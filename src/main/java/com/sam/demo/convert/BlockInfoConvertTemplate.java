package com.sam.demo.convert;

import com.google.common.collect.Lists;
import com.sam.demo.VO.BlockInfo;
import com.sam.demo.VO.BlockInfoVO;
import com.sam.demo.VO.InnerVO;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sam
 * @date 2019/6/19 11:24
 */
public class BlockInfoConvertTemplate implements ConvertTemplate<BlockInfoVO> {
    @Override
    public <T> List<T> convertBeanToTemplate(Object source) {
        return null;
    }

//    @Override
//    public  List<BlockInfoVO> convertBeanToTemplate(Object source) {
//        List<BlockInfoVO> beanList = new ArrayList<>();
//
//        Field[] fields = source.getClass().getDeclaredFields();
//        BlockInfoVO bean = null;
//        for (int i = 0; i < fields.length; i++) {
//            Field field = fields[i];
//            Annotation annotation = field.getAnnotation(InnerVO.class);
//            if (null != annotation) {
//                if (field.getType() == List.class) {
//                    field.setAccessible(true);
//                    List<Object> value = (List<Object>) field.get(source);
//                    field.setAccessible(false);
//                    beanList.addAll(convertBean4ListBean(value));
//                } else {
//                    field.setAccessible(true);
//                    Object value = field.get(source);
//                    field.setAccessible(false);
//                    beanList.addAll(convertBeanToTemplate(value));
//                }
//            } else {
//                bean = swapBean(source, field);
//            }
//            beanList.add(bean);
//        }
//        return beanList;
//    }
//
//    private  <T> List<T> convertBlock4ListBean(List<Object> beans, Class clazz) throws Exception {
//        if (null == beans || beans.isEmpty()) {
//            return Lists.newArrayList();
//        }
//        List<T> beanList = new ArrayList<>();
//        for (Object bean : beans) {
//            beanList.addAll(convertBean2BlockInfo(bean, clazz));
//        }
//        return beanList;
//    }
//
//    public  <T> T swapBean(Object source, Field field, Class<T> targetClazz) throws Exception {
//        if (source == null) {
//            return null;
//        }
//        T bean = null;
//        BlockHeader headerAnnotation = null == source.getClass().getAnnotation(BlockHeader.class) ? field.getAnnotation(BlockHeader.class) : null;
//        BlockInfo infoAnnotation = field.getAnnotation(BlockInfo.class);
//        if (null != headerAnnotation && null != infoAnnotation) {
//            bean = targetClazz.newInstance();
//            convertAnnotation2Field(headerAnnotation, bean);
//            convertAnnotation2Field(infoAnnotation, bean);
//            swapValue(source, field, bean);
//            swapRules(field, bean);
//        }
//        return bean;
//    }
//
//
//    private  <T> void swapValue(Object object, Field field, T bean) throws Exception {
//        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//        for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
//            if (StringUtils.equals(propertyDescriptors[i].getName(), "value")) {
//                field.setAccessible(true);
//                Object value = field.get(object);
//                field.setAccessible(false);
//                BeanUtils.copyProperty(bean, propertyDescriptors[i].getName(), value);
//            }
//        }
//    }
//
//
//    private  <T> void swapRules(Field field, T bean) throws Exception {
//        List<RuleVO> rules = new ArrayList<>();
//        RuleVO rule = null;
//        NotEmpty emptyAnnotation = field.getAnnotation(NotEmpty.class);
//        if (null != emptyAnnotation) {
//            rule = RequiredRuleVO.builder().build();
//            rules.add(rule);
//        }
//        Max maxAnnotation = field.getAnnotation(Max.class);
//        if (null != maxAnnotation) {
//            rule = RequiredRuleVO.builder().build();
//            rules.add(rule);
//        }
//        if (rules.isEmpty()) {
//            return;
//        }
//        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//        for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
//            PropertyDescriptor descriptor = propertyDescriptors[i];
//            String propertyName = descriptor.getName();
//            if (StringUtils.equals("rules", propertyName)) {
//                BeanUtils.copyProperty(bean, propertyName, rules);
//            }
//        }
//    }
//
//    private static <T> void convertAnnotation2Field(Annotation annotation, T bean) throws Exception {
//        Method[] methods = annotation.getClass().getDeclaredMethods();
//        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//        for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
//            PropertyDescriptor descriptor = propertyDescriptors[i];
//            String propertyName = descriptor.getName();
//            if (propertyName.equals("class")) {
//                continue;
//            }
//            for (Method method : methods) {
//                if (StringUtils.equals(method.getName(), propertyName)) {
//                    method.setAccessible(true);
//                    Object value = method.invoke(annotation);
//                    method.setAccessible(false);
//                    BeanUtils.copyProperty(bean, propertyName, value);
//                }
//            }
//        }
//    }
}
