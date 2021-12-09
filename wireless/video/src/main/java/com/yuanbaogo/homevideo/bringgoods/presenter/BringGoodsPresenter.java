package com.yuanbaogo.homevideo.bringgoods.presenter;


import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.homevideo.bringgoods.contract.BringGoodsContract;
import com.yuanbaogo.homevideo.bringgoods.model.LiveGoodsListBean;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lhx
 * @description:
 * @date : 2021/7/21 18:37
 */
public class BringGoodsPresenter extends MvpBasePersenter<BringGoodsContract.View> implements BringGoodsContract.Presenter {

    private List<LiveGoodsListBean> mList;
    List<String> mPointList = new ArrayList<>();

    @Override
    public void getGoodsList(String keyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("goodsName", keyword);

        NetWork.getInstance().post(getContext(), ChildUrl.goodslist, params, new RequestSateListener<List<LiveGoodsListBean>>() {
            @Override
            public void onSuccess(int var1, List<LiveGoodsListBean> list) {
                mList = list;
                if (isActive() && list != null) {
                    getView().setDataList(list);
                }
            }

            @Override
            public void onFailure(Throwable var1) {
            }
        });
    }

    @Override
    public void verification() {
        if (getGoodsListId().size() > 0) {
            if (isActive()) {
                getView().setSearchBarStatus(true);
            }
        }else{
            if (isActive()) {
                getView().setSearchBarStatus(false);
            }
        }
    }

    @Override
    public List<String> getGoodsListId() {
        mPointList.clear();
        for (LiveGoodsListBean bean : mList) {
            if (bean.isChecked()) {
                mPointList.add(bean.getId());
            }
        }
        return mPointList;
    }

}
