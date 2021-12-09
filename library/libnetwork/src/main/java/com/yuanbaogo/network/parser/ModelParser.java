package com.yuanbaogo.network.parser;


import com.alibaba.fastjson.JSON;
import com.yuanbaogo.network.exception.HttpException;

import okhttp3.Response;

public class ModelParser<T> extends BaseParser<T> {
    protected Class<T> clazz;

    public ModelParser(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected T parse(Response response) throws Exception {
        String resp = response.body().string();
        if (response.isSuccessful()) {
            T object = JSON.parseObject(resp, this.clazz);
            return object;
        } else {
            // 为了让错误信息进行回调，这里加上返回信息
            throw new HttpException(response.code(), resp);
        }
    }
}
