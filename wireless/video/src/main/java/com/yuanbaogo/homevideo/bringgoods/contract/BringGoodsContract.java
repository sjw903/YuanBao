package com.yuanbaogo.homevideo.bringgoods.contract;

import com.yuanbaogo.homevideo.bringgoods.model.LiveGoodsListBean;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

import java.util.List;

public interface BringGoodsContract {

    interface View extends IBaseView {

        void setDataList(List<LiveGoodsListBean> list);

        void setSearchBarStatus(Boolean isCan);

    }

    interface Presenter extends IBasePresenter {

        void getGoodsList(String keyword);
        void verification();
        List<String> getGoodsListId();

    }

}
