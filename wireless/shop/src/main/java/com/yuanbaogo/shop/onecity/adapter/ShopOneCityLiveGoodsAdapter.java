package com.yuanbaogo.shop.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.shop.R;

import java.util.List;

/**
 * @Description: 直播推荐
 * @Params:
 * @Problem:  暂时不用
 * @Author: HG
 * @Date: 7/3/21 2:44 PM
 * @Modifier:
 * @Modify:
 */
public class ShopOneCityLiveGoodsAdapter extends BaseRecycleAdapter<ArrayItemBean>
        implements View.OnClickListener {

    private OnCallRecyclerItem onCallBack;

    List<ArrayItemBean> datas;
    int layoutId;

    public void setOnCallback(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public ShopOneCityLiveGoodsAdapter(Context context, List<ArrayItemBean> datas, int layoutId) {
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
        RelativeLayout itemShopOnecityLiveGoodsRlRoot = holder.getView(R.id.item_shop_onecity_live_goods_rl_root);
        ImageView itemShopOnecityLiveGoodsImg = holder.getView(R.id.item_shop_onecity_live_goods_img);
        itemShopOnecityLiveGoodsRlRoot.setOnClickListener(this);
        itemShopOnecityLiveGoodsRlRoot.setTag(position);
        itemShopOnecityLiveGoodsImg.setImageResource(datas.get(position).getIcon());
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }

}
