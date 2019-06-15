package com.sam.demo.helper.excel;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The type Big excel reader.
 */
public abstract class BigExcelReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(BigExcelReader.class);

    /**
     * The enum Xssf data type.
     */
    enum XssfDataType {
        /**
         * Bool xssf data type.
         */
        BOOL, /**
         * Error xssf data type.
         */
        ERROR, /**
         * Formula xssf data type.
         */
        FORMULA, /**
         * Inlinestr xssf data type.
         */
        INLINESTR, /**
         * Sstindex xssf data type.
         */
        SSTINDEX, /**
         * Number xssf data type.
         */
        NUMBER,
    }

    /**
     * The constant ERROR.
     */
    public static final int ERROR = 1;
    /**
     * The constant BOOLEAN.
     */
    public static final int BOOLEAN = 1;
    /**
     * The constant NUMBER.
     */
    public static final int NUMBER = 2;
    /**
     * The constant STRING.
     */
    public static final int STRING = 3;
    /**
     * The constant DATE.
     */
    public static final int DATE = 4;
    /**
     * The constant DATE_FORMAT_STR.
     */
    public static final String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

    private InputStream sheet;
    private XMLReader parser;
    private InputSource sheetSource;
    private int index = 0;

    /**
     * To read the big excel file
     *
     * @param in         of file
     * @param sheetIndex the sheet index
     * @throws IOException        the io exception
     * @throws OpenXML4JException the open xml 4 j exception
     * @throws SAXException       the sax exception
     */
    public BigExcelReader(InputStream in, Integer sheetIndex) throws IOException, OpenXML4JException, SAXException {
        ZipSecureFile.setMinInflateRatio(0);
        OPCPackage pkg = OPCPackage.open(in);
        init(pkg, sheetIndex);
    }


    /**
     * initial,convert Excel to XML
     *
     * @param pkg pkg
     * @throws IOException        io exception
     * @throws OpenXML4JException open xml exception
     * @throws SAXException       sax exception
     */
    private void init(OPCPackage pkg, Integer sheetIndex) throws IOException, OpenXML4JException, SAXException {
        XSSFReader xssfReader = new XSSFReader(pkg);
        SharedStringsTable sharedStringsTable = xssfReader.getSharedStringsTable();
        StylesTable stylesTable = xssfReader.getStylesTable();
        sheet = xssfReader.getSheet("rId" + sheetIndex);
        parser = fetchSheetParser(sharedStringsTable, stylesTable);
        sheetSource = new InputSource(sheet);
    }

    /**
     * parse file
     *
     * @return read the row num of Excel
     */
    public int parse() {
        try {
            parser.parse(sheetSource);
        } catch (IOException e) {
            LOGGER.error("Error happened when parse the big excel. ", e);
        } catch (SAXException e) {
            LOGGER.error("Error happened when parse the big excel. ", e);
        } finally {
            if (sheet != null) {
                try {
                    sheet.close();
                } catch (IOException e) {
                    LOGGER.error("Error happened when parse the big excel. ", e);
                }
            }
        }
        return index;
    }

    private XMLReader fetchSheetParser(SharedStringsTable sharedStringsTable, StylesTable stylesTable) throws SAXException {
        XMLReader parser =
            XMLReaderFactory.createXMLReader(
                "org.apache.xerces.parsers.SAXParser"
            );
        ContentHandler handler = new SheetHandler(sharedStringsTable, stylesTable);
        parser.setContentHandler(handler);
        return parser;
    }

    /**
     * SAX Parse class
     * row data parsed and output via the function outputRow(String[] datas, int[] rowTypes, int rowIndex)
     *
     * @author nancyw
     */
    private class SheetHandler extends DefaultHandler {
        private SharedStringsTable sharedStringsTable;
        private StylesTable stylesTable;
        private String readValue;
        private XssfDataType dataType;
        private String[] rowDatas;
        private int[] rowTypes;
        private int colIdx;

        private int formatIndex;
        private String formatString;

        private SheetHandler(SharedStringsTable sst, StylesTable stylesTable) {
            this.sharedStringsTable = sst;
            this.stylesTable = stylesTable;
        }


        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) {
            if (name.equals("c")) {
                clearElementData();
                colIdx = getColumn(attributes);
                String cellType = attributes.getValue("t");
                String cellStyle = attributes.getValue("s");
                this.dataType = XssfDataType.NUMBER;
                if ("b".equals(cellType)) {
                    this.dataType = XssfDataType.BOOL;
                } else if ("e".equals(cellType)) {
                    this.dataType = XssfDataType.ERROR;
                } else if ("inlineStr".equals(cellType)) {
                    this.dataType = XssfDataType.INLINESTR;
                } else if ("s".equals(cellType)) {
                    this.dataType = XssfDataType.SSTINDEX;
                } else if ("str".equals(cellType)) {
                    this.dataType = XssfDataType.FORMULA;
                } else if (cellStyle != null) {
                    int styleIndex = Integer.parseInt(cellStyle);
                    XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                    this.formatIndex = style.getDataFormat();
                    this.formatString = style.getDataFormatString();
                }
            } else if (name.equals("row")) {// init the array when starting to parse the row
                int cols = getColsNum(attributes);
                rowDatas = new String[cols];
                rowTypes = new int[cols];
            }
        }


        private void dealWithData() {
            switch (this.dataType) {
                case BOOL: {
                    char first = readValue.charAt(0);
                    rowDatas[colIdx] = first == '0' ? "FALSE" : "TRUE";
                    rowTypes[colIdx] = BOOLEAN;
                    break;
                }
                case ERROR: {
                    rowDatas[colIdx] = "ERROR:" + readValue;
                    rowTypes[colIdx] = ERROR;
                    break;
                }
                case INLINESTR: {
                    rowDatas[colIdx] = new XSSFRichTextString(readValue).toString();
                    rowTypes[colIdx] = STRING;
                    break;
                }
                case SSTINDEX: {
                    int idx = Integer.parseInt(readValue);
                    rowDatas[colIdx] = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx)).toString();
                    rowTypes[colIdx] = STRING;
                    break;
                }
                case FORMULA: {
                    rowDatas[colIdx] = readValue;
                    rowTypes[colIdx] = STRING;
                    break;
                }
                case NUMBER: {
                    if (HSSFDateUtil.isADateFormat(formatIndex, formatString)) {
                        Double d = Double.parseDouble(readValue);
                        Date date = HSSFDateUtil.getJavaDate(d);
                        if (date != null) {
                            rowDatas[colIdx] = DateFormatUtils.format(date, DATE_FORMAT_STR);
                        } else {
                            rowDatas[colIdx] = "";
                        }
                        rowTypes[colIdx] = DATE;
                    } else {
                        rowDatas[colIdx] = new BigDecimal(readValue).toPlainString();
                        rowTypes[colIdx] = NUMBER;
                    }
                    break;
                }
                default:
                    break;
            }
        }

        public void endElement(String uri, String localName, String name) {
            if (name.equals("v")) {
                dealWithData();
            } else if (name.equals("row")) { // output the data when entering the end of the row.
                outputRow(rowDatas, rowTypes, index++);
            }
        }

        public void characters(char[] ch, int start, int length) {
            readValue += new String(ch, start, length);
        }

        private void clearElementData() {
            //clear
            readValue = "";
            colIdx = 0;
            dataType = null;
            formatIndex = 0;
            formatString = "";
        }
    }


    /**
     * output the data of the row.
     *
     * @param datas    the datas
     * @param rowTypes the row types
     * @param rowIndex the row index
     */
    protected abstract void outputRow(String[] datas, int[] rowTypes, int rowIndex);

    private int getColumn(Attributes attrubuts) {
        String name = attrubuts.getValue("r");
        int column = -1;
        for (int i = 0; i < name.length(); ++i) {
            if (Character.isDigit(name.charAt(i))) {
                break;
            }
            int c = name.charAt(i);
            column = (column + 1) * 26 + c - 'A';
        }
        return column;
    }

    private int getColsNum(Attributes attrubuts) {
        String spans = attrubuts.getValue("spans");
        String cols = spans.substring(spans.indexOf(":") + 1);
        return Integer.parseInt(cols);
    }
}
