package com.sam.demo.VO.rule;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OptionsVO {
    private List<RuleVO> rules;
}


