package com.sam.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sam.demo.VO.AccountVO;
import com.sam.demo.VO.BlockInfoVO;
import com.sam.demo.VO.CustomerTradetypeRelationVO;
import com.sam.demo.VO.CustomerVO;
import com.sam.demo.convert.BlockInfoConvertTemplate;
import com.sam.demo.convert.ConvertTemplate;
import com.sam.demo.enums.SexEnum;
import com.sam.demo.exception.BaseException;
import com.sam.demo.mysql.dao.CustomerRepository;
import com.sam.demo.mysql.dao.CustomerTradeTypeRelationRespsory;
import com.sam.demo.mysql.entity.CustomerEntity;
import com.sam.demo.mysql.entity.CustomerTradetypeRelationEntity;
import com.sam.demo.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerTradeTypeRelationRespsory customerTradeTypeRelationRespsory;

    @Override
    public List<BlockInfoVO> buildBlock() {
        List<BlockInfoVO> list = new ArrayList<>();
        try {
            CustomerVO customer = CustomerVO.builder().age(1).country("CA").sex("male").name("nacy").build();
            AccountVO accountVO = AccountVO.builder().accountNo("2435").build();
            CustomerTradetypeRelationVO customerTradetypeRelationVO = CustomerTradetypeRelationVO.builder().tradeTypeId("998").build();
            CustomerTradetypeRelationVO customerTradetypeRelationVO2 = CustomerTradetypeRelationVO.builder().tradeTypeId("997").build();
            customer.setAccountVO(accountVO);
            customer.setCustomerTradetypeRelationVOS(Lists.newArrayList(customerTradetypeRelationVO, customerTradetypeRelationVO2));
            ConvertTemplate<BlockInfoVO> convertTemplate = new BlockInfoConvertTemplate();
            list.addAll(convertTemplate.convertBeanToTemplate(customer));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<CustomerVO> queryCustomer() {
        List<CustomerEntity> customers = customerRepository.findAll();
        return null;
    }

    @Override
    public List<CustomerEntity> testEnum() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public void testSave() {
        CustomerEntity customer = CustomerEntity.builder().age(25).country("CH").sex(SexEnum.MALE).name("sam").build();
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void testEntityManager() {
        List<CustomerEntity> customerEntities = customerRepository.findBySex(SexEnum.MALE);
        for (CustomerEntity customerEntity : customerEntities) {
            customerEntity.setAge(88);
        }
    }

    @Override
    @Transactional
    public void testSaveAll() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("漩涡鸣人");
        customerEntity.setCountry("Japan");
        customerEntity.setAge(15);
        customerEntity.setSex(SexEnum.MALE);

        CustomerEntity customerEntity2 = new CustomerEntity();
        customerEntity2.setName("小樱");
        customerEntity2.setCountry("Japan");
        customerEntity2.setAge(17);
        customerEntity2.setSex(SexEnum.FMALE);
        List<CustomerEntity> customerEntities = Lists.newArrayList(customerEntity, customerEntity2);
        customerRepository.saveAll(customerEntities);
    }

    @Override
    @Transactional
    public void persistAll() {
        CustomerTradetypeRelationEntity customerTradetypeRelationEntity = new CustomerTradetypeRelationEntity();
        customerTradetypeRelationEntity.setId("123");
        customerTradetypeRelationEntity.setCustomerId("555");
        customerTradetypeRelationEntity.setTradeTypeId("666");

        CustomerTradetypeRelationEntity customerTradetypeRelationEntity2 = new CustomerTradetypeRelationEntity();
        customerTradetypeRelationEntity2.setId("1234");
        customerTradetypeRelationEntity2.setCustomerId("555");
        customerTradetypeRelationEntity2.setTradeTypeId("666");
        List<CustomerTradetypeRelationEntity> customerTradetypeRelationEntities = Lists.newArrayList(customerTradetypeRelationEntity, customerTradetypeRelationEntity2);
        customerTradeTypeRelationRespsory.persistAll(customerTradetypeRelationEntities);
    }

    @Override
    @Transactional
    public void persistAllWithAuto() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("漩涡鸣人");
        customerEntity.setCountry("Japan");
        customerEntity.setAge(15);
        customerEntity.setSex(SexEnum.MALE);

        CustomerEntity customerEntity2 = new CustomerEntity();
        customerEntity2.setName("小樱");
        customerEntity2.setCountry("Japan");
        customerEntity2.setAge(17);
        customerEntity2.setSex(SexEnum.FMALE);
        List<CustomerEntity> customerEntities = Lists.newArrayList(customerEntity, customerEntity2);
        customerRepository.persistAll(customerEntities);
    }


    @Override
    @Transactional
    public void update() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setName("Jan1");
        customerEntity.setAge(99);
        customerEntity.setSex(SexEnum.FMALE);
        List<CustomerEntity> customerEntities = Lists.newArrayList(customerEntity);

        try {
            customerRepository.updateBatch(customerEntities);
        } catch (BaseException e) {
            e.printStackTrace();
        }

        System.out.println(JSON.toJSONString(customerEntities));


    }



    @Override
    @Transactional
    public void updateBatch() {

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setName("Jan");

        CustomerEntity customerEntity2 = new CustomerEntity();
        customerEntity2.setId(2);
        customerEntity2.setName("Sam");
        List<CustomerEntity> customerEntities = Lists.newArrayList(customerEntity, customerEntity2);
        try {
            customerRepository.updateBatch(customerEntities);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        System.out.println(JSON.toJSONString(customerEntities));
    }

    @Override
    @Transactional
    public void merge() {
        CustomerEntity customerEntity = customerRepository.findById(1).get();
        customerEntity.setAge(99);
        customerRepository.merge(customerEntity);
    }

    @Override
    public void mergeAll() {
        List<CustomerEntity> customerEntityList = customerRepository.findBySex(SexEnum.FMALE);
        for (CustomerEntity customerEntity : customerEntityList) {
            customerEntity.setAge(13);
        }

        customerRepository.mergeAll(customerEntityList);
    }
}
