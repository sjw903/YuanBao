package com.yuanbaogo.mine.detail.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.detail.model.FindLogBean;
import com.yuanbaogo.mine.integral.model.YBIntegralBean;

/**
 * @Description: 收支明细
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/10/21 12:53 PM
 * @Modifier:
 * @Modify:
 */
public interface AssetsDetailListContract {

    interface View extends IBaseView {

        /**
         * 设置查询元宝积分log数据
         */
        void setCoinPointLog(YBIntegralBean bean);

        /**
         * 显示查询元宝积分log数据
         */
        void initCoinPointLog();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 查询元宝积分log
         *
         * @param url
         * @param pageNum
         * @param size
         * @param ybCode
         */
        void getCoinPointLog(String url, int pageNum, int size, String ybCode);

        /**
         * 查询用户优惠券交易日志   查询账户历史记录
         *
         * @param url
         * @param bean
         * @param ybCode
         */
        void getFindLog(String url, FindLogBean bean, String ybCode);

    }

}
