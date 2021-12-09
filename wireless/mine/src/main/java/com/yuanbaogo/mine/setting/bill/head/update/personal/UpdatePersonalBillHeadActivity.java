package com.yuanbaogo.mine.setting.bill.head.update.personal;

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

@Route(path = YBRouter.UPDATE_PERSONAL_HEAD_ACTIVITY)
public class UpdatePersonalBillHeadActivity extends MvpBaseActivityImpl<UpdatePersonalContract.View, UpdatePersonalPresenter> implements UpdatePersonalContract.View, View.OnClickListener {

    private EditText addPersonalBillEtHead;
    private ImageView addPersonalBillIvHead;
    private TextView addPersonalBillTvUpdate;
    private TextView addPersonalBillTvDelete;
    @Autowired(name = YBRouter.BillHeadActivityParams.billHead)
    String mBillHead = "";

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_update_personal_bill_head;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        addPersonalBillEtHead = findViewById(R.id.add_personal_bill_et_head);
        addPersonalBillIvHead = findViewById(R.id.add_personal_bill_iv_head);
        addPersonalBillTvUpdate = findViewById(R.id.add_personal_bill_tv_update);
        addPersonalBillTvDelete = findViewById(R.id.add_personal_bill_tv_delete);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        addPersonalBillIvHead.setOnClickListener(this);
        addPersonalBillTvUpdate.setOnClickListener(this);
        addPersonalBillTvDelete.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if (!mBillHead.equals("") && !TextUtils.isEmpty(mBillHead)) {
            addPersonalBillEtHead.setText(mBillHead);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add_personal_bill_iv_head) {
            addPersonalBillEtHead.setText("");
        } else if (id == R.id.add_personal_bill_tv_update) {

        } else if (id == R.id.add_personal_bill_tv_delete) {

        }
    }
}