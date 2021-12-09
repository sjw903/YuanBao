package com.yuanbaogo.im.login;

import android.content.Context;

import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.yuanbaogo.datacenter.local.user.UserInfo;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.im.model.UsersigBean;

import java.util.ArrayList;
import java.util.List;

public class LoginManager {

    public static void logout() {
        V2TIMManager.getInstance().logout(new V2TIMCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int code, String desc) {

            }
        });
    }


    public static void login() {

        V2TIMManager.getInstance().login(UserStore.getYbCode(), UserStore.getImSign(), new V2TIMCallback() {
            @Override
            public void onSuccess() {
//                Log.e("network","===im=login=onSuccess====");
            }

            @Override
            public void onError(int code, String desc) {
//                Log.e("network","===im=login=onError===="+desc);
            }
        });
    }

    public static void getUserSig(Context context) {
        NetWork.getInstance().post(context, ChildUrl.personalsig, null, new RequestSateListener<UsersigBean>() {
            @Override
            public void onSuccess(int code, UsersigBean bean) {
                UserStore.setImSign(bean.getUsersig());
                login();
            }
            @Override
            public void onFailure(Throwable e) {
            }
        },false);
    }

    public static void getUsersInfo() {
        if (UserStore.isLogin()) {
            List<String> userIDList = new ArrayList<>();
            userIDList.add(UserStore.getYbCode());
            V2TIMManager.getInstance().getUsersInfo(userIDList, new V2TIMValueCallback<List<V2TIMUserFullInfo>>() {
                @Override
                public void onSuccess(List<V2TIMUserFullInfo> list) {
                    UserInfo user = UserStore.getUser();
                    if (user != null) {
                        // 如果修改成功，修改保存的用户信息
                        UserStore.saveUser(user);
                    }
                }
                @Override
                public void onError(int code, String desc) {

                }
            });
        }
    }



}
