package com.yuanbaogo.shop.productdetails.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @Description: 商品分类适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/5/21 11:15 AM
 * @Modifier:
 * @Modify:
 */
public class ShopProductDetailsSortAdapter extends BaseRecycleAdapter<ArrayItemBean>
        implements View.OnClickListener {

    int layoutId;

    List<ArrayItemBean> mDataList;

    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallBack;

    public void setOnCallBack(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public ShopProductDetailsSortAdapter(Context context, List<ArrayItemBean> mDataList, int layoutId) {
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
        RelativeLayout itemShopProductDetailsSortRl = holder.getView(R.id.item_shop_product_details_sort_rl);
        ImageView itemShopProductDetailsSortImg = holder.getView(R.id.item_shop_product_details_sort_img);
        TextView itemShopProductDetailsSortTv = holder.getView(R.id.item_shop_product_details_sort_tv);

        if (position == mDataList.size() - 1) {
            itemShopProductDetailsSortTv.setText(mDataList.get(position).getName());
            itemShopProductDetailsSortTv.setVisibility(View.VISIBLE);
        } else {
            if (!TextUtils.isEmpty(mDataList.get(position).getImgUrl())) {
                Glide.with(mContext).load(mDataList.get(position).getImgUrl()).into(itemShopProductDetailsSortImg);
                itemShopProductDetailsSortTv.setVisibility(View.GONE);
            } else {
                itemShopProductDetailsSortTv.setText(mDataList.get(position).getName());
                itemShopProductDetailsSortTv.setVisibility(View.VISIBLE);
            }
        }
        itemShopProductDetailsSortRl.setOnClickListener(this);
        itemShopProductDetailsSortRl.setTag(position);
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }
}
