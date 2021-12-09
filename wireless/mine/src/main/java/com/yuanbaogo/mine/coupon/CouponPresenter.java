package com.yuanbaogo.mine.coupon;

import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

public class CouponPresenter extends MvpBasePersenter<CouponContract.View> implements CouponContract.Presenter {
    @Override
    public void getCouponList(int page) {
//        if (getView() == null || !isActive()) {
//            return;
//        }
//        if (page == 1) {
//            List<String> stringList = new ArrayList<>();
//            stringList.add("1111");
//            stringList.add("2222");
//            stringList.add("3333");
//            stringList.add("4444");
//            getView().showCouponList(stringList);
//        } else {
//            getView().loadFail(null);
//            ToastView.showToast(R.string.order_no_data_toast);
//        }
        getView().showCouponList(null);
    }
}
