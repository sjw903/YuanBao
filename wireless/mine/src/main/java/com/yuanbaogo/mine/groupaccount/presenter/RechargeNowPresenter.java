package com.yuanbaogo.mine.groupaccount.presenter;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.groupaccount.contract.RechargeNowContract;
import com.yuanbaogo.mine.groupaccount.model.PreRechargeBean;
import com.yuanbaogo.mine.groupaccount.model.RechargeNowBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/29/21 9:18 AM
 * @Modifier:
 * @Modify:
 */
public class RechargeNowPresenter extends MvpBasePersenter<RechargeNowContract.View>
        implements RechargeNowContract.Presenter {

    @Override
    public void getFindNouseCouPonList(String ybCode, int areaType) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.FIND_NOUSE_COUPON_LIST
                        .replace("{areaType}", areaType + ""),
                params,
                new RequestSateListener<List<RechargeNowBean>>() {
                    @Override
                    public void onSuccess(int code, List<RechargeNowBean> bean) {
                        Log.e("==========HG=未使用状态优惠券:::", "getFindNouseCouPonList onSuccess: code:" + code + "   bean:" + bean);
                        if (code == 200 && getView() != null) {
                            getView().setFindNouseCouPonList(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        Log.e("==========HG=未使用状态优惠券:::", "getFindNouseCouPonList onFailure: " + var1.getLocalizedMessage());
                        if (getView() != null) {
                            getView().initFindNouseCouPonList();
                        }
                    }
                });
    }

    @Override
    public void getPreRecharge(String buyerId, int areaId, String[] couponList, long useCash, long useTicket) {
        Map<String, Object> params = new HashMap<>();
//        params.put("buyerId", buyerId);
        params.put("areaId", areaId);
        params.put("couponList", couponList);
        params.put("useCash", useCash);
        params.put("useTicket", useTicket);
        NetWork.getInstance().post(getContext(),
                ChildUrl.PRE_RECHARGE,
                params,
                new RequestSateListener<PreRechargeBean>() {
                    @Override
                    public void onSuccess(int code, PreRechargeBean bean) {
                        Log.e("==========HG=用户拼团预下单:::", "getPreRecharge onSuccess: code:" + code + "   bean:" + bean);
                        if (code == 200 && getView() != null) {
                            getView().setPreRecharge(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        Log.e("==========HG=用户拼团预下单:::", "getPreRecharge onFailure: " + var1.getLocalizedMessage());
                        if (getView() != null) {
                            getView().initPreRecharge();
                        }
                    }
                });
    }

}
