package com.yuanbaogo.zui.recycler.viewholder;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 3:12 PM
 * @Modifier:
 * @Modify:
 */
public class BaseViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> views;

    public BaseViewHolder(View view) {
        super(view);
        this.views = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public SparseArray getSparseArray(){
        return  views;
    }

    public View getRootView() {
        return itemView;
    }

}
