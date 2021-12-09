package com.yuanbaogo.mine.modifyaccount.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.libbase.view.BaseActivity;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.toast.ToastView;

/**
 * @Description: 修改账号
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/12/21 2:38 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.MODIFY_ACCOUNT_ACTIVITY)
public class ModifyAccountActivity extends BaseActivity implements View.OnClickListener {

    HeadView mineModifyAccountHeadView;

    RelativeLayout mineModifyAccountRlPhone;

    TextView mineModifyAccountTvPhone;

    RelativeLayout mineModifyAccountRlWX;

    TextView mineModifyAccountTvWX;

    RelativeLayout mineModifyAccountRlLogout;

    Button mineModifyAccountButLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_modify_account);
        findbyIdView();
        text();
        onClick();
    }

    private void findbyIdView() {
        mineModifyAccountHeadView = findViewById(R.id.mine_modify_account_head_view);
        mineModifyAccountRlPhone = findViewById(R.id.mine_modify_account_rl_phone);
        mineModifyAccountTvPhone = findViewById(R.id.mine_modify_account_tv_phone);
        mineModifyAccountRlWX = findViewById(R.id.mine_modify_account_rl_wx);
        mineModifyAccountTvWX = findViewById(R.id.mine_modify_account_tv_wx);
        mineModifyAccountRlLogout = findViewById(R.id.mine_modify_account_rl_logout);
        mineModifyAccountButLogout = findViewById(R.id.mine_modify_account_but_logout);

    }

    protected void text() {
        initHeadView();
        initSP();
    }


    protected void onClick() {
        mineModifyAccountRlPhone.setOnClickListener(this);
        mineModifyAccountRlWX.setOnClickListener(this);
        mineModifyAccountRlLogout.setOnClickListener(this);
        mineModifyAccountButLogout.setOnClickListener(this);
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("修改账号")
                .setImgRight(false);
        mineModifyAccountHeadView.setHead(headBean);
    }

//    UserInfo userInfoBean;

    private void initSP() {
//        if (SPUtils.getInstance(ModifyAccountActivity.this).getUserSPUtils()
//                .getBeanFromSp("userData", "userInfoBean") != null) {
//            userInfoBean = SPUtils.getInstance(ModifyAccountActivity.this).getUserSPUtils()
//                    .getBeanFromSp("userData", "userInfoBean");
//            mineModifyAccountTvPhone.setText(userInfoBean.getPhone());
//        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mine_modify_account_rl_phone) {//更换手机号
            ToastView.showToast(ModifyAccountActivity.this, 0, "暂不支持换绑手机号");
        } else if (id == R.id.mine_modify_account_rl_wx) {//绑定微信
            ToastView.showToast(ModifyAccountActivity.this, 0, getResources().getString(R.string.toast_view_in_development_msg));
        } else if (id == R.id.mine_modify_account_rl_logout) {//注销
            RouterHelper.toCancellation();
        } else if (id == R.id.mine_modify_account_but_logout) {//退出登录
//            SPUtils.getInstance(ModifyAccountActivity.this).put(SPUtils.ISLGOIN, false);
//            SPUtils.getInstance(ModifyAccountActivity.this).getSearchSPUtils().cleanHistory();
//            SPUtils.getInstance(ModifyAccountActivity.this).getUserSPUtils().cleanUser();
            RouterHelper.toLogin();
            finish();
        }
    }

}