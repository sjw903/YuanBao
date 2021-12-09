package com.yuanbaogo.mine.assets.view;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.integral.model.MessageWrap;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.commonlib.utils.PriceUtils;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.assets.contract.AssetsContract;
import com.yuanbaogo.mine.assets.model.FindAreaAmountBean;
import com.yuanbaogo.mine.assets.presenter.AssetsPresenter;
import com.yuanbaogo.zui.dialog.ConfirmDialogView;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.toast.ToastView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @Description: 我的资产
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/30/21 4:50 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.ASSETS_ACTIVITY)
public class AssetsActivity extends MvpBaseActivityImpl<AssetsContract.View, AssetsPresenter>
        implements View.OnClickListener, AssetsContract.View {

    /**
     * 自定义Head
     */
    HeadView mineAssetsHeadView;

    /**
     * 元宝积分
     */
    RelativeLayout mineAssetsRlYBIntegral;

    /**
     * 立即充值
     */
    TextView mineAssetsTvYBIntegralRecharge;

    /**
     * 元宝积分
     */
    TextView mineAssetsTvIntegral;

    /**
     * 零钱
     */
    RelativeLayout mineAssetsRlDib;

    /**
     * 提现
     */
    TextView mineAssetsTvDibWithdraw;

    /**
     * 零钱
     */
    TextView mineAssetsTvDib;

    /**
     * 可用金额
     */
    double dib = 0;

    /**
     * 冻结零钱
     */
    TextView mineAssetsTvFreezeDib;

    /**
     * 冻结金额
     */
    double frozen = 0;

    /**
     * 五百专区
     */
    RelativeLayout mineAssetsGroupAccountRlFiveHundred;

    /**
     * 五千专区
     */
    RelativeLayout mineAssetsGroupAccountRlFiveThousand;

    /**
     * 五万专区
     */
    RelativeLayout mineAssetsGroupAccountRlFiftyThousand;

    TextView mineAssetsGroupAccountTvFiveHundred;

    TextView mineAssetsGroupAccountTvFiveThousand;

    TextView mineAssetsGroupAccountTvFiftyThousand;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_assets;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mineAssetsHeadView = findViewById(R.id.mine_assets_head_view);
        mineAssetsRlYBIntegral = findViewById(R.id.mine_assets_rl_yb_integral);
        mineAssetsRlDib = findViewById(R.id.mine_assets_rl_dib);
        mineAssetsGroupAccountRlFiveHundred = findViewById(R.id.mine_assets_group_account_rl_five_hundred);
        mineAssetsGroupAccountRlFiveThousand = findViewById(R.id.mine_assets_group_account_rl_five_thousand);
        mineAssetsGroupAccountRlFiftyThousand = findViewById(R.id.mine_assets_group_account_rl_fifty_thousand);
        mineAssetsTvYBIntegralRecharge = findViewById(R.id.mine_assets_tv_yb_integral_recharge);
        mineAssetsTvDibWithdraw = findViewById(R.id.mine_assets_tv_dib_withdraw);
        mineAssetsTvIntegral = findViewById(R.id.mine_assets_tv_integral);
        mineAssetsTvDib = findViewById(R.id.mine_assets_tv_dib);
        mineAssetsTvFreezeDib = findViewById(R.id.mine_assets_tv_freeze_dib);
        mineAssetsGroupAccountTvFiveHundred = findViewById(R.id.mine_assets_group_account_tv_five_hundred);
        mineAssetsGroupAccountTvFiveThousand = findViewById(R.id.mine_assets_group_account_tv_five_thousand);
        mineAssetsGroupAccountTvFiftyThousand = findViewById(R.id.mine_assets_group_account_tv_fifty_thousand);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mineAssetsRlYBIntegral.setOnClickListener(this);
        mineAssetsRlDib.setOnClickListener(this);
        mineAssetsGroupAccountRlFiveHundred.setOnClickListener(this);
        mineAssetsGroupAccountRlFiveThousand.setOnClickListener(this);
        mineAssetsGroupAccountRlFiftyThousand.setOnClickListener(this);
        mineAssetsTvYBIntegralRecharge.setOnClickListener(this);
        mineAssetsTvDibWithdraw.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        //获取元宝积分数据
        mPresenter.getDiffTypeWallet(0, UserStore.getYbCode());
        //获取零钱数据
        mPresenter.getDiffTypeWallet(1, UserStore.getYbCode());
        //获取零钱冻结不可用数据
        mPresenter.getDiffTypeWallet(2, UserStore.getYbCode());
        //查询用户各个专区钱包总额
        mPresenter.getFindAreaAmount(UserStore.getYbCode());
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
                .setTvCenterTitles("我的资产")
                .setImgRight(false);
        mineAssetsHeadView.setHead(headBean);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mine_assets_rl_yb_integral) {//元宝积分
            RouterHelper.toYBIntegral();
        } else if (id == R.id.mine_assets_rl_dib) {//零钱
            RouterHelper.toDib();
        } else if (id == R.id.mine_assets_group_account_rl_five_hundred) {//五百专区
            RouterHelper.toGroupAccount(1);
        } else if (id == R.id.mine_assets_group_account_rl_five_thousand) {//五千专区
            RouterHelper.toGroupAccount(2);
        } else if (id == R.id.mine_assets_group_account_rl_fifty_thousand) {//五万专区
            RouterHelper.toGroupAccount(3);
        } else if (id == R.id.mine_assets_tv_yb_integral_recharge) {//立即充值
            RouterHelper.toIntegralRecharge();
        } else if (id == R.id.mine_assets_tv_dib_withdraw) {//提现
            if (dib >= 100) {//TODO 大于100能提现
                mPresenter.canWithdrawal();
            }
        }
    }

    /**
     * 设置元宝积分数据
     *
     * @param bean
     */
    @Override
    public void setIntegrals(String bean) {
        mineAssetsTvIntegral.setText(bean);
    }

    /**
     * 设置零钱数据
     *
     * @param bean
     */
    @Override
    public void setDIB(String bean) {
        dib = Double.parseDouble(bean);
        Spannable string = new SpannableString("￥" + bean);
        string.setSpan(new AbsoluteSizeSpan(35), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mineAssetsTvDib.setText(string);
        if (dib >= 100) {
            mineAssetsTvDibWithdraw.setTextColor(getResources().getColor(R.color.colorFFFFFF));
            mineAssetsTvDibWithdraw.setBackgroundResource(R.drawable.shape_bg_11b8c3_14);
        } else {
            mineAssetsTvDibWithdraw.setBackgroundResource(R.drawable.shape_bg_b5eaec_16);
        }
    }

    /**
     * 设置零钱冻结不可用数据
     *
     * @param bean
     */
    @Override
    public void setFrozen(String bean) {
        frozen = Double.parseDouble(bean);
        mineAssetsTvFreezeDib.setText("冻结不可用  ￥" + bean);
    }

    List<FindAreaAmountBean> findAreaAmountBeans;

    @Override
    public void setFindAreaAmount(List<FindAreaAmountBean> bean) {
        findAreaAmountBeans = bean;
        if (findAreaAmountBeans != null && findAreaAmountBeans.size() != 0) {
            for (int i = 0; i < findAreaAmountBeans.size(); i++) {
                if (findAreaAmountBeans.get(i).getAreaType() == 1) {
                    mineAssetsGroupAccountTvFiveHundred.setText(findAreaAmountBeans.get(i).getAmount() + "");
                } else if (findAreaAmountBeans.get(i).getAreaType() == 1) {
                    mineAssetsGroupAccountTvFiveThousand.setText(findAreaAmountBeans.get(i).getAmount() + "");
                } else if (findAreaAmountBeans.get(i).getAreaType() == 1) {
                    mineAssetsGroupAccountTvFiftyThousand.setText(findAreaAmountBeans.get(i).getAmount() + "");
                }
            }
        }
    }

    /**
     * 接口请求失败
     *
     * @param walletType
     */
    @Override
    public void onError(int walletType) {
        if (walletType == 0) {
            mineAssetsTvIntegral.setText("0");
        } else if (walletType == 1) {
            mineAssetsTvDib.setText("0");
        } else if (walletType == 2) {
            mineAssetsTvFreezeDib.setText("冻结不可用  ￥0");
        } else if (walletType == 3) {
            mineAssetsGroupAccountTvFiveHundred.setText("0");
            mineAssetsGroupAccountTvFiveThousand.setText("0");
            mineAssetsGroupAccountTvFiftyThousand.setText("0");
        }
    }

    @Override
    public void toWithdrawal() {
        RouterHelper.toWithdraw(dib);
    }

    @Override
    public void toBindBankCard() {
        ConfirmDialogView confirmDialogView = new ConfirmDialogView("取消",
                "提现需绑定一张支持\n" + "提现的银行卡", "前往绑定");
        confirmDialogView.show(getSupportFragmentManager(), "search_history");
        confirmDialogView.setOnCallDialog(new OnCallDialog() {
            @Override
            public void onCallDetermine() {
                RouterHelper.toBindBankCard();
                confirmDialogView.dismiss();
            }
        });
    }

}