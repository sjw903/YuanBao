package com.yuanbaogo.libbase.basemvp;

import android.content.Context;

/**
 * Created by yf on 2018/4/9.
 */

public interface IBaseView {

    // 判断当前view是否存活，异步的时候一定要判断
    boolean isActive();

    Context getViewContext();

}
