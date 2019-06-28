package com.sam.demo.mysql.dao.impl;

import com.google.common.collect.Lists;
import com.sam.demo.VO.CustomerVO;
import com.sam.demo.mysql.dao.CustomerRepositoryCustomized;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomerRepositoryImpl implements CustomerRepositoryCustomized {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceDetailRepositoryImpl.class);
    @PersistenceContext(unitName = "invoicePersistentUnit")
    private EntityManager invoiceEntityManager;

    @Override
    public void updateBatch(List<InvoiceDetail> invoiceDetails) {
        if (null == invoiceDetails || invoiceDetails.isEmpty()) {
            throw new InvoiceBizException("update invoice failed,the invoice number is null!");
        }
        StringBuffer updateSql = new StringBuffer();
        Map<Integer, Object> params = new HashMap<>();
//        for (InvoiceDetail invoiceDetail : invoiceDetails) {
//            updateSql.append("UPDATE HYVE_INVOICE.invoice_detail  SET ");
//            updateSql.append(buildSetParams(invoiceDetail, params, params.size()));
//            updateSql.append("WHERE");
//            updateSql.append(buildWhereParams(invoiceDetail, params,  params.size()));
//            updateSql.append(";");
//        }

        updateSql.append(" UPDATE HYVE_INVOICE.invoice_detail  SET ");
        updateSql.append(buildSetParams(invoiceDetails.get(0), params, params.size()));
        updateSql.append(" WHERE");
        updateSql.append(buildWhereParams(invoiceDetails.get(0), params,  params.size()));
        updateSql.append(";");
        Query update = invoiceEntityManager.createNativeQuery(updateSql.toString());

        params.forEach((k, v) -> {
            update.setParameter(k, v);
        });
        //update.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        update.executeUpdate();
    }


    private String buildSetParams(InvoiceDetail invoiceDetail, Map<Integer, Object> params, Integer index) {
        StringBuffer setSql = new StringBuffer();
        if (null != invoiceDetail.getInvoiceQty()) {
            setSql.append("invoice_qty = ?" + ++index + ",");
            params.put(index, invoiceDetail.getInvoiceQty());
        }
        if (null != invoiceDetail.getUnitPrice()) {
            setSql.append("unit_price = ?" + ++index + ",");
            params.put(index, invoiceDetail.getUnitPrice());
        }
        if (StringUtils.isNotEmpty(invoiceDetail.getCustPartType())) {
            setSql.append("cust_part_type = ?" + ++index + ",");
            params.put(index, invoiceDetail.getCustPartType());
        }
        if (StringUtils.isNotEmpty(invoiceDetail.getCustPartNo())) {
            setSql.append("cust_part_no = ?" + ++index + ",");
            params.put(index, invoiceDetail.getCustPartNo());
        }
        if (null != invoiceDetail.getCustPoLineNo()) {
            setSql.append("invoice_line_no = ?" + ++index + ",");
            params.put(index, invoiceDetail.getCustPoLineNo());
        }
        if (StringUtils.isNotEmpty(invoiceDetail.getOriginName())) {
            setSql.append("origin_name = ?" + ++index + ",");
            params.put(index, invoiceDetail.getOriginName());
        }
        if (StringUtils.isNotEmpty(invoiceDetail.getOriginCountry())) {
            setSql.append("origin_country = ?" + ++index + ",");
            params.put(index, invoiceDetail.getOriginCountry());
        }
        if (null != invoiceDetail.getUpdateId()) {
            setSql.append("update_id = ?" + ++index + ",");
            params.put(index, invoiceDetail.getUpdateId());
        }

        String result = setSql.toString();
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    private String buildWhereParams(InvoiceDetail invoiceDetail, Map<Integer, Object> params, Integer index) {
        StringBuffer whereSql = new StringBuffer();
        if (null != invoiceDetail.getInvoiceNumber()) {
            whereSql.append("and invoice_number  = ?" + ++index);
            params.put(index, invoiceDetail.getInvoiceNumber());
        }

        String result = whereSql.toString();
        if (result.startsWith("and")) {
            result = result.replaceFirst("and", "");
        }
        return result;
    }
}
