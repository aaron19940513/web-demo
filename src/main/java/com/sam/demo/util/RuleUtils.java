package com.sam.demo.util;

import com.google.common.collect.Lists;
import com.sam.demo.VO.rule.*;
import com.sam.demo.constant.RuleConst;
import org.apache.commons.lang3.StringUtils;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sam
 * @date 2019/6/18 15:41
 */
public class RuleUtils {


    public static OptionsVO swapRules2Option(Annotation[] annotations) {

        return OptionsVO.builder().rules(swapRules(annotations)).build();
    }

    public static List<RuleVO> swapRules(Annotation[] annotations) {
        if (null == annotations) {
            return Lists.newArrayList();
        }
        List<RuleVO> rules = new ArrayList<>();
        RuleVO rule;
        for (Annotation annotation : annotations) {
            rule = null;
            if (StringUtils.equals(NotEmpty.class.getName(), annotation.annotationType().getName())) {
                rule = RequiredRuleVO.builder().build();
            } else if (StringUtils.equals(Max.class.getName(), annotation.annotationType().getName())) {
                Max maxAnnotation = (Max) annotation;
                rule = MaxRuleVO.builder().max(maxAnnotation.value()).build();
            } else if (StringUtils.equals(Min.class.getName(), annotation.annotationType().getName())) {
                Min minAnnotation = (Min) annotation;
                rule = MinRuleVO.builder().min(minAnnotation.value()).build();
            }
            if (null != rule) {
                rules.add(rule);
            }
        }
        return rules;
    }


    public static String overMaxErrorMsg(Long max) {
        return String.format(RuleConst.OVER_MAX, max);
    }

    public static String belowMinErrorMsg(Long min) {
        return String.format(RuleConst.BELOW_MIN, min);
    }
}
