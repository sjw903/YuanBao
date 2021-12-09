package com.yuanbaogo.finance.bindBankCard.presenter;

import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.finance.bindBankCard.contract.BindBankCardContract;
import com.yuanbaogo.finance.bindBankCard.model.BankCardInfoBean;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;

import java.util.HashMap;
import java.util.Map;

public class BindBankCardPresenter extends MvpBasePersenter<BindBankCardContract.View>
        implements BindBankCardContract.Presenter {

    @Override
    public void getBankCardInfo(String bankAccountName, String cardNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("bankAccountName", bankAccountName);
        params.put("cardNo", cardNo);
        NetWork.getInstance()
                .post(getContext(),
                        ChildUrl.BANK_CARD_INFO,
                        params,
                        new RequestSateListener<BankCardInfoBean>() {
                            @Override
                            public void onSuccess(int code, BankCardInfoBean bean) {
                                if (code == 200 && getView() != null) {
                                    getView().setBankCardInfo(bean);
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() != null) {

                                }
                            }
                        });
    }

    @Override
    public void getBindBankCard(String accountBank, String bankAccountName, String cardNo, int cardType) {
        Map<String, Object> params = new HashMap<>();
        params.put("accountBank", accountBank);
        params.put("bankAccountName", bankAccountName);
        params.put("cardNo", cardNo);
        params.put("cardType", cardType);
        NetWork.getInstance()
                .post(getContext(),
                        ChildUrl.BIND_BANK_CARD,
                        params,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code == 200 && getView() != null) {
                                    getView().setBindBankCard();
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() != null) {

                                }
                            }
                        });
    }

}
