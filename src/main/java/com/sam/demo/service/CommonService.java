package com.sam.demo.service;

import com.sam.demo.VO.BlockInfoVO;
import com.sam.demo.VO.CustomerVO;
import com.sam.demo.mysql.entity.CustomerEntity;

import java.util.List;

public interface CommonService {
    List<BlockInfoVO> buildBlock();

    List<CustomerVO> queryCustomer();

    List<CustomerEntity> testEnum();

    void testsave();
}
