package com.yuanbaogo.zui.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanbaogo.zui.R;


public class StatusShipButtonPop extends PopupWindowWithMask {

    private View mContentView;
    private TextView tv_ship_btn;
    private ImageView iv_ship_pic;
    private TextView tv_ship_txt;
    private Drawable drawable;
    private String statusTxt;
    private String btnTxt;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClickBack();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public StatusShipButtonPop(Context context, Drawable drawable, String statusTxt, String btnTxt) {
        super(context, true);
        this.statusTxt = statusTxt;
        this.btnTxt = btnTxt;
        this.drawable = drawable;
        initView();
    }

    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.pop_status_btn_ship, null, false);
        return mContentView;
    }

    private void initView() {
        tv_ship_btn = (TextView) mContentView.findViewById(R.id.tv_ship_btn);
        iv_ship_pic = (ImageView) mContentView.findViewById(R.id.iv_ship_pic);
        tv_ship_txt = (TextView) mContentView.findViewById(R.id.tv_ship_txt);
        if(drawable != null){
            iv_ship_pic.setImageDrawable(drawable);
        }
        if(!TextUtils.isEmpty(btnTxt)){
            tv_ship_btn.setText(btnTxt);
        }
        tv_ship_txt.setText(statusTxt);
        tv_ship_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (onItemClickListener != null) {
                    onItemClickListener.onClickBack();
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
