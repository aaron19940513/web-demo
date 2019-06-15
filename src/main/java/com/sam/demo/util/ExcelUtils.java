package com.sam.demo.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sam.demo.VO.ExcelStyleVO;
import com.sam.demo.constant.DateConst;
import com.sam.demo.exception.BaseException;
import com.sam.demo.helper.excel.BigExcelReader;
import com.sam.demo.helper.excel.ColumnStyle;
import com.sam.demo.helper.excel.ExcelTranslate;
import com.sam.demo.helper.excel.SpecFieldValueStyle;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.*;

/**
 * The type Excel export util.
 */
public class ExcelUtils {

    /**
     * The constant logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    private ExcelUtils() {

    }

    /**
     * Build excel sheet
     *
     * @param workbook       work book
     * @param sheetName      sheet name
     * @param excelEnumClazz excel enums class
     * @param exportClazz    export class
     * @param data           data
     */
    public static void buildSheet(Workbook workbook, String sheetName, Class excelEnumClazz, Class exportClazz, List data) {
        Sheet sheet = initSheet(workbook, sheetName);
        Map<String, ExcelStyleVO> styleMap = initCellStyleMap(workbook, exportClazz);
        doExport(workbook, styleMap, sheet, excelEnumClazz, data);
    }

    private static void doExport(Workbook workbook, Map<String, ExcelStyleVO> styleMap, Sheet sheet, Class excelEnumClazz, List data) {
        try {
            List<String> headers = Lists.newArrayList();
            List<String> fields = Lists.newArrayList();
            extractExcelEnum(excelEnumClazz, headers, fields);
            exportHeader(styleMap, sheet, headers, fields);
            exportData(workbook, styleMap, sheet, data, fields);
        } catch (Exception e) {
            //throw new BaseException("Excel export error", e);
        }
    }

    private static void extractExcelEnum(Class excelEnumClazz, List<String> headers, List<String> fields) throws Exception {
        Object[] enumConstants = excelEnumClazz.getEnumConstants();
        Method headerMethod = excelEnumClazz.getDeclaredMethod("getHeader");
        Method fieldMethod = excelEnumClazz.getDeclaredMethod("getField");
        for (Object enumConstant : enumConstants) {
            headers.add(headerMethod.invoke(enumConstant).toString());
            fields.add(fieldMethod.invoke(enumConstant).toString());
        }
    }

    private static void exportData(Workbook workbook, Map<String, ExcelStyleVO> styleMap, Sheet sheet, List data, List<String> fieldNames) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        CellStyle defaultDateStyle = defaultDateStyle(workbook);
        Integer dataStartRowNum = 1;
        for (Object obj : data) {
            Row row = sheet.createRow(dataStartRowNum++);
            for (int i = 0; i < fieldNames.size(); i++) {
                String filedName = fieldNames.get(i);
                Cell cell = row.createCell(i);
                Object fieldValue = null;
                try {
                    fieldValue = FieldUtils.getValueByField(obj, fieldNames.get(i));
                } catch (Exception e) {
                    LOGGER.error("Errors happened when parsing the field value. ", e);
                }
                handleCellValue(cell, fieldValue, defaultDateStyle);
                if (styleMap.get(filedName) != null) {
                    Map<String, CellStyle> specFieldStyleMap = styleMap.get(filedName).getSpecFieldValueStyleMap();
                    CellStyle fieldStyle = styleMap.get(filedName).getFieldStyle();
                    String fieldSpecKey = wrapFieldSpecKey(fieldValue);
                    if (specFieldStyleMap != null && specFieldStyleMap.get(fieldSpecKey) != null) {
                        cell.setCellStyle(specFieldStyleMap.get(fieldSpecKey));
                    } else if (fieldStyle != null) {
                        cell.setCellStyle(fieldStyle);
                    }
                }
            }
        }
    }

    private static void handleCellValue(Cell cell, Object fieldValue, CellStyle defaultDateStyle) {
        if (Date.class.isInstance(fieldValue)) {
            cell.setCellValue((Date) fieldValue);
            cell.setCellType(CellType.NUMERIC);
            cell.setCellStyle(defaultDateStyle);
        } else {
            cell.setCellValue(fieldValue == null ? null : fieldValue.toString());
        }
    }

    private static CellStyle defaultDateStyle(Workbook workbook) {
        String datePattern = DateConst.DATE_DISPLAY_FORMAT;
        CreationHelper creationHelper = workbook.getCreationHelper();
        short dataFormat = creationHelper.createDataFormat().getFormat(datePattern);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(dataFormat);
        return cellStyle;
    }


    private static void exportHeader(Map<String, ExcelStyleVO> styleMap, Sheet sheet, List<String> headerNames, List<String> fieldNames) {
        Integer headerRowNum = 0;
        Row header = sheet.createRow(headerRowNum);
        for (int i = 0; i < headerNames.size(); i++) {
            String headerName = headerNames.get(i);
            String fieldName = fieldNames.get(i);
            Cell cell = header.createCell(i);
            cell.setCellValue(headerName);
            if (styleMap.get(fieldName) != null) {
                CellStyle headerStyle = styleMap.get(fieldName).getHeaderStyle();
                if (headerStyle != null) {
                    cell.setCellStyle(headerStyle);
                }
            }
        }
    }

    private static Map<String, ExcelStyleVO> initCellStyleMap(Workbook workbook, Class exportClazz) {
        Map<String, ExcelStyleVO> styleMap = Maps.newHashMap();
        if (exportClazz == null) {
            return styleMap;
        }
        Arrays.stream(exportClazz.getDeclaredFields()).forEach(field -> {
            ColumnStyle columnStyle = field.getAnnotation(ColumnStyle.class);
            if (columnStyle != null) {
                ExcelStyleVO excelStyleVO = new ExcelStyleVO();
                String headerColor = columnStyle.headerColor();
                if (StringUtils.isNotEmpty(headerColor)) {
                    CellStyle headerStyle = workbook.createCellStyle();
                    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    headerStyle.setFillForegroundColor(obtainColor(headerColor));
                    headerStyle.setFillBackgroundColor(obtainColor(headerColor));
                    excelStyleVO.setHeaderStyle(headerStyle);
                }
                String color = columnStyle.color();
                String dataFormat = columnStyle.dataFormat();
                SpecFieldValueStyle[] specFieldValueStyles = columnStyle.specFieldValueStyle();
                if (StringUtils.isNotEmpty(color) || StringUtils.isNotEmpty(dataFormat)) {
                    CellStyle fieldStyle = fillFieldStyle(workbook, color, dataFormat);
                    excelStyleVO.setFieldStyle(fieldStyle);
                }
                if (ArrayUtils.isNotEmpty(specFieldValueStyles)) {
                    Map<String, CellStyle> specFieldStyleMap = Maps.newHashMap();
                    if (ArrayUtils.isNotEmpty(specFieldValueStyles)) {
                        Arrays.stream(specFieldValueStyles).forEach(spec -> {
                            String specValue = spec.specFiledValue();
                            CellStyle specStyle = fillFieldStyle(workbook, spec.color(), spec.dataFormat());
                            specFieldStyleMap.put(specValue, specStyle);
                        });
                    }
                    excelStyleVO.setSpecFieldValueStyleMap(specFieldStyleMap);
                }
                styleMap.put(field.getName(), excelStyleVO);
            }
        });
        return styleMap;
    }

    private static short obtainColor(String headerColor) {
        return IndexedColors.valueOf(headerColor.toUpperCase()).getIndex();
    }


    private static Sheet initSheet(Workbook workbook, String sheetName) {
        SXSSFWorkbook sxssfWorkbook = (SXSSFWorkbook) workbook;
        sxssfWorkbook.setCompressTempFiles(true);
        SXSSFSheet sheet = sxssfWorkbook.createSheet(sheetName);
        sheet.setRandomAccessWindowSize(100);
        sheet.setColumnWidth(0, 256 * 12);
        return sheet;
    }


    private static String wrapFieldSpecKey(Object fieldValue) {
        return fieldValue == null ? "" : fieldValue.toString();
    }

    private static CellStyle fillFieldStyle(Workbook workbook, String color, String dataFormat) {
        CellStyle fieldStyle = workbook.createCellStyle();
        fieldStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        if (StringUtils.isNotEmpty(color)) {
            fieldStyle.setFillForegroundColor(obtainColor(color));
            fieldStyle.setFillBackgroundColor(obtainColor(color));
        }
        if (StringUtils.isNotEmpty(dataFormat)) {
            CreationHelper creationHelper = workbook.getCreationHelper();
            short format = creationHelper.createDataFormat().getFormat(dataFormat);
            fieldStyle.setDataFormat(format);
        }
        return fieldStyle;
    }


    /**
     * Parse string array to object list.
     *
     * @param <T>             the type parameter
     * @param lines           the lines
     * @param enums           the enums
     * @param contentStartRow the content start row
     * @param targetClazz     the target clazz
     * @return the list
     * @throws Exception the exception
     */
    public static <T> List<T> parseStringArrayToObject(List<String[]> lines, ExcelTranslate[] enums, Integer contentStartRow, Class<T> targetClazz)
        throws Exception {
        LOGGER.info("Excel parse PO  start, target class={}", targetClazz);
        //Do pre handle line data,replace special html character with empty or space.
        preHandleLineData(lines);
        if (CollectionUtils.isEmpty(lines)) {
            return Lists.newArrayList();
        }
        String[] headLine = lines.get(0);
        Map<Integer, ExcelTranslate> indexMap = Maps.newHashMap();
        //Match ExcelTranslate'name with import excel head ignore case.
        matchDefinedEnumField(enums, headLine, indexMap);
        if (contentStartRow == null) {
            contentStartRow = 1;
        }
        int totalSize = lines.size();
        List<T> contents = new ArrayList<>(totalSize);
        for (int i = contentStartRow; i <= lines.size() - 1; i++) {
            T instance;
            try {
                instance = targetClazz.newInstance();
            } catch (Exception e) {
                throw new BaseException("Create target VO failed.", e);
            }
            String[] dataLine = lines.get(i);
            if (dataLine == null) {
                continue;
            }
            //Integer index : indexMap.keySet()
            for (Map.Entry<Integer, ExcelTranslate> entry : indexMap.entrySet()) {
                Integer index = entry.getKey();
                //If Head line array size is larger than other line array,ignore the head cell, and the correspond VO field is default.
                if (index >= dataLine.length) {
                    continue;
                }
                String value = dataLine[index];
                //It means if excel cell is empty,corresponding VO field will not set any value,its value is default through bean's creation.
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                try {
                    //FieldUtils.setValueByFieldWithSpecType(instance, entry.getValue().getField(), value);
                } catch (Exception e) {
                    throw new BaseException(String.format("Row %s,Column[%s] format error.", i, indexMap.get(index).getHeader()), e);
                }
            }
            FieldUtils.setValueByField(instance, "uploadLineNo", i + 1);
            contents.add(instance);
        }
        LOGGER.info("Excel parse PO  end, successful transformed VO size={}", contents.size());
        return contents;
    }

    private static void preHandleLineData(List<String[]> lines) {
        lines.forEach(line -> {
            for (int i = 0; i < line.length; i++) {
                line[i] = WhiteSpaceUtil.sanitize(line[i], true);
            }
        });
    }


    private static void matchDefinedEnumField(ExcelTranslate[] enums, String[] headLine,
                                              Map<Integer, ExcelTranslate> indexMap) {
        Arrays.stream(enums).forEach(transEnum -> {
            for (int i = 0; i < headLine.length; i++) {
                String headValue = StringUtils.isEmpty(headLine[i]) ? headLine[i] : headLine[i].trim();
                if (transEnum.getHeader().equalsIgnoreCase(headValue)) {
                    indexMap.put(i, transEnum);
                    break;
                }
                if (i == headLine.length - 1) {
                    //throw new BaseException(String.format("%s is required but the excel file does not provide.", transEnum.getHeader()));
                }
            }
        });
    }

    /**
     * Trans big excel to vos list.
     *
     * @param <T>                the type parameter
     * @param file               the file
     * @param excelTranslateEnum the excel translate enums
     * @param targetClazz        the target clazz
     * @return the list
     */
    public static <T> List<T> transExcelToVOS(MultipartFile file, Class<? extends ExcelTranslate> excelTranslateEnum, Class<T> targetClazz) {
        return transExcelToVOS(file, excelTranslateEnum, targetClazz, 1);
    }

    /**
     * Trans big excel to vos list.
     *
     * @param <T>                the type parameter
     * @param file               the file
     * @param excelTranslateEnum the excel translate enums
     * @param targetClazz        the target clazz
     * @param sheetIndex         the sheet index
     * @return the list
     */
    public static <T> List<T> transExcelToVOS(MultipartFile file, Class<? extends ExcelTranslate> excelTranslateEnum, Class<T> targetClazz,
                                              Integer sheetIndex) {
        try {
            LOGGER.info("Excel trans string array start");
            ExcelTranslate[] enums = (ExcelTranslate[]) excelTranslateEnum.getMethod("values").invoke(null);
            List<String[]> lines = Lists.newArrayList();
            new BigExcelReader(file.getInputStream(), sheetIndex) {
                @Override
                protected void outputRow(String[] strArray, int[] rowTypes, int rowIndex) {
                    lines.add(strArray);
                }
            }.parse();
            LOGGER.info("Excel trans string array end, line size ={}", lines.size());
            return parseStringArrayToObject(lines, enums, 1, targetClazz);
        } catch (Exception e) {
            // throw new BaseException("Excel imports with error", e);
        }
        return  null;
    }

}
