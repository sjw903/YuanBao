package com.yuanbaogo.mine.dib.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public interface DibContract {

    interface View extends IBaseView {

        /**
         * 设置钱包数据
         */
        void setDiffTypeWallet(String bean);

        /**
         * 显示钱包
         */
        void initDiffTypeWallet();

        /**
         * 设置冻结金额数据
         */
        void setFreezeAmount(String bean);

        /**
         * 去提现
         */
        void toWithdrawal();

        /**
         * 去绑定银行卡
         */
        void toBindBankCard();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 查看钱包数量
         *
         * @param walletType
         * @param ybCode
         */
        void getDiffTypeWallet(int walletType, String ybCode);

        /**
         * 是否可以提现
         */
        void canWithdrawal();

    }

}
