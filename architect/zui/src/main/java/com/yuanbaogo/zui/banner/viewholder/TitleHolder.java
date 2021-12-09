package com.yuanbaogo.zui.banner.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanbaogo.zui.R;


/**
 * @Description: 文字
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 1:15 PM
 * @Modifier:
 * @Modify:
 */
public class TitleHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public TitleHolder(@NonNull View view) {
        super(view);
        title = view.findViewById(R.id.banner_tv_title);
    }
}
