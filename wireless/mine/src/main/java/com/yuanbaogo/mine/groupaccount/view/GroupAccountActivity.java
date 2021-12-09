package com.yuanbaogo.mine.groupaccount.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.utils.PriceUtils;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.groupaccount.contract.GroupAccountContract;
import com.yuanbaogo.mine.groupaccount.model.GroupAccountMoneyBean;
import com.yuanbaogo.mine.groupaccount.presenter.GroupAccountPresenter;
import com.yuanbaogo.mine.detail.view.AssetsDetailFragment;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;

/**
 * @Description: 拼团专区
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/28/21 2:45 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.GROUP_ACCOUNT_ACTIVITY)
public class GroupAccountActivity extends MvpBaseActivityImpl<GroupAccountContract.View, GroupAccountPresenter>
        implements View.OnClickListener, GroupAccountContract.View {

    /**
     * 自定义HeadView
     */
    HeadView mineAssetsGroupAccountHeadView;

    /**
     *
     */
    @Autowired(name = YBRouter.GroupAccountActivityParams.type)
    int type = 1;

    /**
     * 背景
     */
    ImageView mineAssetsGroupAccountImgBg;

    /**
     * 背景
     */
    RelativeLayout mineAssetsGroupAccountRlBg;

    /**
     * 标题
     */
    TextView mineAssetsGroupAccountTvTitle;

    /**
     * 明细
     */
    FrameLayout mineAssetsGroupAccountFl;

    /**
     * 立即充值
     */
    TextView mineAssetsGroupAccountTvRechargeNow;

    /**
     * 当前余额   元宝券
     */
    GroupAccountMoneyBean groupAccountMoney;

    /**
     * 当前余额
     */
    TextView mineAssetsGroupAccountTvPrice;

    /**
     * 元宝券
     */
    TextView mineAssetsGroupAccountTvCoupon;

    long price = 500;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_group_account;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mineAssetsGroupAccountHeadView = findViewById(R.id.mine_assets_group_account_head_view);
        mineAssetsGroupAccountImgBg = findViewById(R.id.mine_assets_group_account_img_bg);
        mineAssetsGroupAccountRlBg = findViewById(R.id.mine_assets_group_account_rl_bg);
        mineAssetsGroupAccountTvTitle = findViewById(R.id.mine_assets_group_account_tv_title);
        mineAssetsGroupAccountFl = findViewById(R.id.mine_assets_group_account_fl);
        mineAssetsGroupAccountTvRechargeNow = findViewById(R.id.mine_assets_group_account_tv_recharge_now);
        mineAssetsGroupAccountTvPrice = findViewById(R.id.mine_assets_group_account_tv_price);
        mineAssetsGroupAccountTvCoupon = findViewById(R.id.mine_assets_group_account_tv_coupon);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mineAssetsGroupAccountTvRechargeNow.setOnClickListener(this);
        mineAssetsGroupAccountHeadView.getLibHeadTvRight().setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        initBG();
        initTitle();
        initData();
        initRecycler();
    }

    /**
     * 自定义HeadView
     */
    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_head_back_white)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("拼团账户")
                .setImgRight(false);
        mineAssetsGroupAccountHeadView.getLibHeadTvTitle()
                .setTextColor(getResources().getColor(R.color.colorFFFFFF));
        mineAssetsGroupAccountHeadView.getLibHeadTvRight().setVisibility(View.VISIBLE);
        mineAssetsGroupAccountHeadView.getLibHeadTvRight().setText("充值权益");
        mineAssetsGroupAccountHeadView.getLibHeadTvRight()
                .setTextColor(getResources().getColor(R.color.colorFFFFFF));
        mineAssetsGroupAccountHeadView.setHead(headBean);
    }

    /**
     * 设置背景
     */
    private void initBG() {
        if (type == 1) {
            mineAssetsGroupAccountImgBg.setBackground(getResources().getDrawable(R.mipmap.icon_assets_five_hundred_bg));
            mineAssetsGroupAccountRlBg.setBackground(getResources().getDrawable(R.mipmap.icon_assets_five_hundred_card));
        } else if (type == 2) {
            mineAssetsGroupAccountImgBg.setBackground(getResources().getDrawable(R.mipmap.icon_assets_five_thousand_bg));
            mineAssetsGroupAccountRlBg.setBackground(getResources().getDrawable(R.mipmap.icon_assets_five_thousand_card));
        } else if (type == 3) {
            mineAssetsGroupAccountImgBg.setBackground(getResources().getDrawable(R.mipmap.icon_assets_fifty_thousand_bg));
            mineAssetsGroupAccountRlBg.setBackground(getResources().getDrawable(R.mipmap.icon_assets_fifty_thousand_card));
        }
    }

    /**
     * 设置标题
     */
    private void initTitle() {
        Drawable drawable = null;
        if (type == 1) {
            price = 500;
            mineAssetsGroupAccountTvTitle.setText("五百专区");
            drawable = getResources().getDrawable(R.mipmap.icon_assets_five_hundred_sign);
        } else if (type == 2) {
            price = 5000;
            mineAssetsGroupAccountTvTitle.setText("五千专区");
            drawable = getResources().getDrawable(R.mipmap.icon_assets_five_thousand_sign);
        } else if (type == 3) {
            price = 50000;
            mineAssetsGroupAccountTvTitle.setText("五万专区");
            drawable = getResources().getDrawable(R.mipmap.icon_assets_fifty_thousand_sign);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mineAssetsGroupAccountTvTitle.setCompoundDrawables(drawable, null, null, null);
        mineAssetsGroupAccountTvTitle.setCompoundDrawablePadding(5);
    }

    /**
     * 设置明细
     */
    private void initRecycler() {
        String[] mTitles = {"余额收支明细", "元宝券收支明细"};
        AssetsDetailFragment assetsDetailFragment = new AssetsDetailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mine_assets_group_account_fl, assetsDetailFragment.getInstance(type, mTitles));
        transaction.commit();
    }

    /**
     * 请求接口
     */
    private void initData() {
        //获取金额 元宝券
        mPresenter.getFindAreaInfo(type, UserStore.getYbCode());
    }

    /**
     * 设置金额 元宝券
     *
     * @param bean
     */
    @Override
    public void setFindAreaInfo(GroupAccountMoneyBean bean) {
        groupAccountMoney = bean;
        initFindAreaInfo();
    }

    /**
     * 展示金额 元宝券
     */
    @Override
    public void initFindAreaInfo() {
        if (groupAccountMoney != null) {
            mineAssetsGroupAccountTvPrice.setText("￥ " + PriceUtils.doubleToString(groupAccountMoney.getAmount()));
            mineAssetsGroupAccountTvCoupon.setText(groupAccountMoney.getCount() + "张");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mine_assets_group_account_tv_recharge_now) {//充值
            RouterHelper.toRechargeNow(type);
        } else if (id == R.id.lib_head_tv_right) {//充值权益
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            RouterHelper.toWebJs(WebUrls.proRecharge + "?price=" + price, false);
        }
    }

}