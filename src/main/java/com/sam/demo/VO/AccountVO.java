package com.sam.demo.VO;

import com.sam.demo.VO.BlockHeader;
import com.sam.demo.VO.BlockInfo;
import com.sam.demo.VO.CustomerTradetypeRelationVO;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@BlockHeader(blockKey = "account",blockTitle = "Account Info")
public class AccountVO {

    private int id;
    @BlockInfo(key = "accountNo", title = "Account#",index = 1)
    @NotEmpty
    private String accountNo;

}
