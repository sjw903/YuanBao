package com.yuanbaogo.datacenter.remote.parser;

import com.alibaba.fastjson.JSON;
import com.yuanbaogo.datacenter.encryption.DesCbcUtil;
import com.yuanbaogo.network.exception.HttpException;
import com.yuanbaogo.network.parser.BaseParser;

import java.util.List;

import okhttp3.Response;

public class DataArrayParser<T> extends BaseParser<List<T>> {
    protected Class<T> clazz;

    public DataArrayParser(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> parse(Response response) throws Exception {

        String resp = response.body().string();

        if (response.isSuccessful()) {
            if (clazz == null) {
                return null;
            }
            if (isSpy()) {
                String des = DesCbcUtil.decode(resp);
                return JSON.parseArray(des, clazz);
            }else{
                return JSON.parseArray(resp, clazz);
            }
        } else {
            // 为了让错误信息进行回调，这里加上返回信息
            throw new HttpException(response.code(), resp);
        }
    }
}