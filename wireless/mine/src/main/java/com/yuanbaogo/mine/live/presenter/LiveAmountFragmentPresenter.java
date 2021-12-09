package com.yuanbaogo.mine.live.presenter;

import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.live.contract.LiveAmountFragmentContract;
import com.yuanbaogo.mine.live.model.LiveAmountBean;

import java.util.HashMap;
import java.util.Map;

public class LiveAmountFragmentPresenter extends MvpBasePersenter<LiveAmountFragmentContract.View>
        implements LiveAmountFragmentContract.Presenter{
    @Override
    public void getMyLiveList(int page, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page+"");
        params.put("size", LOAD_ROWS+"");
        params.put("type", type);
        NetWork.getInstance().get(getContext(), ChildUrl.MY_LIVE_LIST, params,
                new RequestSateListener<LiveAmountBean>() {
            @Override
            public void onSuccess(int code, LiveAmountBean bean) {
                if (code != NetConfig.NETWORK_SUCCESSFUL || !isActive()) {
                    return;
                }
                if (!bean.getRows().isEmpty()){
                    getView().showLiveAmount(bean.getRows());
                }else {
                    getView().loadFail(null);
                }
            }

            @Override
            public void onFailure(Throwable var1) {

            }
        });
    }
}
