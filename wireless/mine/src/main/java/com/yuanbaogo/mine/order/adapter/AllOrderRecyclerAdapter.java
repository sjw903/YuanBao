package com.yuanbaogo.mine.order.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.model.GoodsVOListItem;
import com.yuanbaogo.mine.order.model.OrderListResponseListItem;
import com.yuanbaogo.mine.order.pay.OrderCancelBottomDialog;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.RoundImageView;

import java.util.List;
import java.util.Objects;

import static com.yuanbaogo.mine.order.utils.Configuration.ORDER_CANCEL_TYPE;
import static com.yuanbaogo.mine.order.utils.Configuration.ORDER_FINISH_TYPE;
import static com.yuanbaogo.mine.order.utils.Configuration.ORDER_PAY_TYPE;
import static com.yuanbaogo.mine.order.utils.Configuration.ORDER_RECEIPT_TYPE;
import static com.yuanbaogo.mine.order.utils.Configuration.ORDER_RECEIPT_TYPE_STOCK;

public class AllOrderRecyclerAdapter extends RecyclerView.Adapter<AllOrderRecyclerAdapter.OrderViewHolder> {

    private static final String TAG = "AllOrderRecyclerAdapter";
    private static final int PAY_TYPE = 1;
    private static final int RECEIPT_TYPE = 2;
    private static final int FINISH_TYPE = 3;
    private static final int CANCEL_TYPE = 4;
    private final Context mContext;
    private final List<OrderListResponseListItem> mDataSet;
    private OrderListRefreshListener mOrderListRefreshListener;

    public AllOrderRecyclerAdapter(Context context, List<OrderListResponseListItem> dataList) {
        mContext = context;
        mDataSet = dataList;
    }

    public interface OrderListRefreshListener {
        void orderListRefresh();
    }

    public void setOrderListRefreshListener(OrderListRefreshListener orderListRefreshListener) {
        this.mOrderListRefreshListener = orderListRefreshListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == PAY_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_layout_item_pay, parent, false);
            return new PayViewHolder(view);
        } else if (viewType == RECEIPT_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_layout_item_receipt, parent, false);
            return new ReceiptViewHolder(view);
        } else if (viewType == FINISH_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_layout_item_finish, parent, false);
            return new FinishViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_layout_item_cancel, parent, false);
            return new CancelViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    @Override
    public int getItemViewType(int position) {
        int orderStatus = Objects.requireNonNull(getItem(position)).getOrderStatus();
        if (orderStatus == ORDER_PAY_TYPE) {
            return PAY_TYPE;
        } else if (orderStatus == ORDER_RECEIPT_TYPE || orderStatus == ORDER_RECEIPT_TYPE_STOCK) {
            return RECEIPT_TYPE;
        } else if (orderStatus == ORDER_FINISH_TYPE) {
            return FINISH_TYPE;
        } else if (orderStatus == ORDER_CANCEL_TYPE) {
            return CANCEL_TYPE;
        } else {
            return FINISH_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Nullable
    public OrderListResponseListItem getItem(int position) {
        return mDataSet.get(position);
    }

    class PayViewHolder extends OrderViewHolder implements OrderCancelBottomDialog.OnNextRefund {

        private final LinearLayout orderItemLlPay;
        private final TextView orderItemTvCancel;
        private final TextView orderItemTvPay;
        private final TextView orderItemTvAddress;
        private OrderListResponseListItem item;
        private int position = -1;

        public PayViewHolder(View view) {
            super(view);
            orderItemLlPay = view.findViewById(R.id.order_item_ll_pay);
            orderItemTvCancel = view.findViewById(R.id.order_item_tv_cancel);
            orderItemTvPay = view.findViewById(R.id.order_item_tv_pay);
            orderItemTvAddress = view.findViewById(R.id.order_item_tv_address);
            orderItemLlPay.setOnClickListener(this);
            orderItemTvCancel.setOnClickListener(this);
            orderItemTvPay.setOnClickListener(this);
            orderItemTvAddress.setOnClickListener(this);
        }

        @Override
        public void onBindViewHolder(int position) {
            super.onBindViewHolder(position);
            item = getItem(position);
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            int id = v.getId();
            if (id == R.id.order_item_ll_pay) {
                RouterHelper.toPayDetail(item.getTotalOrderId());
            } else if (id == R.id.order_item_tv_cancel) {
                // 取消订单
                OrderCancelBottomDialog dialog = new OrderCancelBottomDialog(mContext);
                dialog.setOnNextRefundListener(this);
                dialog.show();
            } else if (id == R.id.order_item_tv_address) {
                // 修改地址
                RouterHelper.toPayDetail(item.getTotalOrderId(), true);
            } else if (id == R.id.order_item_tv_pay) {
                // 付款
                toPay(item);
            }
        }

        private void toPay(OrderListResponseListItem item) {
            onCallItem.onClickPayment(item);
        }

        @Override
        public void onNextRefund(String reason, int position) {
            OrderNetworkUtils.cancelOrder(mContext, item.getTotalOrderId(), successful -> {
                ToastView.showToast(successful ? R.string.cancel_order_success : R.string.cancel_order_fail);
                if (!successful || position < 0) {
                    return;
                }
                if (mOrderListRefreshListener != null) {
                    mOrderListRefreshListener.orderListRefresh();
                }
                if (mDataSet.size() > position) {
                    mDataSet.remove(position);
                    notifyDataSetChanged();
                } else {
                    notifyItemRemoved(position);
                }
            });
        }
    }

    class ReceiptViewHolder extends OrderViewHolder {

        private final LinearLayout orderLlReceipt;
        private final TextView orderItemTvConfirm;
        private final TextView orderItemTvRefund;
        private OrderListResponseListItem item;
        private int position = -1;

        public ReceiptViewHolder(View view) {
            super(view);
            orderLlReceipt = view.findViewById(R.id.order_ll_receipt);
            orderItemTvConfirm = view.findViewById(R.id.order_item_tv_confirm);
            orderItemTvRefund = view.findViewById(R.id.order_item_tv_refund);
            orderLlReceipt.setOnClickListener(this);
            orderItemTvConfirm.setOnClickListener(this);
            orderItemTvRefund.setOnClickListener(this);
        }

        @Override
        public void onBindViewHolder(int position) {
            super.onBindViewHolder(position);
            item = getItem(position);
            if (item == null) return;
//            if (item.getOrderStatus() == ORDER_RECEIPT_TYPE) {//TODO HG 待发货  只显示申请退款按钮
//                orderItemTvConfirm.setVisibility(View.GONE);
//                orderItemTvRefund.setVisibility(View.VISIBLE);
//            } else if (item.getOrderStatus() == ORDER_RECEIPT_TYPE_STOCK) {//TODO HG 待收货  只显示确认收货按钮
//                orderItemTvConfirm.setVisibility(View.VISIBLE);
//                orderItemTvRefund.setVisibility(View.GONE);
//            }
//            // 有售后状态时无操作
//            if (item.getAfterSailedStatus() > 0
//                    && item.getAfterSailedStatus() != 6
//                    && item.getAfterSailedStatus() != 7 || item.getLuckStatus() == 2 || item.getLuckStatus() == 3) {
//                orderItemTvConfirm.setVisibility(View.GONE);
//                orderItemTvRefund.setVisibility(View.GONE);
//            }
            if (item.getOrderActivityType() == 1) {//普通订单
                if (item.getAfterSailedStatus() != 1) {// 无售后
                    if (item.getOrderStatus() == 2) {//待收货(正在出库)
                        orderItemTvRefund.setVisibility(View.VISIBLE);
                        orderItemTvConfirm.setVisibility(View.GONE);
                    } else {//待收货(已出库)
                        orderItemTvRefund.setVisibility(View.GONE);
                        orderItemTvConfirm.setVisibility(View.VISIBLE);
                    }
                } else {//有售后
                    orderItemTvConfirm.setVisibility(View.GONE);
                    orderItemTvRefund.setVisibility(View.GONE);
                }
            } else if (item.getOrderActivityType() == 3) {//幸运拼团
                if (item.getAfterSailedStatus() != 1) {// 无售后
                    if (item.getLuckStatus() == 1) {//待抽奖
                        orderItemTvRefund.setVisibility(View.VISIBLE);
                        orderItemTvConfirm.setVisibility(View.GONE);
                    } else if (item.getLuckStatus() == 2) {//已经中奖
                        if (item.getOrderStatus() == 2) {//待收货(正在出库)
                            orderItemTvRefund.setVisibility(View.GONE);
                            orderItemTvConfirm.setVisibility(View.GONE);
                        } else if (item.getOrderStatus() == 3) {//待收货(已出库)
                            orderItemTvRefund.setVisibility(View.GONE);
                            orderItemTvConfirm.setVisibility(View.VISIBLE);
                        }
                    } else {//没有中奖
                        orderItemTvRefund.setVisibility(View.GONE);
                        orderItemTvConfirm.setVisibility(View.GONE);
                    }
                } else {//有售后
                    orderItemTvRefund.setVisibility(View.GONE);
                    orderItemTvConfirm.setVisibility(View.GONE);
                }
            }
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            int id = v.getId();
            if (id == R.id.order_item_tv_confirm) {
                // 确认收货
                OrderNetworkUtils.confirmGoods(mContext, item.getTotalOrderId(), successful -> {
                    ToastView.showToast(successful ? R.string.confirm_success : R.string.confirm_fail);
                    if (successful && position >= 0) {
                        mDataSet.remove(position);
                        notifyDataSetChanged();
                    }
                    if (mOrderListRefreshListener != null) {
                        mOrderListRefreshListener.orderListRefresh();
                    }
                });
            } else if (id == R.id.order_item_tv_refund) {
                // 申请退款
                RouterHelper.toApplyRefund(item.getTotalOrderId(),1);
            } else if (id == R.id.order_ll_receipt) {
                RouterHelper.toReceiptDetail(item.getTotalOrderId());
            }
        }
    }

    class CancelViewHolder extends OrderViewHolder {

        private final LinearLayout orderLlCancel;
        private final TextView orderItemTvRefundDetails;
        private OrderListResponseListItem item;

        public CancelViewHolder(View view) {
            super(view);
            orderLlCancel = view.findViewById(R.id.order_ll_cancel);
            orderItemTvRefundDetails = view.findViewById(R.id.order_item_tv_refund_details);
            orderItemTvRefundDetails.setOnClickListener(this);
            orderLlCancel.setOnClickListener(this);
        }

        @Override
        public void onBindViewHolder(int position) {
            super.onBindViewHolder(position);
            item = getItem(position);
            if (item == null) return;
//            if (item.getOrderStatus() > 2) {
//                orderItemTvRefundDetails.setVisibility(View.VISIBLE);
//            } else {
//                orderItemTvRefundDetails.setVisibility(View.GONE);
//            }
            if (item.getAfterSailedStatus() > 0) {
                orderItemTvRefundDetails.setVisibility(View.VISIBLE);
            } else {
                orderItemTvRefundDetails.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.order_item_tv_refund_details) {
                // 退款明细
//                RouterHelper.toRefundDetail(item.getTotalOrderId());
                //已取消
                RouterHelper.toCancelDetail(item.getTotalOrderId());
            } else if (id == R.id.order_ll_cancel) {
                RouterHelper.toCancelDetail(item.getTotalOrderId());
            }
        }
    }

    class FinishViewHolder extends OrderViewHolder {

        private LinearLayout orderItemLlFinish;
        private LinearLayout orderItemLlRefund;
        private LinearLayout orderLlFinish;
        private final TextView orderItemTvBill;
        private final TextView orderItemTvService;
        private final TextView orderItemTvEvaluated;
        private final TextView orderItemTvRefund;
        private OrderListResponseListItem item;

        public FinishViewHolder(View view) {
            super(view);
            orderLlFinish = view.findViewById(R.id.order_ll_finish);
            orderItemTvBill = view.findViewById(R.id.order_item_tv_bill);
            orderItemTvService = view.findViewById(R.id.order_item_tv_service);
            orderItemTvEvaluated = view.findViewById(R.id.order_item_tv_evaluated);
            orderItemLlFinish = view.findViewById(R.id.order_item_ll_finish);
            orderItemLlRefund = view.findViewById(R.id.order_item_ll_refund);
            orderItemTvRefund = view.findViewById(R.id.order_item_tv_refunding);
            orderLlFinish.setOnClickListener(this);
            orderItemTvBill.setOnClickListener(this);
            orderItemTvService.setOnClickListener(this);
            orderItemTvEvaluated.setOnClickListener(this);
            orderItemTvRefund.setOnClickListener(this);
        }

        @Override
        public void onBindViewHolder(int position) {
            super.onBindViewHolder(position);
            item = getItem(position);
            if (item == null) return;
            if (item.getAfterSailedStatus() > 0) {
                orderItemLlRefund.setVisibility(View.VISIBLE);
                orderItemLlFinish.setVisibility(View.GONE);
            } else {
                orderItemLlRefund.setVisibility(View.GONE);
                orderItemLlFinish.setVisibility(View.VISIBLE);
                if (item.isCommented()) {
                    orderItemTvEvaluated.setVisibility(View.GONE);
                    orderItemTvEvaluated.setText(R.string.order_btn_evaluated);
                } else {
                    orderItemTvEvaluated.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            int id = v.getId();
            int salesStatus = item.getAfterSailedStatus();
            if (id == R.id.order_item_tv_bill) {
                // 电子发票
                RouterHelper.toALLBill();
            } else if (id == R.id.order_item_tv_service) {
                // 售后服务
                RouterHelper.toAfterSalesService(item.getTotalOrderId());
            } else if (id == R.id.order_item_tv_evaluated) {
                // 评价
                RouterHelper.toEvaluation(item.getTotalOrderId());
            } else if (id == R.id.order_item_tv_refunding) {
                // 黄烁要求修改 ，跳转到售后列表
                RouterHelper.toAfterSales();
            } else if (id == R.id.order_ll_finish) {
                // 详情
//                if (item.getAfterSailedStatus() > 0) {
//                    toAfterSales(salesStatus);
//                } else {
//                    RouterHelper.toFinishDetail(item.getTotalOrderId());
//                }
                RouterHelper.toFinishDetail(item.getTotalOrderId());
            }
        }

        private void toAfterSales(int salesStatus) {
            if (salesStatus == 1) {
                Log.d(TAG, "onClick: 进入待审核详情页面：" + salesStatus);
                RouterHelper.toPendingReviewAfterDetail(item.getTotalOrderId());
            } else if (salesStatus == 2) {
                Log.d(TAG, "onClick: 进入待寄回或待收货详情页面：" + salesStatus);
                RouterHelper.toSentBackAfterDetail(item.getTotalOrderId(), item.getAfterSalesId());
            } else if (salesStatus == 3) {
                Log.d(TAG, "onClick: 进入待退款详情页面：" + salesStatus);
                RouterHelper.toPendingRefundDetail(item.getTotalOrderId(), item.getAfterSalesId());
            } else if (salesStatus == 4) {
                Log.d(TAG, "onClick: 进入退款完成详情页面：" + salesStatus);
                RouterHelper.toAfterSalesFinishDetail(item.getTotalOrderId(), item.getAfterSalesId());
            } else if (salesStatus == 6) {
                Log.d(TAG, "onClick: 进入取消详情页面：" + salesStatus);
                RouterHelper.toCancelAfterSalesDetail(item.getTotalOrderId(), item.getAfterSalesId());
            } else if (salesStatus == 7) {
                Log.d(TAG, "onClick: 进入审核失败详情页面：" + salesStatus);
                RouterHelper.toPendingReviewFailAfterDetail(item.getTotalOrderId(), item.getAfterSalesId());
            }
        }
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected final ImageView orderItemIvGroup;
        protected final TextView orderItemTvGroup;
        protected final TextView orderItemTvStatus;
        protected final RoundImageView orderItemIvImg;
        protected final TextView orderItemTvName;
        protected final RecyclerView orderItemRv;
        protected final TextView billItemTvMoney;
        protected final TextView orderItemTvNum;

        public OrderViewHolder(View convertView) {
            super(convertView);
            orderItemIvGroup = convertView.findViewById(R.id.order_item_iv_group);
            orderItemTvGroup = convertView.findViewById(R.id.order_item_tv_group);
            orderItemTvStatus = convertView.findViewById(R.id.order_item_tv_status);
            orderItemIvImg = convertView.findViewById(R.id.order_item_iv_img);
            orderItemTvName = convertView.findViewById(R.id.order_item_tv_name);
            orderItemRv = convertView.findViewById(R.id.order_item_rv);
            billItemTvMoney = convertView.findViewById(R.id.order_item_tv_money);
            orderItemTvNum = convertView.findViewById(R.id.order_item_tv_num);
        }

        public void onBindViewHolder(int position) {
            OrderListResponseListItem item = getItem(position);
            if (item == null || item.getGoodsVOList() == null || item.getGoodsVOList().size() <= 0)
                return;
            initArea(item.getOrderActivityType());
            initAfterSailedStatus(item.getOrderActivityType(), item.getOrderStatus(), item.getLuckStatus()
                    , item.getAfterSailedStatus());
            initGoodsList(item.getGoodsVOList());
            int realPrice = Integer.parseInt(item.getRealPayedPrice()) / 100;
            billItemTvMoney.setText(mContext.getString(R.string.order_money, realPrice + ""));
            orderItemTvNum.setText(mContext.getString(R.string.order_number, item.getTotalBuyQuantity()));
        }

        /**
         * 设置订单专区
         *
         * @param area /
         */
        private void initArea(int area) {
            if (area == 2 || area == 3) {
                orderItemIvGroup.setImageResource(R.mipmap.icon_order_lucky);
                orderItemTvGroup.setText("全民拼团");
            } else {
                orderItemIvGroup.setImageResource(R.mipmap.icon_order_normal);
                orderItemTvGroup.setText("其他商品");
            }
        }

        /**
         * 设置售后状态
         *
         * @param orderStatus /
         * @param luckStatus  /
         */
        private void initAfterSailedStatus(int orderActivityType, int orderStatus, int luckStatus, int afterSailedStatus) {
            //售后状态:1-待审核 2-退货中 3-退款中 4-退款成功 6关闭 7-驳回
            if (orderStatus == 1) {
                orderItemTvStatus.setText(R.string.order_wait_pay);
            } else if (orderStatus == 2 || orderStatus == 3) {
                if (afterSailedStatus > 0 && afterSailedStatus != 6 && afterSailedStatus != 7) {
                    orderItemTvStatus.setText("退款中");
                    return;
                }
                if (orderActivityType == 1) {
                    if (orderStatus == ORDER_RECEIPT_TYPE_STOCK) {
                        orderItemTvStatus.setText(R.string.order_in_transit);
                    } else {
                        orderItemTvStatus.setText(R.string.order_out_of_stock);
                    }
                } else {
                    if (luckStatus == 1) {
                        orderItemTvStatus.setText("待抽奖");
                    } else if (luckStatus == 2) {
                        orderItemTvStatus.setText("已中奖，正在出库");
                    } else {
                        orderItemTvStatus.setText("抽奖结束，退款中");
                    }
                    if (orderStatus == ORDER_RECEIPT_TYPE_STOCK) {
                        orderItemTvStatus.setText(R.string.order_in_transit);
                    }
                }
            } else if (orderStatus == 4) {
                orderItemTvStatus.setText(R.string.order_completed);
            } else {
                if (orderActivityType == 1) {
                    orderItemTvStatus.setText(R.string.order_cancelled);
                } else {
                    if (luckStatus == 3) {
                        orderItemTvStatus.setText("未中奖，已取消");
                    } else {
                        orderItemTvStatus.setText(R.string.order_cancelled);
                    }
                }
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
                orderItemIvImg.setVisibility(View.VISIBLE);
                orderItemTvName.setVisibility(View.VISIBLE);
                orderItemRv.setVisibility(View.GONE);
                orderItemTvName.setText(goodsVOList.get(0).getGoodsTitle());
                Glide.with(mContext).load(goodsVOList.get(0).getGoodsImageUrl()).into(orderItemIvImg);
            } else {
                orderItemIvImg.setVisibility(View.GONE);
                orderItemTvName.setVisibility(View.GONE);
                orderItemRv.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
                orderItemRv.setLayoutManager(linearLayoutManager);
                orderItemRv.setAdapter(new OrderGoodsRecyclerAdapter(mContext, goodsVOList));
            }
        }

        @Override
        public void onClick(View v) {

        }
    }

    OnCallItem onCallItem;

    public void setOnCallItem(OnCallItem onCallItem) {
        this.onCallItem = onCallItem;
    }

    public interface OnCallItem {
        void onClickPayment(OrderListResponseListItem item);
    }

}
