package com.sam.demo.service;

import java.util.List;

import com.sam.demo.mysql.entity.primary.CustomerEntity;

public interface CommonService {

    List<CustomerEntity> testEnum();

    void testSave();

    void testSaveAll();

    void persistAll();

    void persistAllWithAuto();

    void merge();
}
