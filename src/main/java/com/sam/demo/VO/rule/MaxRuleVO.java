package com.sam.demo.VO.rule;


import com.sam.demo.util.RuleUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sam
 * @date 2019/6/18 16:26
 */
@Builder
@Setter
@Getter
public class MaxRuleVO extends RuleVO {

    private Long max;

    @Builder
    public MaxRuleVO(Long max) {
        this.max = max;
        super.setMessage(RuleUtils.overMaxErrorMsg(max));
    }

}
