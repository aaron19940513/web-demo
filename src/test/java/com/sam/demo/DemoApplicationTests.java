package com.sam.demo;

import com.alibaba.fastjson.JSON;
import com.sam.demo.enums.SexEnum;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
       private CustomerRepositoryCustomized customerRepositoryCustomized;

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
    public void testSpecial() {
        List<CustomerEntity> customerEntities = customerRepositoryCustomized.specialWithSex(SexEnum.MALE);
        System.out.println(JSON.toJSONString(customerEntities));
    }

    @Test
    public void testSpecialMulti() {
        List<CustomerEntity> customerEntities = customerRepositoryCustomized.specialWithSexAndAge(SexEnum.MALE,9);
        for (CustomerEntity customerEntity : customerEntities) {
            System.out.println(customerEntity.getSex());
            System.out.println(customerEntity.getAge());
            System.out.println(customerEntity.getCustomerTradetypeRelationEntities());
        }

    }

    @Test
    public void testSpecialTableJoin() {
        List<CustomerEntity> customerEntities = customerRepositoryCustomized.specialMultiTable(SexEnum.FMALE,9);
        for (CustomerEntity customerEntity : customerEntities) {
            System.out.println(customerEntity.getCustomerTradetypeRelationEntities());
        }
        System.out.println(JSON.toJSONString(customerEntities));
    }
}
