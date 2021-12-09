package com.yuanbaogo.shop.spick.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.yuanbaogo.shop.R;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @Description: 秒杀商品适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/5/21 11:43 AM
 * @Modifier:
 * @Modify:
 */
public class SpickAdapter extends BaseRecycleAdapter<String> implements View.OnClickListener {

    int layoutId;

    List<String> datas;

    private OnCallRecyclerItem onCallBack;

    public void setOnCallBack(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public SpickAdapter(Context context, List<String> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
        this.layoutId = layoutId;
        this.datas = mDataList;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        RelativeLayout itemShopSpickRl = holder.getView(R.id.item_shop_spick_rl);
        itemShopSpickRl.setOnClickListener(this);
        itemShopSpickRl.setTag(position);
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }

}
