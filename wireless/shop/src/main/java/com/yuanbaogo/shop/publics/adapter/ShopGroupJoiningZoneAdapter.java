package com.yuanbaogo.shop.publics.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @Description: 专区适配器（五百专区   五千专区  五万专区）
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 3:14 PM
 * @Modifier:
 * @Modify:
 */
public class ShopGroupJoiningZoneAdapter extends BaseRecycleAdapter<ArrayItemBean>
        implements View.OnClickListener {

    int layoutId;

    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallBack;

    /**
     * 数据
     */
    List<ArrayItemBean> datas;

    public void setOnCallback(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public ShopGroupJoiningZoneAdapter(Context context, List<ArrayItemBean> datas, int layoutId) {
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
        RelativeLayout itemShopGroupJoiningZoneRlRoot = holder.getView(R.id.item_shop_group_joining_zone_rl_root);
        itemShopGroupJoiningZoneRlRoot.setOnClickListener(this);
        itemShopGroupJoiningZoneRlRoot.setTag(position);

        ImageView itemShopGroupJoiningZoneImg = holder.getView(R.id.item_shop_group_joining_zone_img);
        initPX(itemShopGroupJoiningZoneImg);
        itemShopGroupJoiningZoneImg.setImageResource(datas.get(position).getIcon());
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }

}
