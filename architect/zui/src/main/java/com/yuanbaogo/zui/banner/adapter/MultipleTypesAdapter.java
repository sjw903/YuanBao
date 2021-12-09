package com.yuanbaogo.zui.banner.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.banner.bean.DataBean;
import com.yuanbaogo.zui.banner.viewholder.ImageHolder;
import com.yuanbaogo.zui.banner.viewholder.TitleHolder;
import com.yuanbaogo.zui.banner.viewholder.Video2Holder;

import java.util.List;

/**
 * @Description: 自定义布局, 多个不同UI切换
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 1:10 PM
 * @Modifier:
 * @Modify:
 */
public class MultipleTypesAdapter extends BannerAdapter<DataBean, RecyclerView.ViewHolder> {

    private Context context;

    private SparseArray<RecyclerView.ViewHolder> mVHMap = new SparseArray<>();

    public MultipleTypesAdapter(Context context, List<DataBean> mDatas) {
        super(mDatas);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new ImageHolder(BannerUtils.getView(parent, R.layout.banner_image));
            case 2:
                return new Video2Holder(BannerUtils.getView(parent, R.layout.banner_video2));
            case 3:
                return new TitleHolder(BannerUtils.getView(parent, R.layout.banner_title));
        }
        return new ImageHolder(BannerUtils.getView(parent, R.layout.banner_image));
    }

    @Override
    public int getItemViewType(int position) {
        return getRealData(position).viewType;
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, DataBean data, int position, int size) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case 1:
                ImageHolder imageHolder = (ImageHolder) holder;
                mVHMap.append(position, imageHolder);
                if (!TextUtils.isEmpty(data.imageUrl)) {
                    Glide.with(context).load(data.imageUrl).into(imageHolder.imageView);
                } else {
                    imageHolder.imageView.setImageResource(data.imageRes);
                }
                break;
            case 2:
                Video2Holder video2Holder = (Video2Holder) holder;
                //设置视频控制器,组件可以控制视频的播放，暂停，快进，组件，不需要你实现
                MediaController mc = new MediaController(context);
                video2Holder.videoView.setMediaController(mc);
                //设置视频的播放地址，网络地址。播放网络视频
                video2Holder.videoView.setVideoURI(Uri.parse(data.imageUrl));
                Bitmap thumb = ThumbnailUtils.createVideoThumbnail(data.imageUrl, MediaStore.Images.Thumbnails.MINI_KIND);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(thumb);
                video2Holder.videoView.setBackground(bitmapDrawable);
                //让VideiView获取焦点
                video2Holder.videoView.requestFocus();
//                video2Holder.videoView.start();
                break;
            case 3:
                TitleHolder titleHolder = (TitleHolder) holder;
                mVHMap.append(position, titleHolder);
                titleHolder.title.setText(data.title);
                titleHolder.title.setBackgroundColor(Color.parseColor(DataBean.getRandColor()));
                break;
        }
    }

    public SparseArray<RecyclerView.ViewHolder> getVHMap() {
        return mVHMap;
    }

}
