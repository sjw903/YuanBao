package com.yuanbaogo.mine.order.pay.detail;

import com.yuanbaogo.commonlib.router.model.AddressOrderBean;
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
public interface PayDetailContract {

    interface View extends IBaseView {
        void showOrderDetail(GoodsDetail goodsDetail);

        void updateOrderAddressSuccess(AddressOrderBean orderAddressBean);

        /**
         * 是否可进入 幸运拼团支付 可以支付
         */
        void setLuckDrawEnter();

        /**
         * 是否可进入 幸运拼团支付 不可以支付
         */
        void initLuckDrawEnter();

        /**
         * 取消订单成功
         */
        void setCancelOrder();

        /**
         * 取消订单失败
         */
        void initCancelOrder();
    }

    interface Presenter extends IBasePresenter {
        void getOrderDetail(String totalOrderId);

        void updateOrderAddress(String totalOrderId, AddressOrderBean orderAddressBean);

        /**
         * 是否可进入 幸运拼团支付
         *
         * @param orderId
         */
        void getLuckDrawEnter(String orderId);

        /**
         *
         * @param totalOrderId
         */
        void getCancelOrder(String totalOrderId);
    }

}

