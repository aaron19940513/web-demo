package com.sam.demo.mysql.dao.primary;


import java.util.List;

import com.sam.demo.enums.SexEnum;
import com.sam.demo.mysql.dao.base.PersistBaseRepository;
import com.sam.demo.mysql.entity.primary.CustomerEntity;
import org.springframework.data.jpa.repository.Query;


public interface CustomerRepository extends PersistBaseRepository<CustomerEntity, Integer>, CustomerRepositoryCustomized {
    @Query("select T from CustomerEntity T where T.sex=?1")
    List<CustomerEntity> queryHpql(Enum e);

    @Query("select T from CustomerEntity T where T.name=?1 and T.age=?2")
    List<CustomerEntity> queryHpqlWithIndex(String name, Integer age);

    @Query(value = "select * from CUSTOMER T where NAME=?1 and AGE=?2", nativeQuery = true)
    List<CustomerEntity> queryHpqlWithNativeSql(String name, Integer age);

    List<CustomerEntity> findBySex(SexEnum male);


}
