package com.yuanbaogo.mine.setting.utils;

import android.content.Context;

import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.Map;

public class PhoneNetworkUtils {

    private static final String TAG = "PhoneNetworkUtils";

    /**
     * 发送验证码
     *
     * @param context  /
     * @param phone    手机号
     * @param listener 回调
     */
    public void sendCode(Context context, String phone, OnPhoneResultListener listener) {
        NetWork.getInstance().get(context, ChildUrl.SEND_CODE + "/" + phone, null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                if (bean == null) {
                    return;
                }
                if (Boolean.parseBoolean(bean)) {
                    listener.onPhoneResult(true);
                } else {
                    listener.onPhoneResult(false);
                    ToastView.showToast(R.string.pay_code_error);
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }

    /**
     * 验证验证码
     *
     * @param context  /
     * @param phone    手机号
     * @param code     验证码
     * @param listener 回调
     */
    public void checkCaptcha(Context context, String phone, String code, OnPhoneResultListener listener) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("phone", phone);
        NetWork.getInstance().post(context, ChildUrl.CHECK_CAPTCHA, map, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                if (bean == null) {
                    return;
                }
                listener.onPhoneResult(Boolean.parseBoolean(bean));
            }

            @Override
            public void onFailure(Throwable var1) {
                listener.onPhoneResult(false);
            }
        });
    }

    /**
     * 结果回调接口
     */
    public interface OnPhoneResultListener {
        void onPhoneResult(boolean successful);
    }

}
