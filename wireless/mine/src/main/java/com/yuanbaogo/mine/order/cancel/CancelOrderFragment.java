package com.yuanbaogo.mine.order.cancel;

import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.yuanbaogo.mine.order.base.BaseOrderFragment;
import com.yuanbaogo.mine.order.model.OrderListResponseListItem;

import java.util.List;

public class CancelOrderFragment extends BaseOrderFragment<CancelOrderContract.View, CancelOrderPresenter>
        implements CancelOrderContract.View {

    public static CancelOrderFragment newInstance() {
        return new CancelOrderFragment();
    }

    private static final String KEYWORD = "keyword";

    private String mKeyword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mKeyword = getArguments().getString(KEYWORD);
        }
    }

    public static CancelOrderFragment newInstance(String keyword) {
        CancelOrderFragment fragment = new CancelOrderFragment();
        Bundle args = new Bundle();
        args.putString(KEYWORD, keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected CancelOrderPresenter createPresenter(Bundle savedInstanceState) {
        return new CancelOrderPresenter();
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        super.bindViews(savedInstanceState);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        super.bindListeners(savedInstanceState);
    }

    @Override
    protected void loadData() {
        mPresenter.getCancelOrder(mPage, mKeyword);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void showCancelOrder(List<OrderListResponseListItem> orderModelList) {
        if (orderModelList == null) {
            mOrderModelList.clear();
        } else {
            if (mPage == 1) {
                if (mOrderModelList.size() != 0) {
                    mOrderModelList.clear();
                }
            }
            mOrderModelList.addAll(orderModelList);
            if (orderModelList.size() != LOAD_ROWS) {
                mOrderListSmartRefresh.finishLoadMoreWithNoMoreData();
            }
        }
        showEmpty(orderModelList == null);
        loadFinish(true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(Throwable throwable) {
        loadFinish(false);
    }
}