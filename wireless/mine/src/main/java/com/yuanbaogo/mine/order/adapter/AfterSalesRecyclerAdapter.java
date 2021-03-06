package com.yuanbaogo.mine.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.finance.pay.weight.AfterSalesDialogFragment;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.after.model.AfterSalesModel;
import com.yuanbaogo.mine.order.model.GoodsVOListItem;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.RoundImageView;

import java.util.List;

public class AfterSalesRecyclerAdapter extends RecyclerView.Adapter<AfterSalesRecyclerAdapter.AfterSalesViewHolder> {

    private static final String TAG = "AfterSalesRecyclerAdapter";
    public static final int OTHER = 0;
    public static final int REFUND_APPLY = 1;
    private final Context mContext;
    private final List<AfterSalesModel> mDataSet;
    private int flag = OTHER;
    private RefreshListListener mRefreshListListener;

    public AfterSalesRecyclerAdapter(Context context, List<AfterSalesModel> dataList) {
        mContext = context;
        mDataSet = dataList;
    }

    @Override
    public AfterSalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_layout_item_refund, parent, false);
        return new AfterSalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AfterSalesViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    public void setRefreshListListener(RefreshListListener refreshListListener) {
        this.mRefreshListListener = refreshListListener;
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Nullable
    public AfterSalesModel getItem(int position) {
        return mDataSet.get(position);
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    class AfterSalesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AfterSalesDialogFragment.OnCancelRefundListener {

        private LinearLayout mRefundItemLl;
        private TextView mRefundItemTvCode;
        private TextView mRefundItemTvStatus;
        private RoundImageView mOrderItemIvImg;
        private TextView mOrderItemTvName;
        private RecyclerView mOrderItemRv;
        private TextView mOrderItemTvMoney;
        private TextView mOrderItemTvNum;
        private TextView mRefundItemTvStatusBig;
        private TextView mRefundItemTvStatusSmall;
        private TextView mRefundItemTvCancel;
        private RelativeLayout mRefundItemRlStatusBig;
        private RelativeLayout mRefundItemRlRefund;
        private TextView mRefundItemTvRefund;
        private TextView mRefundItemTvRefundApply;
        private AfterSalesModel item;

        public AfterSalesViewHolder(View convertView) {
            super(convertView);
            mRefundItemLl = convertView.findViewById(R.id.refund_item_ll);
            mRefundItemTvCode = convertView.findViewById(R.id.refund_item_tv_code);
            mRefundItemTvStatus = convertView.findViewById(R.id.refund_item_tv_status);
            mOrderItemIvImg = convertView.findViewById(R.id.order_item_iv_img);
            mOrderItemTvName = convertView.findViewById(R.id.order_item_tv_name);
            mOrderItemRv = convertView.findViewById(R.id.order_item_rv);
            mOrderItemTvMoney = convertView.findViewById(R.id.order_item_tv_money);
            mOrderItemTvNum = convertView.findViewById(R.id.order_item_tv_num);
            mRefundItemTvStatusBig = convertView.findViewById(R.id.refund_item_tv_status_big);
            mRefundItemTvStatusSmall = convertView.findViewById(R.id.refund_item_tv_status_small);
            mRefundItemTvCancel = convertView.findViewById(R.id.refund_item_tv_cancel);

            mRefundItemRlStatusBig = convertView.findViewById(R.id.refund_item_rl_status_big);
            mRefundItemRlRefund = convertView.findViewById(R.id.refund_item_rl_refund);
            mRefundItemTvRefund = convertView.findViewById(R.id.refund_item_tv_refund);
            mRefundItemTvRefundApply = convertView.findViewById(R.id.refund_item_tv_refund_apply);

            mRefundItemLl.setOnClickListener(this);
            mRefundItemTvCancel.setOnClickListener(this);
            mRefundItemTvRefundApply.setOnClickListener(this);

            if (flag == OTHER) {
                mRefundItemRlStatusBig.setVisibility(View.VISIBLE);
                mRefundItemTvCancel.setVisibility(View.VISIBLE);
                mRefundItemRlRefund.setVisibility(View.GONE);
            } else {
                mRefundItemRlStatusBig.setVisibility(View.GONE);
                mRefundItemTvCancel.setVisibility(View.GONE);
                mRefundItemRlRefund.setVisibility(View.VISIBLE);
            }
        }

        @SuppressLint({"SetTextI18n", "StringFormatMatches"})
        public void onBindViewHolder(int position) {
            item = getItem(position);
            if (item == null) return;
            if (item.getOrderId() == null) return;
            mRefundItemTvCode.setText(item.getOrderId());
            initAfterSailedStatus(Math.toIntExact(item.getSalesStatus()), item.isExpressNumberFlag());
            Log.e(TAG, "onBindViewHolder: " + item.getSalesStatus());
            Log.e(TAG, "onBindViewHolder: toIntExact:" + Math.toIntExact(item.getSalesStatus()));
            initGoodsList(item.getGoodsVoList());
            mRefundItemTvStatus.setText(R.string.refund_refunds_goods);
            long realPrice = item.getAfterSalePrice() / 100;
            mOrderItemTvMoney.setText(mContext.getString(R.string.order_money, realPrice));
            mOrderItemTvNum.setText(mContext.getString(R.string.order_number, item.getGoodsCount()));
            if (item.getCreateTime() > 0) {
                mRefundItemTvRefund.setVisibility(View.VISIBLE);
                mRefundItemTvRefundApply.setVisibility(View.GONE);
            } else {
                mRefundItemTvRefund.setVisibility(View.GONE);
                mRefundItemTvRefundApply.setVisibility(View.VISIBLE);
            }
        }


        /**
         * ??????????????????
         * <p>
         * 1-??????:????????????,??????:????????? 2-??????:????????????,??????:????????? 3-??????:?????????,??????:?????????
         * 4-??????:?????????,??????:???????????? 5-??????:??????????????? 6-??????:?????? 7-??????
         *
         * @param afterSailedStatus /
         * @param expressNumberFlag /
         */
        private void initAfterSailedStatus(int afterSailedStatus, boolean expressNumberFlag) {
            if (afterSailedStatus == 1) {
                mRefundItemTvStatusBig.setText("?????????");
                mRefundItemTvStatusSmall.setText("??????????????????????????????????????????");
                mRefundItemTvCancel.setVisibility(View.VISIBLE);
            } else if (afterSailedStatus == 2) {
                if (expressNumberFlag) {
                    mRefundItemTvStatusBig.setText("?????????");
                    mRefundItemTvStatusSmall.setText("????????????????????????????????????????????????");
//                    mRefundItemTvCancel.setVisibility(View.GONE);
                } else {
                    mRefundItemTvStatusBig.setText("?????????");
                    mRefundItemTvStatusSmall.setText("?????????????????????????????????????????????????????????");
                    mRefundItemTvCancel.setVisibility(View.VISIBLE);
                }
            } else if (afterSailedStatus == 3) {
                mRefundItemTvStatusBig.setText("?????????");
                mRefundItemTvStatusSmall.setText("????????????????????????????????????");
                mRefundItemTvCancel.setVisibility(View.GONE);
            } else if (afterSailedStatus == 4) {
                mRefundItemTvStatusBig.setText("??????");
                mRefundItemTvStatusSmall.setText("?????????????????????????????????????????????");
                mRefundItemTvCancel.setVisibility(View.GONE);
            } else if (afterSailedStatus == 6) {
                mRefundItemTvStatusBig.setText("???????????????");
                mRefundItemTvStatusSmall.setText("??????????????????????????????????????????????????????????????????????????????????????????????????????");
                mRefundItemTvCancel.setVisibility(View.GONE);
            } else if (afterSailedStatus == 7) {
                mRefundItemTvStatusBig.setText("????????????");
                mRefundItemTvStatusSmall.setText("???????????????????????????????????????");
                mRefundItemTvCancel.setVisibility(View.GONE);
            } else {
                mRefundItemTvCancel.setVisibility(View.GONE);
            }
        }

        /**
         * ??????????????????
         *
         * @param goodsVOList /
         */
        private void initGoodsList(List<GoodsVOListItem> goodsVOList) {
            if (goodsVOList == null || goodsVOList.size() == 0) return;
            if (goodsVOList.size() == 1) {
                mOrderItemIvImg.setVisibility(View.VISIBLE);
                mOrderItemTvName.setVisibility(View.VISIBLE);
                mOrderItemRv.setVisibility(View.GONE);
                mOrderItemTvName.setText(goodsVOList.get(0).getGoodsTitle());
                Glide.with(mContext).load(goodsVOList.get(0).getGoodsImageUrl()).into(mOrderItemIvImg);
            } else {
                mOrderItemIvImg.setVisibility(View.GONE);
                mOrderItemTvName.setVisibility(View.GONE);
                mOrderItemRv.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
                mOrderItemRv.setLayoutManager(linearLayoutManager);
                mOrderItemRv.setAdapter(new OrderGoodsRecyclerAdapter(mContext, goodsVOList));
            }
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.refund_item_ll) {
                int salesStatus = item.getSalesStatus();
                if (salesStatus == 1) {
                    Log.d(TAG, "onClick: ??????????????????????????????" + salesStatus);
                    RouterHelper.toPendingReviewAfterDetail(item.getOrderId());
                } else if (salesStatus == 2) {
                    Log.d(TAG, "onClick: ??????????????????????????????????????????" + salesStatus);
                    RouterHelper.toSentBackAfterDetail(item.getOrderId(), item.getAfterSalesId());
                } else if (salesStatus == 3) {
                    Log.d(TAG, "onClick: ??????????????????????????????" + salesStatus);
                    RouterHelper.toPendingRefundDetail(item.getOrderId(), item.getAfterSalesId());
                } else if (salesStatus == 4) {
                    Log.d(TAG, "onClick: ?????????????????????????????????" + salesStatus);
                    RouterHelper.toAfterSalesFinishDetail(item.getOrderId(), item.getAfterSalesId());
                } else if (salesStatus == 6) {
                    Log.d(TAG, "onClick: ???????????????????????????" + salesStatus);
                    RouterHelper.toCancelAfterSalesDetail(item.getOrderId(), item.getAfterSalesId());
                } else if (salesStatus == 7) {
                    Log.d(TAG, "onClick: ?????????????????????????????????" + salesStatus);
                    RouterHelper.toPendingReviewFailAfterDetail(item.getOrderId(), item.getAfterSalesId());
                }

            } else if (id == R.id.refund_item_tv_cancel) {
                // ????????????
                AfterSalesDialogFragment mDialog = new AfterSalesDialogFragment();
                mDialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "AfterSalesRecyclerAdapter");
                mDialog.setOnCancelRefundListener(this);
            } else if (id == R.id.refund_item_tv_refund_apply) {
                // ????????????
                RouterHelper.toAfterSalesService(item.getOrderId());
            }
        }

        @Override
        public void onCancelRefund() {
            OrderNetworkUtils.cancelRefund(mContext, item.getOrderId(), successful -> {
                if (mRefreshListListener != null && successful) {
                    mRefreshListListener.refreshList();
                }
                ToastView.showToast(successful ? R.string.cancel_refund_success : R.string.cancel_refund_fail_limit);
            });
        }
    }

    public interface RefreshListListener {
        void refreshList();
    }

}
