package com.yuanbaogo.mine.setting.bill.all;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

public class AllBillRecyclerAdapter extends BaseRecycleAdapter<String> {

    public AllBillRecyclerAdapter(Context context, List<String> mDataList) {
        this(context, mDataList, 0);
    }

    public AllBillRecyclerAdapter(Context context, List<String> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_layout_all_bill_item;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        String addressBean = getDataList().get(position);
        ImageView mBillItemIvGroup = holder.getView(R.id.bill_item_iv_group);
        TextView mBillItemTvGroup = holder.getView(R.id.bill_item_tv_group);
        ImageView mBillItemIvImg1 = holder.getView(R.id.bill_item_iv_img1);
        ImageView mBillItemIvImg2 = holder.getView(R.id.bill_item_iv_img2);
        ImageView mBillItemIvImg3 = holder.getView(R.id.bill_item_iv_img3);
        ImageView mBillItemIvImg4 = holder.getView(R.id.bill_item_iv_img4);
        TextView mBillItemTvNum = holder.getView(R.id.bill_item_tv_num);
        TextView mBillItemTvBill = holder.getView(R.id.bill_item_tv_bill);
        TextView mBillItemTvName = holder.getView(R.id.bill_item_tv_name);
        TextView mBillItemTvMoney = holder.getView(R.id.bill_item_tv_money);
        TextView mBillItemTvMakeUp = holder.getView(R.id.bill_item_tv_make_up);
        TextView mBillItemTvReopen = holder.getView(R.id.bill_item_tv_reopen);
        TextView mBillItemTvDetails = holder.getView(R.id.bill_item_tv_details);
    }

}
