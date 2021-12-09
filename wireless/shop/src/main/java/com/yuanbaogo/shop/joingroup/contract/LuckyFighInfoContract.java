package com.yuanbaogo.shop.joingroup.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.shop.joingroup.model.LuckyFighInfoBean;
import com.yuanbaogo.shop.productdetails.model.CommentBean;
import com.yuanbaogo.shop.productdetails.model.DetailBean;
import com.yuanbaogo.shop.productdetails.model.DetailsModelBean;
import com.yuanbaogo.shop.publics.model.RecommendBean;

import java.util.List;

/**
 * @Description: 幸运拼团详情Contract
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/28/21 5:01 PM
 * @Modifier:
 * @Modify:
 */
public interface LuckyFighInfoContract {

    interface View extends IBaseView {

        void initTab();

        void initListener();

        void setAdapter(List<DetailsModelBean> list);

        void setGoodsDetail(LuckyFighInfoBean bean);

        void initGoodsDetail();

        /**
         * 设置相关推荐数据
         *
         * @param bean
         */
        void setSearchRecommend(List<RecommendBean> bean);

        /**
         * 显示相关推荐数据
         */
        void initSearchRecommend();

        /**
         * 设置商品评论数据
         */
        void setComment(CommentBean bean);

        /**
         * 显示商品评论数据
         */
        void initComment();

        /**
         * 设置是否加入该抽奖
         *
         * @param bean
         */
        void setParticipated(String bean);

        /**
         * 显示是否加入该抽奖
         */
        void initParticipated();

    }

    interface Presenter extends IBasePresenter {

        void initDisplayData();

        void getGoodsDetail(String id);

        /**
         * 相关推荐
         *
         * @param attribution
         */
        void getSearchRecommend(int attribution);

        /**
         * 商品详情-商品评论
         *
         * @param page
         * @param rows
         * @param spuId
         */
        void getComment(int page, int rows, String spuId);

        /**
         * 是否加入该抽奖
         *
         * @param id
         */
        void getParticipated(String id);

    }

}
