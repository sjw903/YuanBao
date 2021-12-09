package com.yuanbaogo.mine.personal.update.name;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.personal.EditMineContract;
import com.yuanbaogo.mine.personal.EditMinePresenter;
import com.yuanbaogo.mine.personal.model.PersonalSubmitBean;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.toast.ToastView;

@Route(path = YBRouter.UPDATE_NAME_ACTIVITY)
public class UpdateNameActivity extends MvpBaseActivityImpl<EditMineContract.View, EditMinePresenter>
        implements View.OnClickListener, TextWatcher, EditMineContract.View {

    private EditText mUpdateNameEtName;
    private TextView mUpdateNameTvSave;
    private TextView mUpdateNameTvLength;
    private String mName;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_update_name;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mUpdateNameEtName = findViewById(R.id.update_name_et_name);
        mUpdateNameTvLength = findViewById(R.id.update_name_tv_length);
        mUpdateNameTvSave = findViewById(R.id.update_name_tv_save);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mUpdateNameEtName.addTextChangedListener(this);
        mUpdateNameTvSave.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mName = getIntent().getStringExtra(YBRouter.EditMineActivityParams.nickName);
        mUpdateNameEtName.setText(mName);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.update_name_tv_save) {
            if (TextUtils.isEmpty(mName)) {
                ToastView.showToast("昵称不能为空");
                return;
            }
            mPresenter.getPersonalSubmit(new PersonalSubmitBean().setNickName(mName));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void afterTextChanged(Editable s) {
        mName = mUpdateNameEtName.getText().toString().trim();
        mUpdateNameTvLength.setText(mName.length() + "/12");
        if (mName.length() >= 12) {
            ToastView.showToast("已达到最大字数限制");
        }
        mUpdateNameTvSave.setEnabled(!TextUtils.isEmpty(mName));
    }

    @Override
    public void setPersonalSubmit(String bean) {
        ToastView.showToast("修改昵称成功");
        RouterHelper.toUpdateNameResult(this, 300, mName);
        finish();
    }

    @Override
    public void initPersonalSubmit() {

    }

    @Override
    public void setUpdatePortrait(String url) {

    }
}