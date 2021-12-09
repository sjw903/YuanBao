package com.yuanbaogo.mine.order.after.process;

import android.os.Bundle;

import com.yuanbaogo.mine.order.after.model.AfterSalesModel;
import com.yuanbaogo.mine.order.base.BaseRefundFragment;
import com.yuanbaogo.mine.order.utils.Configuration;

import java.util.List;

public class RefundProcessFragment extends BaseRefundFragment<RefundProcessContract.View, RefundProcessPresenter>
        implements RefundProcessContract.View {

    public static RefundProcessFragment newInstance() {
        return new RefundProcessFragment();
    }

    @Override
    protected RefundProcessPresenter createPresenter(Bundle savedInstanceState) {
        return new RefundProcessPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mPresenter.getRefundList(mPage, mSearch);
    }

    @Override
    protected void onSearchTextChange(String search) {
        mSearch = search;
        mPresenter.getRefundList(mPage, search);
    }

    @Override
    public void showRefundList(List<AfterSalesModel> afterSalesModels) {
        mAfterSalesModelList.addAll(afterSalesModels);
        mAdapter.notifyDataSetChanged();
        loadFinish(true);
    }

    @Override
    public void loadFail(String error) {
        if (error != null && error.equals(Configuration.DEFAULT_SEARCH)) {
            mAfterSalesModelList.clear();
            mAdapter.notifyDataSetChanged();
        }
        loadFinish(false);
    }

}