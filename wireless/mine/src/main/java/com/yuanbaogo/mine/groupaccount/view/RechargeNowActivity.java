package com.yuanbaogo.mine.groupaccount.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.finance.pay.presenter.PayCenter;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.commonlib.utils.PriceUtils;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.groupaccount.adapter.RechargeNowAdapter;
import com.yuanbaogo.mine.groupaccount.contract.RechargeNowContract;
import com.yuanbaogo.mine.groupaccount.model.PreRechargeBean;
import com.yuanbaogo.mine.groupaccount.model.RechargeNowBean;
import com.yuanbaogo.mine.groupaccount.presenter.RechargeNowPresenter;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 立即充值
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/29/21 9:16 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.RECHARGE_NOW_ACTIVITY)
public class RechargeNowActivity extends MvpBaseActivityImpl<RechargeNowContract.View, RechargeNowPresenter>
        implements OnCallRecyclerItem, RechargeNowContract.View, View.OnClickListener {

    /**
     * 自定义HeadView
     */
    HeadView mineAssetsRechargeNowHeadView;

    /**
     *
     */
    @Autowired(name = YBRouter.GroupAccountActivityParams.type)
    int type = 0;

    /**
     * 背景
     */
    ImageView mineAssetsRechargeNowImg;

    /**
     * 优惠券
     */
    RecyclerView mineAssetsRechargeNowRecycler;

    /**
     * 优惠券适配器
     */
    RechargeNowAdapter rechargeNowAdapter;

    /**
     * 优惠券数据
     */
    List<RechargeNowBean> rechargeNowBean;

    /**
     * 优惠券布局
     */
    RelativeLayout mineAssetsRechargeNowRlCoupon;

    /**
     * 支付
     */
    Button mineAssetsRechargeNowPay;

    /**
     * 订单
     */
    String mOrderid;

    /**
     * 存储选中的item
     */
    public static Set<String> positionSet = new HashSet<>();

    /**
     * 选择模式 多选或者单选:  true 多选  false 单选
     */
    private boolean selectMode = true;

    TextView mineAssetsRechargeNowTvNoUseticket;

    /**
     * 需要支付金额
     */
    long useCash = 500;

    TextView mineAssetsRechargeNowTvUsecash;

    /**
     * 优惠券金额
     */
    long useTicket = 0;

    TextView mineAssetsRechargeNowTvUseticket;

    /**
     * 生成订单
     */
    PreRechargeBean preRechargeBean;

    TextView mineAssetsRechargeNowTvGet;

    TextView mineAssetsRechargeNowTvGetIngots;

    TextView mineAssetsRechargeNowTvProportion;

    TextView mineAssetsRechargeNowTvPS;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_recharge_now;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mineAssetsRechargeNowHeadView = findViewById(R.id.mine_assets_recharge_now_head_view);
        mineAssetsRechargeNowImg = findViewById(R.id.mine_assets_recharge_now_img);
        mineAssetsRechargeNowRecycler = findViewById(R.id.mine_assets_recharge_now_recycler);
        mineAssetsRechargeNowPay = findViewById(R.id.mine_assets_recharge_now_but_paly);
        mineAssetsRechargeNowRlCoupon = findViewById(R.id.mine_assets_recharge_now_rl_coupon);
        mineAssetsRechargeNowTvUsecash = findViewById(R.id.mine_assets_recharge_now_tv_usecash);
        mineAssetsRechargeNowTvUseticket = findViewById(R.id.mine_assets_recharge_now_tv_useticket);
        mineAssetsRechargeNowTvNoUseticket = findViewById(R.id.mine_assets_recharge_now_tv_no_useticket);
        mineAssetsRechargeNowTvGet = findViewById(R.id.mine_assets_recharge_now_tv_get);
        mineAssetsRechargeNowTvGetIngots = findViewById(R.id.mine_assets_recharge_now_tv_get_ingots);
        mineAssetsRechargeNowTvProportion = findViewById(R.id.mine_assets_recharge_now_tv_proportion);
        mineAssetsRechargeNowTvPS = findViewById(R.id.mine_assets_recharge_now_tv_ps);
    }


    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mineAssetsRechargeNowPay.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        initImg();
        //获取优惠券
        mPresenter.getFindNouseCouPonList(UserStore.getYbCode(), type);
    }

    /**
     * 自定义HeadView
     */
    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("专区充值")
                .setImgRight(false);
        mineAssetsRechargeNowHeadView.setHead(headBean);
    }

    /**
     * 设置默认数据
     */
    private void initImg() {
        if (type == 1) {
            useCash = 50000;
            mineAssetsRechargeNowTvGet.setText("充值后将获得元宝券：");
            mineAssetsRechargeNowTvProportion.setVisibility(View.VISIBLE);
            mineAssetsRechargeNowTvProportion.setText("1元宝券 = 1元人民币");
            mineAssetsRechargeNowTvPS.setVisibility(View.VISIBLE);
            mineAssetsRechargeNowImg.setImageDrawable(
                    getResources().getDrawable(R.mipmap.icon_assets_recharge_now_five_hundred));
        } else if (type == 2) {
            useCash = 500000;
            mineAssetsRechargeNowTvGet.setText("充值后将获得元宝券：");
            mineAssetsRechargeNowTvProportion.setVisibility(View.VISIBLE);
            mineAssetsRechargeNowTvProportion.setText("1元宝券 = 1元人民币");
            mineAssetsRechargeNowTvPS.setVisibility(View.VISIBLE);
            mineAssetsRechargeNowImg.setImageDrawable(
                    getResources().getDrawable(R.mipmap.icon_assets_recharge_now_five_thousand));
        } else if (type == 3) {
            useCash = 5000000;
            mineAssetsRechargeNowTvGet.setText("获得零钱(冻结)：");
            mineAssetsRechargeNowTvProportion.setVisibility(View.GONE);
            mineAssetsRechargeNowTvPS.setVisibility(View.GONE);
            mineAssetsRechargeNowImg.setImageDrawable(
                    getResources().getDrawable(R.mipmap.icon_assets_recharge_now_fifty_thousand));
        }
        mineAssetsRechargeNowTvUsecash.setText(PriceUtils.doubleToString(useCash / 100));
        mineAssetsRechargeNowTvGetIngots.setText(PriceUtils.doubleToString(useCash / 100));
    }

    /**
     * 设置优惠券数据
     *
     * @param bean
     */
    @Override
    public void setFindNouseCouPonList(List<RechargeNowBean> bean) {
        rechargeNowBean = bean;
        initFindNouseCouPonList();
    }

    /**
     * 显示优惠券
     */
    @Override
    public void initFindNouseCouPonList() {
        if (rechargeNowBean == null) {
            mineAssetsRechargeNowRlCoupon.setVisibility(View.GONE);
            mineAssetsRechargeNowTvNoUseticket.setVisibility(View.VISIBLE);
            return;
        }
        initRecycler();
    }

    /**
     * 优惠券
     */
    private void initRecycler() {
        if (rechargeNowBean.size() == 0) {
            mineAssetsRechargeNowRlCoupon.setVisibility(View.GONE);
            return;
        }
        mineAssetsRechargeNowRlCoupon.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mineAssetsRechargeNowRecycler.setLayoutManager(gridLayoutManager);
        rechargeNowAdapter = new RechargeNowAdapter(this, rechargeNowBean,
                R.layout.mine_item_assets_recharge_now);
        rechargeNowAdapter.setOnCallItem(this);
        mineAssetsRechargeNowRecycler.setAdapter(rechargeNowAdapter);
    }

    @Override
    public void onCallItem(View view, int postion) {
        if (selectMode) {
            // 如果当前处于多选状态，则进入多选状态的逻辑
            // 维护当前已选的position
            addOrRemove(postion);
        } else {
            // 如果不是多选状态，则进入单选事件的业务逻辑
            if (!positionSet.contains(postion)) {
                // 选择不同的单位时取消之前选中的单位
                positionSet.clear();
            }
            addOrRemove(postion);
        }
    }

    private void addOrRemove(int position) {
        if (positionSet.contains(rechargeNowBean.get(position).getId())) {
            // 如果包含，则撤销选择
            positionSet.remove(rechargeNowBean.get(position).getId());
            initCipherPrice(2, rechargeNowBean.get(position).getTicketAmount());
        } else {
            if (positionSet.size() == 3) {
                ToastView.showToast("最多选择三个");
                return;
            }
            // 如果不包含，则添加
            positionSet.add(rechargeNowBean.get(position).getId());
            initCipherPrice(1, rechargeNowBean.get(position).getTicketAmount());
        }
        if (positionSet.size() == 0) {
            rechargeNowAdapter.notifyDataSetChanged();
        } else {
            rechargeNowAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 计算金额
     *
     * @param type         1 ++  2 --
     * @param ticketAmount
     */
    private void initCipherPrice(int type, long ticketAmount) {
        if (type == 1) {
            useTicket = useTicket + ticketAmount;
            useCash = useCash - ticketAmount;
        } else if (type == 2) {
            useTicket = useTicket - ticketAmount;
            useCash = useCash + ticketAmount;
        }
        mineAssetsRechargeNowTvUseticket.setText(PriceUtils.doubleToString(useTicket / 100));
        mineAssetsRechargeNowTvUsecash.setText(PriceUtils.doubleToString(useCash / 100));
        mineAssetsRechargeNowTvGetIngots.setText(PriceUtils.doubleToString(useCash / 100));
    }

    @Override
    public void setPreRecharge(PreRechargeBean bean) {
        preRechargeBean = bean;
        initPreRecharge();
    }

    @Override
    public void initPreRecharge() {
        if (preRechargeBean == null) {
            ToastView.showToast("请稍后再试");
            return;
        }
        if (TextUtils.isEmpty(UserStore.getToken())) {
            RouterHelper.toLogin();
            return;
        } else {
            new PayCenter()
                    .startPay(RechargeNowActivity.this,
                            PayCenter.BUYTYPE_GROUPBUY,
                            useCash / 100 + "",
                            preRechargeBean.getRechargeOrderId(),// 假数据提测时候改过来 //TODO 海静
                            new PayCenter.OnPayInfoListener() {
                                @Override
                                public void OnPayReslutListener(String orderid) {
                                    //TODO 海静
                                    mOrderid = orderid;
                                    if (TextUtils.isEmpty(orderid)) {
                                        //如果是空的，不展示返回结果。在微信支付的广播监听里面展示返回结果页面
                                    } else {
                                        //不为空，展示返回结果
                                    }
                                }
                            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        positionSet.clear();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.mine_assets_recharge_now_but_paly) {//支付
            String[] couponList = positionSet.toArray(new String[0]);
            mPresenter.getPreRecharge(UserStore.getYbCode(), type, couponList, useCash, useTicket);
        }
    }
}