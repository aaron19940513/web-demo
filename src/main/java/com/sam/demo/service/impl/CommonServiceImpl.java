package com.sam.demo.service.impl;

import com.google.common.collect.Lists;
import com.sam.demo.enums.SexEnum;
import com.sam.demo.mysql.dao.primary.CustomerRepository;
import com.sam.demo.mysql.dao.primary.CustomerTradeTypeRelationRepository;
import com.sam.demo.mysql.entity.primary.CustomerEntity;
import com.sam.demo.mysql.entity.primary.CustomerTradetypeRelationEntity;
import com.sam.demo.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerTradeTypeRelationRepository customerTradeTypeRelationRepository;




    @Override
    @Cacheable(value = "configQuery", sync = true)
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
        customerEntity2.setSex(SexEnum.FEMALE);
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
        List<CustomerTradetypeRelationEntity> customerTradetypeRelationEntities = Lists.newArrayList(customerTradetypeRelationEntity,
                                                                                                     customerTradetypeRelationEntity2);
        customerTradeTypeRelationRepository.persistAll(customerTradetypeRelationEntities);
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
        customerEntity2.setSex(SexEnum.FEMALE);
        List<CustomerEntity> customerEntities = Lists.newArrayList(customerEntity, customerEntity2);
        customerRepository.persistAll(customerEntities);
    }




    @Override
    @Transactional
    public void merge() {
        CustomerEntity customerEntity = customerRepository.findById(1).get();
        customerEntity.setAge(99);
        customerRepository.merge(customerEntity);
    }

}
