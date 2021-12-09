package com.yuanbaogo.commonlib.utils.interceptor;

public interface Interceptors {

    //没有网络
    int NETWORK_NONE = 1;
    //移动网络
    int NETWORK_MOBILE = 0;
    //无线网络
    int NETWORW_WIFI = 2;

    int netWork();

}
