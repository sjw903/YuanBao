package com.yuanbaogo.zui.head.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @Description: 搜索商品适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 11:15 AM
 * @Modifier:
 * @Modify:
 */
public class ShopSearchHeadAdapter extends BaseRecycleAdapter<String> implements View.OnClickListener {

    int layoutId;

    List<String> mDataList;

    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallBack;

    public void setOnCallBack(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public ShopSearchHeadAdapter(Context context, List<String> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
        this.layoutId = layoutId;
        this.mDataList = mDataList;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        RelativeLayout itemSearchHeadRl = holder.getView(R.id.item_search_head_rl);
        itemSearchHeadRl.setOnClickListener(this);
        itemSearchHeadRl.setTag(position);


        TextView itemSearchHeadTvContent = holder.getView(R.id.item_search_head_tv_content);
        itemSearchHeadTvContent.setText(mDataList.get(position));

    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }
}
