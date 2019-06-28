package com.sam.demo.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author sam
 * @date 2019/6/19 15:37
 */
public class ResultEntity<T> implements Serializable {

    @ApiModelProperty("Actual response data as JSON")
    T data;
    @ApiModelProperty(
            value = "API http response status. 200 is ok, 500 is failed.",
            example = "200",
            required = true
    )
    private int status = 200;
    @ApiModelProperty("Human readable response message")
    private String message;
    @ApiModelProperty(
            value = "Unique request  id",
            example = "5a124806f9f9e98924014e75",
            required = true
    )
    private String requestId;
    @ApiModelProperty(
            value = "Country code, depends on config synnex.system.companyName, defaults to US",
            example = "US",
            required = true
    )
    private String countryCode;
    @ApiModelProperty(
            value = "Currency code, depends on config synnex.currency.locale, defaults to USD",
            example = "USD",
            required = true
    )
    private String currencyCode;
    @ApiModelProperty(
            value = "To be retired",
            example = "en_US"
    )
    private String languageLocale;
    @ApiModelProperty(
            value = "To be retired",
            example = "USD"
    )
    private String localCurrencyCode;
    @ApiModelProperty(
            value = "To be retired",
            example = "snx-us-bg"
    )
    private String headerStyleClass;

    public ResultEntity() {
    }

    public ResultEntity(T data) {
        this.data = data;
    }

    public int getStatus() {
        return this.status;
    }

    public ResultEntity setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ResultEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public ResultEntity setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getLanguageLocale() {
        return this.languageLocale;
    }

    public ResultEntity setLanguageLocale(String languageLocale) {
        this.languageLocale = languageLocale;
        return this;
    }

    public String getLocalCurrencyCode() {
        return this.localCurrencyCode;
    }

    public ResultEntity setLocalCurrencyCode(String localCurrencyCode) {
        this.localCurrencyCode = localCurrencyCode;
        return this;
    }

    public String getHeaderStyleClass() {
        return this.headerStyleClass;
    }

    public ResultEntity setHeaderStyleClass(String headerStyleClass) {
        this.headerStyleClass = headerStyleClass;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public ResultEntity setData(T data) {
        this.data = data;
        return this;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @JsonIgnore
    public boolean normal() {
        return this.status == 200;
    }

    public String toString() {
        return "JsonEntity{status=" + this.status + ", message='" + this.message + '\'' + ", requestId='" + this.requestId + '\'' + ", countryCode='" + this.countryCode + '\'' + ", currencyCode='" + this.currencyCode + '\'' + ", languageLocale='" + this.languageLocale + '\'' + ", localCurrencyCode='" + this.localCurrencyCode + '\'' + ", headerStyleClass='" + this.headerStyleClass + '\'' + ", data=" + this.data + '}';
    }
}
