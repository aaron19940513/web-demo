package com.sam.demo.convert;

import com.google.common.collect.Lists;
import com.sam.demo.VO.BlockHeader;
import com.sam.demo.VO.BlockInfo;
import com.sam.demo.VO.BlockInfoVO;
import com.sam.demo.VO.InnerVO;
import com.sam.demo.VO.rule.RuleVO;
import com.sam.demo.util.RuleUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author sam
 * @date 2019/6/19 11:24
 */
public class BlockInfoConvertTemplate extends AbstractConvertTemplate<BlockInfoVO> {

    public BlockInfoVO swapBean(Object source, Field field) {
        if (source == null) {
            return null;
        }
        BlockInfoVO bean = null;
        BlockHeader headerAnnotation = null == source.getClass().getAnnotation(BlockHeader.class) ? field.getAnnotation(BlockHeader.class) : source.getClass().getAnnotation(BlockHeader.class);
        BlockInfo infoAnnotation = field.getAnnotation(BlockInfo.class);
        if (null != headerAnnotation && null != infoAnnotation) {
            try {
                bean = new BlockInfoVO();
                convertAnnotation2Field(headerAnnotation, bean);
                convertAnnotation2Field(infoAnnotation, bean);
                swapValue(source, field, bean);
                swapRules(field, bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    private void convertAnnotation2Field(Annotation annotation, BlockInfoVO bean) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
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


    private void swapValue(Object object, Field field, BlockInfoVO bean) throws IntrospectionException, IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(object);
        field.setAccessible(false);
        if(null!=value){
            bean.setValue(String.valueOf(value));
        }
    }


    private void swapRules(Field field, BlockInfoVO bean) throws IntrospectionException {
        List<RuleVO> rules = RuleUtils.swapRules(field.getDeclaredAnnotations());
        bean.setRules(rules);
    }
}
