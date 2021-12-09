package com.yuanbaogo.mine.groupaccount.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.groupaccount.model.RechargeNowBean;
import com.yuanbaogo.mine.groupaccount.view.RechargeNowActivity;
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
 * @Date: 7/29/21 10:33 AM
 * @Modifier:
 * @Modify:
 */
public class RechargeNowAdapter extends BaseRecycleAdapter<RechargeNowBean> implements View.OnClickListener {

    int layoutId;

    List<RechargeNowBean> mDataList;

    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallItem;

    public void setOnCallItem(OnCallRecyclerItem onCallItem) {
        this.onCallItem = onCallItem;
    }

    public RechargeNowAdapter(Context context, List<RechargeNowBean> mDataList, int layoutId) {
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
        RelativeLayout itemAssetsRechargeNowRl = holder.getView(R.id.item_assets_recharge_now_rl);
        itemAssetsRechargeNowRl.setOnClickListener(this);
        itemAssetsRechargeNowRl.setTag(position);
        ImageView itemAssetsRechargeNowImg = holder.getView(R.id.item_assets_recharge_now_img);
        ImageView itemAssetsRechargeNowImgSelect = holder.getView(R.id.item_assets_recharge_now_img_select);
        if (mDataList.get(position).getAreaType() == 1) {
            itemAssetsRechargeNowImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_assets_recharge_now_500_un));
        } else if (mDataList.get(position).getAreaType() == 2) {
            itemAssetsRechargeNowImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_assets_recharge_now_5000));
        }
        Set<String> positionSet = RechargeNowActivity.positionSet;
        //检查set里是否包含position，包含则显示选中的背景色，不包含则反之
        if (positionSet.contains(mDataList.get(position).getId())) {
            itemAssetsRechargeNowImgSelect.setVisibility(View.VISIBLE);
        } else {
            itemAssetsRechargeNowImgSelect.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        onCallItem.onCallItem(view, (int) view.getTag());
    }
}
