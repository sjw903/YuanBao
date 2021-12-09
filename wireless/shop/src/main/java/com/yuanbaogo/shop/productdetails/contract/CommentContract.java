package com.yuanbaogo.shop.productdetails.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.shop.productdetails.model.CommentBean;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/9/21 9:48 AM
 * @Modifier:
 * @Modify:
 */
public interface CommentContract {

    interface View extends IBaseView {

        /**
         * 设置商品评论数据
         */
        void setComment(CommentBean bean);

        /**
         * 显示商品评论数据
         */
        void initComment();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 商品详情-商品评论
         *
         * @param page
         * @param rows
         * @param spuId
         */
        void getComment(int page, int rows, String spuId);

    }

}
