package com.yuanbaogo.zui.swipe;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class SwipeLayout extends FrameLayout {

    private static final String TAG = "SwipeLayout";

    private ViewDragHelper mDragHelper;
    private int mTouchSlop;

    public boolean isOpen() {
        return mIsOpen;
    }

    private boolean mIsOpen;

    private View mBottomView;

    private boolean mSwipeEnabled = true;

    // bottomView宽度
    private int mDragRange;

    private float mWillOpenPercentAfterOpen = 0.75f;
    private float mWillOpenPercentAfterClose = 0.25f;

    private GestureDetector mGestureDetector = new GestureDetector(getContext(), new SwipeDetector());

    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (isTouchOnSurface(e)) {
                close();
            }
            return super.onSingleTapUp(e);
        }
    }

    public SwipeLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mDragHelper = ViewDragHelper.create(this, 1.0f, mDragHelperCallback);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mBottomView = getBottomView();
    }

    private ViewDragHelper.Callback mDragHelperCallback = new ViewDragHelper.Callback() {

        boolean isCloseBeforeDragged;

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            boolean result = child == getSurfaceView() || child == getBottomView();
            if (result) {
                isCloseBeforeDragged = getDragStatus() == Status.Close;
            }
            return result;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d(TAG, "clampViewPositionHorizontal left " + left);
            if (child == getSurfaceView()) {
                if (left > getPaddingLeft()) return getPaddingLeft();
                if (left < getPaddingLeft() - mBottomView.getWidth())
                    return getPaddingLeft() - mBottomView.getWidth();
            } else if (child == getBottomView()) {
                if (left < getMeasuredWidth() - mBottomView.getMeasuredWidth())
                    return getMeasuredWidth() - mBottomView.getMeasuredWidth();
            }
            return left;
        }

        // 手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            processHandRelease(xvel, yvel, isCloseBeforeDragged);
            invalidate();
        }

        // View位置发生变化时回调
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            View surfaceView = getSurfaceView();
            View bottomView = getBottomView();
            if (surfaceView == null || bottomView == null) return;
            if (changedView == surfaceView) {
                bottomView.offsetLeftAndRight(dx);
            } else if (changedView == bottomView) {
                surfaceView.offsetLeftAndRight(dx);
            }
            invalidate();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            Log.d(TAG, "getViewHorizontalDragRange");
            return mDragRange;
        }
    };


    // 处理手指放开后的操作
    private void processHandRelease(float xvel, float yvel, boolean isCloseBeforeDragged) {
        float minVelocity = mDragHelper.getMinVelocity();
        View surfaceView = getSurfaceView();
        if (surfaceView == null) return;
        float willOpenPercent = (isCloseBeforeDragged ? mWillOpenPercentAfterClose : mWillOpenPercentAfterOpen);
        if (xvel > minVelocity) close();
        else if (xvel < -minVelocity) open();
        else {
            float openPercent = 1f * (-getSurfaceView().getLeft() / mDragRange);
            if (openPercent > willOpenPercent) open();
            else close();
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        int gravity = Gravity.NO_GRAVITY;
        try {
            gravity = (int) params.getClass().getField("gravity").get(params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (gravity > 0) {
            gravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this));
            if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) {
                mBottomView = child;
            }
        }
        super.addView(child, index, params);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutBottomView();
    }

    private Rect computeSurfaceLayoutArea(boolean open) {
        int l = getPaddingLeft(), t = getPaddingTop();
        if (open) {
            l = getPaddingLeft() - mDragRange;
        }
        return new Rect(l, t, l + getMeasuredWidth(), t + getMeasuredHeight());
    }

    private void layoutBottomView() {
        Rect surfaceRect = computeSurfaceLayoutArea(false);
        Rect bottomRect = computeBottomLayoutAreaViaSurface(surfaceRect);
        mDragRange = mBottomView.getMeasuredWidth();
        mBottomView.layout(bottomRect.left, bottomRect.top, bottomRect.right, bottomRect.bottom);
    }

    private Rect computeBottomLayoutAreaViaSurface(Rect surfaceArea) {
        View bottomView = getBottomView();
        int bl = surfaceArea.left, bt = surfaceArea.top, br = surfaceArea.right, bb = surfaceArea.bottom;
        bl = surfaceArea.right;
        br = bl + (bottomView == null ? 0 : bottomView.getMeasuredWidth());
        return new Rect(bl, bt, br, bb);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isSwipeEnabled()) return false;
        if (getDragStatus() == Status.Open && isTouchOnSurface(ev)) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDragHelper.processTouchEvent(ev);
                mIsBeingDragged = false;
                sX = ev.getRawX();
                sY = ev.getRawY();
                if (getDragStatus() == Status.Middle) {
                    mIsBeingDragged = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                boolean beforeCheck = mIsBeingDragged;
                checkCanDrag(ev);
                if (mIsBeingDragged) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (!beforeCheck && mIsBeingDragged) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                mDragHelper.processTouchEvent(ev);
                break;

        }
        return mIsBeingDragged;
    }

    private float sX = -1, sY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isSwipeEnabled()) return super.onTouchEvent(event);

        mGestureDetector.onTouchEvent(event);

        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDragHelper.processTouchEvent(event);
                sX = event.getRawX();
                sY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                checkCanDrag(event);
                if (mIsBeingDragged) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    mDragHelper.processTouchEvent(event);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                mDragHelper.processTouchEvent(event);
                break;
            default:
                mDragHelper.processTouchEvent(event);
        }
        return super.onTouchEvent(event) || mIsBeingDragged || action == MotionEvent.ACTION_DOWN;
    }

    private enum Status {
        Open,
        Close,
        Middle
    }

    private Status getDragStatus() {
        View surfaceView = getSurfaceView();
        View bottomView = getBottomView();
        if (surfaceView == null || bottomView == null) return Status.Close;
        int surfaceLeft = surfaceView.getLeft();
        int surfaceTop = surfaceView.getTop();
        if (surfaceLeft == getPaddingLeft() && surfaceTop == getPaddingTop()) return Status.Close;

        if (surfaceLeft == getPaddingLeft() - bottomView.getMeasuredWidth()) return Status.Open;
        return Status.Middle;
    }

    private boolean mIsBeingDragged = false;

    private void checkCanDrag(MotionEvent event) {
        if (mIsBeingDragged) return;
        if (getDragStatus() == Status.Middle) {
            mIsBeingDragged = true;
            return;
        }
        Status status = getDragStatus();
        float distanceX = event.getRawX() - sX;
        float distanceY = event.getRawY() - sY;
        float angle = Math.abs(distanceY / distanceX);
        angle = (float) Math.toDegrees(Math.atan(angle));
        boolean suitable = (status == Status.Open && distanceX > mTouchSlop) || (status == Status.Close && distanceX < -mTouchSlop);
        suitable = suitable || (status == Status.Middle);

        boolean doNothing = false;
        if (angle > 30 || !suitable) {
            doNothing = true;
        }
        mIsBeingDragged = !doNothing;
    }

    public void open() {
        View surfaceView = getSurfaceView();
        if (surfaceView == null) {
            mIsOpen = true;
            return;
        }
        Rect rect = computeSurfaceLayoutArea(true);
        mDragHelper.smoothSlideViewTo(surfaceView, rect.left, rect.top);
        mIsOpen = true;
        invalidate();
    }

    public void close() {
        View surface = getSurfaceView();
        if (surface == null) {
            mIsOpen = false;
            return;
        }
        mDragHelper.smoothSlideViewTo(surface, getPaddingLeft(), getPaddingTop());
        mIsOpen = false;
        invalidate();
    }

    public void toggle() {
        if (getDragStatus() == Status.Open) close();
        else if (getDragStatus() == Status.Close) open();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public View getSurfaceView() {
        if (getChildCount() == 0) return null;
        return getChildAt(getChildCount() - 1);
    }

    private View getBottomView() {
        if (getChildCount() == 0) return null;
        return getChildAt(0);
    }

    public void setSwipeEnabled(boolean enabled) {
        mSwipeEnabled = enabled;
    }

    public boolean isSwipeEnabled() {
        return mSwipeEnabled;
    }

    private Rect hitSurfaceRect;

    private boolean isTouchOnSurface(MotionEvent ev) {
        View surfaceView = getSurfaceView();
        if (surfaceView == null) return false;
        if (hitSurfaceRect == null) hitSurfaceRect = new Rect();
        surfaceView.getHitRect(hitSurfaceRect);
        return hitSurfaceRect.contains((int) ev.getX(), (int) ev.getY());
    }

}
