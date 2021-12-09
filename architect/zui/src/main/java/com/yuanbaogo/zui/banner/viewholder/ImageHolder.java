package com.yuanbaogo.zui.banner.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description: 图片
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 1:15 PM
 * @Modifier:
 * @Modify:
 */
public class ImageHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public ImageHolder(@NonNull View view) {
        super(view);
        this.imageView = (ImageView) view;
    }
}