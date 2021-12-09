package com.yuanbaogo.libbase.baseutil;


import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @Description: 提供JSON数据解析和合成JSON字符串的工具类
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/3/21 10:38 AM
 * @Modifier:
 * @Modify:
 */
public class GsonUtil {

    /**
     * 将Bean类转换成json字符串
     *
     * @param object bean类
     * @return 标准JSON字符串
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        gsonString = JSON.toJSONString(object);
        return gsonString;
    }

    /**
     * 将JSON字符串“gsonString”反序列化成bean类<T>
     *
     * @param gsonString JSON格式的字符串
     * @param cls        bean类
     * @return 为传入的参数cls的类型。如果gsonString为null或空则返回null，
     * 如果gsonString中没有相对cls的有效数据则抛出异常JsonSyntaxException。
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;

        t = JSON.parseObject(gsonString, cls);

        return t;
    }


    public static <T> List<T> GsonToList(String gsonString, Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return JSON.parseArray(gsonString, clazz);
    }

}
