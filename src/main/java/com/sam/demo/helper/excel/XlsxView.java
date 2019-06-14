package com.sam.demo.helper.excel;

import com.google.common.net.UrlEscapers;
import com.synnex.hyve.invoice.base.util.ExcelUtils;
import com.synnex.hyve.invoice.service.vo.DownloadExcelVO;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * The type Xlsx view.
 */
@Component
public class XlsxView extends AbstractXlsxStreamingView {
    private Logger logger = LoggerFactory.getLogger(XlsxView.class);
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileName = null;
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                if (DownloadExcelVO.class.isInstance(entry.getValue())) {
                    DownloadExcelVO downloadExcelVO = (DownloadExcelVO) entry.getValue();
                    if (StringUtils.isEmpty(fileName)) {
                        fileName = downloadExcelVO.getFileName();
                        response.setHeader("Content-Disposition",
                                           String.format("attachment; filename=%s.xlsx", UrlEscapers.urlFragmentEscaper().escape(fileName)));
                    }
                    ExcelUtils.buildSheet(workbook, downloadExcelVO.getSheetName(), downloadExcelVO.getExcelEnumClazz(), downloadExcelVO.getExportVOClazz(),
                                          downloadExcelVO.getData());
                }
            }
        } catch (Exception e) {
            logger.error("Error while generate excel", e);
        }
    }
}