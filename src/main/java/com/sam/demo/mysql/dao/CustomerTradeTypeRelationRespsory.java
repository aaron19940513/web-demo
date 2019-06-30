package com.sam.demo.mysql.dao;


import com.sam.demo.mysql.dao.base.PersistBaseRepository;
import com.sam.demo.mysql.entity.CustomerEntity;
import com.sam.demo.mysql.entity.CustomerTradetypeRelationEntity;
import org.springframework.data.repository.CrudRepository;


public interface CustomerTradeTypeRelationRespsory extends PersistBaseRepository<CustomerTradetypeRelationEntity,String> {

}
