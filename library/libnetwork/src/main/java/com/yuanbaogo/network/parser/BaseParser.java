package com.yuanbaogo.network.parser;

import okhttp3.Response;

public abstract class BaseParser<T> {
    protected int code;
    protected String msg;
    private boolean isSpy = false;

    public BaseParser() {
    }

    protected abstract T parse(Response var1) throws Exception;

    public T parseResponse(Response response) throws Exception {
        this.code = response.code();
        this.msg = response.message();
        return this.parse(response);
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.msg;
    }

    public boolean isSpy() {
        return isSpy;
    }

    public void setSpy(boolean spy) {
        isSpy = spy;
    }
}