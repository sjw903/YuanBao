package com.yuanbaogo.homevideo.bringgoods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.live.push.model.CartGoodsListBean;

import java.util.ArrayList;
import java.util.List;


public class ViewerGoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CartGoodsListBean> mTreeList;

    private OnClickListener mOnItemClickListener;


    public interface OnClickListener {
        void onBuyClick(CartGoodsListBean bean);
        void onItemClick(CartGoodsListBean bean);
    }

    public void setOnClickListener(OnClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public ViewerGoodsListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.video_item_viewer_good, parent, false);
        return new ItemNodeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {

        CartGoodsListBean bean = mTreeList.get(position);

        if (bean == null) {
            return;
        }
        ItemNodeViewHolder holder = (ItemNodeViewHolder) h;

        Glide.with(mContext)
                .load(bean.getGoodsPicture())
                .thumbnail(0.1f).into(holder.iv_goods_img);

        holder.tv_goods_title.setText(bean.getGoodsName());
        holder.tv_info.setText(bean.getSpecificationName());
        holder.tv_price.setText("¥" + bean.getGoodsMoney()/100);

        if (true == bean.isShelf()) {
            holder.ll_goods_done.setVisibility(View.GONE);
            holder.tv_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onBuyClick(bean);
                }
            });
        } else {
            holder.ll_goods_done.setVisibility(View.VISIBLE);
        }
        holder.tv_buy.setEnabled(bean.isShelf());
        holder.v_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = mTreeList == null ? 0 : mTreeList.size();
        return count;
    }

    public void setData(List<CartGoodsListBean> list) {
        this.mTreeList = list;
        notifyDataSetChanged();
    }

    public void addData(List<CartGoodsListBean> list) {
        if (mTreeList == null) {
            mTreeList = new ArrayList<>();
        }
        mTreeList.addAll(list);
        notifyDataSetChanged();
//        notifyItemInserted(mList.size());//通知插入了数据
    }


    class ItemNodeViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_goods_title;
        private TextView tv_info;
        private TextView tv_price;
        private TextView tv_buy;
        private ImageView iv_goods_img;
        private View v_root;
        private LinearLayout ll_goods_done;

        public ItemNodeViewHolder(View itemView) {
            super(itemView);
            v_root = itemView;

            tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            iv_goods_img = (ImageView) itemView.findViewById(R.id.iv_goods_img);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_buy = (TextView) itemView.findViewById(R.id.tv_buy);
            ll_goods_done = (LinearLayout) itemView.findViewById(R.id.ll_goods_done);


        }
    }


}
