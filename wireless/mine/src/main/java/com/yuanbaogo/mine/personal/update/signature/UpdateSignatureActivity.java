package com.yuanbaogo.mine.personal.update.signature;

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

@Route(path = YBRouter.UPDATE_SIGNATURE_ACTIVITY)
public class UpdateSignatureActivity extends MvpBaseActivityImpl<EditMineContract.View, EditMinePresenter>
        implements View.OnClickListener, TextWatcher, EditMineContract.View {

    private EditText mUpdateSignEtSign;
    private TextView mUpdateSignTvLength;
    private TextView mUpdateSignTvSave;
    private String mSignature;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_update_signature;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mUpdateSignEtSign = findViewById(R.id.update_sign_et_sign);
        mUpdateSignTvLength = findViewById(R.id.update_sign_tv_length);
        mUpdateSignTvSave = findViewById(R.id.update_sign_tv_save);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mUpdateSignEtSign.addTextChangedListener(this);
        mUpdateSignTvSave.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mSignature = getIntent().getStringExtra(YBRouter.EditMineActivityParams.signature);
        mUpdateSignEtSign.setText(mSignature);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mSignature = mUpdateSignEtSign.getText().toString().trim();
        mUpdateSignTvLength.setText(mSignature.length() + "/30");
        if (mSignature.length() >= 30) {
            ToastView.showToast("已达到最大字数限制");
        }
        mUpdateSignTvSave.setEnabled(!TextUtils.isEmpty(mSignature));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.update_sign_tv_save) {
            if (TextUtils.isEmpty(mSignature)) {
                ToastView.showToast("个性签名不能为空");
                return;
            }
            mPresenter.getPersonalSubmit(new PersonalSubmitBean().setSignature(mSignature));
        }
    }

    @Override
    public void setPersonalSubmit(String bean) {
        RouterHelper.toUpdateSignatureResult(this, 400, mSignature);
        finish();
    }

    @Override
    public void initPersonalSubmit() {

    }

    @Override
    public void setUpdatePortrait(String url) {

    }
}