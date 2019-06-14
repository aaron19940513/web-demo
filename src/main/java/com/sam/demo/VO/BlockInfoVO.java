package com.sam.demo.VO;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockInfoVO {
    // field name
    private String key;

    //  title show on view
    private String title;

    // value show on view
    private String value;

    // which block to point to
    private String blockKey;

    //blockTitle show on view
    private String blockTitle;

    // displayType
    private String displayType;

    // if dispalyType equals selected, provide options
    private List<Object> optionalValues;

    // can this hidden  be edit
    private boolean hidden;

    // can this field  be edit
    private boolean editable;

    private OptionsVO options;

    private RuleVO[] rules;

}
