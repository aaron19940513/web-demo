package com.sam.demo.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

/**
 * The enum Invoice table header enum.
 *
 * @author Rakim
 * @date 2019 /5/20
 */
@AllArgsConstructor
@Getter
public enum InvoiceDetailTableHeaderEnum {
    @NotEmpty
    INVOICE_LINE_NO("invoiceLineNo", null, "LN", null, true,true),
    @Max(12)
    INVOICE_QTY("invoiceQty", null, "Invoice QTY", null, true,true),

    UNIT_PRICE("unitPrice", "currency", "Unit Price", null, true,true),

    CUST_PART_TYPE("custPartType", null, "Cust Part Type", null, true,true),

    IPN("ipn", null, "IPN", null, true,false),

    ASSET_ID("assetId", null, "Asset ID", null, true,false),

    SERIAL("totalInvoiceAmount", null, "Serial#", null, true,false),

    CPO_LINE_NO("cpoLineNo", null, "CPO LINE#", null, true,true),

    INVOICE_NO("invoiceNo", null, "Invoice#", null, true,false),

    HDS_CODE("hdsCode", null, "HDS Code", null, true,false),

    QST("QST", "currency", "QST", null, true,false),

    PST("PST", "currency", "PST", null, true,false),

    GST("GST", "currency", "GST", null, true,false),

    VAT("VAT", "currency", "VAT", null, true,false),

    ORIGIN_NAME("originName", null, "Origin Name", null, true,false),

    ORIGIN_COUNTRY("originCountry", null, "Origin Country", null, true,false);


    private String dataIndex;
    private String dataType;
    private String title;
    private String url;
    private Boolean visible;
    private Boolean sortable;

}
