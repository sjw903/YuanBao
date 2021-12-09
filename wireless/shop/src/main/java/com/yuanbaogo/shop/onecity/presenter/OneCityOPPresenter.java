package com.yuanbaogo.shop.onecity.presenter;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.shop.onecity.contract.OneCityOPContract;
import com.yuanbaogo.shop.onecity.model.QueryVenueBean;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.publics.model.BannerBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 一城一品 Presenter
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 10:38 AM
 * @Modifier:
 * @Modify:
 */
public class OneCityOPPresenter extends MvpBasePersenter<OneCityOPContract.View>
        implements OneCityOPContract.Presenter {

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
                ChildUrl.MALL_BANNER.replace("{location}", "4"),
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
     * 获取首推爆款   获取为你推荐
     *
     * @param searchMerchandiseBean 参数model
     */
    @Override
    public void getRecommend(SearchMerchandiseBean searchMerchandiseBean) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderBy", searchMerchandiseBean.getOrderBy());
        params.put("pageNum", searchMerchandiseBean.getPageNum());
        params.put("pageSize", searchMerchandiseBean.getPageSize());
        params.put("tag", searchMerchandiseBean.getTag());
        params.put("esGoodsName", searchMerchandiseBean.getEsGoodsName());
        params.put("specialId", searchMerchandiseBean.getSpecialId());
        NetWork.getInstance().post(getContext(),
                ChildUrl.SEARCH_MERCHANDISE,
                params,
                new RequestSateListener<SearchMerchandiseListBean>() {
                    @Override
                    public void onSuccess(int code, SearchMerchandiseListBean bean) {
                        if (code == 200 && getView() != null) {
                            if (searchMerchandiseBean.getTag() == 8) {
                                getView().setExplosion(bean);
                            } else if (searchMerchandiseBean.getTag() == 9) {
                                getView().setRecommend(bean);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            if (searchMerchandiseBean.getTag() == 8) {
                                getView().initExplosion();
                            } else if (searchMerchandiseBean.getTag() == 9) {
                                getView().initRecommend();
                            }
                        }
                    }
                });
    }

    /**
     * 查询场馆
     */
    @Override
    public void getQueryVenue() {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.QUERY_VENUE,
                params,
                new RequestSateListener<List<QueryVenueBean>>() {
                    @Override
                    public void onSuccess(int code, List<QueryVenueBean> bean) {
                        if (code == 200 && getView() != null) {
                            getView().setQueryVenue(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initQueryVenue();
                        }
                    }
                });
    }

}
