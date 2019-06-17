package com.sam.demo.mysql.dao.impl;

import com.synnex.hyve.invoice.service.dto.InvoiceDetailDateDTO;
import com.synnex.hyve.invoice.service.vo.SageInvoiceImportVO;

import java.util.List;

/**
 * The interface Hyve history detail date repository customized.
 *
 * @author Rakim
 * @date 2019 /5/22
 */
public interface HyveHistoryDetailDateRepositoryCustomized {
    /**
     * Query invoice detail date list.
     *
     * @param rackImportVOS the rack import vos
     * @return the list
     */
    List<InvoiceDetailDateDTO> queryInvoiceDetailDate(List<SageInvoiceImportVO> rackImportVOS);
}
