package com.yuanbaogo.libbase.basemvp;

/**
 * Created by yf on 2018/4/9.
 */

public interface IBasePresenter<T> {

    T getView();

    void detachView();

}
