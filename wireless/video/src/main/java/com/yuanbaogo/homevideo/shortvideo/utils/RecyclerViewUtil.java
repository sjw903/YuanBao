package com.yuanbaogo.homevideo.shortvideo.utils;

import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ganhuanhui
 * @date 2020/4/28 0028
 * @desc
 */
public class RecyclerViewUtil {

    private RecyclerView recyclerView;

    public RecyclerViewUtil() {
    }

    public void initScrollListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        if (mScrollListener != null) {
            this.recyclerView.addOnScrollListener(mScrollListener);
        }
    }

    private int currentState = -1;
    private Handler cancelScrollHandler = new Handler();
    private Runnable cancelScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (recyclerView != null) {
                currentState = -1;
                // 当用户停止滑动的时候 主动触摸Recyclerview，已达到能够立即消费到停止滚动事件，防止出现点击item2次才会触发点击事件
                recyclerView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0f, 0f, 0));
            }
        }
    };

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            currentState = newState;
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //如果在持续滚动中，则取消回调
            if (cancelScrollHandler == null || cancelScrollRunnable == null) return;
            cancelScrollHandler.removeCallbacks(cancelScrollRunnable);
            //当前RecyclerView在滚动设置到某个位置的动画状态，代码调用时或者惯性滚动时就是这个状态
            if (currentState == RecyclerView.SCROLL_STATE_SETTLING) {
                cancelScrollHandler.postDelayed(cancelScrollRunnable, 20);
            }
        }
    };

    public void destroy() {
        if (cancelScrollHandler != null && cancelScrollRunnable != null) {
            cancelScrollHandler.removeCallbacks(cancelScrollRunnable);
            cancelScrollHandler = null;
        }
        if (recyclerView != null && mScrollListener != null) {
            recyclerView.removeOnScrollListener(mScrollListener);
            recyclerView = null;
            mScrollListener = null;
        }
    }
    /**
     * 移动到对应的位置
     *
     * @param position tab 的下标
     */
    public void move(RecyclerView recyclerView,int position) {
//        String proMenuId = proMenuBeans.get(position).getId();
//        LogUtils.d("proMenuId:" + proMenuId);
//        //体系列表的下标
//        int index = 0;
//        for (SystemBean systemBean : systemList) {
//            if (systemBean.getProjectid().equals(proMenuId)) {
//                LogUtils.d("systemList Id:" + systemBean.getProjectid());
//                index = systemList.indexOf(systemBean);
//                LogUtils.d("systemList Index" + index);
//            }
//        }
//        LogUtils.d("list 中 索引的位置：" + index);


        smoothMoveToPosition(recyclerView, position);



    }
    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
}
