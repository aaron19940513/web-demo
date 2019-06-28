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
public class MinRuleVO extends RuleVO {

    private Long min;

    @Builder
    public MinRuleVO(Long min) {
        this.min = min;
        super.setMessage(RuleUtils.belowMinErrorMsg(min));
    }

}
