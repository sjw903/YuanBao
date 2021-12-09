package com.yuanbaogo.mine.order.pay;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.order.model.LikeListModelItem;
import com.yuanbaogo.mine.order.model.OrderListResponseListItem;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 4:31 PM
 * @Modifier:
 * @Modify:
 */
public interface PayOrderContract {

    interface View extends IBaseView {
        void showPayOrder(List<OrderListResponseListItem> orderModelList);

        void loadFail(Throwable throwable);

        void showLikeLise(List<LikeListModelItem> likeListModelItems);

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

        /**
         * 获取代付款的订单
         *
         * @param page   页码
         * @param screen 关键字
         */
        void getPayOrder(int page, String screen);

        /**
         * 获取代付款的订单
         *
         * @param page 页码
         */
        void getPayOrder(int page);

        /**
         * 获取猜你喜欢列表
         *
         * @param page 页码
         */
        void getLikeList(int page);

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
