package com.yuanbaogo.commonlib.base;

import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.libbase.view.BaseFragment;
import com.yuanbaogo.network.HttpUtil;

public abstract class BaseFragmentImpl extends BaseFragment {




    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpUtil.cancel(this);
        ToastUtil.cancel();
    }


}
