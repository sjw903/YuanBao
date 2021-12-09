package com.yuanbaogo.shop.main.presenter;

import android.Manifest;
import android.graphics.Color;

import androidx.fragment.app.FragmentActivity;

import com.permissionx.guolindev.PermissionX;
import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.main.contract.ShopContract;
import com.yuanbaogo.shop.main.model.ShopRecommendVideoBean;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 商城P层
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 9:44 AM
 * @Modifier:
 * @Modify:
 */
public class ShopPresenter extends MvpBasePersenter<ShopContract.View>
        implements ShopContract.Presenter {

    /**
     * 刷新数据
     */
    @Override
    public void initDisplayData() {
        getView().initListener();
        getView().initTopSearchLocation();
        getView().initFunction();
    }

    /**
     * 功能点击事件
     *
     * @param postion
     */
    @Override
    public void initFunctionInfo(int postion) {
        switch (postion) {

            case 1://一城一品
                RouterHelper.toShopOneCity();
                break;

            case 2://拼团
//                RouterHelper.toShopJoinGroup();
                RouterHelper.toShopLuckyFight();
                break;

            case 3://秒杀
                RouterHelper.toShopSpick();
                break;

            case 5://元宝国际
                RouterHelper.toYBInternational();
                break;

            case 6://话费充值
                if (!UserStore.isLogin()) {
                    RouterHelper.toLogin();
                    return;
                }
                initPhoneGasoline(1);
                break;

            case 8://加油卡
                if (!UserStore.isLogin()) {
                    RouterHelper.toLogin();
                    return;
                }
                initPhoneGasoline(2);
                break;

            case 10://更多
                ToastView.showToast(getView().getViewContext(), 0,
                        "暂无更多");
                break;

            default:
                ToastView.showToast(getView().getViewContext(), 0,
                        getView().getViewContext().getResources().getString(R.string.toast_view_in_development_msg));
                break;

        }
    }

    /**
     * 推荐搜索
     */
    @Override
    public void getADSearch() {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance()
                .post(getContext(),
                        ChildUrl.RECOMMENDED_SEARCH,
                        params,
                        new RequestSateListener<List<String>>() {
                            @Override
                            public void onSuccess(int code, List<String> bean) {
                                if (code == 200 && getView() != null) {
                                    getView().setADSearch(bean);
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() != null) {
                                    getView().initADSearch();
                                }
                            }
                        },false);
    }

    /**
     * 获取推荐直播数据
     */
    @Override
    public void getRecommendLive() {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance()
                .get(getContext(),
//                        ChildUrl.FIRST_LIVE_RECOMMEND,
                        ChildUrl.LAUNCH_LIS,
                        params,
//                        new RequestSateListener<List<RecommendLiveBean>>() {
                        new RequestSateListener<List<ShopRecommendVideoBean>>() {
                            @Override
//                            public void onSuccess(int code, List<RecommendLiveBean> bean) {
                            public void onSuccess(int code, List<ShopRecommendVideoBean> bean) {
                                if (code == 200 && getView() != null) {
                                    getView().setRecommendLive(bean);
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() != null) {
                                    getView().initRecommendLive();
                                }
                            }
                        },false);
    }

    /**
     * 获取福利社手机充值服务以及加油服务接口url
     *
     * @param serviceSign 1 手机充值  2 加油
     */
    @Override
    public void initPhoneGasoline(int serviceSign) {
        Map<String, Object> params = new HashMap<>();
        params.put("serviceSign", serviceSign);
        NetWork.getInstance()
                .post(getContext(),
                        ChildUrl.PHONE_GASOLINE,
                        params,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code == 200 && getView() != null) {

                                    PermissionX.init((FragmentActivity) getContext())
                                            .permissions(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
                                            .setDialogTintColor(Color.parseColor("#008577"), Color.parseColor("#83e8dd"))
                                            .onExplainRequestReason((scope, deniedList) -> {
                                                String message = "需要您同意以下权限才能正常使用";
                                                scope.showRequestReasonDialog(deniedList, message, "确定", "取消");
                                            })
                                            .request((allGranted, grantedList, deniedList) -> {
                                                if (allGranted) {
                                                    RouterHelper.toWebJs(bean.replace("\"", ""), true);
                                                } else {
                                                    ToastUtil.showToast("请先打开定位权限");
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() != null) {

                                }
                            }
                        });
    }

    @Override
    public void getVodInfo(String vodId) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance()
                .get(getContext(),
                        ChildUrl.VOD_INFO.replace("{vodId}", vodId),
                        params,
                        new RequestSateListener<RecommendVideoBean.RowsBean>() {
                            @Override
                            public void onSuccess(int code, RecommendVideoBean.RowsBean bean) {
                                if (code == 200 && getView() != null) {
                                    getView().setVodInfo(bean);
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() != null) {
                                    getView().initVodInfo();
                                }
                            }
                        });
    }

}
