package com.yuanbaogo.datacenter.remote.interfaces;

public abstract class RequestSateListener<T> {
    public RequestSateListener() {
    }

    public void onStart() {
    }

    public abstract void onSuccess(int var1, T var2);

    public abstract void onFailure(Throwable var1);

//    //如果需处理code和Message 重写这个
//    public boolean onHandleMessage(String code, String msg) {
//        return false;
//    }

    //如果需自己处理 code和Message 重写此方法并返回true
    public boolean onHandleMessage(Throwable var1,String code, String msg) {
        return false;
    }
}
