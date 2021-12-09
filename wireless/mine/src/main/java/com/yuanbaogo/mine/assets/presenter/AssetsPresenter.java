package com.yuanbaogo.mine.assets.presenter;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.assets.contract.AssetsContract;
import com.yuanbaogo.mine.assets.model.FindAreaAmountBean;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 我的资产Presenter
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 11:09 AM
 * @Modifier:
 * @Modify:
 */
public class AssetsPresenter extends MvpBasePersenter<AssetsContract.View>
        implements AssetsContract.Presenter {

    /**
     * 查询不同类型钱包数量
     *
     * @param walletType
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
                            if (walletType == 0) {
                                getView().setIntegrals(bean);
                            } else if (walletType == 1) {
                                getView().setDIB(bean);
                            } else if (walletType == 2) {
                                getView().setFrozen(bean);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().onError(walletType);
                        }
                    }
                });
    }

    /**
     * 查询用户各个专区钱包总额
     *
     * @param ybCode
     */
    @Override
    public void getFindAreaAmount(String ybCode) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.FIND_AREA_AMOUNT,
                params,
                new RequestSateListener<List<FindAreaAmountBean>>() {
                    @Override
                    public void onSuccess(int code, List<FindAreaAmountBean> bean) {
                        if (code == 200 && getView() != null) {
                            getView().setFindAreaAmount(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().onError(3);
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
