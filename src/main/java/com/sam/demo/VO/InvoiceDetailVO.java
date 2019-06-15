package com.sam.demo.VO;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@BlockHeader(blockKey="invoiceDetail" , blockTitle="Invoice Detail")
public class InvoiceDetailVO {

    private int invoiceQty;

    private BigDecimal unitPrice;

    private String custPartType;

    private String ipn;

    private String assetId;

    private  String serialNo;

    private int cpoLineNo;

    private int invoiceNo;

    private String HDSCode;

    private BigDecimal qst;

    private BigDecimal pst;

    private BigDecimal gst;

    private BigDecimal vat;

    private String originName;

    private String originCountry;

}
