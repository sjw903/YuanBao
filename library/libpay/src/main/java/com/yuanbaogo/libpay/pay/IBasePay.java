package com.yuanbaogo.libpay.pay;

public interface IBasePay {
    void onSuccess(int var1, String... var2);

    void onFail(int var1, String... var2);

    void onWaiting(int var1, String... var2);

    void onCancel(int var1, String... var2);
}
