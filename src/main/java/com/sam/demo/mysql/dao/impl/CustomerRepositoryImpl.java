package com.sam.demo.mysql.dao.impl;

import com.google.common.collect.Lists;
import com.sam.demo.VO.CustomerVO;
import com.sam.demo.mysql.dao.CustomerRepositoryCustomized;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


public class CustomerRepositoryImpl implements CustomerRepositoryCustomized {

    //@PersistenceContext(unitName = "persistentUnit")
    private EntityManager entityManager;

    @Override
    public List<CustomerVO> queryInvoiceDetailDate() {

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

    private List<CustomerVO> wrapCustomer(List<Object[]> objArrList) {
        List<CustomerVO> CustomerVO = Lists.newArrayList();
        if (CollectionUtils.isEmpty(objArrList)) {
            return Lists.newArrayList();
        }
        objArrList.forEach(objArr -> {

        });
        return CustomerVO;
    }
}
