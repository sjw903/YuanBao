package com.yuanbaogo.homevideo.live.home.contract;

import com.yuanbaogo.homevideo.live.home.model.LiveRecommendBean;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

import java.util.List;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/21 18:36
 */
public interface LiveContract {

    interface View extends IBaseView {

        void setListData(List<LiveRecommendBean.RowsBean> list);

        void addListData(List<LiveRecommendBean.RowsBean> list);

        void loadFinish(boolean isSuccess);

        void showEmptyView();

        void finishLoadMoreWithNoMoreData();

    }

    interface Presenter extends IBasePresenter {

        void getLivingRecommend(int page);

    }

}
