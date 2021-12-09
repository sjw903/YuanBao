package com.yuanbaogo.homevideo.live.push.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.zui.dialog.PopupWindowWithMask;

public class AnchorSettingPop extends PopupWindowWithMask implements PopupWindow.OnDismissListener, View.OnClickListener {

    private View mContentView;
    private OnClickListener mListener;
    Activity mActivity;
    LinearLayout ll_beautify;
    LinearLayout ll_flash_lamp;
    LinearLayout ll_camera;


    public interface OnClickListener {
        void onBeautifyClick();

        void onCameraClick();

        void onLampClick();
    }

    public void setOnPayClickListener(OnClickListener listener) {
        this.mListener = listener;
    }

    public AnchorSettingPop(Activity activity) {
        super(activity, true);
        this.mActivity = activity;
        initView();
    }

    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.video_pop_anchor_setting, null, false);
        return mContentView;
    }

    @Override
    protected int initHeight() {
//        int screenHeight = ScreenUtils.getScreenHeight(context);
//        return (int) (screenHeight * 0.4);
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int initWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    private void initView() {
        TextView back = mContentView.findViewById(R.id.tv_back);
        ll_beautify = mContentView.findViewById(R.id.ll_beautify);
        ll_flash_lamp = mContentView.findViewById(R.id.ll_flash_lamp);
        ll_camera = mContentView.findViewById(R.id.ll_camera);
        back.setOnClickListener(this);
        ll_flash_lamp.setOnClickListener(this);
        ll_beautify.setOnClickListener(this);
        ll_camera.setOnClickListener(this);
    }

    @Override
    public void onDismiss() {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_back) {
            dismiss();
        } else if (id == R.id.ll_beautify) {
            if (mListener != null) {
                dismiss();
                mListener.onBeautifyClick();
            }
        } else if (id == R.id.ll_flash_lamp) {
            if (mListener != null) {
                mListener.onLampClick();
            }
        } else if (id == R.id.ll_camera) {
            if (mListener != null) {
                mListener.onCameraClick();
            }
        }
    }

}

