package com.yuanbaogo.shop.search.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 3:32 PM
 * @Modifier:
 * @Modify:
 */
public interface ProductDetailsListContract {

    interface View extends IBaseView {

        /**
         * 根据输入框内容检索商品列表
         *
         * @param bean
         */
        void setSearchMerchandise(SearchMerchandiseListBean bean);

        /**
         *
         */
        void initSearchMerchandise();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 根据输入框内容检索商品列表
         *
         * @param bean 参数模型
         */
        void getSearchMerchandise(SearchMerchandiseBean bean);

    }

}
