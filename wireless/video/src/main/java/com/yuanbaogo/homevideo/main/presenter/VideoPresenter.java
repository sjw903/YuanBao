package com.yuanbaogo.homevideo.main.presenter;

import android.content.Intent;

import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.homevideo.live.auth.view.PersonalAuthActivity;
import com.yuanbaogo.homevideo.live.push.view.PushMainActivity;
import com.yuanbaogo.homevideo.main.bean.LiveAuthenticationBean;
import com.yuanbaogo.homevideo.main.contract.VideoContract;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.zui.toast.ToastView;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/21 18:12
 */
public class VideoPresenter extends MvpBasePersenter<VideoContract.View>
        implements VideoContract.Presenter {


    @Override
    public void isOpenLivingRoom() {

        if (TextUtils.isEmpty(UserStore.getToken())) {
            RouterHelper.toLogin();
            return;
        }

        NetWork.getInstance().get(getContext(), ChildUrl.isOpenLivingRoom, null, new RequestSateListener<LiveAuthenticationBean>() {
            @Override
            public void onSuccess(int code, LiveAuthenticationBean bean) {
                if (bean.isAuthentication()) {
                    if(bean.isBan()){
                        ToastView.showToast(getContext(),bean.getMessage());
                    }else{
                        getContext().startActivity(new Intent(getContext(), PushMainActivity.class));
                    }
                }else{
                    getContext().startActivity(new Intent(getContext(), PersonalAuthActivity.class));
                }


            }

            @Override
            public void onFailure(Throwable e) {
            }
        },false);


    }


}
