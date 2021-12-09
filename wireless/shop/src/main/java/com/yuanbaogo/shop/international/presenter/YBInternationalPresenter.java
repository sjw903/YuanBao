package com.yuanbaogo.shop.international.presenter;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.shop.international.contract.YBInternationalContract;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.publics.model.BannerBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 元宝国际Presenter
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/5/21 2:49 PM
 * @Modifier:
 * @Modify:
 */
public class YBInternationalPresenter extends MvpBasePersenter<YBInternationalContract.View>
        implements YBInternationalContract.Presenter {

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
                ChildUrl.MALL_BANNER.replace("{location}", "5"),
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
     * @param searchMerchandiseBean
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
                            if (searchMerchandiseBean.getTag() == 10
                                    && searchMerchandiseBean.getPageNum() == 1) {
                                getView().setBuy(bean);
                            } else if (searchMerchandiseBean.getTag() == 10
                                    && searchMerchandiseBean.getPageNum() >= 2) {
                                getView().setRecommend(bean);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            if (searchMerchandiseBean.getTag() == 10
                                    && searchMerchandiseBean.getPageNum() == 1) {
                                getView().initBuy();
                            } else if (searchMerchandiseBean.getTag() == 10
                                    && searchMerchandiseBean.getPageNum() >= 2) {
                                getView().initRecommend();
                            }
                        }
                    }
                });
    }

}
