package com.yuanbaogo.zui.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanbaogo.zui.R;


public class StatusShipPop extends PopupWindowWithMask {

    private View mContentView;
    private ImageView iv_ship_back;
    private ImageView iv_ship_pic;
    private TextView tv_ship_txt;
    private Drawable drawable;
    private String statusTxt;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClickBack();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public StatusShipPop(Context context, Drawable drawable, String statusTxt) {
        super(context, true);
        this.statusTxt = statusTxt;
        this.drawable = drawable;
        initView();
    }

    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.pop_status_ship, null, false);
        return mContentView;
    }

    private void initView() {
        iv_ship_back = (ImageView) mContentView.findViewById(R.id.iv_ship_back);
        iv_ship_pic = (ImageView) mContentView.findViewById(R.id.iv_ship_pic);
        tv_ship_txt = (TextView) mContentView.findViewById(R.id.tv_ship_txt);
        iv_ship_pic.setImageDrawable(drawable);
        tv_ship_txt.setText(statusTxt);
        iv_ship_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (onItemClickListener != null){
                    if (tv_ship_txt.getText().equals("认证成功")) {
                        onItemClickListener.onClickBack();
                    }
                }
            }
        });
    }

    @Override
    protected int initHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int initWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }
}
