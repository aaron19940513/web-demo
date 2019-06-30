package com.sam.demo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sam.demo.enums.SexEnum;
import com.sam.demo.exception.BaseException;
import com.sam.demo.mysql.dao.CustomerRepository;
import com.sam.demo.mysql.dao.CustomerRepositoryCustomized;
import com.sam.demo.mysql.entity.CustomerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidatorFactory;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ValidatorFactory validatorFactory;


    @Test
    public void contextLoads() {

    }

    @Test
    public void testSortAndPage() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<CustomerEntity> customerEntityLis = customerRepository.findAll(sort);
        System.out.println(JSON.toJSON(customerEntityLis));

        Pageable pageable = PageRequest.of(1, 1);
        System.out.println(JSON.toJSON(customerRepository.findAll(pageable).getContent()));

        Pageable pageAndSort = PageRequest.of(1, 1, Sort.Direction.DESC, "id");
        System.out.println(JSON.toJSON(customerRepository.findAll(pageAndSort).getContent()));
    }

    @Test
    public void testJpql() {
        List<CustomerEntity> customerEntities = customerRepository.queryHpql(SexEnum.MALE);
        System.out.println(JSON.toJSONString(customerEntities));
    }

    @Test
    public void testJpal2() {
        List<CustomerEntity> customerEntities = customerRepository.queryHpqlWithIndex("sam", 22);
        System.out.println(JSON.toJSONString(customerEntities));
    }

    @Test
    public void testJpal3() {
        List<CustomerEntity> customerEntities = customerRepository.queryHpqlWithNativeSql("sam", 22);
        System.out.println(JSON.toJSONString(customerEntities));
    }

    @Test
    public void testNativeSql() throws BaseException {
        List<CustomerEntity> customerEntities = customerRepository.queryByNameWithNative("sam");
        System.out.println(JSON.toJSONString(customerEntities));
    }

    @Test
    public void testNativeSql2() throws BaseException {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setName("Jan");

        List<CustomerEntity> customerEntities = Lists.newArrayList(customerEntity);
        customerRepository.updateBatch(customerEntities);
        System.out.println(JSON.toJSONString(customerEntities));
    }

    @Test
    public void testNativeSql3() throws BaseException {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setName("Jan");

        CustomerEntity customerEntity2 = new CustomerEntity();
        customerEntity2.setId(2);
        customerEntity2.setName("Sam");
        List<CustomerEntity> customerEntities = Lists.newArrayList(customerEntity, customerEntity2);
        customerRepository.updateBatch(customerEntities);
        System.out.println(JSON.toJSONString(customerEntities));
    }


    @Test
    @Transactional
    public void test() {
        List<CustomerEntity> customerEntities = customerRepository.findBySex(SexEnum.MALE);
        for (CustomerEntity customerEntity : customerEntities) {
            customerEntity.setAge(88);
        }
        customerRepository.refresh();
        System.out.println(JSON.toJSONString(customerEntities));
    }

    @Test
    public void testSpecial() {
        List<CustomerEntity> customerEntities = customerRepository.specialWithSex(SexEnum.MALE);
        System.out.println(JSON.toJSONString(customerEntities));
    }

    @Test
    public void testSpecialMulti() {
        List<CustomerEntity> customerEntities = customerRepository.specialWithSexAndAge(SexEnum.MALE, 9);
        for (CustomerEntity customerEntity : customerEntities) {
            System.out.println(customerEntity.getSex());
            System.out.println(customerEntity.getAge());
            System.out.println(customerEntity.getCustomerTradetypeRelationEntities());
        }

    }

    @Test
    public void testSpecialTableJoin() {
        List<CustomerEntity> customerEntities = customerRepository.specialMultiTable(SexEnum.FMALE, 9);
        for (CustomerEntity customerEntity : customerEntities) {
            System.out.println(customerEntity.getCustomerTradetypeRelationEntities());
        }
        System.out.println(JSON.toJSONString(customerEntities));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testSave() {
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

    @Test
    @Transactional
    public void testSave2() {
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


    @Test
    public void test99() {
        String a = validatorFactory.getMessageInterpolator().interpolate("{max.not.valid}", null);
        System.out.println(a);
    }
}
