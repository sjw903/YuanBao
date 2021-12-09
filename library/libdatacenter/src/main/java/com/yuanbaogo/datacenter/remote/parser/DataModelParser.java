package com.yuanbaogo.datacenter.remote.parser;


import com.alibaba.fastjson.JSON;
import com.yuanbaogo.datacenter.encryption.DesCbcUtil;
import com.yuanbaogo.libbase.baseutil.LogUtil;
import com.yuanbaogo.network.exception.HttpException;
import com.yuanbaogo.network.parser.ModelParser;

import okhttp3.Response;

public class DataModelParser<T> extends ModelParser<T> {
    public DataModelParser(Class<T> clazz) {
        super(clazz);
    }

    protected T parse(Response response) throws Exception {
        String resp = response.body().string();
        LogUtil.d("datacenter", "ModelParser-&-response:" + resp);
        if (response.isSuccessful()) {


            if (isSpy()) {
                String des = DesCbcUtil.decode(resp);
                T object = JSON.parseObject(des, this.clazz);
                return object;
            }else{
                T object = JSON.parseObject(resp, this.clazz);
                return object;
            }
        } else {
            // 为了让错误信息进行回调，这里加上返回信息
            throw new HttpException(response.code(), resp);
        }

    }
}