package com.yuanbaogo.shop.spick.presenter;

import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.shop.publics.model.BannerBean;
import com.yuanbaogo.shop.spick.contract.SpickContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 秒杀Presenter
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 3:08 PM
 * @Modifier:
 * @Modify:
 */
public class SpickPresenter extends MvpBasePersenter<SpickContract.View>
        implements SpickContract.Presenter {

    @Override
    public void initDisplayData() {
        getView().initListener();
    }

    /**
     * 获取Banner数据
     */
    @Override
    public void getBanner() {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.MALL_BANNER.replace("{location}", "2"),
                params,
                new RequestSateListener<List<BannerBean>>() {
                    @Override
                    public void onSuccess(int code, List<BannerBean> bean) {
                        if (code == 200 && getView() != null) {
                            getView().setBanner(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initBanner();
                        }
                    }
                });
    }


}
