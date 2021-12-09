package com.yuanbaogo.mine.myfans.contract;

import com.yuanbaogo.libbase.basemvp.IBasePresenter;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.mine.myfans.model.FollowBean;
import com.yuanbaogo.mine.myfans.model.MyFansFollowItem;

import java.util.List;

public interface MyFansContract {
    interface View extends IBaseView {
        void showMyFansList(List<MyFansFollowItem> myFansFollowItems);

        void followFans(FollowBean followBeans);

        void loadFail(Throwable throwable);
    }

    interface Presenter extends IBasePresenter {
        void getMyFansList(int page);

        void getFollowFans(String state, String followerYbCode);
    }
}
