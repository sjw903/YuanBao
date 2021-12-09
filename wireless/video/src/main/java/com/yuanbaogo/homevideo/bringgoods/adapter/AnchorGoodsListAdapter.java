package com.yuanbaogo.homevideo.bringgoods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.live.push.model.CartGoodsListBean;
import com.yuanbaogo.zui.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;


public class AnchorGoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CartGoodsListBean> mTreeList;

    private OnClickListener mOnItemClickListener;

    public void setExplainStaus(int position) {
        swap(mTreeList, position, 0);
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onExplainClick(int pos, String liveGoodsId);
        void onSort(int pos, String type);
        void onItemClick(CartGoodsListBean bean);
    }

    public void setOnClickListener(OnClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public AnchorGoodsListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.video_item_anchor_good, parent, false);
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
        holder.tv_price.setText("¥"+bean.getGoodsMoney()/100);

        if(bean.isExplainStatus()){
            holder.ll_explain.setVisibility(View.VISIBLE);
            holder.ll_container.setVisibility(View.GONE);
        }else{
            holder.ll_explain.setVisibility(View.GONE);
            holder.ll_container.setVisibility(View.VISIBLE);
        }

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTreeList.remove(position);
                notifyDataSetChanged();
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onSort(position, "del");
                }
            }
        });


        if (position == 0) { //第一个是讲解，不能移动
            holder.up.setVisibility(View.GONE);
            if(bean.isExplainStatus()){
                holder.sl_shop.setSwipeEnabled(false);
                holder.sl_shop.isSwipeEnabled();
                holder.down.setVisibility(View.GONE);
                holder.del.setVisibility(View.GONE);
            }else{
                holder.sl_shop.setSwipeEnabled(true);
                holder.sl_shop.isSwipeEnabled();
                holder.down.setVisibility(View.VISIBLE);
                holder.del.setVisibility(View.VISIBLE);
            }
            //同时也是最后一个
            if (position == mTreeList.size() - 1) {
                holder.down.setVisibility(View.GONE);
            }else {
                holder.down.setVisibility(View.VISIBLE);
            }
        } else if (position == 1) { //第一个去掉上移
            if (mTreeList.get(0).isExplainStatus()) {
                holder.up.setVisibility(View.GONE);
            } else {
                holder.up.setVisibility(View.VISIBLE);
            }
            //同时也是最后一个
            if (position == mTreeList.size() - 1){
                holder.down.setVisibility(View.GONE);
            } else {
                holder.down.setVisibility(View.VISIBLE);
            }
        }else if (position == mTreeList.size() - 1) {//最后一个，不能下移
            holder.down.setVisibility(View.GONE);
        }else{
            holder.down.setVisibility(View.VISIBLE);
            holder.up.setVisibility(View.VISIBLE);
            holder.del.setVisibility(View.VISIBLE);
        }

        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0 && position <= mTreeList.size() - 1) {
                    Toast.makeText(mContext, "上:" + position, Toast.LENGTH_SHORT).show();
                    swap(mTreeList, position, position - 1);
                    notifyDataSetChanged();
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onSort(position, "up");
                    }
                }
            }
        });
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position >= 0 && position < mTreeList.size() - 1) {
                    Toast.makeText(mContext, "下:" + position, Toast.LENGTH_SHORT).show();
                    swap(mTreeList, position, position + 1);
                    notifyDataSetChanged();
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onSort(position, "down");
                    }
                }
            }
        });
        holder.tv_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position >= 0 && position <= mTreeList.size() - 1) {
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onExplainClick(position, bean.getShoppingId());
                    }
                }
            }
        });

        holder.item_bring_goods_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(bean);
                }
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


    class ItemNodeViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_goods_title;
        private TextView del;
        private TextView up;
        private TextView down;
        private TextView tv_explain;
        private TextView tv_info;
        private TextView tv_price;
        private ImageView iv_goods_img;
        private View v_root;
        private LinearLayout ll_explain;
        private LinearLayout ll_container;
        private RelativeLayout item_bring_goods_rl;
        private SwipeLayout sl_shop;

        public ItemNodeViewHolder(View itemView) {
            super(itemView);
            v_root = itemView;

            tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            iv_goods_img = (ImageView) itemView.findViewById(R.id.iv_goods_img);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            ll_explain = (LinearLayout) itemView.findViewById(R.id.ll_explain);
            ll_container = (LinearLayout) itemView.findViewById(R.id.ll_container);

            tv_explain = (TextView) itemView.findViewById(R.id.tv_explain);
            del = (TextView) itemView.findViewById(R.id.tv_delete);
            up = (TextView) itemView.findViewById(R.id.tv_up);
            down = (TextView) itemView.findViewById(R.id.tv_down);

            item_bring_goods_rl = (RelativeLayout) itemView.findViewById(R.id.item_bring_goods_rl);
            sl_shop = (SwipeLayout) itemView.findViewById(R.id.sl_shop);
        }
    }


    public <T> void swap(List<T> list, int oldPosition, int newPosition){
        if(null == list){
            return;
        }
        T tempElement = list.get(oldPosition);

        // 向前移动，前面的元素需要向后移动
        if(oldPosition < newPosition){
            for(int i = oldPosition; i < newPosition; i++){
                list.set(i, list.get(i + 1));
            }
            list.set(newPosition, tempElement);
        }
        // 向后移动，后面的元素需要向前移动
        if(oldPosition > newPosition){
            for(int i = oldPosition; i > newPosition; i--){
                list.set(i, list.get(i - 1));
            }
            list.set(newPosition, tempElement);
        }
    }

}
