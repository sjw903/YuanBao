package com.yuanbaogo.mine.integral.contract;

import android.app.Activity;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 4:11 PM
 * @Modifier:
 * @Modify:
 */
public interface IntegralRechargeContract {

    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter {

        void getCoinpointResult(Activity activity, String orderId);

    }

}
