package com.yuanbaogo.mine.setting.account.update;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.Map;

public class UpdatePhonePresenter extends MvpBasePersenter<UpdatePhoneContract.View> implements UpdatePhoneContract.Presenter {

    private static final String TAG = "UpdatePhonePresenter";

    @Override
    public void sendCode() {
        NetWork.getInstance().get(getContext(), ChildUrl.SEND_CODE + "/" + UserStore.getUserPhone(), null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.e(TAG, "onSuccess: code:" + code + "  bean:" + bean);
                if (!isActive() || bean == null) {
                    return;
                }
                if (Boolean.parseBoolean(bean)) {
                    getView().startTimer();
                } else {
                    ToastView.showToast(R.string.pay_code_error);
                }
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
    public void checkCaptcha(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("phone", UserStore.getUserPhone());
        NetWork.getInstance().post(getContext(), ChildUrl.CHECK_CAPTCHA, map, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                if (!isActive() || bean == null) {
                    return;
                }
                getView().checkCaptcha(Boolean.parseBoolean(bean));
            }

            @Override
            public void onFailure(Throwable var1) {
                if (!isActive()) {
                    return;
                }
                getView().checkCaptcha(false);
            }
        });
    }

}
