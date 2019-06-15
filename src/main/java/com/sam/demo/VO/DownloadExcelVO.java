package com.sam.demo.VO;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DownloadExcelVO {
    private String fileName;

    private String sheetName;

    private Class excelEnumClazz;

    private Class ExportVOClazz;

    private List data;

}
