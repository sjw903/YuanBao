package com.yuanbaogo.mine.collection;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.collection.model.CollectionItem;
import com.yuanbaogo.mine.collection.model.MoveShopCartModel;

import java.util.HashSet;
import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:27 PM
 * @Modifier:
 * @Modify:
 */
public interface CollectionContract {

    interface View extends IBaseView {
        /**
         * 显示收藏列表
         *
         * @param collectionItems 收藏列表
         */
        void showCollectionList(List<CollectionItem> collectionItems);

        void loadFail(Throwable throwable);

        /**
         * 移入购物车是否成功
         *
         * @param result 结果
         */
        void moveShopCart(boolean result);

        /**
         * 删除收藏夹商品是否成功
         *
         * @param result 结果
         */
        void deleteFavorites(boolean result);
    }

    interface Presenter extends IBasePresenter {
        /**
         * 获取收藏列表
         *
         * @param page 页码
         */
        void getCollectionList(int page);

        /**
         * 移入购物车
         *
         * @param model 参数
         */
        void moveShopCart(MoveShopCartModel model);

        /**
         * 删除收藏夹商品
         *
         * @param idList id 集合
         */
        void deleteFavorites(HashSet<String> idList);
    }

}

