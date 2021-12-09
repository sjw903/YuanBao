package com.yuanbaogo.shopcart.order.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.shopcart.R;
import com.yuanbaogo.shopcart.order.contract.OrderSuccessContract;
import com.yuanbaogo.shopcart.order.presenter.OrderSuccessPresenter;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;

@Route(path = YBRouter.ORDER_SUCCESS_ACTIVITY)
public class OrderSuccessActivity extends MvpBaseActivityImpl<OrderSuccessContract.View, OrderSuccessPresenter>
        implements OrderSuccessContract.View, View.OnClickListener {

    HeadView orderSuccessHeadView;

    @Autowired(name = YBRouter.OrderSuccessActivityParams.price)
    String price;

    TextView orderSuccessTvPrice;

    Button orderSuccessButContinue;

    Button orderSuccessButHome;

    @Autowired(name = YBRouter.OrderSuccessActivityParams.type)
    int type;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_order_success;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        orderSuccessHeadView = findViewById(R.id.order_success_head_view);
        orderSuccessTvPrice = findViewById(R.id.order_success_tv_price);
        orderSuccessButContinue = findViewById(R.id.order_success_but_continue);
        orderSuccessButHome = findViewById(R.id.order_success_but_home);
        if (type == TagParameteBean.liveBringGoods || type == TagParameteBean.videoBringGoods) {
            orderSuccessButHome.setVisibility(View.GONE);
        } else {
            orderSuccessButHome.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        orderSuccessButContinue.setOnClickListener(this);
        orderSuccessButHome.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();

        String countMoney = "共支付¥" + price;
        Spannable string = new SpannableString(countMoney);
        // 实付
        string.setSpan(new AbsoluteSizeSpan(40), 0, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // ￥
        string.setSpan(new AbsoluteSizeSpan(30), 3, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // 100
        string.setSpan(new AbsoluteSizeSpan(40), 4, string.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        orderSuccessTvPrice.setText(string);
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_left_close)
                .setRlSearch(false)
                .setTvLeftTitle(true)
                .setTvCenterTitle(true)
                .setTvCenterTitles("支付结果");
        orderSuccessHeadView.setHead(headBean);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.order_success_but_continue) {
            finish();
        } else if (id == R.id.order_success_but_home) {
            RouterHelper.toMain();
        }
    }
}