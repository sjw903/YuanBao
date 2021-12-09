package com.yuanbaogo.mine.order.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.finance.pay.presenter.PayCenter;
import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.libpay.pay.Constant;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.OrderActivity;
import com.yuanbaogo.mine.order.adapter.AllOrderRecyclerAdapter;
import com.yuanbaogo.mine.order.adapter.LikeListRecyclerAdapter;
import com.yuanbaogo.mine.order.model.LikeListModelItem;
import com.yuanbaogo.mine.order.model.OrderListResponseListItem;
import com.yuanbaogo.zui.dialog.SureDialogFragment;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 待付款
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/15/21 10:02 AM
 * @Modifier:
 * @Modify:
 */
public class PayOrderFragment2 extends MvpBaseFragmentImpl<PayOrderContract.View, PayOrderPresenter>
        implements PayOrderContract.View, OnRefreshListener,
        OnLoadMoreListener, LikeListRecyclerAdapter.OnCallClick, AllOrderRecyclerAdapter.OrderListRefreshListener,
        AllOrderRecyclerAdapter.OnCallItem, SureDialogFragment.OnSureListener {

    private static final String KEYWORD = "keyword";

    private String mKeyword;

    int pageNum = 1;

    RecyclerView orderListRV;

    protected AllOrderRecyclerAdapter mAdapter;

    protected List<OrderListResponseListItem> mOrderModelList = new ArrayList<>();

    private RecyclerView mOrderListRvLike;

    protected LikeListRecyclerAdapter mLikeAdapter;

    SmartRefreshLayout orderListSmartRefresh;

    /**
     * true:请求  false：不请求
     */
    boolean isLoadMore = true;

    ImageView orderListImgEmpty;

    private Handler handler;

    public static PayOrderFragment2 newInstance() {
        return new PayOrderFragment2();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mKeyword = getArguments().getString(KEYWORD);
        }
    }

    public static PayOrderFragment2 newInstance(String keyword) {
        PayOrderFragment2 fragment = new PayOrderFragment2();
        Bundle args = new Bundle();
        args.putString(KEYWORD, keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected PayOrderPresenter createPresenter(Bundle savedInstanceState) {
        return new PayOrderPresenter();
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_pay_order_fragment2;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        orderListSmartRefresh = (SmartRefreshLayout) findViewById(R.id.order_list_smart_refresh);
        orderListRV = (RecyclerView) findViewById(R.id.order_list_rv);
        mOrderListRvLike = (RecyclerView) findViewById(R.id.order_list_rv_like);
        orderListImgEmpty = (ImageView) findViewById(R.id.order_list_img_empty);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.WX_MSG_OK);
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);

        if (TextUtils.isEmpty(mKeyword)){
            OrderActivity activity = (OrderActivity) getActivity();
            /**
             * 得到Activity的Handler
             */
            handler = activity.handler;
        }

    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        orderListSmartRefresh.setOnRefreshListener(this);
        orderListSmartRefresh.setOnLoadMoreListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mPresenter.getLikeList(pageNum);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getPayOrder(1, mKeyword);
    }

    @Override
    public void showPayOrder(List<OrderListResponseListItem> orderModelList) {
        mOrderModelList = orderModelList;
        if (mOrderModelList != null && mOrderModelList.size() != 0) {
            orderListRV.setVisibility(View.VISIBLE);
            orderListImgEmpty.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            orderListRV.setLayoutManager(linearLayoutManager);
            mAdapter = new AllOrderRecyclerAdapter(getActivity(), mOrderModelList);
            mAdapter.setOnCallItem(this);
            mAdapter.setOrderListRefreshListener(this);
            orderListRV.setAdapter(mAdapter);
        } else {
            orderListRV.setVisibility(View.GONE);
            orderListImgEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadFail(Throwable throwable) {

    }

    @Override
    public void showLikeLise(List<LikeListModelItem> likeListModelItems) {
        loadFinish(true);
        if (likeListModelItems.size() < 10) {
            isLoadMore = false;
        } else {
            isLoadMore = true;
        }
        if (likeListModelItems != null && likeListModelItems.size() != 0) {
            if (pageNum == 1) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                mOrderListRvLike.setLayoutManager(gridLayoutManager);
                mLikeAdapter = new LikeListRecyclerAdapter(getActivity(), likeListModelItems);
                mLikeAdapter.setOnCallClick(this);
                mOrderListRvLike.setAdapter(mLikeAdapter);
            } else {
                mLikeAdapter.addAll(likeListModelItems);
            }
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        mPresenter.getLikeList(pageNum);
        mPresenter.getPayOrder(1, mKeyword);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (!isLoadMore) {
            ToastView.showToast(getActivity(), "暂无更多数据");
            loadFinish(true);
            return;
        }
        pageNum++;
        mPresenter.getLikeList(pageNum);
    }

    protected void loadFinish(boolean successful) {
        orderListSmartRefresh.finishRefresh(successful);
        orderListSmartRefresh.finishLoadMore(successful);
    }

    @Override
    public void onClickItem(String spuId, int specialType) {
        RouterHelper.toShopProductDetails(spuId + "",
                specialType, null, null, false);
    }

    @Override
    public void orderListRefresh() {
        mPresenter.getPayOrder(1, mKeyword);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int resp = intent.getIntExtra("code", 0);
            if (action.equals(Constant.WX_MSG_OK)) {
                if (resp == 0) {
                    payCenter.onDismiss();
                } else if (resp == -2) {

                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * 待付款列表 付款按钮点击事件
     */
    PayCenter payCenter;

    OrderListResponseListItem orderListResponseListItem;

    @Override
    public void onClickPayment(OrderListResponseListItem item) {
        this.orderListResponseListItem = item;
        if (item.getOrderActivityType() == 3) {
            mPresenter.getLuckDrawEnter(item.getTotalOrderId());
        } else if (item.getOrderActivityType() == 1) {
            initPay();
        }
    }

    @Override
    public void setLuckDrawEnter() {
        initPay();
    }

    SureDialogFragment mSureDialogFragment;

    @Override
    public void initLuckDrawEnter() {
        mSureDialogFragment = new SureDialogFragment("十分抱歉，预约已结束", " 取消订单");
        mSureDialogFragment.setOnSureListener(this);
        mSureDialogFragment.setCancelable(false);
        mSureDialogFragment.show(getActivity().getSupportFragmentManager(), "luck_draw_enter");
    }

    /**
     * 取消订单按钮
     */
    @Override
    public void onSure() {
        mPresenter.getCancelOrder(orderListResponseListItem.getTotalOrderId());
    }

    /**
     * 取消订单
     */
    @Override
    public void setCancelOrder() {
        if (mSureDialogFragment != null) {
            mSureDialogFragment.dismiss();
        }
        OrderActivity.orderType = OrderActivity.CANCEL_TYPE;
        /**
         * 发送指令到Activity
         */
        handler.sendEmptyMessage(100);
    }

    /**
     * 接口请求失败
     */
    @Override
    public void initCancelOrder() {

    }

    private void initPay() {
        payCenter = new PayCenter();
        payCenter.startPay((FragmentActivity) mContext,
                PayCenter.BUYTYPE_GOODSBUY,
                Integer.parseInt(orderListResponseListItem.getRealPayedPrice()) / 100 + "",
                orderListResponseListItem.getTotalOrderId(),
                orderId -> {
                    RouterHelper.toOrderSuccess((Integer.parseInt(orderListResponseListItem.getRealPayedPrice()) / 100) + "", 1);
                });
    }

}
