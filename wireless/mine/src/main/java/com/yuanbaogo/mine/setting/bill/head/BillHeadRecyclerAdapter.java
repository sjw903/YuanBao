package com.yuanbaogo.mine.setting.bill.head;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

public class BillHeadRecyclerAdapter extends BaseRecycleAdapter<String> {

    public BillHeadRecyclerAdapter(Context context, List<String> mDataList) {
        this(context, mDataList, 0);
    }

    public BillHeadRecyclerAdapter(Context context, List<String> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_layout_bill_head_item;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        String addressBean = getDataList().get(position);
        TextView mBillHeadItemTvName = holder.getView(R.id.bill_head_item_tv_name);
        ImageView mBillHeadItemIvUpdate = holder.getView(R.id.bill_head_item_iv_update);
        mBillHeadItemTvName.setText(addressBean);
        mBillHeadItemIvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterHelper.toUpdateUnitBillHead(addressBean);
//                if (flag == UNIT) {
//                    RouterHelper.toAddUnitBillHead("");
//                } else {
//                    RouterHelper.toAddPersonalBillHead("");
//                }
            }
        });
    }

}
