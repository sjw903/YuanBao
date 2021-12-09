package com.yuanbaogo.mine.myfans.presenter;

import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.myfans.contract.MyFansContract;
import com.yuanbaogo.mine.myfans.model.FollowBean;
import com.yuanbaogo.mine.myfans.model.MyFansBean;

import java.util.HashMap;
import java.util.Map;

public class MyFansPresenter extends MvpBasePersenter<MyFansContract.View> implements MyFansContract.Presenter {

    @Override
    public void getMyFansList(int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("size", LOAD_ROWS);
        params.put("type", 2);//类型：1.关注，2.粉丝
        NetWork.getInstance().post(getContext(), ChildUrl.MY_FOLLOW_LIST, params,
                new RequestSateListener<MyFansBean>() {
                    @Override
                    public void onSuccess(int code, MyFansBean bean) {
                        if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                            return;
                        }
                        if (!bean.getRows().isEmpty()) {
                            getView().showMyFansList(bean.getRows());
                        } else {
                            getView().loadFail(null);
                        }

                    }

                    @Override
                    public void onFailure(Throwable var1) {

                    }
                });
    }

    @Override
    public void getFollowFans(String state, String followerYbCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("followerYbCode", followerYbCode);
        params.put("state", state);//1.关注，2.取消关注
        params.put("type","");
        NetWork.getInstance().post(getContext(), ChildUrl.follow, params,
                new RequestSateListener<FollowBean>() {
                    @Override
                    public void onSuccess(int code, FollowBean bean) {
                        if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                            return;
                        }
                        if (!bean.getYbCode().isEmpty()) {
                            getView().followFans(bean);
                        } else {
                            getView().loadFail(null);
                        }

                    }

                    @Override
                    public void onFailure(Throwable var1) {

                    }
                });
    }
}
