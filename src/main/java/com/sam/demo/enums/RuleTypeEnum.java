package com.sam.demo.enums;

import com.sam.demo.VO.RuleVO;
import com.sam.demo.VO.rule.RequiredRuleVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum RuleTypeEnum {

    REQUIRED("required", RequiredRuleVO.class),

    MAX("max", RequiredRuleVO.class),

    MIN("min", RequiredRuleVO.class);

    private String value;

    private Class<? extends RuleVO> rule;

    public Class<? extends RuleVO> getRule() {
        return rule;
    }

    public String getValue() {
        return value;
    }

    public static List<Class<? extends RuleVO>> getRules() {
        return Arrays.stream(RuleTypeEnum.values()).map(ruleTypeEnum -> ruleTypeEnum.getRule()).collect(Collectors.toList());
    }
}
