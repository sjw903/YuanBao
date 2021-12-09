package com.yuanbaogo.zui.dialog;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;

public class SureDialogFragment extends DialogsFragment implements View.OnClickListener {

    private OnSureListener mOnSureListener;

    private final String mTitle;
    private final String mBtnName;
    private TextView mSureTvContent;
    private TextView mSureTvSure;

    public SureDialogFragment(String title, String btnName) {
        mTitle = title;
        mBtnName = btnName;
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_sure_dialog;
    }

    @Override
    protected void intViews(View view) {
        mSureTvContent = view.findViewById(R.id.sure_tv_content);
        mSureTvSure = view.findViewById(R.id.sure_tv_sure);
    }

    @Override
    protected void setTexts() {
        mSureTvContent.setText(mTitle);
        mSureTvSure.setText(mBtnName);
    }

    @Override
    protected void setOnClicks() {
        mSureTvSure.setOnClickListener(this);
    }

    public void setOnSureListener(OnSureListener mOnSureListener) {
        this.mOnSureListener = mOnSureListener;
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
        if (id == R.id.sure_tv_sure) {
            dismiss();
            if (mOnSureListener != null) {
                mOnSureListener.onSure();
            }
        }
    }

    public interface OnSureListener {
        void onSure();
    }

}
