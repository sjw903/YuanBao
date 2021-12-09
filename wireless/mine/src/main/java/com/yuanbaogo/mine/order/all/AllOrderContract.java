package com.yuanbaogo.mine.order.all;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
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
public interface AllOrderContract {

    interface View extends IBaseView {
        void showAllOrder(List<OrderListResponseListItem> orderModelList);
        void loadFail(Throwable throwable);
    }

    interface Presenter extends IBasePresenter {

        /**
         * 获取所有的订单
         *
         * @param page   页码
         * @param screen 关键字
         */
        void getAllOrder(int page, String screen);

        /**
         * 获取所有的订单
         *
         * @param page   页码
         */
        void getAllOrder(int page);
    }

}
