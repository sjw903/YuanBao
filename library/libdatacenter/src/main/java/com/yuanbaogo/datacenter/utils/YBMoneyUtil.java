package com.yuanbaogo.datacenter.utils;

import android.text.TextUtils;

public class YBMoneyUtil {

    public static String YbPrice(String original) {
        if (TextUtils.isEmpty(original)) return "";
        String price = "";
        try {
            Integer p = Integer.parseInt(original);
            price = String.valueOf(p / 100) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;

    }
}
