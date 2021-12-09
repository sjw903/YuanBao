package com.yuanbaogo.mine.collection;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.collection.model.CollectionModel;
import com.yuanbaogo.mine.collection.model.MoveShopCartModel;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;
import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

public class CollectionPresenter extends MvpBasePersenter<CollectionContract.View> implements CollectionContract.Presenter {

    @Override
    public void getCollectionList(int page) {
        String url = ChildUrl.GET_COLLECTION_LIST + "/" + page + "/" + LOAD_ROWS;
        NetWork.getInstance().get(getContext(), url, null, new RequestSateListener<CollectionModel>() {
            @Override
            public void onSuccess(int code, CollectionModel bean) {
                Log.d(TAG, "getCollectionList onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                if (bean.getRows() != null && bean.getRows().size() > 0) {
                    getView().showCollectionList(bean.getRows());
                } else {
                    if (page == DEFAULT_ONE) {
                        getView().showCollectionList(null);
                    } else {
                        getView().loadFail(null);
                        ToastView.showToast(R.string.order_no_data_toast);
                    }
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "getCollectionList onFailure: " + var1.getMessage());
                OrderNetworkUtils.disposeError(var1);
                if (getView() == null || !isActive()) {
                    return;
                }
                getView().loadFail(var1);
            }
        });
    }

    @Override
    public void moveShopCart(MoveShopCartModel model) {
        Map<String, Object> params = JSON.parseObject(JSON.toJSONString(model));
        NetWork.getInstance().post(getContext(), ChildUrl.MOVE_SHOP_CART, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "moveShopCart onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().moveShopCart(bean != null);
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "moveShopCart onFailure: " + var1.getMessage());
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }

    @Override
    public void deleteFavorites(HashSet<String> idList) {
        Iterator<String> iterator = idList.iterator();
        long[] ids = new long[idList.size()];
        int i = 0;
        while (iterator.hasNext()) {
            ids[i] = Long.parseLong(iterator.next());
            i++;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("idList", ids);
        NetWork.getInstance().post(getContext(), ChildUrl.DELETE_FAVORITES, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.d(TAG, "deleteFavorites onSuccess: code:" + code + "   bean:" + bean);
                if (code != NetConfig.NETWORK_SUCCESSFUL || getView() == null || !isActive()) {
                    return;
                }
                getView().deleteFavorites(bean != null);
            }

            @Override
            public void onFailure(Throwable var1) {
                Log.e(TAG, "deleteFavorites onFailure: " + var1.getMessage());
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }

}
