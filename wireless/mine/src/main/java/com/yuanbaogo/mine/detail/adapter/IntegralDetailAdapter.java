package com.yuanbaogo.mine.detail.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.integral.model.YBIntegralBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @Description: 我的资产  明细适配器
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 2:21 PM
 * @Modifier:
 * @Modify:
 */
public class IntegralDetailAdapter extends BaseRecycleAdapter<YBIntegralBean.YBIntegralListBean> {

    int layoutId;

    List<YBIntegralBean.YBIntegralListBean> mDataList;

    /**
     * 1 五百专区
     * 2 五千专区
     * 3 五万专区
     * 4 元宝积分
     * 5 零钱
     */
    int type;

    public IntegralDetailAdapter(Context context,
                                 List<YBIntegralBean.YBIntegralListBean> mDataList, int type,
                                 int layoutId) {
        super(context, mDataList, layoutId);
        this.layoutId = layoutId;
        this.type = type;
        this.mDataList = mDataList;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        TextView itemAssetsIntegralDetailTvTitle = holder.getView(R.id.item_assets_integral_detail_tv_title);
        TextView itemAssetsIntegralDetailTvIntegral = holder.getView(R.id.item_assets_integral_detail_tv_integral);

        if (type == 1 || type == 2 || type == 3) {
            itemAssetsIntegralDetailTvTitle.setText(mDataList.get(position).getProjectName());
            if (mDataList.get(position).getBusinessType() == 1 || mDataList.get(position).getBusinessType() == 3) {
                itemAssetsIntegralDetailTvIntegral.setText("+" + (mDataList.get(position).getUseAmount() / 100));
                itemAssetsIntegralDetailTvIntegral.setTextColor(mContext.getResources().getColor(R.color.color00BFA5));
            } else if (mDataList.get(position).getBusinessType() == 2) {
                itemAssetsIntegralDetailTvIntegral.setText("-" + (mDataList.get(position).getUseAmount() / 100));
                itemAssetsIntegralDetailTvIntegral.setTextColor(mContext.getResources().getColor(R.color.colorF63C09));
            }
        } else if (type == 4 || type == 5) {
            itemAssetsIntegralDetailTvTitle.setText(mDataList.get(position).getTransactionType());
            if (mDataList.get(position).getDirect() == 0) {
                itemAssetsIntegralDetailTvIntegral.setText("+" + mDataList.get(position).getChangeNumber());
                itemAssetsIntegralDetailTvIntegral.setTextColor(mContext.getResources().getColor(R.color.color00BFA5));
            } else if (mDataList.get(position).getDirect() == 1) {
                itemAssetsIntegralDetailTvIntegral.setText("-" + mDataList.get(position).getChangeNumber());
                itemAssetsIntegralDetailTvIntegral.setTextColor(mContext.getResources().getColor(R.color.colorF63C09));
            }
        }


        TextView itemAssetsIntegralDetailTvTime = holder.getView(R.id.item_assets_integral_detail_tv_time);
        itemAssetsIntegralDetailTvTime.setText(DateUtils.timeStampToStrs(mDataList.get(position).getTransactionTime()));

        View itemAssetsIntegralDetailLine = holder.getView(R.id.item_assets_integral_detail_line);

    }
}
