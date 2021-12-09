package com.yuanbaogo.mine.order.after.apply;

import android.os.Bundle;

import com.yuanbaogo.mine.order.adapter.AfterSalesRecyclerAdapter;
import com.yuanbaogo.mine.order.after.model.AfterSalesModel;
import com.yuanbaogo.mine.order.base.BaseRefundFragment;
import com.yuanbaogo.mine.order.utils.Configuration;

import java.util.List;


public class RefundApplyFragment extends BaseRefundFragment<RefundApplyContract.View, RefundApplyPresenter>
        implements RefundApplyContract.View {

    public static RefundApplyFragment newInstance() {
        return new RefundApplyFragment();
    }

    @Override
    protected RefundApplyPresenter createPresenter(Bundle savedInstanceState) {
        return new RefundApplyPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mAdapter.setFlag(AfterSalesRecyclerAdapter.REFUND_APPLY);
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