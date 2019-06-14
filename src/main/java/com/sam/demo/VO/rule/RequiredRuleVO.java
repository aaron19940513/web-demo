package com.sam.demo.VO.rule;


import com.sam.demo.VO.RuleVO;
import com.sam.demo.constant.RuleConst;
import lombok.*;

/**
 * @author sam
 * @date 2019/6/12 16:26
 */
@Setter
@Getter
public class RequiredRuleVO extends RuleVO {

    private boolean required;

    @Builder
    private RequiredRuleVO() {
        super.setMessage(RuleConst.REQUIRED_BUT_NULL);
        this.required = true;
    }
    @Builder
    private RequiredRuleVO(boolean required) {
        if(required==true){
            new RequiredRuleVO();
        }
    }
}
