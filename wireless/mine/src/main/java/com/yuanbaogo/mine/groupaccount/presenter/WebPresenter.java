package com.yuanbaogo.mine.groupaccount.presenter;

import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libshare.model.ShareParamete;
import com.yuanbaogo.mine.groupaccount.contract.WebContract;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/13/21 10:24 AM
 * @Modifier:
 * @Modify:
 */
public class WebPresenter extends MvpBasePersenter<WebContract.View> implements WebContract.Presenter {

    /**
     * 获取小程序邀请二维码
     *
     * @param page  扫码进入的小程序页面路径
     * @param scene 链接参数，32个可见字符,示例值(a=1)
     */
    @Override
    public void getWXcode(String page, String scene) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);//TODO HG page=小程序路径  需要上线后修改
        params.put("scene", scene);
        NetWork.getInstance()
                .post(getContext(),
                        ChildUrl.GET_WXCODE,
                        params,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code == 200 && getView() != null) {
                                    getView().setWXcode(bean);
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() != null) {
                                    getView().initWXcode();
                                }
                            }
                        });
    }

}
