package com.yuanbaogo.shop.onecity.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.shop.onecity.model.QueryVenueBean;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.publics.model.BannerBean;

import java.util.List;

/**
 * @Description: 一城一品MVP接口
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 10:36 AM
 * @Modifier:
 * @Modify:
 */
public interface OneCityOPContract {

    interface View extends IBaseView {

        /**
         * 设置Banner数据
         */
        void setBanner(List<BannerBean> bean);

        /**
         * 显示Banner
         */
        void initBanner();

        /**
         * 设置首款爆推数据
         *
         * @param bean
         */
        void setExplosion(SearchMerchandiseListBean bean);

        /**
         * 显示首款爆推
         */
        void initExplosion();

        /**
         * 设置为你推荐数据
         *
         * @param bean
         */
        void setRecommend(SearchMerchandiseListBean bean);

        /**
         * 显示为你推荐
         */
        void initRecommend();

        /**
         * 查询场馆
         */
        void setQueryVenue(List<QueryVenueBean> bean);

        /**
         * 查询场馆
         */
        void initQueryVenue();

        /**
         * 设置滚动监听
         */
        void initListener();

        void initFunction();

        void initLiveGoods();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 获取Banner数据
         */
        void getBanner();

        /**
         * 推荐商品
         */
        void getRecommend(SearchMerchandiseBean bean);

        /**
         * 查询场馆
         */
        void getQueryVenue();

        /**
         * 展示数据
         */
        void initDisplayData();

    }

}
