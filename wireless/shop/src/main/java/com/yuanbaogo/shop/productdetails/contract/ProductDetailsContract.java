package com.yuanbaogo.shop.productdetails.contract;

import com.yuanbaogo.commonlib.router.model.OrderNumBean;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.shop.productdetails.model.CommentBean;
import com.yuanbaogo.shop.productdetails.model.DetailBean;
import com.yuanbaogo.shop.productdetails.model.DetailsModelBean;
import com.yuanbaogo.shop.productdetails.model.ProductDetailsBean;
import com.yuanbaogo.zui.dialog.model.SkuBean;
import com.yuanbaogo.shop.publics.model.RecommendBean;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 2:38 PM
 * @Modifier:
 * @Modify:
 */
public interface ProductDetailsContract {

    interface View extends IBaseView {

        void initBanner();

        void initTab();

        void initListener();

        void setAdapter(List<DetailsModelBean> list);

        /**
         * 商品详情
         *
         * @param bean
         */
        void setSearchMerchandiseDetail(ProductDetailsBean bean);

        /**
         * 设置详情数据
         */
        void initSearchMerchandiseDetail();

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
         * 设置商品详情数据
         *
         * @param bean
         */
        void setDetail(DetailBean bean);

        /**
         * 显示商品详情数据
         */
        void initDetail();

        /**
         * 设置规格参数及sku数据
         *
         * @param bean
         */
        void setSku(SkuBean bean);

        /**
         * 显示规格参数及sku数据
         */
        void initSku();

        /**
         * 设置商品详情-商品库存数据
         *
         * @param bean
         */
        void setStock(String bean);

        /**
         * 显示商品详情-商品库存数据
         */
        void initStock();

        /**
         * 设置APP-查询商品是否加入收藏夹接口数据
         *
         * @param bean
         */
        void setFindFavoritesFlag(String bean);

        /**
         * 显示APP-查询商品是否加入收藏夹接口数据
         */
        void initFindFavoritesFlag();

        /**
         * 设置收藏夹新增商品接口数据
         *
         * @param bean
         */
        void setAddFavorites(String bean);

        /**
         * 显示收藏夹新增商品接口数据
         */
        void initAddFavorites();

        /**
         * 设置商品详情页移除收藏夹接口数据
         *
         * @param bean
         */
        void setDeleteFavorites(String bean);

        /**
         * 显示商品详情页移除收藏夹接口数据
         */
        void initDeltetFavorites();

        /**
         * 设置购物车新增商品接口
         *
         * @param bean
         */
        void setAddGoods(String bean);

        /**
         * 显示购物车新增商品接口 1 成功  2 接口失败
         */
        void initAddGoods(int type);

    }

    interface Presenter extends IBasePresenter {

        void getSearchMerchandiseDetail(String spuId);

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
         * 商品详情
         *
         * @param id
         */
        void getDetail(String id);

        /**
         * 商品详情-规格参数及sku
         *
         * @param spuId
         */
        void getSku(String spuId);

        /**
         * 商品详情-商品库存
         *
         * @param spuId
         */
        void getStock(String spuId);

        /**
         * APP-查询商品是否加入收藏夹接口
         *
         * @param spuId
         */
        void getFindFavoritesFlag(String spuId, String ybCode);

        /**
         * APP-收藏夹新增商品接口
         *
         * @param spuId
         */
        void getAddFavorites(String spuId, String createBy, int goodsType);

        /**
         * APP-商品详情页移除收藏夹
         *
         * @param spuId
         */
        void getDeleteFavorites(String spuId, String createBy);

        /**
         * APP-购物车新增商品接口
         *
         * @param bean
         */
        void getAddGoods(OrderNumBean bean);

        void initDisplayData();

    }

}
