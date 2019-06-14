package com.sam.demo.rule;

import com.sam.demo.VO.InvoiceDetailVO;
import com.sam.demo.VO.RuleVO;
import com.sam.demo.VO.rule.Rule;
import com.sam.demo.enums.RuleTypeEnum;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author sam
 * @date 2019/6/13 9:58
 */
public class RuleContext {

    public RuleValidator getInstance(Rule rule){
        List<Class<? extends RuleVO>> rulesClass = RuleTypeEnum.getRules();
        for (Class<? extends RuleVO> calzz : rulesClass) {
            if(rule.getClass() == calzz){

            }
        }
    }

    public static boolean validate(InvoiceDetailVO invoiceDetailVO){
        Field[] fields = invoiceDetailVO.getClass().getDeclaredFields();
        for (Field field : fields) {
            Rule ruleAnnotation = field.getAnnotation(Rule.class);
            if(null != ruleAnnotation){
                for (RuleTypeEnum rule : ruleAnnotation.rules()) {
                    rule.getRule();
                }
            }
        }
    }

}
