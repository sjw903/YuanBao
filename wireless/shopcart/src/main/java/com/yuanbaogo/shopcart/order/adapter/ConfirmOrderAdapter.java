package com.yuanbaogo.shopcart.order.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.shopcart.R;
import com.yuanbaogo.shopcart.main.model.ShoppingCartBean;
import com.yuanbaogo.shopcart.order.model.OrderCheckBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/4/21 5:20 PM
 * @Modifier:
 * @Modify:
 */
public class ConfirmOrderAdapter extends BaseRecycleAdapter<OrderCheckBean> {

    List<OrderCheckBean> datas;

    int layoutId;

    public ConfirmOrderAdapter(Context context, List<OrderCheckBean> datas, int layoutId) {
        super(context, datas, layoutId);
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {

        ImageView itemShopCartChildImgGoods = holder.getView(R.id.item_shop_cart_child_img_goods);
        Glide.with(mContext).load(datas.get(position).getCoverImages()).into(itemShopCartChildImgGoods);

        TextView itemShopCartChildTvGoodsName = holder.getView(R.id.item_shop_cart_child_tv_goods_name);
        itemShopCartChildTvGoodsName.setText(datas.get(position).getGoodsName());

        TextView itemShopCartChildTvReduce = holder.getView(R.id.item_shop_cart_child_tv_reduce);
        itemShopCartChildTvReduce.setText("X " + datas.get(position).getNum());

        TextView itemShopCartChildTvPriceNew = holder.getView(R.id.item_shop_cart_child_tv_price_new);
        if (datas.get(position).getGroupGoodsPrice() != null) {
            itemShopCartChildTvPriceNew.setText("￥" + datas.get(position).getGroupGoodsPrice() / 100);
        } else {
            itemShopCartChildTvPriceNew.setText("￥" + datas.get(position).getSalePrice() / 100);
        }

        TextView itemShopCartChildTvReturn = holder.getView(R.id.item_shop_cart_child_tv_return);
        itemShopCartChildTvReturn.setText(datas.get(position).getSkuIndexesName());

    }

}
