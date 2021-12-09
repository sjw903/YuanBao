package com.yuanbaogo.mine.myfollow.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.myfans.model.FollowBean;
import com.yuanbaogo.mine.myfans.model.MyFansFollowItem;

import java.util.List;

public interface MyFollowContract {
    interface View extends IBaseView {
        void showMyFollowList(List<MyFansFollowItem> myFansFollowItems);

        void followFans(FollowBean followBeans);

        void loadFail(Throwable throwable);

        void followFansFail(Throwable throwable);

    }

    interface Presenter extends IBasePresenter {
        void getMyFollowList(String keyword, int page);

        void getFollowFans(String state, String followerYbCode);
    }
}
