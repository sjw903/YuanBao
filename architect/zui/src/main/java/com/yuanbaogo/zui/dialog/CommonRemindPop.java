package com.yuanbaogo.zui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yuanbaogo.zui.R;


public class CommonRemindPop extends PopupWindowWithMask {

    private View mContentView;

    private TextView tv_ship_txt;
    private TextView tv_ship_right;
    private TextView tv_ship_left;
    private String titleTxt;
    private String rightTxt;
    private String leftTxt;

    private OnClickListener mListener;

    public interface OnClickListener {
        void onLeftClick();

        void onRightClick();
    }

    public CommonRemindPop(Context context, String titleTxt, String leftTxt, String rightTxt, OnClickListener listener) {
        super(context, false);
        this.titleTxt = titleTxt;
        this.rightTxt = rightTxt;
        this.leftTxt = leftTxt;
        this.mListener = listener;

        initView();
    }


    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.pop_common_remind, null, false);
        return mContentView;
    }

    private void initView() {

        tv_ship_txt = (TextView) mContentView.findViewById(R.id.tv_ship_txt);
        tv_ship_left = (TextView) mContentView.findViewById(R.id.tv_ship_left);
        tv_ship_right = (TextView) mContentView.findViewById(R.id.tv_ship_right);

        tv_ship_txt.setText(titleTxt);
        tv_ship_right.setText(rightTxt);
        tv_ship_left.setText(leftTxt);

        tv_ship_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onLeftClick();
            }
        });
        tv_ship_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onRightClick();

            }
        });
         mContentView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return true;
            }
        });

    }

    @Override
    protected int initHeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    /**
     * 功能：设置左按钮颜色值
     * 无参
     * 返回值：空
     * 时间：2021/8/17 10:45
     */
    public void setShipLeftColor(int color) {
        if (tv_ship_left != null) {
            tv_ship_left.setTextColor(color);
        }
    }

    /**
     * 功能：设置右按钮颜色值
     * 无参
     * 返回值：空
     * 时间：2021/8/17 10:45
     */
    public void setShipRightColor(int color) {
        if (tv_ship_right != null) {
            tv_ship_right.setTextColor(color);
        }
    }

    @Override
    protected int initWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }
}
