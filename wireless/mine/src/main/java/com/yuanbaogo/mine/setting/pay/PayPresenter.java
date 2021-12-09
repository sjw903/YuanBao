package com.yuanbaogo.mine.setting.pay;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;

public class PayPresenter extends MvpBasePersenter<PayContract.View> implements PayContract.Presenter {

    private static final String TAG = "PayPresenter";

    @Override
    public void getUserPassword() {

        NetWork.getInstance().get(getContext(), ChildUrl.VERIFY_HAS_PAY_PASSWORD, null, new RequestSateListener<String>() {

            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getUserPassword onSuccess: code:" + code + "   bean:" + bean);
                if (getView() == null || !isActive()) {
                    return;
                }
                if (code == NetConfig.NETWORK_SUCCESSFUL) {
                    getView().showAddPasswordItem(!Boolean.parseBoolean(bean));
                } else {
                    getView().showAddPasswordItem(true);
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getUserPassword onFailure: " + var1.getLocalizedMessage());
                if (getView() == null || !isActive()) {
                    return;
                }

                getView().showAddPasswordItem(true);
            }
        });
    }

}
