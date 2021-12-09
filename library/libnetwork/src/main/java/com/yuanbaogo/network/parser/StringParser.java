package com.yuanbaogo.network.parser;

import com.yuanbaogo.network.exception.HttpException;

import okhttp3.Response;

public class StringParser extends BaseParser<String> {
    public StringParser() {
    }

    protected String parse(Response response) throws Exception {
        String responseInfo = null;
        if (response.body() != null) {
            responseInfo = response.body().string();
        }
        // 为了让错误信息进行回调，这里不做限制
        if (responseInfo != null) {
            return responseInfo;
        } else {
            return "";
        }
    }
}
