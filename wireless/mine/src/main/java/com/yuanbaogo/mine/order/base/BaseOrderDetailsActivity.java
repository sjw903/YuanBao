package com.yuanbaogo.mine.order.base;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.commonlib.utils.PriceUtils;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.adapter.OrderGoodsListRecyclerAdapter;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.mine.order.utils.Configuration;
import com.yuanbaogo.zui.toast.ToastView;

public abstract class BaseOrderDetailsActivity<V extends IBaseView, P extends MvpBasePersenter>
        extends MvpBaseActivityImpl<V, P> implements IBaseView, View.OnClickListener {

    protected RecyclerView mOrderRvGoodsList;
    protected TextView mOrderTvCopy;
    protected TextView mOrderTvCode;
    protected TextView mOrderTvTime;
    protected TextView mOrderTvMethod;
    protected RelativeLayout mOrderRlDate;
    protected TextView mOrderTvDate;
    protected TextView mOrderTvTotal;
    protected TextView mOrderTvFreight;
    protected TextView mOrderTvMoneyInfo;
    protected TextView mOrderTvMoney;
    protected GoodsDetail data;
    protected OrderGoodsListRecyclerAdapter mAdapter;

    @CallSuper
    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mOrderRvGoodsList = findViewById(R.id.order_rv_goods_list);
        mOrderTvCopy = findViewById(R.id.order_tv_copy);
        mOrderTvCode = findViewById(R.id.order_tv_code);
        mOrderTvTime = findViewById(R.id.order_tv_time);
        mOrderTvMethod = findViewById(R.id.order_tv_method);
        mOrderRlDate = findViewById(R.id.order_rl_date);
        mOrderTvDate = findViewById(R.id.order_tv_date);
        mOrderTvTotal = findViewById(R.id.order_tv_total);
        mOrderTvFreight = findViewById(R.id.order_tv_freight);
        mOrderTvMoneyInfo = findViewById(R.id.order_tv_money_info);
        mOrderTvMoney = findViewById(R.id.order_tv_money);
    }

    @SuppressLint("SetTextI18n")
    protected void getData(GoodsDetail data) {
        if (data == null) return;
        this.data = data;
        mAdapter = new OrderGoodsListRecyclerAdapter(this, data.getGoodsVOList());
        mOrderRvGoodsList.setAdapter(mAdapter);
        mOrderTvCode.setText(data.getTotalOrderId());
        if (data.getPayedTime() != null) {
            mOrderTvDate.setText(DateUtils.getTime(data.getPayedTime()));
        }
        if ((data.getCreatedTime()) != null) {
            mOrderTvTime.setText(DateUtils.getTime(data.getCreatedTime()));
        }


        mOrderTvTotal.setText(getString(R.string.order_money, PriceUtils.doubleToStringNo(Double.parseDouble(data.getRealPayedPrice()))));
        int money = Integer.parseInt(data.getRealPayedPrice()) / 100;
        mOrderTvMoney.setText(getString(R.string.order_money, money + ""));
        mOrderTvCopy.setOnClickListener(v -> onCopyOrderId());

        if (data.isPayed()) {
            long time = data.getPayedTime();
            if (time <= 0) {
                time = System.currentTimeMillis();
            }
            mOrderTvDate.setText(DateUtils.getTime(time));
            mOrderTvMoneyInfo.setText(R.string.wait_pay_money_already);
            if (data.getPayedType() != null) {
                if (data.getPayedType() == Configuration.PAY_WE_CHART) {
                    mOrderTvMethod.setText(R.string.order_pay_type_2);
                } else if (data.getPayedType() == Configuration.PAY_ALI) {
                    mOrderTvMethod.setText(R.string.order_pay_type_3);
                } else if (data.getPayedType() == Configuration.PAY_BANK) {
                    mOrderTvMethod.setText(R.string.order_pay_type_4);
                } else {
                    mOrderTvMethod.setText("在线支付");
                }
            } else {
            }

            mOrderRlDate.setVisibility(View.VISIBLE);
        } else {
            mOrderRlDate.setVisibility(View.GONE);
            mOrderTvMoneyInfo.setText(R.string.wait_pay_money_details);
            mOrderTvMethod.setText(R.string.cancel_online_pay);
        }
    }

    public void onCopyOrderId() {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("YB", data.getTotalOrderId());
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastView.showToast(R.string.order_code_copy);
    }

}
