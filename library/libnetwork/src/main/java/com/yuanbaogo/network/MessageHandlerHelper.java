package com.yuanbaogo.network;

import android.content.Intent;
import android.text.TextUtils;

public class MessageHandlerHelper {
    public MessageHandlerHelper() {
    }

    public static void handleMessage(String messageCode) {
        handleMessage(messageCode, (String) null);
    }

    public static void handleMessage(String messageCode, String message) {
        if (!TextUtils.isEmpty(messageCode)) {
            Intent intentBroadcast;

//            if (!TextUtils.isEmpty(message)) {
//                ToastUtil.showToast(message);
//            } else if ("10000".equals(messageCode)) {
//                ToastUtil.showToast("");
//            } else if ("40005".equals(messageCode)) {
//                ToastUtil.showToast("");
//            } else {
//                ToastUtil.showToast("");
//            }
        }
    }

    public class Code {
        public static final String NET = "10000";
        public static final String DATA = "10001";
        public static final String SUCCESS = "20000";
        public static final String VCODE = "40001";
        public static final String USER_EXISTS = "40002";
        public static final String PASSWORD = "40003";
        public static final String USER_UNEXITS = "40004";
        public static final String TOKEN_INVALID = "40005";
        public static final String ACCOUNT_FROZEN = "40006";
        public static final String ACCOUNT_FORBIDDEN = "40007";
        public static final String THREE_INFO_INVALID = "40010";
        public static final String VCODE_FREQUENCY = "40011";
        public static final String OPERATION_EXPIRED = "40012";
        public static final String SERVER = "50000";

        public Code() {
        }
    }
}
