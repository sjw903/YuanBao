package com.yuanbaogo.shop.joingroup.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.shop.joingroup.model.LuckDrawListBean;
import com.yuanbaogo.shop.joingroup.model.LuckFightParamsBean;
import com.yuanbaogo.shop.joingroup.model.LuckyFightBean;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/26/21 10:44 AM
 * @Modifier:
 * @Modify:
 */
public interface LuckyFightContract {

    interface View extends IBaseView {

        /**
         * 设置活动名称列表数据
         */
        void setLuckDrawTagList(List<LuckDrawListBean> bean);

        /**
         * 显示活动名称列表数据
         */
        void initLuckDrawTagList();

        /**
         * 设置分页查询幸运大抽奖数据
         */
        void setLuckDrawList(LuckyFightBean bean);

        /**
         * 显示分页查询幸运大抽奖数据
         */
        void initLuckDrawList();
    }

    interface Presenter extends IBasePresenter {

        /**
         * 活动名称列表
         */
        void getLuckDrawTagList();

        /**
         * 分页查询幸运大抽奖
         */
        void getLuckDrawList(LuckFightParamsBean parameteBean);

    }

}
