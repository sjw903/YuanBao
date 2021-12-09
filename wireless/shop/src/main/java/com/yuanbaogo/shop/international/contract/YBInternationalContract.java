package com.yuanbaogo.shop.international.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.publics.model.BannerBean;

import java.util.List;

/**
 * @Description: 元宝国际MVP接口
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/5/21 2:48 PM
 * @Modifier:
 * @Modify:
 */
public interface YBInternationalContract {

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
         * 设置好货必Buy数据
         *
         * @param bean
         */
        void setBuy(SearchMerchandiseListBean bean);

        /**
         * 显示好货必Buy
         */
        void initBuy();

        /**
         * 设置列表数据
         *
         * @param bean
         */
        void setRecommend(SearchMerchandiseListBean bean);

        /**
         * 显示列表
         */
        void initRecommend();

        /**
         * 设置滚动监听
         */
        void initListener();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 获取Banner数据
         */
        void getBanner();

        /**
         * 推荐商品
         *
         * @param bean
         */
        void getRecommend(SearchMerchandiseBean bean);

        /**
         * 展示数据
         */
        void initDisplayData();

    }
}
