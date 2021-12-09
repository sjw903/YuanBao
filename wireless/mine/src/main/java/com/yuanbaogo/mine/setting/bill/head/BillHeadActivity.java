package com.yuanbaogo.mine.setting.bill.head;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.BILL_HEAD_ACTIVITY)
public class BillHeadActivity extends MvpBaseActivityImpl<BillHeadContract.View, BillHeadPresenter> implements BillHeadContract.View, View.OnClickListener {

    private static final String TAG = "BillHeadActivity";
    private static final int UNIT = 0;
    private static final int PERSONAL = 1;
    private TextView mBillHeadTvUnit;
    private TextView mBillHeadTvPersonal;
    private TextView mBillHeadTvAdd;
    private RecyclerView mBillHeadRv;
    private BillHeadRecyclerAdapter mAdapter;
    private ArrayList<String> strings = new ArrayList<>();
    private int flag = UNIT;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_bill_head;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mBillHeadTvUnit = findViewById(R.id.bill_head_tv_unit);
        mBillHeadTvPersonal = findViewById(R.id.bill_head_tv_personal);
        mBillHeadRv = findViewById(R.id.bill_head_rv);
        mBillHeadTvAdd = findViewById(R.id.bill_head_tv_add);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mBillHeadTvUnit.setOnClickListener(this);
        mBillHeadTvPersonal.setOnClickListener(this);
        mBillHeadTvAdd.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mAdapter = new BillHeadRecyclerAdapter(this, strings);
        mBillHeadRv.setAdapter(mAdapter);
        strings.add("11");
        mAdapter.notifyDataSetChanged();
        initCheckItem();
    }

    private void initCheckItem() {
        mBillHeadTvUnit.setTextColor(getColor(flag == UNIT ? R.color.bill_tab_selected : R.color.bill_tab_normal));
        mBillHeadTvPersonal.setTextColor(getColor(flag == UNIT ? R.color.bill_tab_normal : R.color.bill_tab_selected));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bill_head_tv_unit) {
            flag = UNIT;
            initCheckItem();
            mPresenter.loadUnitBillList();
            strings.add("33");
            mAdapter.notifyDataSetChanged();
        } else if (id == R.id.bill_head_tv_personal) {
            flag = PERSONAL;
            initCheckItem();
            mPresenter.loadPersonalBillList();
            strings.add("44");
            mAdapter.notifyDataSetChanged();
        } else if (id == R.id.bill_head_tv_add) {
            RouterHelper.toAddBillHeadCommon();
        }
    }

    @Override
    public void loadUnitBillList(List<String> billList) {
        strings.clear();
        strings.addAll(billList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadPersonalBillList(List<String> billList) {
        strings.clear();
        strings.addAll(billList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(Throwable throwable) {
        Log.e(TAG, "loadFail: " + throwable.getMessage());
    }
}