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
}
