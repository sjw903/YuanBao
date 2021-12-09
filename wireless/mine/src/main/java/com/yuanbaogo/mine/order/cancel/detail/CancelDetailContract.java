package com.yuanbaogo.mine.order.cancel.detail;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.order.model.GoodsDetail;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public interface CancelDetailContract {

    interface View extends IBaseView {
        void showOrderDetail(GoodsDetail goodsDetail);
    }

    interface Presenter extends IBasePresenter {
        void getOrderDetail(String totalOrderId);
    }

}
