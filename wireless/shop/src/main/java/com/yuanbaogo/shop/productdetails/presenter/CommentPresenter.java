package com.yuanbaogo.shop.productdetails.presenter;

import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.shop.productdetails.contract.CommentContract;
import com.yuanbaogo.shop.productdetails.model.CommentBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/9/21 9:49 AM
 * @Modifier:
 * @Modify:
 */
public class CommentPresenter extends MvpBasePersenter<CommentContract.View>
        implements CommentContract.Presenter {

    /**
     * 商品详情-商品评论
     *
     * @param page
     * @param rows
     * @param spuId
     */
    @Override
    public void getComment(int page, int rows, String spuId) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("size", rows);
        params.put("spuId", spuId);
        NetWork.getInstance().post(getContext(),
                ChildUrl.COMMENT,
                params,
                new RequestSateListener<CommentBean>() {
                    @Override
                    public void onSuccess(int code, CommentBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setComment(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initComment();
                        }
                    }
                });
    }
}
