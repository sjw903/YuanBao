package com.yuanbaogo.mine.setting.pay.password.two;

import android.text.TextUtils;
import android.util.Log;

import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.libbase.baseutil.Md5Util;
import com.yuanbaogo.network.parser.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

public class PayTwoPasswordSetPresenter extends MvpBasePersenter<PayTwoPasswordContract.View> implements PayTwoPasswordContract.Presenter {

    private static final String TAG = "PayTwoPasswordSetPresenter";

    @Override
    public void setPayPassword(String password) {
        Map<String, Object> params = new HashMap<>();
        String md5Password = Md5Util.stringMd5(password);
        params.put("payPassword", md5Password);
        params.put("payPasswordConfirm", md5Password);
        NetWork.getInstance().post(getContext(), ChildUrl.SET_PAY_PASSWORD, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "setPayPassword onSuccess: code:" + code + "   bean:" + bean);
                if (getView() == null || !isActive()) {
                    return;
                }
                if (code == NetConfig.NETWORK_SUCCESSFUL) {
                    getView().setSuccess();
                } else {
                    getView().setFail(null);
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                String message = var1.getMessage();
                if (getView() == null || !isActive() || TextUtils.isEmpty(message)) {
                    return;
                }
                ErrorResponse errorResponse = GsonUtil.GsonToBean(message, ErrorResponse.class);
                getView().setFail(errorResponse.getMessage());
            }
        });
    }
}
