package com.yuanbaogo.finance.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.finance.R;
import com.yuanbaogo.finance.pay.model.PayTypeBean;

import java.util.List;

import static com.yuanbaogo.finance.pay.presenter.PayCenter.PAYTYPE_YB;


public class PayTypeUnitListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<PayTypeBean> mTreeList;

    public PayTypeUnitListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.pay_item_pay_type_group, parent, false);
        return new ItemUnitViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {

        PayTypeBean bean = mTreeList.get(position);

        if (bean == null) {
            return;
        }
        ItemUnitViewHolder holder = (ItemUnitViewHolder) h;

        Glide.with(mContext)
                .load(bean.getAndroidIconUrl())
                .thumbnail(0.1f).into(holder.tv_type_icon);

        if (bean.getPayAwayCode().equals(PAYTYPE_YB)) {
            holder.tv_price.setText("¥" +bean.getBalance());
        }else{
            holder.tv_price.setText("¥" +bean.getMoney());
        }
        holder.tv_type.setText(bean.getPayAwayName());
    }

    @Override
    public int getItemCount() {
        int count = mTreeList == null ? 0 : mTreeList.size();
        return count;
    }

    public void setData(List<PayTypeBean> list) {
        this.mTreeList = list;
        notifyDataSetChanged();
    }

    class ItemUnitViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_price;
        private TextView tv_type;
        private ImageView tv_type_icon;
        private View v_root;

        public ItemUnitViewHolder(View itemView) {
            super(itemView);
            v_root = itemView;
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_type_icon = (ImageView) itemView.findViewById(R.id.tv_type_icon);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }

}
