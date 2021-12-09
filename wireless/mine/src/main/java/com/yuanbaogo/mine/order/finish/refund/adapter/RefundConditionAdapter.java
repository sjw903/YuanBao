package com.yuanbaogo.mine.order.finish.refund.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.after.logistics.LogisticsCompanyModel;
import com.yuanbaogo.mine.order.finish.refund.dialog.RefundReasonBottomDialog;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/13/21 4:12 PM
 * @Modifier:
 * @Modify:
 */
public class RefundConditionAdapter extends BaseRecycleAdapter<LogisticsCompanyModel> implements View.OnClickListener {

    int layoutId;

    List<LogisticsCompanyModel> mDataList;
    /**
     * item 回调
     */
    private OnCallRecyclerItem onCallBack;

    public void setOnCallBack(OnCallRecyclerItem onCallBack) {
        this.onCallBack = onCallBack;
    }

    public RefundConditionAdapter(Context context,
                                  List<LogisticsCompanyModel> mDataList,
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
        RelativeLayout itemRefundConditionRl = holder.getView(R.id.item_refund_condition_rl);
        itemRefundConditionRl.setOnClickListener(this);
        itemRefundConditionRl.setTag(position);

        CheckBox itemRefundConditionCB = holder.getView(R.id.item_refund_condition_cb);
        //检查set里是否包含position，包含则显示选中的背景色，不包含则反之
        Set<Integer> positionSet = RefundReasonBottomDialog.positionSet;
        if (positionSet.contains(position)) {
            itemRefundConditionCB.setChecked(true);
        } else {
            itemRefundConditionCB.setChecked(false);
        }

        TextView itemRefundConditionTvTitle = holder.getView(R.id.item_refund_condition_tv_title);
        itemRefundConditionTvTitle.setText(mDataList.get(position).getName());
    }

    @Override
    public void onClick(View view) {
        onCallBack.onCallItem(view, (int) view.getTag());
    }
}
