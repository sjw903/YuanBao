package com.yuanbaogo.zui.dialog;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;

public class NormalDialogFragment extends DialogsFragment implements View.OnClickListener {

    private TextView notDialogCancel;
    private TextView notDialogOpen;
    private OnNormalListener onNormalListener;
    private TextView mNotDialogTitle;
    private TextView mNotDialogContent;

    private String mTitle, mSubtitle, mLeftTxt, mRightTxt;

    public NormalDialogFragment(String title, String subtitle, String leftTxt, String rightTxt) {
        mTitle = title;
        mSubtitle = subtitle;
        mLeftTxt = leftTxt;
        mRightTxt = rightTxt;
    }

    public NormalDialogFragment(int title, int subtitle, int leftTxt, int rightTxt) {
        mTitle = getString(title);
        mSubtitle = getString(subtitle);
        mLeftTxt = getString(leftTxt);
        mRightTxt = getString(rightTxt);
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_normal_dialog;
    }

    @Override
    protected void intViews(View view) {
        notDialogCancel = view.findViewById(R.id.not_dialog_cancel);
        notDialogOpen = view.findViewById(R.id.not_dialog_open);
        mNotDialogTitle = view.findViewById(R.id.not_dialog_title);
        mNotDialogContent = view.findViewById(R.id.not_dialog_content);
    }

    @Override
    protected void setTexts() {
        mNotDialogTitle.setText(mTitle);
        mNotDialogContent.setText(mSubtitle);
        notDialogCancel.setText(mLeftTxt);
        notDialogOpen.setText(mRightTxt);
    }

    @Override
    protected void setOnClicks() {
        notDialogCancel.setOnClickListener(this);
        notDialogOpen.setOnClickListener(this);
    }

    public void setOnNormalListener(OnNormalListener onToLoginListener) {
        this.onNormalListener = onToLoginListener;
    }

    @Override
    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return (int) (dm.widthPixels * 0.8);
    }


    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.not_dialog_cancel) {
            dismiss();
        } else if (id == R.id.not_dialog_open) {
            dismiss();
            if (onNormalListener != null) {
                onNormalListener.onNormalSure();
            }
        }
    }

    public interface OnNormalListener {
        void onNormalSure();
    }

}
