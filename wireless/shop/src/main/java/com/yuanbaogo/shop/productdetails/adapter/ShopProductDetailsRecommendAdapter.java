package com.yuanbaogo.shop.productdetails.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.yuanbaogo.commonlib.utils.PriceUtils;
import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.libbase.baseutil.glide.GlideRoundTransform;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.publics.model.RecommendBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @Description: 推荐相关商品适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 11:15 AM
 * @Modifier:
 * @Modify:
 */
public class ShopProductDetailsRecommendAdapter extends BaseRecycleAdapter<RecommendBean>
        implements View.OnClickListener {

    int layoutId;

    List<RecommendBean> mDataList;

    OnCallRecyclerItem onCallRecyclerItem;

    public void setOnCallRecyclerItem(OnCallRecyclerItem onCallRecyclerItem) {
        this.onCallRecyclerItem = onCallRecyclerItem;
    }

    public ShopProductDetailsRecommendAdapter(Context context,
                                              List<RecommendBean> mDataList,
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

        RelativeLayout itemShopProductDetailsRecommendRl = holder.getView(R.id.item_shop_product_details_recommend_rl);
        itemShopProductDetailsRecommendRl.setOnClickListener(this);
        itemShopProductDetailsRecommendRl.setTag(position);

        ImageView itemShopProductDetailsRecommendImg = holder.getView(R.id.item_shop_product_details_recommend_img);

        Glide.with(mContext)
                .load(mDataList.get(position).getCoverImages())
                .transform(new GlideRoundTransform(5))
                .into(itemShopProductDetailsRecommendImg);

        TextView itemShopProductDetailsRecommendTvTitle = holder.getView(R.id.item_shop_product_details_recommend_tv_title);
        itemShopProductDetailsRecommendTvTitle.setText(mDataList.get(position).getGoodsName());

        TextView itemShopProductDetailsRecommendTvPrice = holder.getView(R.id.item_shop_product_details_recommend_tv_price);
        itemShopProductDetailsRecommendTvPrice.setText("￥" + PriceUtils.doubleToStringNo(mDataList.get(position).getMinSalePrice()));
    }

    @Override
    public void onClick(View v) {
        onCallRecyclerItem.onCallItem(v, (int) v.getTag());
    }
}
