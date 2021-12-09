package com.yuanbaogo.mine.groupaccount.presenter;

import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.groupaccount.contract.GroupAccountContract;
import com.yuanbaogo.mine.groupaccount.model.GroupAccountMoneyBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/28/21 2:47 PM
 * @Modifier:
 * @Modify:
 */
public class GroupAccountPresenter extends MvpBasePersenter<GroupAccountContract.View>
        implements GroupAccountContract.Presenter {

    @Override
    public void getFindAreaInfo(int areaType, String ybCode) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.FIND_AREA_INFO.replace("{areaType}", areaType + ""),
                params,
                new RequestSateListener<GroupAccountMoneyBean>() {
                    @Override
                    public void onSuccess(int code, GroupAccountMoneyBean bean) {
                        Log.e("==========HG=零钱:::", "getFindAreaInfo onSuccess: code:" + code + "   bean:" + bean);
                        if (code == 200 && getView() != null) {
                            getView().setFindAreaInfo(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        Log.e("==========HG=零钱:::", "getFindAreaInfo onFailure: " + var1.getLocalizedMessage());
                        if (getView() != null) {
                            getView().initFindAreaInfo();
                        }
                    }
                });
    }

}
