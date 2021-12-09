package com.yuanbaogo.shop.joingroup.presenter;

import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.shop.joingroup.contract.JoinGroupContract;
import com.yuanbaogo.shop.publics.model.BannerBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 全名拼团Presenter
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 3:12 PM
 * @Modifier:
 * @Modify:
 */
public class JoinGroupPresenter extends MvpBasePersenter<JoinGroupContract.View>
        implements JoinGroupContract.Presenter {

    @Override
    public void initDisplayData() {

        getView().initListener();

        getView().initGroupJoiningZone();

    }

    /**
     * 获取Banner数据
     */
    @Override
    public void getBanner() {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.MALL_BANNER.replace("{location}", "3"),
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

    /**
     * 推荐商品
     *
     * @param attribution 1 销量排行
     */
    @Override
    public void getRecommend(int attribution) {
        Map<String, Object> params = new HashMap<>();
        params.put("attribution", attribution);
        params.put("pageNum", "1");
        params.put("pageSize", "20");
        NetWork.getInstance().post(getContext(),
                ChildUrl.RECOMMEND,
                params,
                new RequestSateListener<SearchMerchandiseListBean>() {
                    @Override
                    public void onSuccess(int code, SearchMerchandiseListBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setRecommend(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initRecommend();
                        }
                    }
                });
    }

}
