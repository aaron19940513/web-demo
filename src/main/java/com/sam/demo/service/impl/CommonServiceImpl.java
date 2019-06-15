package com.sam.demo.service.impl;

import com.google.common.collect.Lists;
import com.sam.demo.VO.AccountVO;
import com.sam.demo.VO.BlockInfoVO;
import com.sam.demo.VO.CustomerTradetypeRelationVO;
import com.sam.demo.VO.CustomerVO;
import com.sam.demo.service.CommonService;
import com.sam.demo.util.BeanConvertUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public List<BlockInfoVO> buildBlock() {
        List<BlockInfoVO> list = new ArrayList<>();
        try {
            CustomerVO customer = CustomerVO.builder().age(1).country("CA").sex("male").name("nacy").build();
            AccountVO accountVO = AccountVO.builder().accountNo("2435").build();
            CustomerTradetypeRelationVO customerTradetypeRelationVO =CustomerTradetypeRelationVO.builder().tradeTypeId("998").build();
            CustomerTradetypeRelationVO customerTradetypeRelationVO2 =CustomerTradetypeRelationVO.builder().tradeTypeId("997").build();
            customer.setAccountVO(accountVO);
            customer.setCustomerTradetypeRelationVOS(Lists.newArrayList(customerTradetypeRelationVO,customerTradetypeRelationVO2));
            list.addAll(BeanConvertUtils.convertBlock(customer, BlockInfoVO.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
