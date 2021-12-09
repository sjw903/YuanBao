package com.yuanbaogo.shop.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.yuanbaogo.shop.R;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/2/21 9:33 AM
 * @Modifier:
 * @Modify:
 */
public class InviteFriendsPromoteAdapter extends BaseRecycleAdapter<String> {

    int layoutId;

    List<String> mDataList;

    public InviteFriendsPromoteAdapter(Context context,
                                       List<String> mDataList,
                                       int layoutId) {
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


    }

}
