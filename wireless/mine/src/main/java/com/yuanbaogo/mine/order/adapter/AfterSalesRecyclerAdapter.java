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
         * 设置售后状态
         * <p>
         * 1-操作:提交审核,状态:待审核 2-操作:审核通过,状态:退货中 3-操作:已退货,状态:退款中
         * 4-操作:已退款,状态:退款成功 5-操作:订单已完成 6-操作:关闭 7-驳回
         *
         * @param afterSailedStatus /
         * @param expressNumberFlag /
         */
        private void initAfterSailedStatus(int afterSailedStatus, boolean expressNumberFlag) {
            if (afterSailedStatus == 1) {
                mRefundItemTvStatusBig.setText("待审核");
                mRefundItemTvStatusSmall.setText("您的申请已提交，等待平台审核");
                mRefundItemTvCancel.setVisibility(View.VISIBLE);
            } else if (afterSailedStatus == 2) {
                if (expressNumberFlag) {
                    mRefundItemTvStatusBig.setText("待收货");
                    mRefundItemTvStatusSmall.setText("商家已收到商品，正在等待商家退款");
//                    mRefundItemTvCancel.setVisibility(View.GONE);
                } else {
                    mRefundItemTvStatusBig.setText("待寄回");
                    mRefundItemTvStatusSmall.setText("审核已通过，请您尽快发货并填写快递单号");
                    mRefundItemTvCancel.setVisibility(View.VISIBLE);
                }
            } else if (afterSailedStatus == 3) {
                mRefundItemTvStatusBig.setText("待退款");
                mRefundItemTvStatusSmall.setText("商品已寄出，等待商家收货");
                mRefundItemTvCancel.setVisibility(View.GONE);
            } else if (afterSailedStatus == 4) {
                mRefundItemTvStatusBig.setText("完成");
                mRefundItemTvStatusSmall.setText("服务已完成，感谢您的支持与理解");
                mRefundItemTvCancel.setVisibility(View.GONE);
            } else if (afterSailedStatus == 6) {
                mRefundItemTvStatusBig.setText("申请已取消");
                mRefundItemTvStatusSmall.setText("申请已取消，您还有一次申请机会，如有需要可在申请售后界面重新提交申请");
                mRefundItemTvCancel.setVisibility(View.GONE);
            } else if (afterSailedStatus == 7) {
                mRefundItemTvStatusBig.setText("审核失败");
                mRefundItemTvStatusSmall.setText("平台审核失败，申请自动取消");
                mRefundItemTvCancel.setVisibility(View.GONE);
            } else {
                mRefundItemTvCancel.setVisibility(View.GONE);
            }
        }

        /**
         * 设置商品信息
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
                    Log.d(TAG, "onClick: 进入待审核详情页面：" + salesStatus);
                    RouterHelper.toPendingReviewAfterDetail(item.getOrderId());
                } else if (salesStatus == 2) {
                    Log.d(TAG, "onClick: 进入待寄回或待收货详情页面：" + salesStatus);
                    RouterHelper.toSentBackAfterDetail(item.getOrderId(), item.getAfterSalesId());
                } else if (salesStatus == 3) {
                    Log.d(TAG, "onClick: 进入待退款详情页面：" + salesStatus);
                    RouterHelper.toPendingRefundDetail(item.getOrderId(), item.getAfterSalesId());
                } else if (salesStatus == 4) {
                    Log.d(TAG, "onClick: 进入退款完成详情页面：" + salesStatus);
                    RouterHelper.toAfterSalesFinishDetail(item.getOrderId(), item.getAfterSalesId());
                } else if (salesStatus == 6) {
                    Log.d(TAG, "onClick: 进入取消详情页面：" + salesStatus);
                    RouterHelper.toCancelAfterSalesDetail(item.getOrderId(), item.getAfterSalesId());
                } else if (salesStatus == 7) {
                    Log.d(TAG, "onClick: 进入审核失败详情页面：" + salesStatus);
                    RouterHelper.toPendingReviewFailAfterDetail(item.getOrderId(), item.getAfterSalesId());
                }

            } else if (id == R.id.refund_item_tv_cancel) {
                // 取消申请
                AfterSalesDialogFragment mDialog = new AfterSalesDialogFragment();
                mDialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "AfterSalesRecyclerAdapter");
                mDialog.setOnCancelRefundListener(this);
            } else if (id == R.id.refund_item_tv_refund_apply) {
                // 申请售后
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
