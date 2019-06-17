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

//    @Autowired
//    private CustomerRepositoryCustomized customerRepositoryCustomized;

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
        CustomerEntity customerEntity = CustomerEntity.builder().name("sam").build();
//        List<CustomerEntity> customerEntities = customerRepository.querySpecial(customerEntity);
//        System.out.println(JSON.toJSONString(customerEntities));
    }
}
