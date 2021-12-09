package com.yuanbaogo.mine.setting.bill.head.update.unit;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;

@Route(path = YBRouter.UPDATE_UNIT_BILL_HEAD_ACTIVITY)
public class UpdateUnitBillHeadActivity extends MvpBaseActivityImpl<UpdateUnitContract.View, UpdateUnitPresenter> implements UpdateUnitContract.View, View.OnClickListener {


    private EditText addUnitBillEtHead;
    private ImageView addUnitBillIvHead;
    private EditText addUnitBillEtNum;
    private EditText addUnitBillEtAddress;
    private EditText addUnitBillEtPhone;
    private EditText addUnitBillEtBank;
    private EditText addUnitBillEtBankNum;
    private TextView addUnitBillTvUpdate;
    private TextView addUnitBillTvDelete;
    @Autowired(name = YBRouter.BillHeadActivityParams.billHead)
    String mBillHead = "";

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_update_unit_bill_head;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        addUnitBillEtHead = findViewById(R.id.add_unit_bill_et_head);
        addUnitBillIvHead = findViewById(R.id.add_unit_bill_iv_head);
        addUnitBillEtNum = findViewById(R.id.add_unit_bill_et_num);
        addUnitBillEtAddress = findViewById(R.id.add_unit_bill_et_address);
        addUnitBillEtPhone = findViewById(R.id.add_unit_bill_et_phone);
        addUnitBillEtBank = findViewById(R.id.add_unit_bill_et_bank);
        addUnitBillEtBankNum = findViewById(R.id.add_unit_bill_et_bank_num);
        addUnitBillTvUpdate = findViewById(R.id.add_unit_bill_tv_update);
        addUnitBillTvDelete = findViewById(R.id.add_unit_bill_tv_delete);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        addUnitBillIvHead.setOnClickListener(this);
        addUnitBillTvUpdate.setOnClickListener(this);
        addUnitBillTvDelete.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if (!mBillHead.equals("") && !TextUtils.isEmpty(mBillHead)) {
            addUnitBillEtHead.setText(mBillHead);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add_unit_bill_iv_head) {
            addUnitBillEtHead.setText("");
        } else if (id == R.id.add_unit_bill_tv_update) {

        } else if (id == R.id.add_unit_bill_tv_delete) {

        }
    }
}