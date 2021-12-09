package com.yuanbaogo.commonlib.callcenter;

import android.util.Log;

import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserInfo;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libbase.baseutil.GsonUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CallCenter {


    private static String root = "https://webchat-bj.clink.cn/chat.html";

    public static void toService(String goodsId, String orderId, String price, String imageUrl, String goodsName) {
        String visitorId = "";
        String visitorName = "";
        String tel = "";
        UserInfo user = UserStore.getUser();
        if (user != null) {
            visitorId = user.getUserId();
            visitorName = user.getNickName();
            tel = user.getPhone();
        } else {
            RouterHelper.toLogin();
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("visitorName", visitorName);
        params.put("visitorId", visitorId);
        params.put("accessId", "74715b7f-d6f6-485c-bd67-6607116a0df5");
        params.put("tel", tel);
        params.put("language", "zh_CN");

        Map<String, String> goodsParams = new HashMap<>();
        if (!TextUtils.isEmpty(imageUrl)) {
            goodsParams.put("img", imageUrl);
        }
        if (!TextUtils.isEmpty(price)) {
            goodsParams.put("price", price);
        }
        if (!TextUtils.isEmpty(goodsId)) {
            goodsParams.put("subTitle", goodsName + "【" + goodsId + "】");
        } else if (!TextUtils.isEmpty(orderId)) {
            goodsParams.put("subTitle", goodsName + "【" + orderId + "】");
        }

        String jsonStr = null;
        if (goodsParams.size() != 0) {
            jsonStr = GsonUtil.GsonString(goodsParams);
//            params.put("linkCard", EncodingUtil.encodeURIComponent(jsonStr));
        }

        String serviceUrl = appendParams(root, params);
        if (jsonStr != null) {
            RouterHelper.toWebJs(serviceUrl + "&linkCard=" + EncodingUtil.encodeURIComponent(jsonStr), true);
        } else {
            RouterHelper.toWebJs(serviceUrl, true);
        }

    }


    private static String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        if (url.contains("?")) {
            sb.append(url + "&");
        } else {
            sb.append(url + "?");
        }

        if (params != null && !params.isEmpty()) {
            Iterator var4 = params.keySet().iterator();

            while (var4.hasNext()) {
                String key = (String) var4.next();
                sb.append(key).append("=").append(params.get(key) == null ? "" : (String) params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
