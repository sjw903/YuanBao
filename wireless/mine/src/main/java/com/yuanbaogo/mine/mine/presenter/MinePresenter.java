package com.yuanbaogo.mine.mine.presenter;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.mine.contract.MineContract;
import com.yuanbaogo.mine.mine.model.PersonalInfoBean;
import com.yuanbaogo.mine.mine.model.UserInfoModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 4:32 PM
 * @Modifier:
 * @Modify:
 */
public class MinePresenter extends MvpBasePersenter<MineContract.View> implements MineContract.Presenter {

    private static final String TAG = "MinePresenter";

    @Override
    public void getUserInfo() {
        NetWork.getInstance().post(getContext(), ChildUrl.GET_USER_INFO, null, new RequestSateListener<UserInfoModel>() {
            @Override
            public void onSuccess(int code, UserInfoModel bean) {

                if (!isActive() || bean == null) {
                    return;
                }
                getView().getUserInfoSuccess(bean);
            }

            @Override
            public void onFailure(Throwable var1) {

            }
        },false);
    }

    /**
     * 获取个人信息
     *
     * @param ybCode
     */
    @Override
    public void getPersonalInfo(String ybCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("ybCode", ybCode);
        NetWork.getInstance().get(getContext(),
                ChildUrl.PERSONAL_INFO,
                params,
                new RequestSateListener<PersonalInfoBean>() {
                    @Override
                    public void onSuccess(int code, PersonalInfoBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setPersonalInfo(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initPersonalInfo();
                        }
                    }
                },false);
    }

}
