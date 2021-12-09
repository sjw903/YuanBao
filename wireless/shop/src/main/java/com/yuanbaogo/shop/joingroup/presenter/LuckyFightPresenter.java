package com.yuanbaogo.shop.joingroup.presenter;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.shop.joingroup.contract.LuckyFightContract;
import com.yuanbaogo.shop.joingroup.model.LuckDrawListBean;
import com.yuanbaogo.shop.joingroup.model.LuckFightParamsBean;
import com.yuanbaogo.shop.joingroup.model.LuckyFightBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/26/21 10:45 AM
 * @Modifier:
 * @Modify:
 */
public class LuckyFightPresenter extends MvpBasePersenter<LuckyFightContract.View>
        implements LuckyFightContract.Presenter {

    /**
     * 活动名称列表
     */
    @Override
    public void getLuckDrawTagList() {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.LUCK_DRAW_LIST,
                params,
                new RequestSateListener<List<LuckDrawListBean>>() {
                    @Override
                    public void onSuccess(int code, List<LuckDrawListBean> bean) {
                        if (code == 200 && getView() != null) {
                            getView().setLuckDrawTagList(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initLuckDrawTagList();
                        }
                    }
                });
    }

    /**
     * 分页查询幸运大抽奖
     */
    @Override
    public void getLuckDrawList(LuckFightParamsBean parameteBean) {
        Map<String, Object> params = new HashMap<>();
        params.put("activityId", parameteBean.getActivityId());
        params.put("page", parameteBean.getPageReq());
        params.put("timeFlag", parameteBean.getTimeFlag());
        NetWork.getInstance().post(getContext(),
                ChildUrl.LUCK_DRAW,
                params,
                new RequestSateListener<LuckyFightBean>() {
                    @Override
                    public void onSuccess(int code, LuckyFightBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setLuckDrawList(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initLuckDrawList();
                        }
                    }
                });
    }

}
