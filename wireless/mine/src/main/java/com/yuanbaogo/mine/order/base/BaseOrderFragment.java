package com.yuanbaogo.mine.order.base;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.adapter.AllOrderRecyclerAdapter;
import com.yuanbaogo.mine.order.model.OrderListResponseListItem;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseOrderFragment<V extends IBaseView, P extends MvpBasePersenter> extends MvpBaseFragmentImpl<V, P> implements IBaseView, OnRefreshListener, OnLoadMoreListener, AllOrderRecyclerAdapter.OrderListRefreshListener {

    protected static final String TAG = BaseOrderFragment.class.getSimpleName();
    protected SmartRefreshLayout mOrderListSmartRefresh;
    protected RecyclerView mOrderListRv;
    protected ImageView mOrderEmpty;
    protected AllOrderRecyclerAdapter mAdapter;
    protected List<OrderListResponseListItem> mOrderModelList = new ArrayList<>();
    protected int mPage = DEFAULT_ONE;
    protected int mLikePage = DEFAULT_ONE;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_base_order_fragment;
    }

    @CallSuper
    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mOrderListSmartRefresh = (SmartRefreshLayout) findViewById(R.id.order_list_smart_refresh);
        mOrderListRv = (RecyclerView) findViewById(R.id.order_list_rv);
        mOrderEmpty = (ImageView) findViewById(R.id.order_empty);
        mAdapter = new AllOrderRecyclerAdapter(getContext(), mOrderModelList);
        mAdapter.setOrderListRefreshListener(this);
        mOrderListRv.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        clear();
        loadData();
    }

    @CallSuper
    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mOrderListSmartRefresh.setOnRefreshListener(this);
        mOrderListSmartRefresh.setOnLoadMoreListener(this);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage = DEFAULT_ONE;
        mLikePage = DEFAULT_ONE;
        clear();
        loadData();
    }

    private void clear() {
        if (mOrderModelList.size() <= 0) {
            return;
        }
        mOrderModelList.clear();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        mLikePage++;
        loadData();
    }

    protected void loadFail() {
        if (mPage > 1) mPage--;
        else mPage = DEFAULT_ONE;
        if (mLikePage > 1) mLikePage--;
        else mLikePage = DEFAULT_ONE;
    }

    abstract protected void loadData();

    protected void loadFinish(boolean successful) {
        mOrderListSmartRefresh.finishRefresh(successful);
        mOrderListSmartRefresh.finishLoadMore(successful);
        if (!successful) loadFail();
    }

    /**
     * 是否显示缺省页面
     *
     * @param isShowEmpty true 显示  false 不显示
     */
    protected void showEmpty(boolean isShowEmpty) {
        if (isShowEmpty) {
            mOrderEmpty.setVisibility(View.VISIBLE);
            mOrderListSmartRefresh.setVisibility(View.GONE);
        } else {
            mOrderEmpty.setVisibility(View.GONE);
            mOrderListSmartRefresh.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void orderListRefresh() {
        loadData();
    }
    
}