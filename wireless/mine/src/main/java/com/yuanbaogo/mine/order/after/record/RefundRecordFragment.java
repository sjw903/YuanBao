package com.yuanbaogo.mine.order.after.record;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;

import android.os.Bundle;

import com.yuanbaogo.mine.order.adapter.AfterSalesRecyclerAdapter;
import com.yuanbaogo.mine.order.after.model.AfterSalesModel;
import com.yuanbaogo.mine.order.base.BaseRefundFragment;
import com.yuanbaogo.mine.order.utils.Configuration;

import java.util.List;

public class RefundRecordFragment extends BaseRefundFragment<RefundRecordContract.View, RefundRecordPresenter>
        implements RefundRecordContract.View, AfterSalesRecyclerAdapter.RefreshListListener {

    public static RefundRecordFragment newInstance() {
        return new RefundRecordFragment();
    }

    @Override
    protected RefundRecordPresenter createPresenter(Bundle savedInstanceState) {
        return new RefundRecordPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mAdapter.setRefreshListListener(this);
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
        if (afterSalesModels == null) {
            mAfterSalesModelList.clear();
        } else {
            if (mPage == 1) {
                mAfterSalesModelList.clear();
            }
            mAfterSalesModelList.addAll(afterSalesModels);
        }
        showEmpty(afterSalesModels == null);
        loadFinish(true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(String error) {
        if (error != null && error.equals(Configuration.DEFAULT_SEARCH)) {
            mAfterSalesModelList.clear();
            mAdapter.notifyDataSetChanged();
        }
        loadFinish(false);
    }

    @Override
    public void refreshList() {
        mPage = DEFAULT_ONE;
        clear();
        loadData();
    }
}