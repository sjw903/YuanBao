package com.yuanbaogo.shop.joingroup.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.publics.model.BannerBean;

import java.util.List;

/**
 * @Description: 全名拼团MVP接口
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 3:11 PM
 * @Modifier:
 * @Modify:
 */
public interface JoinGroupContract {

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
        void setRecommend(SearchMerchandiseListBean bean);

        /**
         * 显示为你推荐
         */
        void initRecommend();

        void initListener();

        void initGroupJoiningZone();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 获取Banner数据
         */
        void getBanner();

        /**
         * 推荐商品
         *
         * @param attribution 1 销量排行
         */
        void getRecommend(int attribution);

        void initDisplayData();

    }

}
