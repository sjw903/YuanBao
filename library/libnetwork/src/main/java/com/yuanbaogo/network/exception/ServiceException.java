package com.yuanbaogo.network.exception;

public class ServiceException extends Exception {
    private int code;
    private String codeStr;

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(int code, String detailMessage) {
        super(detailMessage);
        this.code = code;
    }


    public ServiceException(String code) {
        this.codeStr = code;
    }

    public ServiceException(String code, String detailMessage) {
        super(detailMessage);
        this.codeStr = code;
    }


    public int getCode() {
        return this.code;
    }

    public String getStrCode() {
        return this.codeStr == null ? this.code + "" : this.codeStr;
    }

}

