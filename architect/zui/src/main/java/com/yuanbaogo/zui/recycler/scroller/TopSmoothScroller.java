package com.yuanbaogo.zui.recycler.scroller;

import android.content.Context;

import androidx.recyclerview.widget.LinearSmoothScroller;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/12/21 11:06 AM
 * @Modifier:
 * @Modify:
 */
public class TopSmoothScroller extends LinearSmoothScroller {
    public TopSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected int getHorizontalSnapPreference() {
        return SNAP_TO_START;//具体见源码注释
    }

    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;//具体见源码注释
    }
}

