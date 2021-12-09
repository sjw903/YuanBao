package com.yuanbaogo.mine.mine.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.utils.TextUtils;
import com.bumptech.glide.Glide;
import com.permissionx.guolindev.PermissionX;
import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.commonlib.callcenter.CallCenter;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.mine.contract.MineContract;
import com.yuanbaogo.mine.mine.model.OrderListItem;
import com.yuanbaogo.mine.mine.model.PersonalInfoBean;
import com.yuanbaogo.mine.mine.model.UserInfoModel;
import com.yuanbaogo.mine.mine.presenter.MinePresenter;
import com.yuanbaogo.mine.order.OrderActivity;
import com.yuanbaogo.mine.order.receipt.detail.utils.LogisticsState;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.CircleImageView;
import com.yuanbaogo.zui.view.RoundImageView;
import com.yuanbaogo.zui.view.ScrollUpView;

import java.util.ArrayList;
import java.util.List;


public class MineFragment extends MvpBaseFragmentImpl<MineContract.View, MinePresenter>
        implements MineContract.View, View.OnClickListener {

    MinePresenter minePresenter = new MinePresenter();

    private ImageView mMineIbSetting;
    private ImageView mMineIvTopUpRights;
    private CircleImageView mMineAssetsIvHeader;
    private TextView mMineTvName;
    private LinearLayout mMineLlAssets;
    private LinearLayout mMineLlCoupon;
    private LinearLayout mMineLlCollection;
    private TextView mMineOrderTvAll;
    private TextView mMineOrderTvPendingPaymentMsg;
    private RelativeLayout mMineOrderRlPendingPayment;
    private TextView mMineOrderTvToBeReceivedMsg;
    private RelativeLayout mMineOrderRlToBeReceived;
    private TextView mMineOrderTvBeEvaluatedMsg;
    private RelativeLayout mMineOrderRlBeEvaluated;
    private ScrollUpView mMineOrderScroll;
    private RelativeLayout mMineOrderRlAfterSale;
    private TextView mMineOrderTvAfterSaleMsg;
    private RelativeLayout mMineCallRlOnline;
    private RelativeLayout mMineCallRlPhone;
    private RoundImageView mMineIvInvite;
    private LinearLayout mMineAssetsLl;

    @Override
    protected MinePresenter createPresenter(Bundle savedInstanceState) {
        return minePresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_fragment_mine;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mMineIbSetting = (ImageView) findViewById(R.id.mine_ib_setting);
        mMineIvTopUpRights = (ImageView) findViewById(R.id.mine_iv_top_up_rights);
        mMineAssetsIvHeader = (CircleImageView) findViewById(R.id.mine_assets_iv_header);
        mMineTvName = (TextView) findViewById(R.id.mine_tv_name);
        mMineLlAssets = (LinearLayout) findViewById(R.id.mine_ll_assets);
        mMineLlCoupon = (LinearLayout) findViewById(R.id.mine_ll_coupon);
        mMineLlCollection = (LinearLayout) findViewById(R.id.mine_ll_collection);
        mMineOrderTvAll = (TextView) findViewById(R.id.mine_order_tv_all);
        mMineOrderTvPendingPaymentMsg = (TextView) findViewById(R.id.mine_order_tv_pending_payment_msg);
        mMineOrderRlPendingPayment = (RelativeLayout) findViewById(R.id.mine_order_rl_pending_payment);
        mMineOrderTvToBeReceivedMsg = (TextView) findViewById(R.id.mine_order_tv_to_be_received_msg);
        mMineOrderRlToBeReceived = (RelativeLayout) findViewById(R.id.mine_order_rl_to_be_received);
        mMineOrderTvBeEvaluatedMsg = (TextView) findViewById(R.id.mine_order_tv_be_evaluated_msg);
        mMineOrderRlBeEvaluated = (RelativeLayout) findViewById(R.id.mine_order_rl_be_evaluated);
        mMineOrderScroll = (ScrollUpView) findViewById(R.id.mine_order_scroll);
        mMineOrderRlAfterSale = (RelativeLayout) findViewById(R.id.mine_order_rl_after_sale);
        mMineOrderTvAfterSaleMsg = (TextView) findViewById(R.id.mine_order_tv_after_sale_msg);
        mMineCallRlOnline = (RelativeLayout) findViewById(R.id.mine_call_rl_online);
        mMineCallRlPhone = (RelativeLayout) findViewById(R.id.mine_call_rl_phone);
        mMineIvInvite = (RoundImageView) findViewById(R.id.mine_iv_invite);
        mMineAssetsLl = (LinearLayout) findViewById(R.id.mine_assets_ll);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserStore.isLogin()) {
            mPresenter.getPersonalInfo(UserStore.getYbCode());
            mPresenter.getUserInfo();
        } else {
            mMineTvName.setText(R.string.mine_not_logged_in);
            mMineAssetsIvHeader.setImageResource(R.mipmap.icon_default_head);
            mMineOrderScroll.setVisibility(View.GONE);
            setAngleMark(mMineOrderTvPendingPaymentMsg, 0);
            setAngleMark(mMineOrderTvToBeReceivedMsg, 0);
            setAngleMark(mMineOrderTvBeEvaluatedMsg, 0);
            setAngleMark(mMineOrderTvAfterSaleMsg, 0);
        }
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mMineAssetsIvHeader.setOnClickListener(this);
        mMineTvName.setOnClickListener(this);
        mMineIbSetting.setOnClickListener(this);
        mMineIvTopUpRights.setOnClickListener(this);
        mMineLlAssets.setOnClickListener(this);
        mMineLlCoupon.setOnClickListener(this);
        mMineLlCollection.setOnClickListener(this);
        mMineOrderTvAll.setOnClickListener(this);
        mMineOrderRlPendingPayment.setOnClickListener(this);
        mMineOrderRlToBeReceived.setOnClickListener(this);
        mMineOrderRlBeEvaluated.setOnClickListener(this);
        mMineOrderRlAfterSale.setOnClickListener(this);
        mMineCallRlOnline.setOnClickListener(this);
        mMineCallRlPhone.setOnClickListener(this);
        mMineIvInvite.setOnClickListener(this);
        mMineAssetsLl.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mine_ib_setting) {
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            // 设置
            RouterHelper.toSetting();
        } else if (id == R.id.mine_assets_ll || id == R.id.mine_assets_iv_header || id == R.id.mine_tv_name) {
            // 个人信息
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            RouterHelper.toMineActivity(UserStore.getYbCode());
        } else if (id == R.id.mine_iv_top_up_rights) {
            // 重置权益
            RouterHelper.toRechargeNow(1);
        } else if (id == R.id.mine_ll_assets) {
            // 资产管理
            RouterHelper.toAssets();
        } else if (id == R.id.mine_ll_coupon) {
            // 卡券
            RouterHelper.toCoupon();
        } else if (id == R.id.mine_ll_collection) {
            // 收藏夹
            RouterHelper.toCollection();
        } else if (id == R.id.mine_order_tv_all) {
            // 全部订单
            RouterHelper.toOrderList(OrderActivity.ALL_TYPE);
        } else if (id == R.id.mine_order_rl_pending_payment) {
            // 待付款
            RouterHelper.toOrderList(OrderActivity.PAY_TYPE);
        } else if (id == R.id.mine_order_rl_to_be_received) {
            // 待收货
            RouterHelper.toOrderList(OrderActivity.RECEIPT_TYPE);
        } else if (id == R.id.mine_order_rl_be_evaluated) {
            // 待评价
            RouterHelper.toOrderList(OrderActivity.FINISH_TYPE);
        } else if (id == R.id.mine_order_rl_after_sale) {
            // 退换/售后
            RouterHelper.toAfterSales();
        } else if (id == R.id.mine_call_rl_online) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            // 在线客服
            CallCenter.toService(null, null, null, null, null);
        } else if (id == R.id.mine_call_rl_phone) {
            // 客服热线
            PermissionX.init(this)
                    .permissions(Manifest.permission.CALL_PHONE)
                    .setDialogTintColor(Color.parseColor("#008577"), Color.parseColor("#83e8dd"))
                    .onExplainRequestReason((scope, deniedList) -> {
                        String message = "需要您同意以下权限才能正常使用";
                        scope.showRequestReasonDialog(deniedList, message, "确定", "取消");
                    })
                    .request((allGranted, grantedList, deniedList) -> {
                        if (allGranted) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_DIAL);
                            //Url统一资源定位符
                            intent.setData(Uri.parse("tel:4006005118"));
                            startActivity(intent);
                        } else {
                            ToastUtil.showToast("请打开通话权限");
                        }
                    });
        } else if (id == R.id.mine_iv_invite) {
            // 邀请新用户
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            if (!android.text.TextUtils.isEmpty(UserStore.getInviteCode())) {
                //防暴力点击
                if (ClickUtils.isFastClick()) {
                    return;
                }
                RouterHelper.toWebJs(WebUrls.inviUser
                        .replace("{inviteCode}", UserStore.getInviteCode())
                        .replace("{token}", UserStore.getToken()), true);
            } else {
                ToastView.showToast("账号有问题？？？邀请码为null");
            }
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void getUserInfoSuccess(UserInfoModel userInfoModel) {
        setAngleMark(mMineOrderTvPendingPaymentMsg, userInfoModel.getPrePayCount());
        setAngleMark(mMineOrderTvToBeReceivedMsg, userInfoModel.getPreReceiveCount());
        setAngleMark(mMineOrderTvBeEvaluatedMsg, userInfoModel.getPreEvaluateCount());
        setAngleMark(mMineOrderTvAfterSaleMsg, userInfoModel.getAfterSaleCount());
        List<OrderListItem> orderList = userInfoModel.getOrderList();
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            OrderListItem order = orderList.get(i);
            @SuppressLint("InflateParams") View view =
                    LayoutInflater.from(getContext()).inflate(R.layout.mine_layout_order_logistics, null);
            RoundImageView logisticsIvImg = view.findViewById(R.id.logistics_iv_img);
            TextView logisticsTvStatus = view.findViewById(R.id.logistics_tv_status);
            TextView logisticsTvMsg = view.findViewById(R.id.logistics_tv_msg);
            Glide.with(requireContext()).load(order.getImgUrl()).into(logisticsIvImg);
            logisticsTvStatus.setText(LogisticsState.getLogisticsState(order.getType()).first);
            logisticsTvMsg.setText(order.getDes());
            viewList.add(view);
            view.setOnClickListener(v -> {
                OrderNetworkUtils.getOrderStatus(getContext(), order.getOrderId(), goodsDetail -> {
                    int orderStatus = goodsDetail.getOrderStatus();
                    if (orderStatus == 2 || orderStatus == 3) {
                        RouterHelper.toReceiptDetail(order.getOrderId());
                    } else if (orderStatus == 4) {
                        RouterHelper.toFinishDetail(order.getOrderId());
                    }
                });
            });
        }
        if (viewList.size() > 0) {
            mMineOrderScroll.setVisibility(View.VISIBLE);
            mMineOrderScroll.setViews(viewList);
        } else {
            mMineOrderScroll.setVisibility(View.GONE);
        }
    }

    /**
     * 设置头像  昵称
     *
     * @param bean
     */
    @Override
    public void setPersonalInfo(PersonalInfoBean bean) {
        mMineTvName.setText(bean.getUser().getNickName());
        if (TextUtils.isEmpty(bean.getUser().getPortraitUrl()) || bean.getUser().getPortraitUrl().equals("http://")) {
            mMineAssetsIvHeader.setImageResource(R.mipmap.icon_default_head);
        } else {
            Glide.with(mMineAssetsIvHeader).load(bean.getUser().getPortraitUrl()).into(mMineAssetsIvHeader);
        }
    }

    @Override
    public void initPersonalInfo() {

    }

    @SuppressLint("DefaultLocale")
    private void setAngleMark(TextView tvAngleMark, int count) {
        if (count > 0) {
            tvAngleMark.setVisibility(View.VISIBLE);
            tvAngleMark.setText(String.format("%d", count));
        } else {
            tvAngleMark.setVisibility(View.GONE);
        }
    }

}
