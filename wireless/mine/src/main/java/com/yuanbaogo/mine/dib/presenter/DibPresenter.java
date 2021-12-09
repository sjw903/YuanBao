package com.yuanbaogo.mine.dib.presenter;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.dib.contract.DibContract;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 零钱 Presenter
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:28 PM
 * @Modifier:
 * @Modify:
 */
public class DibPresenter extends MvpBasePersenter<DibContract.View> implements DibContract.Presenter {

    /**
     * @param walletType 1 零钱    2 未释放零钱
     * @param ybCode
     */
    @Override
    public void getDiffTypeWallet(int walletType, String ybCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("walletType", walletType);
        params.put("ybCode", ybCode);
        NetWork.getInstance().post(getContext(),
                ChildUrl.GET_DIFF_TYPE_WALLET,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        if (code == 200 && getView() != null) {
                            if (walletType == 1) {
                                getView().setDiffTypeWallet(bean);
                            } else if (walletType == 2) {
                                getView().setFreezeAmount(bean);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initDiffTypeWallet();
                        }
                    }
                });
    }

    @Override
    public void canWithdrawal() {
        NetWork.getInstance().get(getContext(),
                ChildUrl.CAN_WITHDRAWAL,
                null,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                         if (code == 200 && getView() != null) {
                             getView().toWithdrawal();
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {

                        }
                    }

                    @Override
                    public boolean onHandleMessage(Throwable var1, String code, String msg) {
                        if (getView() != null && code.equals("C0606")) {
                            getView().toBindBankCard();
                        } else if (getView() != null && code.equals("C0607")) {
                            ToastView.showToast(msg);
                        }
                        return super.onHandleMessage(var1, code, msg);
                    }
                });
    }

}
