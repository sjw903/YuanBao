package com.yuanbaogo.homevideo.live.home.adapter;


import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int interval;

    public SpacesItemDecoration(Context context, int interval) {
        this.context = context;
        this.interval = interval;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int position = parent.getChildAdapterPosition(view);
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        // 获取item在span中的下标
        int spanIndex = params.getSpanIndex();
        int interval = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                this.interval, context.getResources().getDisplayMetrics());
        // 中间间隔
//        if (spanIndex % 2 == 0) {
//            outRect.left = interval;
//            outRect.right = 0;
//        } else {
//            // item为奇数位，设置其左间隔为5dp
//            outRect.left = interval / 2;
//            outRect.right = interval;
//        }

//        if (parent.getChildLayoutPosition(view) % 2 == 0) {
//
//            outRect.left = interval;
//
//            outRect.right = interval / 2;

//        } else {
//
//            outRect.left = interval / 2;
//
//            outRect.right = interval;
//
//        }

        if (spanIndex % 2 == 0) {
            outRect.left = interval;
            outRect.right = interval / 2;
        } else {
            // item为奇数位，设置其左间隔为5dp
            outRect.left = interval / 2;
            outRect.right = interval;
        }


    }

}