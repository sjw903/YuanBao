package com.yuanbaogo.login.login.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.luck.picture.lib.tools.SPUtils;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.datacenter.remote.netmonitor.NetWorkMonitor;
import com.yuanbaogo.datacenter.remote.netmonitor.NetWorkMonitorManager;
import com.yuanbaogo.datacenter.remote.netmonitor.NetWorkState;
import com.yuanbaogo.datacenter.url.http.RootUrl;
import com.yuanbaogo.datacenter.utils.NetUtils;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.libbase.baseutil.ValidatePhoneUtil;
import com.yuanbaogo.login.R;
import com.yuanbaogo.login.login.contract.LoginContract;
import com.yuanbaogo.login.login.presenter.LoginPresenter;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.toast.ToastView;


/**
 * @Description: 常规登录
 * @Author: yf
 * @Date: 7/16/21 11:50 AM
 */
@Route(path = YBRouter.LOGIN_ACTIVITY)
public class LoginActivity extends MvpBaseActivityImpl<LoginContract.View, LoginPresenter> implements LoginContract.View, View.OnClickListener {

    private HeadView mLoginHeadView;
    private EditText mLoginPhoneNum;
    private EditText mLoginEditVerificationCode;
    private EditText mLoginEditInviteCode;
    private Button mLoginButVerification;
    private Button mLoginBut;

    private CheckBox mLoginCbPrivacy;
    private TextView mLoginTvService;
    private TextView mLoginTvPrivacy;

    private TextView mLoginTimerVerification;
    private ImageView mLoginImgWechat;
    private RelativeLayout mInviteView;

    boolean mIsRead = false;//是否点选协议

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_login_service) {//用户协议
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            RouterHelper.toWeb(WebUrls.proUser);
        } else if (id == R.id.tv_login_privacy) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            RouterHelper.toWeb(WebUrls.proPrivacy);
        } else if (id == R.id.cb_login_privacy) {
            mPresenter.changeCanLogin(mLoginPhoneNum.getText().toString(),
                    mLoginEditVerificationCode.getText().toString(),
                    mLoginCbPrivacy.isChecked());
        } else if (id == R.id.btn_get_verification) {
            if (ClickUtils.isFastClick()) {
                return;
            }
            ValidatePhoneUtil.hideShowKeyboard(this, mLoginButVerification);
            if (!NetUtils.isConnected(this)) {
                ToastView.showToast("网络异常,请检查网络后重试");
                return;
            }
            mPresenter.verification();
        } else if (id == R.id.btn_login_but) {
            if (ClickUtils.isFastClick()) {
                return;
            }
            if (!NetUtils.isConnected(this)) {
                ToastView.showToast("网络异常,请检查网络后重试");
                return;
            }
            mPresenter.login(mLoginPhoneNum.getText().toString(),
                    mLoginEditVerificationCode.getText().toString(), mLoginEditInviteCode.getText().toString());
        } else if (id == R.id.iv_login_img_wechat) {
//            RouterHelper.toChangePhone();
//            new PayCenter()
//                    .startPay(this,
//                            PayCenter.BUYTYPE_GOODSBUY,
//                            UserStore.getYbCode(),
//                            "1",
//                            "1000000097",// 假数据提测时候改过来 //TODO 海静
//                            new PayCenter.OnPayInfoListener() {
//                                @Override
//                                public void OnPayReslutListener(String orderid) {
//                                    //TODO 海静
//                                    if(TextUtils.isEmpty(orderid)){
//                                        //如果是空的，不展示返回结果。在微信支付的广播监听里面展示返回结果页面
//                                    }else{
//                                        //不为空，展示返回结果
//                                    }
//                                }
//                            });

        }
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    Button butEnvironment;
    EditText et_env;

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mLoginHeadView = findViewById(R.id.zi_hv_login_head);
        mLoginPhoneNum = findViewById(R.id.et_login_phone_num);
        mLoginEditVerificationCode = findViewById(R.id.et_login_verification_code);
        mLoginEditInviteCode = findViewById(R.id.et_login_invite_code);
        mLoginButVerification = findViewById(R.id.btn_get_verification);
        mLoginTimerVerification = findViewById(R.id.tv_timer_verification);
        mLoginBut = findViewById(R.id.btn_login_but);

        mLoginCbPrivacy = findViewById(R.id.cb_login_privacy);
        mLoginTvService = findViewById(R.id.tv_login_service);
        mLoginTvPrivacy = findViewById(R.id.tv_login_privacy);
        mLoginImgWechat = findViewById(R.id.iv_login_img_wechat);
        mInviteView = findViewById(R.id.rl_invite);

        //TODO HG 正式上线需要删除
        butEnvironment = findViewById(R.id.but_environment);
        et_env = findViewById(R.id.et_env);
        String web_root_pay = SPUtils.getInstance().getString("WEB_ROOT_PAY");
        et_env.setText("".equals(web_root_pay) ? RootUrl.WEB_ROOT : web_root_pay);
        butEnvironment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RootUrl.WEB_ROOT = et_env.getText().toString();
                SPUtils.getInstance().put("WEB_ROOT_PAY", RootUrl.WEB_ROOT);
                ToastView.showToast("应用成功");
            }
        });
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mLoginTvService.setOnClickListener(this);
        mLoginTvPrivacy.setOnClickListener(this);
        mLoginCbPrivacy.setOnClickListener(this);
        mLoginButVerification.setOnClickListener(this);
        mLoginBut.setOnClickListener(this);

        mLoginImgWechat.setOnClickListener(this);

        mLoginPhoneNum.addTextChangedListener(mPresenter.getTextWatcher(1));
        mLoginEditVerificationCode.addTextChangedListener(mPresenter.getTextWatcher(0));
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mPresenter.setJiYan();
        initHead();
        mPresenter.changeCanGetCode(mLoginPhoneNum.getText().toString());
        mPresenter.changeCanLogin(mLoginPhoneNum.getText().toString(), mLoginEditVerificationCode.getText().toString(),
                mLoginCbPrivacy.isChecked());
        mLoginTimerVerification.setVisibility(View.GONE);

    }

    private void initHead() {
        HeadBean headBean = new HeadBean()
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setImgRight(false);
        mLoginHeadView.setHead(headBean);
    }

    /**
     * 改变获取验证码按钮颜色
     *
     * @param isCan
     */
    @Override
    public void changeCodeColor(boolean isCan) {
        if (isCan) {
            mLoginButVerification.setTextColor(getResources().getColor(R.color.colorEA5504, null));
            mLoginButVerification.setBackgroundResource(R.drawable.shape_gradient_bg_but_line_orrange);
        } else {
            mLoginButVerification.setTextColor(getResources().getColor(R.color.colorAAAAAA, null));
            mLoginButVerification.setBackgroundResource(R.drawable.shape_gradient_bg_but_line_gray);
        }
    }

    @Override
    public void changeInviteView(boolean isCan) {
        if (isCan) {
            mInviteView.setVisibility(View.VISIBLE);
        } else {
            mInviteView.setVisibility(View.GONE);
        }
    }

    @Override
    public void close() {
        finish();
    }

    /**
     * 改变登录按钮颜色
     *
     * @param isCan
     */
    @Override
    public void changeLoginColor(boolean isCan) {
        if (isCan) {
            mLoginBut.setBackground(getResources().getDrawable(R.drawable.selector_bg_but_login));
        } else {
            mLoginBut.setBackground(getResources().getDrawable(R.drawable.shape_gradient_bg_but_alpha_50));
        }
    }

    @Override
    public String getLoginPhoneNum() {
        return mLoginPhoneNum.getText().toString();
    }

    @Override
    public String getLoginEditVerificationCode() {
        return mLoginEditVerificationCode.getText().toString();
    }

    @Override
    public String getLoginEditInviteCode() {
        return mLoginEditInviteCode.getText().toString();
    }

    @Override
    public boolean getPrivacyChecked() {
        return mLoginCbPrivacy.isChecked();
    }

    @Override
    public void changeVerificationTimer(int time) {
        mLoginTimerVerification.setVisibility(View.VISIBLE);
        mLoginButVerification.setVisibility(View.GONE);
        mLoginTimerVerification.setText("已发送(" + time + "s)");
    }

    @Override
    public void changeVerificationButton() {
        mLoginTimerVerification.setVisibility(View.GONE);
        mLoginButVerification.setVisibility(View.VISIBLE);
        mLoginButVerification.setText("重新获取");
    }

    @Override
    public void clearPhoneNum() {
        mLoginPhoneNum.setText("");
    }


    @Override
    public void onStart() {
        super.onStart();
        NetWorkMonitorManager.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NetWorkMonitorManager.getInstance().unregister(this);
    }

    //不加注解默认监听所有的状态，方法名随意，只需要参数是一个NetWorkState即可
    @NetWorkMonitor(monitorFilter = {NetWorkState.NONE})//只接受网络状态变为GPRS类型的消息
    public void onNetWorkStateChange(NetWorkState netWorkState) {
       mPresenter.callNoNet();
    }

}