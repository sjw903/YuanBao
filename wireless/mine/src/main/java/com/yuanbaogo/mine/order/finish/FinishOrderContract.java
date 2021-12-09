package com.yuanbaogo.mine.order.finish;

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
public interface FinishOrderContract {

    interface View extends IBaseView {
        void showFinishOrder(List<OrderListResponseListItem> orderModelList);

        void loadFail(Throwable throwable);
    }

    interface Presenter extends IBasePresenter {

        /**
         * 获取已完成的订单
         *
         * @param page   页码
         * @param screen 关键字
         */
        void getFinishOrder(int page, String screen);

        /**
         * 获取已完成的订单
         *
         * @param page 页码
         */
        void getFinishOrder(int page);
    }

}
