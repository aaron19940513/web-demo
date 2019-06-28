package com.sam.demo.mysql.dao.impl;

import com.google.common.collect.Lists;
import com.sam.demo.VO.CustomerTradetypeRelationVO;
import com.sam.demo.VO.CustomerVO;
import com.sam.demo.mysql.dao.CustomerRepository;
import com.sam.demo.mysql.dao.CustomerRepositoryCustomized;
import com.sam.demo.mysql.entity.CustomerEntity;
import com.sam.demo.mysql.entity.CustomerTradetypeRelationEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;


public class CustomerRepositoryImpl implements CustomerRepositoryCustomized {

    @Autowired
    private CustomerRepository customerRepository;

    @PersistenceContext(unitName = "persistentUnit")
    private EntityManager entityManager;



    @Override
    public List<CustomerEntity> querySpecial(CustomerEntity customerEntity) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" SELECT "
                + " dd.region_no, "
                + " dd.order_type, "
                + " dd.order_no, "
                + " dd.order_line_no, "
                + " dd.cust_po_line_no, "
                + " dd.rate "
                + " FROM HYVE.hyve_history_detail_date dd "
                + " WHERE "
                + " dd.order_line_no is not null "
                + " and (");

        stringBuilder.append(" );");
        Query query = entityManager.createNativeQuery(stringBuilder.toString());

        List<Object[]> objArrList = query.getResultList();
        return wrapCustomer(objArrList);
    }



    private List<CustomerEntity> wrapCustomer(List<Object[]> objArrList) {
        List<CustomerEntity> CustomerVO = Lists.newArrayList();
        if (CollectionUtils.isEmpty(objArrList)) {
            return Lists.newArrayList();
        }
        objArrList.forEach(objArr -> {

        });
        return CustomerVO;
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
    public List<CustomerEntity> specialWithSexAndAge(Enum e,int age) {
        //return customerRepository.findAll(sexAndAge(e,age));
        return customerRepository.findAll(sexAndAge1(e,age));
    }



    public static Specification<CustomerEntity> sexAndAge(Enum e,int age) {
        return new Specification<CustomerEntity>() {
            @Override
            public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {

                return builder.and(builder.equal(root.get("sex"), e),builder.greaterThan(root.get("age"), age));
            }
        };
    }

    public static Specification<CustomerEntity> sexAndAge1(Enum e,int age) {
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
        return customerRepository.findAll(tableJoin(e,age));
    }
    public static Specification<CustomerEntity> tableJoin(Enum e,int age) {
        return new Specification<CustomerEntity>() {
            @Override
            public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate finalConditions = builder.conjunction();
                Join<CustomerEntity, CustomerTradetypeRelationEntity> join = root.join("customerTradetypeRelationEntities",JoinType.LEFT);
                Predicate p1 = builder.equal(join.get("tradeTypeId"),"1");
                finalConditions = builder.and(finalConditions, p1);
                finalConditions = builder.and(finalConditions, builder.equal(root.get("sex"), e));
                finalConditions = builder.and(finalConditions, builder.greaterThan(root.get("age"), age));
                return query.where(finalConditions).getRestriction();
            }
        };
    }
}
