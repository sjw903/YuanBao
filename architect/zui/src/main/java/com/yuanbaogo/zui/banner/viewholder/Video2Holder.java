package com.yuanbaogo.zui.banner.viewholder;

import android.view.View;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanbaogo.zui.R;

/**
 * @Description: 视频
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 1:15 PM
 * @Modifier:
 * @Modify:
 */
public class Video2Holder extends RecyclerView.ViewHolder {

    public VideoView videoView;

    public Video2Holder(@NonNull View view) {
        super(view);
        videoView = view.findViewById(R.id.banner_video);
    }
}
