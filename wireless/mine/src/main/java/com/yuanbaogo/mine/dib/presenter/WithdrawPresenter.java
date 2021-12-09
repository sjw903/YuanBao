package com.yuanbaogo.mine.dib.presenter;

import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.Md5Util;
import com.yuanbaogo.mine.dib.contract.WithdrawContract;
import com.yuanbaogo.mine.dib.model.BindBankCardInfoBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 7:32 PM
 * @Modifier:
 * @Modify:
 */
public class WithdrawPresenter extends MvpBasePersenter<WithdrawContract.View>
        implements WithdrawContract.Presenter {

    @Override
    public void getUserPassword() {
        NetWork.getInstance()
                .get(getContext(),
                        ChildUrl.VERIFY_HAS_PAY_PASSWORD,
                        null,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code == NetConfig.NETWORK_SUCCESSFUL && Boolean.parseBoolean(bean)) {
                                    checkUserStatus();
                                } else {
                                    getView().showNumberPasswordDialog();
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                            }
                        });
    }

    //查看用户状态(冻结是true正常状态是false)
    public void checkUserStatus() {
        NetWork.getInstance()
                .get(getContext(),
                        ChildUrl.checkUserStatus,
                        null,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code == 200 && getView() != null) {
                                    getView().setCheckUserStatus(code, bean);
                                }

                            }

                            @Override
                            public void onFailure(Throwable var1) {
                            }
                        });
    }

    @Override
    public void verifyUserPayPassword(String payPassword) {
        Map<String, Object> params = new HashMap<>();
        String md5Password = Md5Util.stringMd5(payPassword);
        params.put("payPassword", md5Password);
        NetWork.getInstance()
                .post(getContext(),
                        ChildUrl.VERIFY_PAY_PASSWORD,
                        params,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String str) {
                                getView().setVerifyUserPayPassword(code, str);
                            }

                            @Override
                            public void onFailure(Throwable e) {
                            }

                            @Override
                            public boolean onHandleMessage(Throwable var1, String code, String msg) {
                                getView().setVerifyUserPayPasswordErr(code, msg);
                                return true;
                            }
                        });
    }

    @Override
    public void getWithdrawal(String money, int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("money", money);
        params.put("type", type);
        NetWork.getInstance()
                .post(getContext(),
                        ChildUrl.WITHDRAWAL,
                        params,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code == 200 && getView() != null) {
                                    getView().setWithdrawal();
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
    public void getBindBankCardInfo() {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance()
                .get(getContext(),
                        ChildUrl.BIND_BANK_CARD_INFO,
                        params,
                        new RequestSateListener<BindBankCardInfoBean>() {
                            @Override
                            public void onSuccess(int code, BindBankCardInfoBean bean) {
                                if (code == 200 && getView() != null) {
                                    getView().setBindBankCardInfo(bean);
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
