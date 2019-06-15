package com.sam.demo.VO;

import lombok.*;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.Map;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelStyleVO {
    private CellStyle FieldStyle;

    private CellStyle headerStyle;

    private Map<String, CellStyle> SpecFieldValueStyleMap;
}
