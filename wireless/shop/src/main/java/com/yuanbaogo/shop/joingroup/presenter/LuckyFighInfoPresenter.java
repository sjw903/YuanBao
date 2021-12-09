package com.yuanbaogo.shop.joingroup.presenter;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.shop.joingroup.contract.LuckyFighInfoContract;
import com.yuanbaogo.shop.joingroup.model.LuckyFighInfoBean;
import com.yuanbaogo.shop.productdetails.model.CommentBean;
import com.yuanbaogo.shop.productdetails.model.DetailsModelBean;
import com.yuanbaogo.shop.publics.model.RecommendBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 幸运拼团详情Presenter
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/28/21 5:00 PM
 * @Modifier:
 * @Modify:
 */
public class LuckyFighInfoPresenter extends MvpBasePersenter<LuckyFighInfoContract.View>
        implements LuckyFighInfoContract.Presenter {

    @Override
    public void initDisplayData() {
        getView().initTab();
        getView().initListener();
        List<DetailsModelBean> detailsBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DetailsModelBean bean = new DetailsModelBean();
            bean.setType(i + 1);
            detailsBeanList.add(bean);
        }
        getView().setAdapter(detailsBeanList);
    }

    /**
     * @param id 抽奖ID
     */
    @Override
    public void getGoodsDetail(String id) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.GOODS_DETAIL.replace("{id}", id),
                params,
                new RequestSateListener<LuckyFighInfoBean>() {
                    @Override
                    public void onSuccess(int code, LuckyFighInfoBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setGoodsDetail(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initGoodsDetail();
                        }
                    }
                });
    }

    @Override
    public void getSearchRecommend(int attribution) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", "1");
        params.put("pageSize", "6");
        NetWork.getInstance().post(getContext(),
                ChildUrl.RECOMMEND,
                params,
                new RequestSateListener<List<RecommendBean>>() {
                    @Override
                    public void onSuccess(int code, List<RecommendBean> bean) {
                        if (code == 200 && getView() != null) {
                            getView().setSearchRecommend(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initSearchRecommend();
                        }
                    }
                });
    }

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

    @Override
    public void getParticipated(String id) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.PARTICIPATED.replace("{id}", id),
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String bean) {
                        if (code == 200 && getView() != null) {
                            getView().setParticipated(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initParticipated();
                        }
                    }
                });
    }

}
