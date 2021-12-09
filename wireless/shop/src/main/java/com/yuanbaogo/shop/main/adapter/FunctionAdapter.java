package com.yuanbaogo.shop.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @Description: 功能适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/19/21 1:19 PM
 * @Modifier:
 * @Modify:
 */
public class FunctionAdapter extends BaseRecycleAdapter<ArrayItemBean>
        implements View.OnClickListener {

    /**
     * 布局
     */
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

    public FunctionAdapter(Context context, List<ArrayItemBean> datas, int layoutId) {
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
        RelativeLayout itemShopFunctionRlRoot = holder.getView(R.id.item_shop_function_rl_root);
        itemShopFunctionRlRoot.setOnClickListener(this);
        itemShopFunctionRlRoot.setTag(position);

        ImageView itemShopFunctionImg = holder.getView(R.id.item_shop_function_img);
        itemShopFunctionImg.setImageResource(datas.get(position).getIcon());

        TextView itemShopFunctionTv = holder.getView(R.id.item_shop_function_tv);
        itemShopFunctionTv.setText(datas.get(position).getName());
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }

}
