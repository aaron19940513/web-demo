package com.sam.demo.VO;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionsVO {
    private List<RuleVO> rules;
}


