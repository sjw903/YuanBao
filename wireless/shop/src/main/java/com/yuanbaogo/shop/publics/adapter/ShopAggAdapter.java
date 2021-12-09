package com.yuanbaogo.shop.publics.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.publics.model.RecommendBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.view.CircleImageView;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/13/21 1:44 PM
 * @Modifier:
 * @Modify:
 */
public class ShopAggAdapter extends BaseRecycleAdapter<RecommendBean.RecommendAGGBean.RecommendAGGListBean>
        implements View.OnClickListener {

    int layoutId;

    List<RecommendBean.RecommendAGGBean.RecommendAGGListBean> mDataList;

    public ShopAggAdapter(Context context,
                          List<RecommendBean.RecommendAGGBean.RecommendAGGListBean> mDataList,
                          int layoutId
    ) {
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
        RelativeLayout itemShopAgg = holder.getView(R.id.item_shop_agg);

        CircleImageView itemShopAggImg = holder.getView(R.id.item_shop_agg_img);
        if (mDataList.get(position).getImageUrl() != null) {
            Glide.with(mContext).load(mDataList.get(position).getImageUrl()).into(itemShopAggImg);
        }else{
            itemShopAggImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_after_refund_only));
        }

    }

    @Override
    public void onClick(View view) {

    }

}
