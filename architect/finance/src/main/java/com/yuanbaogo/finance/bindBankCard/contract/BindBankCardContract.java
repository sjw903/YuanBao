package com.yuanbaogo.finance.bindBankCard.contract;

import com.yuanbaogo.finance.bindBankCard.model.BankCardInfoBean;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

public interface BindBankCardContract {

    interface View extends IBaseView {

        /**
         * 设置银行卡信息
         *
         * @param bean
         */
        void setBankCardInfo(BankCardInfoBean bean);

        /**
         *
         */
        void setBindBankCard();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 获取银行卡基本信息
         */
        void getBankCardInfo(String bankAccountName, String cardNo);

        /**
         * 绑定银行卡
         *
         * @param accountBank
         * @param bankAccountName
         * @param cardNo
         * @param cardType
         */
        void getBindBankCard(String accountBank, String bankAccountName, String cardNo, int cardType);

    }

}
