package com.yuanbaogo.commonlib.base;

import com.yuanbaogo.datacenter.utils.NetUtils;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.libbase.basemvp.MvpBaseFragment;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.network.HttpUtil;

/**
 * @author yangf
 * @version 1.0
 */
public abstract class MvpBaseFragmentImpl<V extends IBaseView, P extends MvpBasePersenter> extends MvpBaseFragment<V, P>
        implements IBaseView {

    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpUtil.cancel(this);
        ToastUtil.cancel();
    }

    public boolean isNetworkAvailable() {
        return NetUtils.isConnected(getViewContext());
    }


}
