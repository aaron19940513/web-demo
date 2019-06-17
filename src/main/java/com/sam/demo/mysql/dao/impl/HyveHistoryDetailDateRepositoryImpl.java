package com.sam.demo.mysql.dao.impl;

import com.google.common.collect.Lists;
import com.synnex.hyve.invoice.service.dto.InvoiceDetailDateDTO;
import com.synnex.hyve.invoice.service.vo.SageInvoiceImportVO;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

/**
 * The type Hyve history detail date repository.
 *
 * @author Rakim
 * @date 2019 /5/22
 */
public class HyveHistoryDetailDateRepositoryImpl implements HyveHistoryDetailDateRepositoryCustomized {

    @PersistenceContext(unitName = "hyvePersistentUnit")
    private EntityManager hyveEntityManager;

    @Override
    public List<InvoiceDetailDateDTO> queryInvoiceDetailDate(List<SageInvoiceImportVO> params) {
        if (CollectionUtils.isEmpty(params)) {
            return Lists.newArrayList();
        }
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
        for (int i = 1; i <= params.size(); i++) {
            int index = i * 3;
            stringBuilder.append(" ( dd.region_no=?");
            stringBuilder.append(String.valueOf(index - 2));
            stringBuilder.append(" and dd.order_type=?");
            stringBuilder.append(String.valueOf(index - 1));
            stringBuilder.append(" and dd.order_no=?");
            stringBuilder.append(String.valueOf(index));
            stringBuilder.append(" )");
            if (i != params.size()) {
                stringBuilder.append(" or ");
            }
        }
        stringBuilder.append(" );");
        Query query = hyveEntityManager.createNativeQuery(stringBuilder.toString());
        for (int i = 1; i <= params.size(); i++) {
            int index = i * 3;
            query.setParameter(index - 2, params.get(i - 1).getRegionNo());
            query.setParameter(index - 1, params.get(i - 1).getOriginOrderType());
            query.setParameter(index, params.get(i - 1).getOriginOrderNo());
        }
        List<Object[]> objArrList = query.getResultList();
        return wrapInvoiceDetailDateDTO(objArrList);
    }

    private List<InvoiceDetailDateDTO> wrapInvoiceDetailDateDTO(List<Object[]> objArrList) {
        List<InvoiceDetailDateDTO> invoiceDetailDateDTOS = Lists.newArrayList();
        if (CollectionUtils.isEmpty(objArrList)) {
            return Lists.newArrayList();
        }
        objArrList.forEach(objArr -> {
            InvoiceDetailDateDTO dto = new InvoiceDetailDateDTO();
            dto.setRegionNo(objArr[0] == null ? null : Integer.valueOf(objArr[0].toString()));
            dto.setOriginOrderType(objArr[1] == null ? null : Integer.valueOf(objArr[1].toString()));
            dto.setOriginOrderNo(objArr[2] == null ? null : Integer.valueOf(objArr[2].toString()));
            dto.setOrderLineNo(objArr[3] == null ? null : Integer.valueOf(objArr[3].toString()));
            dto.setCustPoLineNo(objArr[4] == null ? null : Integer.valueOf(objArr[4].toString()));
            dto.setRate(objArr[5] == null ? null : BigDecimal.valueOf(Double.valueOf(objArr[5].toString())));
            invoiceDetailDateDTOS.add(dto);
        });
        return invoiceDetailDateDTOS;
    }
}
