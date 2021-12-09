package com.yuanbaogo.shopcart.order.contract;

import com.yuanbaogo.commonlib.router.model.AddressOrderBean;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.shopcart.order.model.CartSettleBean;
import com.yuanbaogo.shopcart.order.model.OrderCheckBean;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/4/21 4:04 PM
 * @Modifier:
 * @Modify:
 */
public interface ConfirmOrderContract {

    interface View extends IBaseView {

        /**
         * 设置默认地址信息数据
         *
         * @param bean
         */
        void setAddress(AddressOrderBean bean);

        /**
         * 显示默认地址信息数据
         */
        void initAddress();

        /**
         * 设置订单确认信息
         *
         * @param bean
         */
        void setOrderCheck(List<OrderCheckBean> bean);

        /**
         * 显示订单确认信息
         */
        void initOrderCheck();

        /**
         * 设置下单数据
         *
         * @param bean
         */
        void setCreateOrder(String bean);

        /**
         * 设置购物车下单数据
         *
         * @param bean
         */
        void setCartSettle(String bean);

        /**
         * 支付
         */
        void initCreateOrder();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 获取默认收货地址
         *
         * @param userId
         */
        void getAddress(String userId);

        /**
         * 订单确认信息
         *
         * @param skuIdList
         */
        void getOrderCheck(List<String> skuIdList);

        /**
         * 下单
         *
         * @param addressId
         * @param num
         * @param skuId
         * @param type
         */
        void getCreateOrder(Long addressId, int num, String skuId, int type);

        /**
         * 购物车下单
         *
         * @param bean
         */
        void getCartSettle(CartSettleBean bean);

        /**
         * 直播带货下单
         *
         * @param bizId 业务ID(短视频ID,直播聊天室ID,场次号)
         * @param bean
         */
        void getLiveBuyNow(String bizId, CartSettleBean bean);

        /**
         * 大抽奖订单确认信息
         *
         * @param id
         */
        void getGoodsInfo(String id);

        /**
         * 预约大抽奖
         *
         * @param id
         */
        void getLuckDrawSubscribe(Long addressId, String id);

    }

}
