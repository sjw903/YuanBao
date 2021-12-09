package com.yuanbaogo.homevideo.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.mine.model.VodListBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description: 适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/7/21 6:51 PM
 * @Modifier:
 * @Modify:
 */
public class VideoAdapter extends BaseRecycleAdapter<RecommendVideoBean.RowsBean>
        implements View.OnClickListener {

    int layoutId;

    List<RecommendVideoBean.RowsBean> mDataList;

    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallBack;

    public void setOnCallBack(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public VideoAdapter(Context context,
                        List<RecommendVideoBean.RowsBean> mDataList,
                        int layoutId) {
        super(context, mDataList, layoutId);
        this.layoutId = layoutId;
        this.mDataList = mDataList;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    public void addData(List<RecommendVideoBean.RowsBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        FrameLayout itemFlVideo = holder.getView(R.id.item_fl_video);
        itemFlVideo.setOnClickListener(this);
        itemFlVideo.setTag(position);

        AppCompatImageView itemImgVideoCover = holder.getView(R.id.item_img_video_cover);
        Glide.with(mContext).load(mDataList.get(position).getCoverThumbUrl()).into(itemImgVideoCover);

        AppCompatTextView itemTvVideoCount = holder.getView(R.id.item_tv_video_count);
        itemTvVideoCount.setText(mDataList.get(position).getViewCount() + "次播放");
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }
}
