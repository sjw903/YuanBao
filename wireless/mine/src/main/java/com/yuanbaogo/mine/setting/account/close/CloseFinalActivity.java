package com.yuanbaogo.mine.setting.account.close;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;
import com.yuanbaogo.zui.toast.ToastView;

@Route(path = YBRouter.CLOSE_FINAL_ACTIVITY)
public class CloseFinalActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> implements View.OnClickListener {

    private RelativeLayout mCloseRlReason;
    private TextView mCloseTvReason;
    private TextView mCloseTvFinal;
    private BottomSheetDialog mSheetDialog;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_close_final;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mCloseRlReason = findViewById(R.id.close_rl_reason);
        mCloseTvReason = findViewById(R.id.close_tv_reason);
        mCloseTvFinal = findViewById(R.id.close_tv_final);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mCloseRlReason.setOnClickListener(this);
        mCloseTvFinal.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initBtnStatus();
    }

    private void initBtnStatus() {
        if (mSheetDialog != null) {
            mSheetDialog.dismiss();
        }
        if (!mCloseTvReason.getText().toString().equals(getString(R.string.close_select_reason))) {
            mCloseTvFinal.setTextColor(getColor(R.color.wing_selected_btn_text));
            mCloseTvFinal.setBackgroundResource(R.drawable.icon_close_next_btn_bg);
        } else {
            mCloseTvFinal.setTextColor(getColor(R.color.close_btn));
            mCloseTvFinal.setBackgroundResource(R.drawable.icon_close_final_bg);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.close_rl_reason) {
            showReasonDialog();
        } else if (id == R.id.close_dialog_tv_reason1) {
            mCloseTvReason.setText(R.string.close_dialog_reason1);
            initBtnStatus();
        } else if (id == R.id.close_dialog_tv_reason2) {
            mCloseTvReason.setText(R.string.close_dialog_reason2);
            initBtnStatus();
        } else if (id == R.id.close_dialog_tv_reason3) {
            mCloseTvReason.setText(R.string.close_dialog_reason3);
            initBtnStatus();
        } else if (id == R.id.close_dialog_tv_cancel) {
            mSheetDialog.dismiss();
        } else if (id == R.id.close_tv_final) {
            if (!mCloseTvReason.getText().toString().equals(getString(R.string.close_select_reason))) {
                ToastView.showToast(R.string.close_toast_tip);
                new Handler().postDelayed(() -> {
                    ToastView.showToast(R.string.close_toast_fail);
                    finish();
                }, 10000L);
            }
        }
    }

    private void showReasonDialog() {
        mSheetDialog = new BottomSheetDialog(this);
        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.mine_dialog_close_reason, null);
        mSheetDialog.setContentView(dialogView);
        TextView closeDialogTvReason1 = dialogView.findViewById(R.id.close_dialog_tv_reason1);
        TextView closeDialogTvReason2 = dialogView.findViewById(R.id.close_dialog_tv_reason2);
        TextView closeDialogTvReason3 = dialogView.findViewById(R.id.close_dialog_tv_reason3);
        TextView closeDialogTvCancel = dialogView.findViewById(R.id.close_dialog_tv_cancel);
        closeDialogTvReason1.setOnClickListener(this);
        closeDialogTvReason2.setOnClickListener(this);
        closeDialogTvReason3.setOnClickListener(this);
        closeDialogTvCancel.setOnClickListener(this);
        mSheetDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSheetDialog != null) {
            mSheetDialog.cancel();
        }
    }
}