package com.sam.demo.mysql.dao;

import com.sam.demo.VO.CustomerVO;
import com.sam.demo.mysql.entity.CustomerEntity;

import java.util.List;


public interface CustomerRepositoryCustomized {

    List<CustomerEntity> querySpecial(CustomerEntity customerEntity);

    List<CustomerEntity> specialWithSex(Enum e);

    List<CustomerEntity> specialWithSexAndAge(Enum e,int age);

    List<CustomerEntity> specialMultiTable(Enum e,int age);
}
