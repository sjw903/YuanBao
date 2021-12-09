package com.yuanbaogo.datacenter.remote.parser;


import com.yuanbaogo.network.parser.ModelParser;

public class ModelParserWithException<T> extends ModelParser<T> {
    public ModelParserWithException(Class<T> clazz) {
        super(clazz);
    }

//    protected T parse(Response response) throws Exception {
//        if (response.isSuccessful()) {
//            String result = response.body().string();
//            JSONObject jsonObject = JSONObject.parseObject(result);
//            if (jsonObject != null) {
//                String error_code = jsonObject.get("status").toString();
//                if ("success".equals(error_code)) {
//                    T object = JSON.parseObject(result, this.clazz);
//                    return object;
//                } else if (jsonObject.get("message") != null) {
//                    throw new ServiceException(0, jsonObject.get("message").toString());
//                } else {
//                    throw new ServiceException(0, "请求数据失败");
//                }
//            } else {
//                throw new ServiceException(0);
//            }
//        } else {
//            throw new HttpException(response.code());
//        }
//    }
}
