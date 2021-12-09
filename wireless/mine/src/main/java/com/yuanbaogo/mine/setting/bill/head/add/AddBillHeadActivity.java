package com.yuanbaogo.mine.setting.bill.head.add;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;

@Route(path = YBRouter.ADD_BILL_HEAD_ACTIVITY)
public class AddBillHeadActivity extends MvpBaseActivityImpl<AddBillHeadContract.View, AddBillHeadPresenter> implements AddBillHeadContract.View, View.OnClickListener {

    private TextView addBillTvPersonal;
    private TextView addBillTvUnit;
    private EditText addBillEtHead;
    private ImageView addBillIvHead;
    private TextView addBillTvSave;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_add_bill_head;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        addBillTvPersonal = findViewById(R.id.add_bill_tv_personal);
        addBillTvUnit = findViewById(R.id.add_bill_tv_unit);
        addBillEtHead = findViewById(R.id.add_bill_et_head);
        addBillIvHead = findViewById(R.id.add_bill_iv_head);
        addBillTvSave = findViewById(R.id.add_bill_tv_save);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        addBillTvPersonal.setOnClickListener(this);
        addBillTvUnit.setOnClickListener(this);
        addBillIvHead.setOnClickListener(this);
        addBillTvSave.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add_bill_tv_personal) {

        } else if (id == R.id.add_bill_tv_unit) {

        } else if (id == R.id.add_bill_iv_head) {
            addBillEtHead.setText("");
        } else if (id == R.id.add_bill_tv_save) {

        }
    }
}