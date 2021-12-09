package com.yuanbaogo.mine.order.finish.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.finish.LogisticsParamete;
import com.yuanbaogo.mine.order.receipt.detail.model.NewLogisticsModel;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @Description: 物流信息适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/14/21 2:58 PM
 * @Modifier:
 * @Modify:
 */
public class FinishDetailLogisticsAdapter extends BaseRecycleAdapter<NewLogisticsModel>
        implements View.OnClickListener {

    int layoutId;

    List<NewLogisticsModel> mDataList;

    public FinishDetailLogisticsAdapter(Context context,
                                        List<NewLogisticsModel> mDataList,
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
        NewLogisticsModel luckyFightRowsBean = mDataList.get(position);
        if (luckyFightRowsBean != null) {
            TextView finishDetailLogisticsTvTime = holder.getView(R.id.finish_detail_logistics_tv_time);
            if (luckyFightRowsBean.getCreateTime() != null && luckyFightRowsBean.getCreateTime() > 0) {
                finishDetailLogisticsTvTime.setText(DateUtils.getTime(luckyFightRowsBean.getCreateTime()));
            }
            TextView finishDetailLogisticsTvDes = holder.getView(R.id.finish_detail_logistics_tv_des);
            finishDetailLogisticsTvDes.setText(luckyFightRowsBean.getDes());
            TextView finishDetailLogisticsTvType = holder.getView(R.id.finish_detail_logistics_tv_type);
            if (luckyFightRowsBean.getDataStatus() != null) {
                finishDetailLogisticsTvType.setText(LogisticsParamete.setParamete(luckyFightRowsBean.getDataStatus()));
            }
        }
    }

    @Override
    public void onClick(View view) {

    }


}
