package com.yuanbaogo.mine.integral.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.finance.pay.presenter.PayCenter;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libpay.pay.Constant;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.integral.contract.IntegralRechargeContract;
import com.yuanbaogo.mine.integral.presenter.IntegralRechargePresenter;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 积分充值
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 4:09 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.INTEGRAL_RECHARGE_ACTIVITY)
public class IntegralRechargeActivity
        extends MvpBaseActivityImpl<IntegralRechargeContract.View, IntegralRechargePresenter>
        implements View.OnClickListener {

    /**
     * 自定义HeadView
     */
    HeadView mineAssetsIntegralRechargeHeadView;

    /**
     * 金额输入框
     */
    EditText mineAssetsIntegralRechargeEdit;

    /**
     * 清除输入框内容
     */
    ImageView mineAssetsIntegralRechargeImgClear;

    /**
     * 支付
     */
    Button mineAssetsIntegralRechargeButPaly;

    /**
     * 订单ID
     */
    String mOrderid;

    /**
     * 正则  判断输入框内容
     */
    Pattern pattern = Pattern.compile("([0-9]\\d{0,4})?");

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_integral_recharge;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mineAssetsIntegralRechargeHeadView = findViewById(R.id.mine_assets_integral_recharge_head_view);
        mineAssetsIntegralRechargeEdit = findViewById(R.id.mine_assets_integral_recharge_edit);
        mineAssetsIntegralRechargeImgClear = findViewById(R.id.mine_assets_integral_recharge_img_clear);
        mineAssetsIntegralRechargeButPaly = findViewById(R.id.mine_assets_integral_recharge_but_paly);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mineAssetsIntegralRechargeEdit.addTextChangedListener(textWatcher);
        mineAssetsIntegralRechargeImgClear.setOnClickListener(this);
        mineAssetsIntegralRechargeButPaly.setOnClickListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.WX_MSG_OK);
        registerReceiver(mBroadcastReceiver, intentFilter);

        SpannableString ss = new SpannableString("请输入充值金额");//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mineAssetsIntegralRechargeEdit.setHint(new SpannedString(ss));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mine_assets_integral_recharge_img_clear) {
            if (!TextUtils.isEmpty(mineAssetsIntegralRechargeEdit.getText().toString())) {
                mineAssetsIntegralRechargeEdit.setText("");
            }
        } else if (id == R.id.mine_assets_integral_recharge_but_paly) {
            String integral = mineAssetsIntegralRechargeEdit.getText().toString();
            if (!TextUtils.isEmpty(integral)) {
                if (integral.startsWith("0")) {//如果第一位是0  点击不能支付
                    ToastView.showToast(IntegralRechargeActivity.this, "请输入支付金额");
                    return;
                }
                String price = integral;
                if (TextUtils.isEmpty(UserStore.getToken())) {
                    RouterHelper.toLogin();
                    return;
                } else {
                    new PayCenter()
                            .startPay(this,
                                    PayCenter.BUYTYPE_COINPOINTBUY,
                                    price,
                                    "0",// 积分充值订单号固定是0
                                    new PayCenter.OnPayInfoListener() {
                                        @Override
                                        public void OnPayReslutListener(String orderid) {
                                            mOrderid = orderid;
                                        }
                                    });
                }
                return;
            }
            ToastView.showToast(IntegralRechargeActivity.this, "请输入支付金额");
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("积分充值")
                .setImgRight(false);
        mineAssetsIntegralRechargeHeadView.setHead(headBean);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String price = mineAssetsIntegralRechargeEdit.getText().toString();
            Matcher matcher = pattern.matcher(price);
            if (matcher.matches()) {
                changeLoginColor(mineAssetsIntegralRechargeEdit.getText().toString() != null);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Matcher matcher = pattern.matcher(editable.toString());
            if (!matcher.matches()) {//根据正则判断
                editable.delete(editable.length() - 1, editable.length());
            } else {//第一位是0  第二位不是小数点  就不能输入
                if (editable.toString().length() > 1
                        && String.valueOf(editable.toString().charAt(0)).equals("0")
                        && !String.valueOf(editable.toString().charAt(1)).equals(".")) {
                    editable.delete(1, 2);
                }
            }
        }
    };

    /**
     * 改变登录按钮颜色
     *
     * @param isCan
     */
    public void changeLoginColor(boolean isCan) {
        if (isCan) {
            mineAssetsIntegralRechargeButPaly.setBackground(
                    getResources().getDrawable(R.drawable.selector_bg_but_login));
        } else {
            mineAssetsIntegralRechargeButPaly.setBackground(
                    getResources().getDrawable(R.drawable.shape_gradient_bg_but_alpha_50));
        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.WX_MSG_OK)) {
//                mPresenter.getCoinpointResult(IntegralRechargeActivity.this, mOrderid);
                Intent mIntent = new Intent(IntegralRechargeActivity.this, PayStatusActivity.class);
                startActivity(mIntent);
                finish();
            }
        }
    };

    //拦截返回键 弹出提示框  TODO 别海静

    //            CommonRemindPop pop = new CommonRemindPop(mActivity, "确认离开吗？",
    //            "继续充值", "确认离开", new CommonRemindPop.OnClickListener() {
//                @Override
//                public void onLeftClick() {
//
//                }
//
//                @Override
//                public void onRightClick() {
//                    dismiss();
//                }
//            });
//            pop.showAtLocation(mActivity.getWindow().getDecorView(),
//            Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

}