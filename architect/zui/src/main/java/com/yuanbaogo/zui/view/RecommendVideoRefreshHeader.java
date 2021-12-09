package com.yuanbaogo.zui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.yuanbaogo.zui.R;

import org.jetbrains.annotations.NotNull;

/**
 * 功能：
 * 时间 ： 2021/8/17:.
 * 作者：11190
 */

public class RecommendVideoRefreshHeader extends LinearLayout implements RefreshHeader {

    private TextView mHeaderText;//标题文本
    private OnHeadStateChangedListener mChangedListener;//状态变化监听
    public RecommendVideoRefreshHeader(Context context) {
        this(context,null);
    }
    public RecommendVideoRefreshHeader(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }
    public RecommendVideoRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        View.inflate(context, R.layout.head_refresh_recommend_video, this);
        final View thisView = this;
        this.initView(thisView);
    }
    private void initView(View view) {
        mHeaderText = view.findViewById(R.id.srl_classics_title);

    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
//        if (success){
////            mHeaderText.setText("刷新完成");
//        } else {
////            mHeaderText.setText("刷新失败");
//            return 500;//延迟500毫秒之后再弹回
//        }
        return 0;//延迟500毫秒之后再弹回
    }

    public void setChangedListener(OnHeadStateChangedListener changedListener) {
        mChangedListener = changedListener;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
                if(mChangedListener!=null){
                    mChangedListener.refreshFinishListener();
                }
                break;
            case PullDownToRefresh:
                mHeaderText.setText("下拉刷新内容");
                if(mChangedListener!=null){
                    mChangedListener.refreshPullDownListener();
                }
                break;
            case Refreshing:
                mHeaderText.setText("下拉刷新内容");
                break;
            case ReleaseToRefresh:
                mHeaderText.setText("下拉刷新内容");
                break;
        }
    }
    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
    @Override
    public void onInitialized(RefreshKernel kernel, int height, int maxDragHeight) {
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull @NotNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void setPrimaryColors(@ColorInt int ... colors){
    }
    public interface  OnHeadStateChangedListener {
        void refreshPullDownListener();
        void refreshFinishListener();
    }
}