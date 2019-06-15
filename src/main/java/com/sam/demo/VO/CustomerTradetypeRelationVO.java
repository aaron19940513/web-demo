package com.sam.demo.VO;


import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@BlockHeader(blockKey = "CustomerTradetypeRelation",blockTitle = "customer Tradetype Relation")
public class CustomerTradetypeRelationVO {

    private String id;

    private String customerId;
    @BlockInfo(key = "tradeTypeId" ,title = "TradeTypeId",index = 1)
    private String tradeTypeId;


}
