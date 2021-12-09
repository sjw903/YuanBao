package com.yuanbaogo.mine.coupon;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public interface CouponContract {

    interface View extends IBaseView {
        void showCouponList(List<String> couponList);
        void loadFail(Throwable throwable);
    }

    interface Presenter extends IBasePresenter {
        /**
         * 获取卡券列表
         *
         * @param page 页码
         */
        void getCouponList(int page);
    }

}

