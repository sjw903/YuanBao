package com.yuanbaogo.datacenter.remote.parameter;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class ParameterBuilder {

    public static Map<String, Object> buildCommon(Context context) {
        Map<String, Object> header = new HashMap<>();

        header.put("uuid", "sdjkfjdskgjklfjgkfjdkjklkl");

        header.put("appVersion", "");

        header.put("timestamp", System.currentTimeMillis());

        return header;
    }
}
