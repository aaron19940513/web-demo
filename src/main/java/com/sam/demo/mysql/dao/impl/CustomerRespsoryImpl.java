package com.sam.demo.mysql.dao.impl;

import com.sam.demo.mysql.dao.CustomerRespsory;
import com.sam.demo.mysql.dao.base.BaseRepositoryImpl;
import com.sam.demo.mysql.entity.CustomerEntity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;



@Repository
public class CustomerRespsoryImpl extends BaseRepositoryImpl<CustomerEntity, Integer> implements CustomerRespsory<CustomerEntity>{

    public CustomerRespsoryImpl(Class<CustomerEntity> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
    }

    public List<CustomerEntity> queryHpql(Enum e) {
        return null;
    }


    public List<CustomerEntity> querySpecial(CustomerEntity customerEntity) {
        return findAll(filterName(customerEntity));
    }


    public static Specification<CustomerEntity> filterName(CustomerEntity customerEntity) {
        return new Specification<CustomerEntity>() {
            public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {


                return builder.equal(root.get("name"),customerEntity.getName() );
            }
        };
    }
}
