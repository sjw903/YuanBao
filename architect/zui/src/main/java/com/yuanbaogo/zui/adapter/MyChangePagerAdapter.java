package com.yuanbaogo.zui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.LinkedList;
import java.util.List;

public abstract class MyChangePagerAdapter extends PagerAdapter {

    private final List mRecyclerList = new LinkedList<>();

    private final List mUsedList = new LinkedList<>();

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((PagerHolder) object).itemView;
    }

    @Override
    public int getItemPosition(Object object) {
//1、如果列表为空，直接返回POSITION_NONE， 防止移除时不能刷新
//2、如果下标越界，直接返回POSITION_NONE
//3、根据子类isViewHolderChanged返回值 决定是否返回POSITION_NONE
//如果返回POSITION_NONE 将会重新instantiateItem
        if (getCount() == 0 || ((PagerHolder) object).getItemPosition() >= getCount() || isViewHolderChanged((PagerHolder)
                object)) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PagerHolder holder;
        if (!mRecyclerList.isEmpty()) {//是否有可复用的item
            holder = (PagerHolder) mRecyclerList.remove(0);
        } else {//新建item
            holder = onCreateViewHolder(position);
        }
        holder.position = position;
//绑定item数据
        onBindViewHolder(holder);
        container.addView(holder.itemView);
        mUsedList.add(holder);
        return holder;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        PagerHolder holder = (PagerHolder) object;
        container.removeView(holder.itemView);
        mUsedList.remove(holder);
        mRecyclerList.add(holder);
    }

    public List getUsedHolders() {
        return mUsedList;
    }

    public abstract PagerHolder onCreateViewHolder(int position);

    public abstract void onBindViewHolder(PagerHolder holder);

    public abstract boolean isViewHolderChanged(PagerHolder holder);


    public static class PagerHolder {
        public View itemView;
        private int position;

        public PagerHolder(View view) {
            this.itemView = view;
        }

        public int getItemPosition() {
            return position;
        }
    }
}
