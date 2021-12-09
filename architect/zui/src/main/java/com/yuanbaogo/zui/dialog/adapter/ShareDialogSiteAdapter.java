package com.yuanbaogo.zui.dialog.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/20/21 9:05 AM
 * @Modifier:
 * @Modify:
 */
public class ShareDialogSiteAdapter extends BaseRecycleAdapter<ArrayItemBean>
        implements View.OnClickListener {

    Context mContext;

    private OnCallRecyclerItem onCallBack;

    List<ArrayItemBean> datas;

    int layoutId;

    public void setOnCallback(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public ShareDialogSiteAdapter(Context context, List<ArrayItemBean> datas, int layoutId) {
        super(context, datas, layoutId);
        this.mContext = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        RelativeLayout itemSiteViewRl = holder.getView(R.id.item_site_view_rl);
        TextView itemShareCiewTv = holder.getView(R.id.item_site_view_tv);
        itemShareCiewTv.setText(datas.get(position).getName());
        Drawable drawable = mContext.getResources().getDrawable(datas.get(position).getIcon());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        itemShareCiewTv.setCompoundDrawables(null, drawable, null, null);
        itemShareCiewTv.setCompoundDrawablePadding(30);
        itemSiteViewRl.setOnClickListener(this);
        itemSiteViewRl.setTag(position);
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }

}
