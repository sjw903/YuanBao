package com.yuanbaogo.homevideo.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yuanbaogo.libbase.baseutil.glide.GlideRoundTransform;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.mainui.list.TCVideoInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/9 11:00
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private Context mContext;
    private List<TCVideoInfo> mList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public VideoListAdapter(Context mContext, List<TCVideoInfo> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //给图片顶部设置圆角
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(8));
        Glide.with(mContext).load(mList.get(position).frontcover).apply(options).into(holder.iv_video_list_bg);
        holder.tv_video_list_name.setText(mList.get(position).nickname);
        Glide.with(mContext).load(mList.get(position).headpic).into(holder.iv_video_list_head);
        holder.rl_item.setOnClickListener(v -> onItemClickListener.onItemClickListener(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_item;
        ImageView iv_video_list_bg;
        TextView tv_video_list_describe;
        ImageView iv_video_list_head;
        TextView tv_video_list_name;
        TextView tv_video_list_like_count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_item = itemView.findViewById(R.id.rl_item);
            iv_video_list_bg = itemView.findViewById(R.id.iv_video_list_bg);
            tv_video_list_describe = itemView.findViewById(R.id.tv_video_list_describe);
            iv_video_list_head = itemView.findViewById(R.id.iv_video_list_head);
            tv_video_list_name = itemView.findViewById(R.id.tv_video_list_name);
            tv_video_list_like_count = itemView.findViewById(R.id.tv_video_list_like_count);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

}
