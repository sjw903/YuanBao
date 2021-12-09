package com.yuanbaogo.shop.main.contract;

import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.shop.main.model.ShopRecommendVideoBean;

import java.util.List;

/**
 * @Description: 商城MVP接口
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 9:44 AM
 * @Modifier:
 * @Modify:
 */
public interface ShopContract {

    interface View extends IBaseView {

        /**
         * 设置搜索推荐
         *
         * @param bean 推荐数据
         */
        void setADSearch(List<String> bean);

        /**
         * 显示搜索推荐
         */
        void initADSearch();

        /**
         * 设置推荐直播
         *
         * @param bean 会挑会选有本事数据
         */
//        void setRecommendLive(List<RecommendLiveBean> bean);
        void setRecommendLive(List<ShopRecommendVideoBean> bean);

        /**
         * 显示推荐直播
         */
        void initRecommendLive();

        /**
         * 设置滚动监听
         */
        void initListener();

        /**
         * 设置搜索框位置
         */
        void initTopSearchLocation();

        /**
         * 功能表
         */
        void initFunction();

        /**
         * 超值拼团
         */
        void initGroupJoiningZone();

        /**
         * 单个短视频信息详情
         */
        void setVodInfo(RecommendVideoBean.RowsBean bean);

        /**
         * 单个短视频信息详情
         */
        void initVodInfo();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 获取推荐搜索数据
         */
        void getADSearch();

        /**
         * 获取推荐直播数据
         */
        void getRecommendLive();

        /**
         * 功能模块 点击跳转
         *
         * @param postion
         */
        void initFunctionInfo(int postion);

        /**
         * 获取福利社手机充值服务以及加油服务接口url
         *
         * @param serviceSign
         */
        void initPhoneGasoline(int serviceSign);

        /**
         * 展示数据
         */
        void initDisplayData();

        /**
         * 单个短视频信息详情
         */
        void getVodInfo(String vodId);

    }

}
