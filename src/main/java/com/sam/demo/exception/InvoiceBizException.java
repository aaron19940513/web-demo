package com.sam.demo.exception;


/**
 * The type Invoice biz exception.
 */
public class InvoiceBizException extends Exception {

    /**
     * Instantiates a new Invoice biz exception.
     */
    public InvoiceBizException() {
    }

    /**
     * Instantiates a new Invoice biz exception.
     *
     * @param message the message
     */
    public InvoiceBizException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Invoice biz exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public InvoiceBizException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Invoice biz exception.
     *
     * @param cause the cause
     */
    public InvoiceBizException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Invoice biz exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public InvoiceBizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Instantiates a new Invoice biz exception.
     *
     * @param params the params
     */
    public InvoiceBizException(Object[] params) {
        super(params);
    }

    /**
     * Instantiates a new Invoice biz exception.
     *
     * @param message the message
     * @param params  the params
     */
    public InvoiceBizException(String message, Object[] params) {
        super(message, params);
    }


}
