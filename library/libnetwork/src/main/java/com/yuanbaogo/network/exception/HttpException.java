package com.yuanbaogo.network.exception;

public class HttpException extends Exception {
    private int mStatusCode;

    public HttpException() {
    }

    public HttpException(int code) {
        this.mStatusCode = code;
    }

    public HttpException(int code, String detailMessage) {
        super(detailMessage);
        this.mStatusCode = code;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }
}
