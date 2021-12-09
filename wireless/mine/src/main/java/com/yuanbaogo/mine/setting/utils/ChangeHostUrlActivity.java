package com.yuanbaogo.mine.setting.utils;

import static com.yuanbaogo.mine.order.utils.Configuration.YB_CODE_DEFAULT_TYPE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.datacenter.local.SharePreferenceConfigImpl;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.toast.ToastView;

public class ChangeHostUrlActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mChangeBtnYbCodeFixed;
    private Button mChangeBtnYbCode;
    private SharePreferenceConfigImpl instance;
    private EditText mChangeEtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_change_host_url);
        initView();
    }

    private void initView() {
        mChangeEtCode = findViewById(R.id.change_et_code);
        mChangeBtnYbCodeFixed = findViewById(R.id.change_btn_yb_code_fixed);
        mChangeBtnYbCode = findViewById(R.id.change_btn_yb_code);
        mChangeBtnYbCodeFixed.setOnClickListener(this);
        mChangeBtnYbCode.setOnClickListener(this);
        instance = SharePreferenceConfigImpl.getInstance(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.change_btn_yb_code_fixed) {
            String code = mChangeEtCode.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                ToastView.showToast("请输入ybCode");
                return;
            }
            if (code.length() < 5) {
                ToastView.showToast("请输入正确格式的ybCode");
                return;
            }
            instance.setString(YB_CODE_DEFAULT_TYPE, code);
            ToastView.showToast("已将ybCode设置为:" + code);
            finish();
        } else if (id == R.id.change_btn_yb_code) {
            instance.setString(YB_CODE_DEFAULT_TYPE, UserStore.getYbCode());
            ToastView.showToast("已将ybCode恢复为默认");
            finish();
        }
    }
}