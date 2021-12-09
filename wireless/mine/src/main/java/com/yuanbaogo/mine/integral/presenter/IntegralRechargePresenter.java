package com.yuanbaogo.mine.integral.presenter;

import android.app.Activity;
import android.content.Intent;

import com.yuanbaogo.finance.pay.presenter.PayCenter;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.integral.contract.IntegralRechargeContract;
import com.yuanbaogo.mine.integral.model.PayStatusBean;
import com.yuanbaogo.mine.integral.view.PayStatusActivity;
import com.yuanbaogo.zui.toast.ToastView;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 4:12 PM
 * @Modifier:
 * @Modify:
 */
public class IntegralRechargePresenter extends MvpBasePersenter<IntegralRechargeContract.View>
        implements IntegralRechargeContract.Presenter {


    public void getCoinpointResult(Activity activity, String orderId) {
//        SUCCESS("SUCCESS","支付成功", 0),
//                REFUND("REFUND","转入退款", 1),
//                NOTPAY("NOTPAY","未支付", 2),
//                CLOSED("CLOSED","已关闭", 3),
//                REVOKED("REVOKED","已撤销（付款码支付）", 4),
//                USERPAYING("USERPAYING","用户支付中（付款码支付）", 5),
//                PAYERROR("PAYERROR","支付失败(其他原因，如银行返回失败)", 6),
//                ACCEPT("ACCEPT","已接收，等待扣款", 7);
        String url = "/" + orderId + "/" + PayCenter.PAYTYPE_WX;
        NetWork.getInstance().get(getContext(), ChildUrl.getCoinpointResult + url,
                null, new RequestSateListener<PayStatusBean>() {
                    @Override
                    public void onSuccess(int code, PayStatusBean bean) {
                        if (isActive() && bean != null && bean.getStatus() != null) {
                            if (bean.getStatus().equals("0") || bean.getStatus().equals("7")) {
                                Intent mIntent = new Intent(getContext(), PayStatusActivity.class);
                                getContext().startActivity(mIntent);
                                activity.finish();
                            } else {
                                ToastView.showToast(getContext(), "支付失败");
                            }

                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        ToastView.showToast(getContext(), "支付失败");
                    }
                });
    }
}
