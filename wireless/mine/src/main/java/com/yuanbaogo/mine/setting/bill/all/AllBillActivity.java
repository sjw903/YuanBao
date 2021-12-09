package com.yuanbaogo.mine.setting.bill.all;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;

import java.util.ArrayList;

@Route(path = YBRouter.ALL_BILL_ACTIVITY)
public class AllBillActivity extends MvpBaseActivityImpl<AllBillContract.View, AllBillPresenter> implements AllBillContract.View {

    private RecyclerView mAllBillRvList;
    private AllBillRecyclerAdapter mAdapter;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_all_bill;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mAllBillRvList = findViewById(R.id.all_bill_rv_list);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ArrayList<String> objects = new ArrayList<>();
        mAdapter = new AllBillRecyclerAdapter(this,objects);
        mAllBillRvList.setAdapter(mAdapter);
        objects.add("哈哈哈");
        mAdapter.notifyDataSetChanged();
    }

}