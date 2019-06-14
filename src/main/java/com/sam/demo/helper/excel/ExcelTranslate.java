package com.sam.demo.helper.excel;
import java.util.List;

/**
 * The interface Excel translate enum.
 *
 * @author Rakim
 * @date 2018 /9/3
 */
public interface ExcelTranslate {
    /**
     * Gets name.
     *
     * @return the name
     */
    String getHeader();

    /**
     * Gets value.
     *
     * @return the value
     */
    String getField();


    /**
     * Gets headers.
     *
     * @return the headers
     */
    List<String> getHeaders();

    /**
     * Gets fields.
     *
     * @return the fields
     */
    List<String> getFields();

}
