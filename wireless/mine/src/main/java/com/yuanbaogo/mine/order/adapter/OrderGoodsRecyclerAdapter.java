package com.yuanbaogo.mine.order.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.model.GoodsVOListItem;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.view.RoundImageView;

import java.util.List;

public class OrderGoodsRecyclerAdapter extends BaseRecycleAdapter<GoodsVOListItem> {

    public OrderGoodsRecyclerAdapter(Context context, List<GoodsVOListItem> mDataList) {
        this(context, mDataList, 0);
    }

    public OrderGoodsRecyclerAdapter(Context context, List<GoodsVOListItem> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_layout_order_good_item;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        GoodsVOListItem goodsVOList = getDataList().get(position);
        RoundImageView orderItemIvGoodImg = holder.getView(R.id.order_item_iv_good_img);
        Glide.with(orderItemIvGoodImg.getContext()).load(goodsVOList.getGoodsImageUrl()).into(orderItemIvGoodImg);
    }

}
