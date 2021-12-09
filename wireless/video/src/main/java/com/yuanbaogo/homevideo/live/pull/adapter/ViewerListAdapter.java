package com.yuanbaogo.homevideo.live.pull.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.live.personal.model.ViewerListBean;

import java.util.ArrayList;
import java.util.List;


public class ViewerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ViewerListBean.RowsBean> mTreeList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(int pos, String ybcode);
    }

    public ViewerListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.video_item_viewer_list, parent, false);
        return new ItemNodeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {

        ViewerListBean.RowsBean bean = mTreeList.get(position);

        if (bean == null) {
            return;
        }
        ItemNodeViewHolder holder = (ItemNodeViewHolder) h;


        if (position == 0) {
            holder.tv_number.setTextColor(mContext.getResources().getColor(R.color.colorEA5504));
        } else if (position == 1) {
            holder.tv_number.setTextColor(mContext.getResources().getColor(R.color.colorFB8C00));
        } else if (position == 2) {
            holder.tv_number.setTextColor(mContext.getResources().getColor(R.color.colorFFE082));
        } else {
            holder.tv_number.setTextColor(mContext.getResources().getColor(R.color.color999999));
        }
        holder.tv_number.setText(position + 1 + "");
        holder.tv_money.setText(bean.getCharmValue());

        if (bean.getBaseUser() != null) {
            holder.tv_name.setText(bean.getBaseUser().getNickName());
            Glide.with(mContext)
                    .load(bean.getBaseUser().getPortraitUrl())
                    .circleCrop()
                    .thumbnail(0.1f).into(holder.iv_img);
        }

        if (bean.getFansState() == 0 || bean.getFansState() == 2) {
            holder.iv_add_follow.setVisibility(View.VISIBLE);
            holder.iv_add_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null)
                        onItemClickListener.onClick(position, bean.getYbCode());
                }
            });
        } else {
            holder.iv_add_follow.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        int count = mTreeList == null ? 0 : mTreeList.size();
        return count;
    }

    public void setData(List<ViewerListBean.RowsBean> list) {
        this.mTreeList = list;
        notifyDataSetChanged();
    }

    public void addData(List<ViewerListBean.RowsBean> list) {
        if (mTreeList == null) {
            mTreeList = new ArrayList<>();
        }
        mTreeList.addAll(list);
        notifyDataSetChanged();
//        notifyItemInserted(mList.size());//通知插入了数据
    }

    private void itemNotifyItemChanged(int position) {
        ViewerListBean.RowsBean bean = mTreeList.get(position);
        bean.setFansState(0);
        notifyItemChanged(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ItemNodeViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_number;
        private TextView tv_name;
        private TextView tv_money;
        private ImageView iv_img;
        private ImageView iv_add_follow;
        private View v_root;

        public ItemNodeViewHolder(View itemView) {
            super(itemView);
            v_root = itemView;

            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            iv_add_follow = (ImageView) itemView.findViewById(R.id.iv_add_follow);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
        }
    }


}
