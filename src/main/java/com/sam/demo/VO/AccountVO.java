package com.sam.demo.VO;

import lombok.*;

import javax.validation.constraints.NotEmpty;

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
