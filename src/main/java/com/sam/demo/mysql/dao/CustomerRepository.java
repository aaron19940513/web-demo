package com.sam.demo.mysql.dao;


import com.sam.demo.mysql.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustomerRepository extends JpaRepository<CustomerEntity,Integer>, JpaSpecificationExecutor<CustomerEntity>{
    @Query("select T from CustomerEntity T where T.sex=?1")
    List<CustomerEntity> queryHpql(Enum e);
}
