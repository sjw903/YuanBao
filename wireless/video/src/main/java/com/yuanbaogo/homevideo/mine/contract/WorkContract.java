package com.yuanbaogo.homevideo.mine.contract;

import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.homevideo.mine.model.VodListBean;
import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;

/**
 * @author lxx
 * @description:
 * @date : 2021/8/24 18:30
 */
public interface WorkContract {

    interface View extends IBaseView {

        /**
         * 设置作品列表
         */
        void setVodList(RecommendVideoBean bean);

        /**
         * 显示作品列表
         */
        void initVodList();

    }

    interface Presenter extends IBasePresenter {

        /**
         * 获取作品列表
         */
        void getWorkVodList(int page, int size, String title, String ybCode);

        /**
         * 我的点赞列表
         */
        void getLikeVodList(int page, int size, String title, String ybCode);

    }

}
