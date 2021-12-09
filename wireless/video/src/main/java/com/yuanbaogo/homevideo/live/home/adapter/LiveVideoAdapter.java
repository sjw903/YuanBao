package com.yuanbaogo.homevideo.live.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.yuanbaogo.homevideo.live.home.model.LiveRecommendBean;
import com.yuanbaogo.video.R;

import java.util.ArrayList;
import java.util.List;

public class LiveVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<LiveRecommendBean.RowsBean> mList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onItmClick(LiveRecommendBean.RowsBean bean);

    }

    public LiveVideoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.video_item_live_recommend, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        ItemViewHolder holder = (ItemViewHolder) h;
        final LiveRecommendBean.RowsBean bean = mList.get(position);
        if (bean == null) {
            return;
        }

        if (bean.getSellGoods().equals("0")) {
            holder.tv_selling.setVisibility(View.VISIBLE);
        } else {
            holder.tv_selling.setVisibility(View.GONE);
        }

        Glide.with(context).load(R.drawable.icon_shop_recommend_live_diverge_view_second4).into(holder.itemLiveImgLive);

        holder.tv_viewer.setText(bean.getVisitors() + "观看");
        holder.tv_living_title.setText(bean.getTitle());
        holder.tv_anchor_name.setText(bean.getAnchorNickName());

        holder.cd_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItmClick(bean);
                }
            }
        });

        int resourceId = R.mipmap.icon_shop_international_banner;
        Glide.with(context).load(bean.getPortraitUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.cv_anchor_head);

        Glide.with(context)
                .load(bean.getCoverUrl())
                .thumbnail(0.1f).into(holder.iv_anchor_img);


    }

    @Override
    public int getItemCount() {
        int count = mList == null ? 0 : mList.size();
        return count;
    }

    public void setData(List<LiveRecommendBean.RowsBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void addData(List<LiveRecommendBean.RowsBean> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.addAll(list);
        notifyDataSetChanged();
//        notifyItemInserted(mList.size());//通知插入了数据
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isStaggeredGridLayout(holder)) {
            handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
        }
    }

    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }

    private void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder, int position) {
        StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
//        if (getItemViewType(position) == TYPE_FOOTER || getItemViewType(position) == TYPE_HEADER) {
//        if (getItemViewType(position) == TYPE_HEADER) {
//            p.setFullSpan(true);
//        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout tv_living;
        private CardView cd_root;

        private TextView tv_viewer;
        private TextView tv_living_title;
        private TextView tv_selling;
        private TextView tv_anchor_name;
        private ImageView iv_anchor_img;
        private ImageView cv_anchor_head;
        private ImageView itemLiveImgLive;

        public ItemViewHolder(View itemView) {
            super(itemView);

            cd_root = (CardView) itemView.findViewById(R.id.cd_root);
            tv_living = (LinearLayout) itemView.findViewById(R.id.tv_living);

            iv_anchor_img = (ImageView) itemView.findViewById(R.id.iv_anchor_img);
            tv_viewer = (TextView) itemView.findViewById(R.id.tv_viewer);
            tv_selling = (TextView) itemView.findViewById(R.id.tv_selling);
            tv_living_title = (TextView) itemView.findViewById(R.id.tv_living_title);
            tv_anchor_name = (TextView) itemView.findViewById(R.id.tv_anchor_name);
            cv_anchor_head = (ImageView) itemView.findViewById(R.id.cv_anchor_head);
            itemLiveImgLive = (ImageView) itemView.findViewById(R.id.item_live_img_live);


        }
    }


}

