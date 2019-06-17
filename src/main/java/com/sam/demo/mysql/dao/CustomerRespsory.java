package com.sam.demo.mysql.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustomerRespsory<CustomerEntity> extends JpaRepository<CustomerEntity,Integer>{

}
