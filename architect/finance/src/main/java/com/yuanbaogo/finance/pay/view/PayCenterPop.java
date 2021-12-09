package com.yuanbaogo.finance.pay.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanbaogo.finance.R;
import com.yuanbaogo.finance.pay.adapter.PayTypeListAdapter;
import com.yuanbaogo.finance.pay.model.PayTypeBean;
import com.yuanbaogo.finance.pay.presenter.PayCenter;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.zui.dialog.CommonRemindPop;
import com.yuanbaogo.zui.dialog.PopupWindowWithMask;

import java.util.List;

public class PayCenterPop extends PopupWindowWithMask implements PopupWindow.OnDismissListener, View.OnClickListener {

    private View mContentView;
    private OnPayClickListener mListener;
    Activity mActivity;
    String mPayAwayCode;
    int mPayChanelType;
    String orderid;
    String price;
    TextView mSubmitPay;
    PayTypeListAdapter mAdapter;
    List<PayTypeBean> mList;
    PayCenterPopGroup mPopupWindowGroup;

    /**
     *
     */
    String buyType;//购买类型(coinPointBuy:元宝积分充值, goodsBuy:购物, groupBuy:拼团),示例值(coinPointBuy)

    public interface OnPayClickListener {
        void OnPayClick(int payChanelType);
    }

    public void setOnPayClickListener(OnPayClickListener listener) {
        this.mListener = listener;
    }

    public PayCenterPop(Activity activity, List<PayTypeBean> list, String price, String orderid, String buyType) {
        super(activity, true);
        this.mActivity = activity;
        this.price = price;
        this.mList = list;
        this.orderid = orderid;
        this.buyType = buyType;
        initView();
    }

    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.pay_pop_center, null, false);
        return mContentView;
    }

    @Override
    protected int initHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int initWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    private void initView() {
        ImageView back = mContentView.findViewById(R.id.tv_back);
        back.setOnClickListener(this);
        mSubmitPay = mContentView.findViewById(R.id.tv_pay_submit);
        mSubmitPay.setOnClickListener(this);
        TextView tv_price = mContentView.findViewById(R.id.tv_price);
        tv_price.setText("¥" + price);

        RecyclerView mRecyclerView = mContentView.findViewById(R.id.rv_paylist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new PayTypeListAdapter(mActivity);
        mAdapter.setOnItemClickListener(new PayTypeListAdapter.OnItemClickListener() {
            @Override
            public void onPayGroupClick(String balance) {
                //如果元宝积分为0  就不能组合支付
                if (balance.equals("0")) {
                    return;
                }
                mPopupWindowGroup = new PayCenterPopGroup(context, price, mList, buyType);
                mPopupWindowGroup.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                mPopupWindowGroup.setOnPayClickListener(new PayCenterPopGroup.OnPayClickListener() {
                    @Override
                    public void OnPayClick() {
                        if (mListener != null)
                            mListener.OnPayClick(PayCenter.PAYCHANNEL_GROUP);
                    }
                });
                dismiss();
            }

            @Override
            public void onPayCheckStatus(String payAwayCode, int payType) {
                mPayChanelType = payType;
                mPayAwayCode = payAwayCode;
                if (payType == PayCenter.PAYCHANNEL_GROUP) {
                    mSubmitPay.setEnabled(false);
                } else {
                    mSubmitPay.setEnabled(true);
                }
                if (mRecyclerView.isComputingLayout()) {
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(mList, price);

    }

    @Override
    public void onDismiss() {
    }

    CommonRemindPop pop;

    String titleTxt;
    String leftTxt;
    String rightTxt;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_back) {
//            dismiss();
            if (buyType.equals(PayCenter.BUYTYPE_COINPOINTBUY)) {
                titleTxt = "确认离开吗？";
                leftTxt = "继续充值";
                rightTxt = "确认离开";
            } else if (buyType.equals(PayCenter.BUYTYPE_GOODSBUY)) {
                titleTxt = "确认离开吗？";
                leftTxt = "继续支付";
                rightTxt = "确认离开";
            }
            pop = new CommonRemindPop(mActivity, titleTxt, leftTxt, rightTxt,
                    new CommonRemindPop.OnClickListener() {
                        @Override
                        public void onLeftClick() {
                            pop.dismiss();
                        }

                        @Override
                        public void onRightClick() {
                            if (onCallRight != null) {
                                onCallRight.onRight();
                            }
                            pop.dismiss();
                            dismiss();
                        }
                    });
            pop.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else if (id == R.id.tv_pay_submit) {
            if (ClickUtils.isFastClick()) {
                return;
            }
//            dismiss();
            if (mListener != null)
                mListener.OnPayClick(mPayChanelType);
        }
    }

    OnCallRight onCallRight;

    public void setOnCallRight(OnCallRight onCallRight) {
        this.onCallRight = onCallRight;
    }

    public interface OnCallRight {
        void onRight();
    }

}

