package com.sam.demo.VO;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@BlockHeader(blockKey = "customer",blockTitle = "Customer Info")
public class CustomerVO {
    private int id;
    @BlockInfo(key = "name", title = "Name",index = 1)
    @NotEmpty
    private String name;
    @BlockInfo(key = "country", title = "Country",index = 2)
    @Max(value = 123,message = "over the max value")
    private String country;
    @BlockInfo(key = "age", title = "Age",index = 3)
    private int age;
    @BlockInfo(key = "sex", title = "Sex",index = 4)
    private String sex;
    @InnerVO
    private AccountVO accountVO;

    private List<CustomerTradetypeRelationVO> customerTradetypeRelationVOS;

}
