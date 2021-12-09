package com.yuanbaogo.mine.setting.account.newphone;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;

import java.util.HashMap;
import java.util.Map;

public class NewPhonePresenter extends MvpBasePersenter<NewPhoneContract.View> implements NewPhoneContract.Presenter {

    private static final String TAG = "NewPhonePresenter";

    @Override
    public void sendCode(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        NetWork.getInstance().post(getContext(), ChildUrl.SEND_NEW_RESET_CODE, map, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.e(TAG, "onSuccess: code:" + code + "  bean:" + bean);
                if (!isActive() || bean == null) {
                    return;
                }
                getView().startTimer();
            }

            @Override
            public void onFailure(Throwable var1) {
                if (!isActive()) {
                    return;
                }
                Log.e(TAG, "onFailure: Throwable:" + var1.getMessage());
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }

    @Override
    public void checkCaptcha(String phone, String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("phone", phone);
        NetWork.getInstance().post(getContext(), ChildUrl.NEXT_STEP_PHONE, map, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                if (!isActive() || bean == null) {
                    return;
                }
                getView().checkCaptcha(true, bean);
            }

            @Override
            public void onFailure(Throwable var1) {
                if (!isActive()) {
                    return;
                }
                getView().checkCaptcha(false, "");
            }
        });
    }


    @Override
    public void bindNewPhone(String newPhone, String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("newPhone", newPhone);
        map.put("code", code);
        NetWork.getInstance().post(getContext(), ChildUrl.CONFIRM_UPDATE_PHONE, map, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.e(TAG, "onSuccess: code:" + code + "  bean:" + bean);
                if (!isActive() || bean == null) {
                    return;
                }
                getView().bindSuccess(newPhone);
            }

            @Override
            public void onFailure(Throwable var1) {
                if (!isActive()) {
                    return;
                }
                Log.e(TAG, "onFailure: Throwable:" + var1.getMessage());
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }

}
