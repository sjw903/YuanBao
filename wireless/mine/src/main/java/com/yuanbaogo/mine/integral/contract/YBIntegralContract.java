package com.yuanbaogo.mine.integral.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 1:41 PM
 * @Modifier:
 * @Modify:
 */
public interface YBIntegralContract {

    interface View extends IBaseView {

        /**
         * 设置钱包数据
         */
        void setDiffTypeWallet(String bean);

        /**
         * 显示钱包
         */
        void initDiffTypeWallet();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 查看钱包数量
         *
         * @param walletType
         * @param ybCode
         */
        void getDiffTypeWallet(int walletType, String ybCode);

    }

}
