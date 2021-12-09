package com.yuanbaogo.mine.coupon;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.coupon.adapter.CouponRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.COUPON_ACTIVITY)
public class CouponActivity extends MvpBaseActivityImpl<CouponContract.View, CouponPresenter> implements CouponContract.View, OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mCouponSmartRefresh;
    private RecyclerView mCouponRv;
    private LinearLayout mCouponLlEmpty;

    private final List<String> mCouponItems = new ArrayList<>();
    private int mPage = DEFAULT_ONE;
    private CouponRecyclerAdapter mAdapter;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_coupon;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mCouponSmartRefresh = findViewById(R.id.coupon_smart_refresh);
        mCouponRv = findViewById(R.id.coupon_rv);
        mCouponLlEmpty = findViewById(R.id.coupon_ll_empty);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mCouponSmartRefresh.setOnRefreshListener(this);
        mCouponSmartRefresh.setOnLoadMoreListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mAdapter = new CouponRecyclerAdapter(this, mCouponItems);
        mCouponRv.setAdapter(mAdapter);
        clear();
        loadData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage = DEFAULT_ONE;
        clear();
        loadData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        loadData();
    }

    private void loadData() {
        mPresenter.getCouponList(mPage);
    }

    private void clear() {
        if (mCouponItems.size() <= 0) {
            return;
        }
        mCouponItems.clear();
    }

    private void loadFail() {
        if (mPage > 1) mPage--;
        else mPage = DEFAULT_ONE;
    }

    protected void loadFinish(boolean successful) {
        mCouponSmartRefresh.finishRefresh(successful);
        mCouponSmartRefresh.finishLoadMore(successful);
        if (!successful) loadFail();
    }

    @Override
    public void showCouponList(List<String> couponList) {
        showEmpty(couponList == null);
        if (couponList == null) return;
        mCouponItems.addAll(couponList);
        mAdapter.notifyDataSetChanged();
        loadFinish(true);
    }

    @Override
    public void loadFail(Throwable throwable) {
        loadFinish(false);
    }

    /**
     * 是否显示缺省页面
     *
     * @param isShowEmpty true 显示  false 不显示
     */
    protected void showEmpty(boolean isShowEmpty) {
        if (isShowEmpty) {
            mCouponLlEmpty.setVisibility(View.VISIBLE);
            mCouponSmartRefresh.setVisibility(View.GONE);
        } else {
            mCouponLlEmpty.setVisibility(View.GONE);
            mCouponSmartRefresh.setVisibility(View.VISIBLE);
        }
    }

}