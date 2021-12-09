package com.yuanbaogo.shop.productdetails.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.shop.R;

import java.util.List;

/**
 * @Description: 商品标签适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 11:15 AM
 * @Modifier:
 * @Modify:
 */
public class ShopProductDetailsLabelAdapter extends BaseRecycleAdapter<ArrayItemBean> {

    int layoutId;

    List<ArrayItemBean> mDataList;

    public ShopProductDetailsLabelAdapter(Context context, List<ArrayItemBean> mDataList, int layoutId) {
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
        ImageView itemShopProductDetailsLabelImg = holder.getView(R.id.item_shop_product_details_label_img);
        itemShopProductDetailsLabelImg.setImageResource(mDataList.get(position).getIcon());
    }

}
