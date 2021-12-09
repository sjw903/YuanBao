package com.yuanbaogo.shop.spick.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.shop.publics.model.BannerBean;

import java.util.List;

/**
 * @Description: 秒杀MVP接口
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 3:07 PM
 * @Modifier:
 * @Modify:
 */
public interface SpickContract {

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
         * 展示数据
         */
        void initDisplayData();
    }

}
