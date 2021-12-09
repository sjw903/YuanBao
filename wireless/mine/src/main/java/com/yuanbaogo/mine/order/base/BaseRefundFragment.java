package com.yuanbaogo.mine.order.base;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.adapter.AfterSalesRecyclerAdapter;
import com.yuanbaogo.mine.order.after.model.AfterSalesModel;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRefundFragment<V extends IBaseView, P extends MvpBasePersenter>
        extends MvpBaseFragmentImpl<V, P> implements IBaseView, OnRefreshListener, OnLoadMoreListener, Observer<String> {

    protected static final String TAG = BaseRefundFragment.class.getSimpleName();
    protected SmartRefreshLayout mOrderListSmartRefresh;
    protected RecyclerView mOrderListRv;
    protected ImageView mOrderEmpty;
    protected AfterSalesRecyclerAdapter mAdapter;
    protected List<AfterSalesModel> mAfterSalesModelList = new ArrayList<>();
    protected int mPage = DEFAULT_ONE;
    protected String mSearch = "";
    private ImageView orderListIvTop;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_base_order_fragment;
    }

    @CallSuper
    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mOrderListSmartRefresh = (SmartRefreshLayout) findViewById(R.id.order_list_smart_refresh);
        mOrderListRv = (RecyclerView) findViewById(R.id.order_list_rv);
        orderListIvTop = (ImageView) findViewById(R.id.order_list_iv_top);
        mOrderEmpty = (ImageView) findViewById(R.id.order_empty);
        mAdapter = new AfterSalesRecyclerAdapter(getContext(), mAfterSalesModelList);
        mOrderListRv.setAdapter(mAdapter);
        mOrderListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    orderListIvTop.setVisibility(View.GONE);
                } else if (dy < 0) {
                    orderListIvTop.setVisibility(View.VISIBLE);
                } else if (dy > 0) {
                    orderListIvTop.setVisibility(View.GONE);
                }
            }
        });
        BaseRefundViewModel mViewModel = new ViewModelProvider(requireActivity()).get(BaseRefundViewModel.class);
        mViewModel.searchText.observe(getViewLifecycleOwner(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPage = DEFAULT_ONE;
        clear();
        loadData();
    }

    @CallSuper
    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mOrderListSmartRefresh.setOnRefreshListener(this);
        mOrderListSmartRefresh.setOnLoadMoreListener(this);
        orderListIvTop.setOnClickListener(v -> mOrderListRv.smoothScrollToPosition(0));
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage = DEFAULT_ONE;
        clear();
        loadData();
    }

    protected void clear() {
        if (mAfterSalesModelList.size() <= 0) {
            return;
        }
        mAfterSalesModelList.clear();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        loadData();
    }

    private void loadFail() {
        if (mPage > 1) mPage--;
        else mPage = DEFAULT_ONE;
    }

    abstract protected void loadData();

    protected void loadFinish(boolean successful) {
        mOrderListSmartRefresh.finishRefresh(successful);
        mOrderListSmartRefresh.finishLoadMore(successful);
        if (!successful) loadFail();
    }

    @Override
    public void onChanged(String s) {
        Log.d(TAG, "onChanged: " + s);
        onSearchTextChange(s);
    }

    /**
     * View初始化操作
     */
    protected abstract void onSearchTextChange(String search);

    /**
     * 是否显示缺省页面
     *
     * @param isShowEmpty true 显示  false 不显示
     */
    protected void showEmpty(boolean isShowEmpty) {
        if (isShowEmpty) {
            mOrderEmpty.setVisibility(View.VISIBLE);
            mOrderEmpty.setImageResource(R.mipmap.icon_no_data);
            mOrderListSmartRefresh.setVisibility(View.GONE);
        } else {
            mOrderEmpty.setVisibility(View.GONE);
            mOrderListSmartRefresh.setVisibility(View.VISIBLE);
        }
    }

}