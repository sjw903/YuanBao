package com.yuanbaogo.shop.productdetails.adapter;

import android.content.Context;
import android.widget.TextView;

import com.luck.picture.lib.tools.StringUtils;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.productdetails.model.ProductParametersBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 商品参数
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 11:15 AM
 * @Modifier:
 * @Modify:
 */
public class ShopProductDetailsParameterAdapter extends BaseRecycleAdapter<ProductParametersBean> {

    int layoutId;

    List<ProductParametersBean> mDataList;

    int type = 1;

    public void setType(int type) {
        this.type = type;
    }

    public ShopProductDetailsParameterAdapter(Context context, List<ProductParametersBean> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
        this.layoutId = layoutId;
        this.mDataList = mDataList;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public int getItemCount() {
        if (type == 1) {
            if (mDataList.size() > 2) {
                return 2;
            }
        }
        return super.getItemCount();
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {

        TextView itemShopProductDetailsRecommendTvTitle = holder.getView(R.id.item_shop_product_details_recommend_tv_title);
        itemShopProductDetailsRecommendTvTitle.setText(mDataList.get(position).getSpecName());
        TextView itemShopProductDetailsRecommendTvPrice = holder.getView(R.id.item_shop_product_details_recommend_tv_price);
        String valueAgg = Arrays.toString(mDataList.get(position).getValueAggList());
        itemShopProductDetailsRecommendTvPrice.setText(valueAgg.substring(1, valueAgg.length() - 1));

    }

}
