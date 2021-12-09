package com.yuanbaogo.mine.setting.account;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.im.login.LoginManager;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;

import java.util.HashMap;

public class AccountPresenter extends MvpBasePersenter<AccountContract.View> implements AccountContract.Presenter {

    private static final String TAG = "AccountPresenter";

    @Override
    public void signOut() {
        NetWork.getInstance().post(getContext(), ChildUrl.SIGN_OUT, new HashMap<>(), new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getAddressList onSuccess: code:" + code + "   bean:" + bean);
                if (getView() == null || !isActive()) {
                    Log.e(TAG, "onSuccess: 页面不存在，直接跳出");
                    return;
                }
                if (code == NetConfig.NETWORK_SUCCESSFUL
                        && Boolean.parseBoolean(bean)) {
                    getView().signSuccess();
                    LoginManager.logout();
                } else {
                    getView().signFail(null);
                    Log.e(TAG, "getAddressList onFailure: code:" + code);
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getAddressList onFailure: " + var1.getMessage());
                if (getView() != null && isActive()) {
                    getView().signFail(var1);
                }
            }
        });
    }
}
