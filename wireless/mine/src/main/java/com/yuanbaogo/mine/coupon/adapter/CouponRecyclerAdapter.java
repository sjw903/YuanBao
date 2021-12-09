package com.yuanbaogo.mine.coupon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.RoundImageView;

import java.util.List;

public class CouponRecyclerAdapter extends RecyclerView.Adapter<CouponRecyclerAdapter.CouponViewHolder> {

    private List<String> mDataList;
    private Context mContext;

    public CouponRecyclerAdapter(Context context, List<String> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @NonNull
    @Override
    public CouponRecyclerAdapter.CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_layout_coupon_item, parent, false);
        return new CouponRecyclerAdapter.CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponRecyclerAdapter.CouponViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Nullable
    public String getItem(int position) {
        return mDataList.get(position);
    }

    class CouponViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private LinearLayout mCouponItemLlHidden;
        private TextView mCouponItemTvSource;
        private TextView mCouponItemTvUse;
        private TextView mCouponItemTvRules;
        private RoundImageView mCouponItemIvImg;
        private TextView mCouponItemTvName;
        private TextView mCouponTvUse;
        private CheckBox mCouponItemCb;

        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            mCouponItemLlHidden = itemView.findViewById(R.id.coupon_item_ll_hidden);
            mCouponItemTvSource = itemView.findViewById(R.id.coupon_item_tv_source);
            mCouponItemTvUse = itemView.findViewById(R.id.coupon_item_tv_use);
            mCouponItemTvRules = itemView.findViewById(R.id.coupon_item_tv_rules);
            mCouponItemIvImg = itemView.findViewById(R.id.coupon_item_iv_img);
            mCouponItemTvName = itemView.findViewById(R.id.coupon_item_tv_name);
            mCouponTvUse = itemView.findViewById(R.id.coupon_tv_use);
            mCouponItemCb = itemView.findViewById(R.id.coupon_item_cb);
            mCouponItemCb.setOnCheckedChangeListener(this);
            mCouponTvUse.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.coupon_tv_use) {
                ToastView.showToast("立即使用");
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                mCouponItemLlHidden.setVisibility(View.VISIBLE);
            } else {
                mCouponItemLlHidden.setVisibility(View.GONE);
            }
        }

        public void onBindViewHolder(int position) {
            getItem(position);
        }
    }

}
