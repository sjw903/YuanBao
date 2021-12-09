package com.yuanbaogo.finance.pay.view;

import android.app.Activity;
import android.content.Context;
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
import com.yuanbaogo.finance.pay.adapter.PayTypeUnitListAdapter;
import com.yuanbaogo.finance.pay.model.PayTypeBean;
import com.yuanbaogo.finance.pay.presenter.PayCenter;
import com.yuanbaogo.zui.dialog.CommonRemindPop;
import com.yuanbaogo.zui.dialog.PopupWindowWithMask;

import java.util.List;

class PayCenterPopGroup extends PopupWindowWithMask implements PopupWindow.OnDismissListener, View.OnClickListener {

    private View contentView;
    private Context mContext;

    private String price;
    private PayTypeUnitListAdapter mAdapter;
    private List<PayTypeBean> list;
    private OnPayClickListener mListener;

    /**
     *
     */
    String buyType;//购买类型(coinPointBuy:元宝积分充值, goodsBuy:购物, groupBuy:拼团),示例值(coinPointBuy)


    public interface OnPayClickListener {
        void OnPayClick();
    }

    public void setOnPayClickListener(OnPayClickListener listener) {
        this.mListener = listener;
    }

    public PayCenterPopGroup(Context context, String price, List<PayTypeBean> list, String buyType) {
        super(context, true);
        this.mContext = context;
        this.price = price;
        this.list = list;
        this.buyType = buyType;
        initView();
    }

    @Override
    protected View initContentView() {
        contentView = LayoutInflater.from(context).inflate(R.layout.pay_pop_center_group, null, false);
        return contentView;
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

        ImageView back = contentView.findViewById(R.id.tv_back);
        back.setOnClickListener(this);
        TextView tv_pay_submit = contentView.findViewById(R.id.tv_pay_submit);
        tv_pay_submit.setOnClickListener(this);
        TextView tv_price = contentView.findViewById(R.id.tv_price);
        tv_price.setText("¥" + price);

        RecyclerView mRecyclerView = contentView.findViewById(R.id.rv_paylist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new PayTypeUnitListAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(list);
    }

    @Override
    public void onDismiss() {
    }

    String titleTxt;
    String leftTxt;
    String rightTxt;

    CommonRemindPop pop;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_back) {
            if (buyType.equals(PayCenter.BUYTYPE_COINPOINTBUY)) {
                titleTxt = "确认离开吗？";
                leftTxt = "继续充值";
                rightTxt = "确认离开";
            } else if (buyType.equals(PayCenter.BUYTYPE_GOODSBUY)) {
                titleTxt = "确认离开吗？";
                leftTxt = "继续支付";
                rightTxt = "确认离开";
            }
            pop = new CommonRemindPop(mContext, titleTxt, leftTxt, rightTxt,
                    new CommonRemindPop.OnClickListener() {
                        @Override
                        public void onLeftClick() {
                            pop.dismiss();
                        }

                        @Override
                        public void onRightClick() {
                            pop.dismiss();
                            dismiss();
                        }
                    });
            pop.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else if (id == R.id.tv_pay_submit) {
            dismiss();
            if (mListener != null)
                mListener.OnPayClick();
        }
    }


}

