package com.sam.demo.service.impl;

import com.google.common.collect.Lists;
import com.sam.demo.VO.AccountVO;
import com.sam.demo.VO.BlockInfoVO;
import com.sam.demo.VO.CustomerTradetypeRelationVO;
import com.sam.demo.VO.CustomerVO;
import com.sam.demo.convert.BlockInfoConvertTemplate;
import com.sam.demo.convert.ConvertTemplate;
import com.sam.demo.enums.SexEnum;
import com.sam.demo.mysql.dao.CustomerRepository;
import com.sam.demo.mysql.entity.CustomerEntity;
import com.sam.demo.service.CommonService;
import com.sam.demo.util.BeanConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CustomerRepository customerRepository;

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
            ConvertTemplate<BlockInfoVO> convertTemplate = new BlockInfoConvertTemplate();
            list.addAll(convertTemplate.convertBeanToTemplate(customer));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<CustomerVO> queryCustomer() {
        List<CustomerEntity> customers =customerRepository.findAll();
        return null;
    }

    @Override
    public List<CustomerEntity> testEnum() {
        return customerRepository.findAll();
    }

    @Override
    public void testsave() {
        CustomerEntity customer = CustomerEntity.builder().age(25).country("CH").sex(SexEnum.MALE).name("sam").build();
        customerRepository.save(customer);
    }

}
