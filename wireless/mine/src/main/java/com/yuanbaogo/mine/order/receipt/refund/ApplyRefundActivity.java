package com.yuanbaogo.mine.order.receipt.refund;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.receipt.detail.ReceiptDetailActivity;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.datacenter.local.user.UserInfo;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.libbase.baseutil.ValidatePhoneUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.pay.OrderCancelBottomDialog;
import com.yuanbaogo.zui.dialog.SureDialogFragment;

@Route(path = YBRouter.APPLY_REFUND_ACTIVITY)
public class ApplyRefundActivity extends MvpBaseActivityImpl<ApplyRefundContract.View, ApplyRefundDetailPresenter> implements
        ApplyRefundContract.View, View.OnClickListener, OrderCancelBottomDialog.OnNextRefund, SureDialogFragment.OnSureListener {

    private LinearLayout mApplyLlReason;
    private TextView mApplyTvReason;
    private EditText mApplyEtName;
    private EditText mApplyEtPhone;
    private TextView mApplyTvSubmit;
    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";

    /**
     * 1 待收货列表  2 订单状态详情页
     */
    @Autowired(name = YBRouter.OrderDetailsActivityParams.type)
    int type;

    private OrderCancelBottomDialog mOrderCancelBottomDialog;
    private SureDialogFragment mSureDialogFragment;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_apply_refund;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mApplyLlReason = findViewById(R.id.apply_ll_reason);
        mApplyTvReason = findViewById(R.id.apply_tv_reason);
        mApplyEtName = findViewById(R.id.apply_et_name);
        mApplyEtPhone = findViewById(R.id.apply_et_phone);
        mApplyTvSubmit = findViewById(R.id.apply_tv_submit);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mApplyLlReason.setOnClickListener(this);
        mApplyTvSubmit.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        UserInfo user = UserStore.getUser();
        if (user == null) return;
        mPresenter.getOrderInformation(totalOrderId);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.apply_ll_reason) {
            // 退款原因
            mOrderCancelBottomDialog = new OrderCancelBottomDialog(this, "退款原因");
            mOrderCancelBottomDialog.setOnNextRefundListener(this);
            mOrderCancelBottomDialog.show();
        } else if (id == R.id.apply_tv_submit) {
            // 提交
            String name = mApplyEtName.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                ToastUtil.showToast("请输入退款联系人");
                return;
            }
            String phone = mApplyEtPhone.getText().toString().trim();
            if (!ValidatePhoneUtil.validateMobileNumber(phone)) {
                ToastUtil.showToast(R.string.apply_toast);
                return;
            }
            String reason = mApplyTvReason.getText().toString().trim();
            if (reason.equals("请选择退款原因")) {
                ToastUtil.showToast("请选择退款原因");
                return;
            }
            mPresenter.applyRefund(totalOrderId, reason, name, phone);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mOrderCancelBottomDialog != null) {
            mOrderCancelBottomDialog.cancel();
        }
        if (mSureDialogFragment != null) {
            mSureDialogFragment.dismiss();
        }
    }

    @Override
    public void onNextRefund(String reason, int position) {
        mApplyTvReason.setText(reason);
    }

    @Override
    public void applyResult(boolean result) {
        if (result) {
            mSureDialogFragment = new SureDialogFragment("退款申请已提交成功！", "查看订单");
            mSureDialogFragment.setOnSureListener(this);
            mSureDialogFragment.show(getSupportFragmentManager(), "applyRefund");
        } else {
            ToastUtil.showToast(R.string.apply_submit_fail);
        }
    }

    @Override
    public void setOrderInformation(GoodsDetail bean) {
        mApplyEtName.setText(bean.getOrderInfoAddressVO().getName());
        mApplyEtPhone.setText(bean.getOrderInfoAddressVO().getMobile());
    }

    @Override
    public void onSure() {
//        RouterHelper.toPendingReviewAfterDetail(totalOrderId);
        if (type == 2) {
            ReceiptDetailActivity.receiptDetailActivity.finish();
        }
        finish();
    }

}