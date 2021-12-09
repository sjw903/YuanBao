package com.yuanbaogo.mine.order.after.logistics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.utils.TextUtils;
import com.github.gzuliyujiang.wheelview.widget.WheelView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

public class SetLogisticsCodeActivity extends MvpBaseActivityImpl<SetLogisticsCodeContract.View, SetLogisticsCodePresenter> implements
        SetLogisticsCodeContract.View, View.OnClickListener, TextWatcher {

    public static final String ORDER_ID = "ORDER_ID";
    private LinearLayout mSetCodeLlCompany;
    private TextView mSetCodeTvCompany;
    private EditText mSetCodeEtCode;
    private TextView mSetCodeTvSubmit;
    private String mCode;
    private String mOrderId;
    private List<LogisticsCompanyModel> logisticsCompanyModels;
    private String expressDeliveryName;
    private int expressDeliveryCode;
    private BottomSheetDialog mSheetDialog;
    private WheelView mSelectWheelView;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_set_logistics_code;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mSetCodeLlCompany = findViewById(R.id.set_code_ll_company);
        mSetCodeTvCompany = findViewById(R.id.set_code_tv_company);
        mSetCodeEtCode = findViewById(R.id.set_code_et_code);
        mSetCodeTvSubmit = findViewById(R.id.set_code_tv_submit);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mSetCodeLlCompany.setOnClickListener(this);
        mSetCodeTvSubmit.setOnClickListener(this);
        mSetCodeEtCode.addTextChangedListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mOrderId = getIntent().getStringExtra(ORDER_ID);
        mPresenter.selectLogisticsCompany();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.set_code_ll_company) {
            selectLogisticsCompany();
        } else if (id == R.id.select_tv_sure) {
            expressDeliveryName = (String) mSelectWheelView.getCurrentItem();
            mSetCodeTvCompany.setText(expressDeliveryName);
            if (mSheetDialog != null) {
                mSheetDialog.dismiss();
            }
        } else if (id == R.id.set_code_tv_submit) {
            for (int i = 0; i < logisticsCompanyModels.size(); i++) {
                if (expressDeliveryName.equals(logisticsCompanyModels.get(i).getName())) {
                    expressDeliveryCode = logisticsCompanyModels.get(i).getCode();
                }
            }
            mPresenter.setLogisticsCode(expressDeliveryCode, mCode, mOrderId);
            ToastView.showToast("提交");
        }
    }

    List<String> strings = new ArrayList<>();

    private void selectLogisticsCompany() {
        if (logisticsCompanyModels.isEmpty()) {
            ToastView.showToast("快递公司列表加载失败");
            return;
        }
        for (int i = 0; i < logisticsCompanyModels.size(); i++) {
            strings.add(logisticsCompanyModels.get(i).getName());
        }
        mSheetDialog = new BottomSheetDialog(this);
        @SuppressLint("InflateParams") final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.mine_dialog_select_logistics_company, null);
        mSheetDialog.setContentView(dialogView);
        mSelectWheelView = dialogView.findViewById(R.id.select_wheel_view);
        TextView selectTvSure = dialogView.findViewById(R.id.select_tv_sure);
        selectTvSure.setOnClickListener(this);
        mSelectWheelView.setDefaultValue(strings);
        mSelectWheelView.setData(strings);
        mSheetDialog.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mCode = mSetCodeEtCode.getText().toString().trim();
        mSetCodeTvSubmit.setEnabled(!TextUtils.isEmpty(mCode));
    }

    @Override
    public void showLogisticsList(List<LogisticsCompanyModel> logisticsCompanyModels) {
        this.logisticsCompanyModels = logisticsCompanyModels;
    }

    @Override
    public void showSetLogisticsResult() {
        ToastView.showToast(R.string.set_logistics_success);
        Intent intent = new Intent();
        intent.putExtra("logisticsId", mCode);
        setResult(201, getIntent());//定义返回的参数parama
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSheetDialog != null) {
            mSheetDialog.cancel();
        }
    }

}