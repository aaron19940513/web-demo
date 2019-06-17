package com.sam.demo;

import com.alibaba.fastjson.JSON;
import com.sam.demo.enums.SexEnum;
import com.sam.demo.mysql.dao.CustomerRespsory;
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
    private CustomerRespsory<CustomerEntity> customerRespsory;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testSortAndPage() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<CustomerEntity> customerEntityLis = customerRespsory.findAll(sort);
        System.out.println(JSON.toJSON(customerEntityLis));

        Pageable pageable = PageRequest.of(1, 1);
        System.out.println(JSON.toJSON(customerRespsory.findAll(pageable).getContent()));

        Pageable pageAndSort = PageRequest.of(1, 1, Sort.Direction.DESC, "id");
        System.out.println(JSON.toJSON(customerRespsory.findAll(pageAndSort).getContent()));
    }

    @Test
    public void testJpql() {
        //List<CustomerEntity> customerEntities = customerRespsory.queryHpql(SexEnum.MALE);
        //System.out.println(JSON.toJSONString(customerEntities));
    }


    @Test
    public void testSpecial() {
//        CustomerEntity customerEntity = CustomerEntity.builder().name("sam").build();
//        List<CustomerEntity> customerEntities = customerRespsory.querySpecial(customerEntity);
//        System.out.println(JSON.toJSONString(customerEntities));
    }
}
