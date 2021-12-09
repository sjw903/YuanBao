package com.yuanbaogo.shop.publics.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.utils.PriceUtils;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseRowsBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.shop.R;

import java.util.List;

/**
 * @Description: 一城一品（首推爆款）  元宝国际（好货必Buy） 适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/3/21 2:44 PM
 * @Modifier:
 * @Modify:
 */
public class ShopOneCityExplosionAdapter extends BaseRecycleAdapter<SearchMerchandiseRowsBean>
        implements View.OnClickListener {

    /**
     * Item点击事件
     */
    private OnCallRecyclerItem onCallBack;

    /**
     * 数据集合
     */
    List<SearchMerchandiseRowsBean> datas;

    int layoutId;

    public void setOnCallback(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public ShopOneCityExplosionAdapter(Context context, List<SearchMerchandiseRowsBean> datas, int layoutId) {
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
        SearchMerchandiseRowsBean recommendBean = datas.get(position);

        RelativeLayout itemShopOnecityLiveExplosionRlRoot = holder.getView(R.id.item_shop_onecity_live_explosion_rl_root);
        itemShopOnecityLiveExplosionRlRoot.setOnClickListener(this);
        itemShopOnecityLiveExplosionRlRoot.setTag(position);

        //销售价格
        TextView itemShopOnecityLiveExplosionTvPrice = holder.getView(R.id.item_shop_onecity_live_explosion_tv_price);
        itemShopOnecityLiveExplosionTvPrice.setText(PriceUtils.doubleToStringNo(recommendBean.getMinSalePrice()));

        //原价
        TextView itemShopOnecityLiveExplosionTvOriginalPrice = holder.getView(R.id.item_shop_onecity_live_explosion_tv_original_price);
        itemShopOnecityLiveExplosionTvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        itemShopOnecityLiveExplosionTvOriginalPrice.setText("¥" + PriceUtils.doubleToStringNo(recommendBean.getMinLinePrice()));

        //商品封面
        ImageView itemShopOnecityLiveExplosionImg = holder.getView(R.id.item_shop_onecity_live_explosion_img);
        Glide.with(mContext).load(recommendBean.getCoverImages()).into(itemShopOnecityLiveExplosionImg);

        //商品名称
        TextView itemShopOnecityLiveExplosionTvName = holder.getView(R.id.item_shop_onecity_live_explosion_tv_name);
        itemShopOnecityLiveExplosionTvName.setText(recommendBean.getGoodsName());

    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }

}
