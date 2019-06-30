package com.sam.demo.mysql.dao.impl;

import com.sam.demo.exception.BaseException;
import com.sam.demo.mysql.dao.CustomerRepository;
import com.sam.demo.mysql.dao.CustomerRepositoryCustomized;
import com.sam.demo.mysql.entity.CustomerEntity;
import com.sam.demo.mysql.entity.CustomerTradetypeRelationEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomerRepositoryImpl implements CustomerRepositoryCustomized {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRepositoryImpl.class);

    @PersistenceContext(unitName = "persistentUnit")
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<CustomerEntity> queryByNameWithNative(String name) throws BaseException {

        StringBuffer querySql = new StringBuffer();
        Map<Integer, Object> params = new HashMap<>();

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(name);
        querySql.append("SELECT * FROM customer ");
        querySql.append("WHERE");
        querySql.append(buildWhereParamsWithAllCondition(customerEntity, params, params.size()));


        Query excuteSql = entityManager.createNativeQuery(querySql.toString(), CustomerEntity.class);

        params.forEach((k, v) -> {
            excuteSql.setParameter(k, v);
        });
        List<CustomerEntity> customerEntities = excuteSql.getResultList();
        return customerEntities;
    }


    @Override
    @Transactional
    public void updateBatch(List<CustomerEntity> customerEntities) throws BaseException {
        if (null == customerEntities || customerEntities.isEmpty()) {
            throw new BaseException("update invoice failed,the invoice number is null!");
        }
        StringBuffer updateSql = new StringBuffer();
        Map<Integer, Object> params = new HashMap<>();
        for (CustomerEntity customerEntity : customerEntities) {
            updateSql.append(" UPDATE customer  SET ");
            updateSql.append(buildSetParams(customerEntity, params, params.size()));
            updateSql.append(" WHERE ");
            updateSql.append(buildWhereParamsWithPK(customerEntity, params, params.size()));
            updateSql.append(";");
        }
        updateSql.replace(updateSql.length() - 1, updateSql.length(), "");

        Query update = entityManager.createNativeQuery(updateSql.toString());

        params.forEach((k, v) -> {
            update.setParameter(k, v);
        });

        update.executeUpdate();
    }


    private String buildSetParams(CustomerEntity customerEntity, Map<Integer, Object> params, Integer index) {
        StringBuffer setSql = new StringBuffer();
        if (null != customerEntity.getAge()) {
            setSql.append("AGE = ?" + ++index + ",");
            params.put(index, customerEntity.getAge());
        }
        if (StringUtils.isNotEmpty(customerEntity.getName())) {
            setSql.append("NAME = ?" + ++index + ",");
            params.put(index, customerEntity.getName());
        }
        if (StringUtils.isNotEmpty(customerEntity.getCountry())) {
            setSql.append("COUNTRY = ?" + ++index + ",");
            params.put(index, customerEntity.getCountry());
        }

        String result = setSql.toString();
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    private String buildWhereParamsWithPK(CustomerEntity customerEntity, Map<Integer, Object> params, Integer index) {
        StringBuffer whereSql = new StringBuffer();
        if (null != customerEntity.getId()) {
            whereSql.append(" and ID  = ?" + ++index);
            params.put(index, customerEntity.getId());
        }

        String result = whereSql.toString();
        if (result.startsWith(" and")) {
            result = result.replaceFirst(" and", " ");
        }
        return result;
    }

    private String buildWhereParamsWithAllCondition(CustomerEntity customerEntity, Map<Integer, Object> params, Integer index) {
        StringBuffer whereSql = new StringBuffer();
        if (null != customerEntity.getId()) {
            whereSql.append(" and ID  = ?" + ++index);
            params.put(index, customerEntity.getId());
        }
        if (null != customerEntity.getName()) {
            whereSql.append(" and NAME  = ?" + ++index);
            params.put(index, customerEntity.getName());
        }
        String result = whereSql.toString();
        if (result.startsWith(" and")) {
            result = result.replaceFirst(" and", " ");
        }
        return result;
    }

    @Override
    public List<CustomerEntity> querySpecial(CustomerEntity customerEntity) {
        return null;
    }

    @Override
    public List<CustomerEntity> specialWithSex(Enum e) {
        return customerRepository.findAll(sex(e));
    }


    public static Specification<CustomerEntity> sex(Enum e) {
        return new Specification<CustomerEntity>() {
            @Override
            public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {

                return builder.equal(root.get("sex"), e);
            }
        };
    }

    public static Specification<CustomerEntity> aget(int age) {
        return new Specification<CustomerEntity>() {
            @Override
            public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {

                return builder.greaterThan(root.get("age"), age);
            }
        };
    }

    @Override
    public List<CustomerEntity> specialWithSexAndAge(Enum e, int age) {
        //return customerRepository.findAll(sexAndAge(e,age));
        return customerRepository.findAll(sexAndAge1(e, age));
    }


    public static Specification<CustomerEntity> sexAndAge(Enum e, int age) {
        return new Specification<CustomerEntity>() {
            @Override
            public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {

                return builder.and(builder.equal(root.get("sex"), e), builder.greaterThan(root.get("age"), age));
            }
        };
    }

    public static Specification<CustomerEntity> sexAndAge1(Enum e, int age) {
        return new Specification<CustomerEntity>() {
            @Override
            public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate finalConditions = builder.conjunction();
                finalConditions = builder.and(finalConditions, builder.equal(root.get("sex"), e));
                finalConditions = builder.and(finalConditions, builder.greaterThan(root.get("age"), age));
                return query.where(finalConditions).getRestriction();
            }
        };
    }

    @Override
    public List<CustomerEntity> specialMultiTable(Enum e, int age) {
        return customerRepository.findAll(tableJoin(e, age));
    }

    public static Specification<CustomerEntity> tableJoin(Enum e, int age) {
        return new Specification<CustomerEntity>() {
            @Override
            public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate finalConditions = builder.conjunction();
                Join<CustomerEntity, CustomerTradetypeRelationEntity> join = root.join("customerTradetypeRelationEntities", JoinType.LEFT);
                Predicate p1 = builder.equal(join.get("sex"), "e");
                finalConditions = builder.and(finalConditions, p1);
                finalConditions = builder.and(finalConditions, builder.equal(root.get("sex"), e));
                finalConditions = builder.and(finalConditions, builder.greaterThan(root.get("age"), age));
                return query.where(finalConditions).getRestriction();
            }
        };
    }

    @Override
    @Transactional
    public void refresh() {
        entityManager.flush();
        entityManager.clear();
    }
}
