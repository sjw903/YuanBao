package com.yuanbaogo.homevideo.mine.presenter;

import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.homevideo.mine.contract.WorkContract;
import com.yuanbaogo.homevideo.mine.model.VodListBean;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lxx
 * @description:
 * @date : 2021/8/24
 */
public class WorkPresenter extends MvpBasePersenter<WorkContract.View>
        implements WorkContract.Presenter {

    /**
     * 作品列表
     *
     * @param page
     * @param size
     * @param title
     * @param ybCode
     */
    @Override
    public void getWorkVodList(int page, int size, String title, String ybCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("size", size);
        params.put("title", title);
        params.put("ybCode", ybCode);
        NetWork.getInstance().post(getContext(),
                ChildUrl.MY_VOD_LIST,
                params,
                new RequestSateListener<RecommendVideoBean>() {
                    @Override
                    public void onSuccess(int code, RecommendVideoBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setVodList(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initVodList();
                        }
                    }
                });
    }

    @Override
    public void getLikeVodList(int page, int size, String title, String ybCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("size", size);
        params.put("title", title);
        params.put("ybCode", ybCode);
        NetWork.getInstance().post(getContext(),
                ChildUrl.LIKE_VOD_LIST,
                params,
                new RequestSateListener<RecommendVideoBean>() {
                    @Override
                    public void onSuccess(int code, RecommendVideoBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setVodList(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initVodList();
                        }
                    }
                });
    }
}
