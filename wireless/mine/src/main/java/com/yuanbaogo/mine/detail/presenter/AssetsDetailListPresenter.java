package com.yuanbaogo.mine.detail.presenter;

import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.detail.contract.AssetsDetailListContract;
import com.yuanbaogo.mine.detail.model.FindLogBean;
import com.yuanbaogo.mine.integral.model.YBIntegralBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 收支明细Presenter
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/10/21 12:54 PM
 * @Modifier:
 * @Modify:
 */
public class AssetsDetailListPresenter extends MvpBasePersenter<AssetsDetailListContract.View>
        implements AssetsDetailListContract.Presenter {

    /**
     * 查询元宝积分log
     *
     * @param url
     * @param pageNum
     * @param size
     * @param ybCode
     */
    @Override
    public void getCoinPointLog(String url, int pageNum, int size, String ybCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("size", size);
        NetWork.getInstance().post(getContext(),
                url,
                params,
                new RequestSateListener<YBIntegralBean>() {
                    @Override
                    public void onSuccess(int code, YBIntegralBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setCoinPointLog(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initCoinPointLog();
                        }
                    }
                });
    }

    /**
     * 查询用户优惠券交易日志   查询账户历史记录
     *
     * @param url
     * @param bean
     * @param ybCode
     */
    @Override
    public void getFindLog(String url, FindLogBean bean, String ybCode) {

        Map<String, Object> params = new HashMap<>();
        params.put("areaType", bean.getAreaType());
        params.put("pageReq", bean.getPageReq());
        NetWork.getInstance().post(
                getContext(),
                url,
                params,
                new RequestSateListener<YBIntegralBean>() {
                    @Override
                    public void onSuccess(int code, YBIntegralBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setCoinPointLog(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initCoinPointLog();
                        }
                    }
                });
    }

}
