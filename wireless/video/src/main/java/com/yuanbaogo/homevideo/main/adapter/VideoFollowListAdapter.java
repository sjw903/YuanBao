package com.yuanbaogo.homevideo.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.main.bean.VideoLiveFollowListBean;

import java.util.List;


public class VideoFollowListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<VideoLiveFollowListBean.ListBean> mTreeList;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClickClick(String url);
    }

    public VideoFollowListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_live_video_follow, parent, false);
        return new ItemNodeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {

        VideoLiveFollowListBean.ListBean bean = mTreeList.get(position);

        if (bean == null) {
            return;
        }
        ItemNodeViewHolder holder = (ItemNodeViewHolder) h;

        Glide.with(mContext)
                .load(bean.getPortraitUrl())
                .centerCrop()
                .circleCrop()
                .into(holder.iv_video_list_head);

    }

    @Override
    public int getItemCount() {
        int count = mTreeList == null ? 0 : mTreeList.size();
        return count;
    }

    public void setData(List<VideoLiveFollowListBean.ListBean> list) {
        this.mTreeList = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ItemNodeViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_video_list_head;
        private View v_root;

        public ItemNodeViewHolder(View itemView) {
            super(itemView);
            v_root = itemView;
            iv_video_list_head = (ImageView) itemView.findViewById(R.id.iv_video_list_head);

        }
    }


}
