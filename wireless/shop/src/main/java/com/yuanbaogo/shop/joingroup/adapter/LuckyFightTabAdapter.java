package com.yuanbaogo.shop.joingroup.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.joingroup.model.LuckDrawListBean;
import com.yuanbaogo.shop.joingroup.view.LuckyFightActivity;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/6/21 11:34 AM
 * @Modifier:
 * @Modify:
 */
public class LuckyFightTabAdapter extends BaseRecycleAdapter<LuckDrawListBean>
        implements View.OnClickListener {

    int layoutId;

    List<LuckDrawListBean> mDataList;

    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallBack;

    public void setOnCallBack(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public LuckyFightTabAdapter(Context context,
                                List<LuckDrawListBean> mDataList,
                                int layoutId) {
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
        TextView shopLuckyDrawTvLowestInterval = holder.getView(R.id.shop_lucky_draw_tv_lowest_interval);
        shopLuckyDrawTvLowestInterval.setOnClickListener(this);
        shopLuckyDrawTvLowestInterval.setTag(position);

        shopLuckyDrawTvLowestInterval.setText(mDataList.get(position).getName());

        Set<Integer> positionSet = LuckyFightActivity.positionSet;
        //检查set里是否包含position，包含则显示选中的背景色，不包含则反之
        if (positionSet.contains(position)) {
            shopLuckyDrawTvLowestInterval.setTextColor(mContext.getResources().getColor(R.color.colorFFFFFF));
            shopLuckyDrawTvLowestInterval.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_shop_lucky_draw_gradient_bg_un));
        } else {
            shopLuckyDrawTvLowestInterval.setTextColor(mContext.getResources().getColor(R.color.colorEA5504));
            shopLuckyDrawTvLowestInterval.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_shop_lucky_draw_gradient_bg));
        }
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }
}
