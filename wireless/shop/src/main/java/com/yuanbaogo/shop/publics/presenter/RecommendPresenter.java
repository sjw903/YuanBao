package com.yuanbaogo.shop.publics.presenter;

import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.shop.publics.contract.RecommendContract;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseRowsBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/16/21 3:12 PM
 * @Modifier:
 * @Modify:
 */
public class RecommendPresenter extends MvpBasePersenter<RecommendContract.View>
        implements RecommendContract.Presenter {

    /**
     * 推荐商品
     *
     * @param attribution 4 好货必buy  5 列表
     */
    @Override
    public void getRecommend(int pageNum, int pageSize, int attribution) {
        Map<String, Object> params = new HashMap<>();
        params.put("attribution", attribution);
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        NetWork.getInstance().post(getContext(),
                ChildUrl.RECOMMEND,
                params,
                new RequestSateListener<List<SearchMerchandiseRowsBean>>() {
                    @Override
                    public void onSuccess(int code, List<SearchMerchandiseRowsBean> bean) {
                        if (code == 200 && getView() != null) {
                            getView().setRecommend(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initRecommend();
                        }
                    }
                });
    }

}
