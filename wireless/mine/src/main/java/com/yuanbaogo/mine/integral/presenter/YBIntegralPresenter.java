package com.yuanbaogo.mine.integral.presenter;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.integral.contract.YBIntegralContract;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 1:42 PM
 * @Modifier:
 * @Modify:
 */
public class YBIntegralPresenter extends MvpBasePersenter<YBIntegralContract.View>
        implements YBIntegralContract.Presenter {

    @Override
    public void getDiffTypeWallet(int walletType, String ybCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("walletType", walletType);

        NetWork.getInstance().post(getContext(),
                ChildUrl.GET_DIFF_TYPE_WALLET,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        Log.e("==========HG=元宝积分:::", "getDiffTypeWallet onSuccess: code:" + code + "   bean:" + bean);
                        if (code == 200 && getView() != null) {
                            getView().setDiffTypeWallet(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        Log.e("==========HG=元宝积分:::", "getDiffTypeWallet onFailure: " + var1.getLocalizedMessage());
                        if (getView() != null) {
                            getView().initDiffTypeWallet();
                        }
                    }
                });
    }

}
