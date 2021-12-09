package com.yuanbaogo.libbase.basemvp;

/**
 * Created by yf on 2018/4/9.
 */

public class IBaseContract {
    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter<View> {

    }
}
