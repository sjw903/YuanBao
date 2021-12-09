package com.yuanbaogo.mine.setting.address.add;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.setting.model.AddressBean;

import java.util.HashMap;
import java.util.Map;

public class AddAddressPresenter extends MvpBasePersenter<AddAddressContract.View> implements AddAddressContract.Presenter {

    private static final String TAG = "AddAddressPresenter";

    @Override
    public void addAddress(AddressBean addressBean) {
        Map<String, Object> params = new HashMap<>();
        params.put("addressDetails", addressBean.getAddressDetails());
        params.put("city", addressBean.getCity());
        params.put("addressId", addressBean.getAddressId());
        params.put("contactsName", addressBean.getContactsName());
        params.put("contactsPhone", addressBean.getContactsPhone());
        params.put("country", addressBean.getCountry());
        params.put("defaulted", addressBean.getDefaulted());
        params.put("province", addressBean.getProvince());
        params.put("userId", UserStore.getYbCode());
        NetWork.getInstance().post(getContext(), ChildUrl.ADD_ADDRESS, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getAddressList onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                if (Boolean.parseBoolean(bean)) {
                    getView().addFinish();
                } else {
                    getView().addFail("新增收货地址失败，请检查网络");
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getAddressList onFailure: " + var1.getMessage());
                if (getView() == null || !isActive()) {
                    return;
                }
                getView().addFail(var1.getMessage());
            }
        });
    }

    @Override
    public void getDefaultAddress() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", UserStore.getYbCode());
        NetWork.getInstance().post(getContext(), ChildUrl.GET_DEFAULT_ADDRESS, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "getAddressList onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().hasDefaultAddress(bean != null);
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getAddressList onFailure: " + var1.getMessage());
                if (getView() == null || !isActive()) {
                    return;
                }
                getView().hasDefaultAddress(false);
            }
        });
    }
}
