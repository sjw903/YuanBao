package com.yuanbaogo.shopcart.main.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.shopcart.main.model.MoveFavoritesBean;
import com.yuanbaogo.shopcart.main.model.ShopCartBean;
import com.yuanbaogo.shopcart.main.model.UpdateCartParametBean;
import com.yuanbaogo.zui.dialog.model.SkuBean;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/23/21 10:04 AM
 * @Modifier:
 * @Modify:
 */
public interface ShopCartContract {

    interface View extends IBaseView {

        void setQueryCartGoods(List<ShopCartBean> bean);

        void initQueryCartGoods();

        void setUpdateCartGoodsCount(ShopCartBean bean);

        /**
         *
         * @param isThrough true:接口请求通过  false：接口请求失败
         */
        void initUpdateCartGoodsCount(boolean isThrough);

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
         * 设置删除购物车商品数据
         *
         * @param bean
         */
        void setDeleteGoods(String bean);

        /**
         * 显示-删除购物车商品数据
         */
        void initDeleteGoods();


    }

    interface Presenter extends IBasePresenter {

        void getQueryCartGoods(String ybCode,boolean isLoad);

        void getUpdateCartGoodsCount(UpdateCartParametBean bean);

        void getSku(String spuId);

        void getStock(String skuId);

        /**
         * 获取-删除购物车商品
         *
         * @param idList
         */
        void getDeleteGoods(List<String> idList);

        /**
         * 移入收藏夹
         *
         * @param shopCartId
         */
        void getMoveFavorites(List<MoveFavoritesBean> shopCartId);

    }

}
