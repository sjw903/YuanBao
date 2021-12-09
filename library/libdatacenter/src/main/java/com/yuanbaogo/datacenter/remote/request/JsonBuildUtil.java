package com.yuanbaogo.datacenter.remote.request;


import com.yuanbaogo.datacenter.encryption.DesCbcUtil;
import com.yuanbaogo.datacenter.encryption.EncryptUtil;
import com.yuanbaogo.libbase.baseutil.GsonUtil;

import java.util.Map;

public class JsonBuildUtil {

    public static String getParam(Map<String, Object> params, Object requestObj) {
        String jsonStr = "";
        if (requestObj != null) {
            jsonStr = GsonUtil.GsonString(requestObj);
        } else if (null != params && !params.isEmpty()) {
            jsonStr = GsonUtil.GsonString(params);
        }

        return jsonStr;
    }

    public static String getSpyParam(Map<String, Object> params, Object requestObj) {
        String jsonStr = "";
        if (requestObj != null) {
            jsonStr = GsonUtil.GsonString(requestObj);
        } else if (null != params && !params.isEmpty()) {
            jsonStr = GsonUtil.GsonString(params);
        }
        String des = DesCbcUtil.encode(jsonStr);
        return des;
    }

    public static String toMD5ForNetworkHelper(Map<String, String> map) {
        StringBuffer signCode = new StringBuffer();
        String[] keys = new String[map.size()];
        map.keySet().toArray(keys);

        int i;
        for (i = 0; i < keys.length; ++i) {
            for (int j = 0; j < keys.length - i - 1; ++j) {
                if (keys[j].compareTo(keys[j + 1]) > 0) {
                    String temp = keys[j];
                    keys[j] = keys[j + 1];
                    keys[j + 1] = temp;
                }
            }
        }

        for (i = 0; i < map.size(); ++i) {
            if (map.get(keys[i]) != null) {
                signCode.append(keys[i] + "=" + (String) map.get(keys[i]) + "");
            } else {
                signCode.append(keys[i] + "=");
            }
        }

        String sign = EncryptUtil.toMd5(signCode.toString().getBytes());
        return sign;
    }

}
