package com.yuanbaogo.homevideo.live.home.presenter;


import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.homevideo.live.home.contract.LiveContract;
import com.yuanbaogo.homevideo.live.home.model.LiveRecommendBean;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;

import java.util.HashMap;
import java.util.Map;


/**
 * @description:
 * @date : 2021/7/21 18:37
 */
public class LivePresenter extends MvpBasePersenter<LiveContract.View> implements LiveContract.Presenter {

    int page;
    int size = 20;

    @Override
    public void getLivingRecommend(int page) {
        this.page = page;
        Map<String, Object> params = new HashMap<>();
        params.put("page", page + "");
        params.put("size", size + "");
        String url = ChildUrl.DEFAULT_LIVE_RECOMMEND;
        NetWork.getInstance().get(getContext(), url, params, new RequestSateListener<LiveRecommendBean>() {
            @Override
            public void onSuccess(int code, LiveRecommendBean bean) {

                if (bean != null && bean.getRows() != null && bean.getRows().size() > 0) {
                    if (isActive()) { //必须加
                        if (page == 1) {
                            if (isActive())
                                getView().setListData(bean.getRows());
                        } else {
                            if (isActive())
                                getView().addListData(bean.getRows());
                        }
                    }
                } else {
                    if (page == 1) {
                        if (isActive())
                            getView().showEmptyView();
                    } else {
                        if (isActive()) { //必须加
//                            getView().showEmptyView();
                            getView().finishLoadMoreWithNoMoreData();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Throwable e) {
                if (isActive())
                    getView().showEmptyView();
            }
        }, false);
    }

}
