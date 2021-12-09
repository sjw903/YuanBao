package com.yuanbaogo.shop.publics.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseRowsBean;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/16/21 3:13 PM
 * @Modifier:
 * @Modify:
 */
public interface RecommendContract {

    interface View extends IBaseView {

        /**
         * 设置推荐数据
         *
         * @param bean
         */
        void setRecommend(List<SearchMerchandiseRowsBean> bean);

        /**
         * 显示推荐
         */
        void initRecommend();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 推荐商品
         *
         * @param attribution 2 首款爆推  3 为你推荐
         */
        void getRecommend(int pageNum, int pageSize, int attribution);

    }

}
