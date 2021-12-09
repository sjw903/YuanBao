package com.yuanbaogo.shop.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.shop.onecity.model.QueryVenueBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.shop.R;

import java.util.List;

/**
 * @Description: 场馆适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/3/21 2:44 PM
 * @Modifier:
 * @Modify:
 */
public class ShopOneCityPavilionAdapter extends BaseRecycleAdapter<QueryVenueBean>
        implements View.OnClickListener {

    int layoutId;

    List<QueryVenueBean> datas;

    private OnCallRecyclerItem onCallBack;

    public void setOnCallback(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public ShopOneCityPavilionAdapter(Context context, List<QueryVenueBean> datas, int layoutId) {
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
        RelativeLayout itemShopOnecityPavilionRlRoot = holder.getView(R.id.item_shop_onecity_live_goods_rl_root);
        itemShopOnecityPavilionRlRoot.setOnClickListener(this);
        itemShopOnecityPavilionRlRoot.setTag(position);

        ImageView itemShopOnecityPavilionImg = holder.getView(R.id.item_shop_onecity_live_goods_img);
        initPX(itemShopOnecityPavilionImg);
        Glide.with(mContext).load(datas.get(position).getImageUrl()).into(itemShopOnecityPavilionImg);
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }

    public void initPX(View view) {
        int screenWidth = ScreenUtils.getScreenWidth(mContext); //屏幕宽度
        int width = screenWidth - 70;
        int item = width / 2;
        view.setLayoutParams(new RelativeLayout.LayoutParams(item, item / 4 * 2 + 80));
    }

}
