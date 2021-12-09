package com.yuanbaogo.zui.swipe;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;


public class ViewUtils {
    private static final String TAG = "ViewUtils";

    public static boolean isScrollToTop(RecyclerView recyclerView) {
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null) {
            return true;
        }
        if (manager.getItemCount() == 0) {
            return false;
        }

        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) manager;

            int firstChildTop = 0;
            if (recyclerView.getChildCount() > 0) {
                // 处理item高度超过一屏幕时的情况
                View firstVisibleChild = recyclerView.getChildAt(0);
                if (firstVisibleChild != null && firstVisibleChild.getMeasuredHeight() >= recyclerView.getMeasuredHeight()) {
                    return !ViewCompat.canScrollVertically(recyclerView, -1);
                }

                View firstChild = recyclerView.getChildAt(0);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) firstChild.getLayoutParams();
                int top = getRecyclerViewItemInset(layoutParams).top;
                firstChildTop = firstChild.getTop() - layoutParams.topMargin - top - recyclerView.getPaddingTop();
            }

            return layoutManager.findFirstCompletelyVisibleItemPosition() == 0 && firstChildTop == 0;
        }

        return false;
    }

    public static boolean isScrollToBottom(ViewGroup parent, RecyclerView recyclerView) {
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null || manager.getItemCount() == 0) {
            return false;
        }

        if (manager instanceof LinearLayoutManager) {
            // 处理item高度超过一屏幕时的情况
            View lastVisibleChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
            if (lastVisibleChild != null && lastVisibleChild.getMeasuredHeight() >= recyclerView.getMeasuredHeight()) {
                return !ViewCompat.canScrollVertically(recyclerView, 1);
            }

            LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
            if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                View lastChild = layoutManager.getChildAt(layoutManager.findLastCompletelyVisibleItemPosition());
                if (lastChild == null) {
                    Log.d(TAG, "scroll to childBottom last null");
                    return true;
                }
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) lastChild.getLayoutParams();

                int[] childLocation = new int[2];
                int[] parentLocation = new int[2];
                lastChild.getLocationInWindow(childLocation);
                parent.getLocationInWindow(parentLocation);

                int lastChildBottom = childLocation[1] + lastChild.getMeasuredHeight();
                int parentBottom = parentLocation[1] + parent.getMeasuredHeight();
                int bottomInset = getRecyclerViewItemInset(layoutParams).bottom;
                if (lastChildBottom + bottomInset <= parentBottom) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 通过反射获取RecyclerView的item的Inset
     *
     * @param layoutParams
     * @return
     */
    private static Rect getRecyclerViewItemInset(RecyclerView.LayoutParams layoutParams) {
        try {
            Field field = RecyclerView.LayoutParams.class.getDeclaredField("mDecorInsets");
            field.setAccessible(true);
            Rect decorInsets = (Rect) field.get(layoutParams);
            return decorInsets;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Rect(0, 0, 0, 0);
    }
}
