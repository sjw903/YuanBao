package com.yuanbaogo.mine.dib.view;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.FragmentTransaction;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.mine.integral.model.MessageWrap;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.detail.view.AssetsDetailFragment;
import com.yuanbaogo.mine.dib.contract.DibContract;
import com.yuanbaogo.mine.dib.presenter.DibPresenter;
import com.yuanbaogo.zui.dialog.AssetsDialogView;
import com.yuanbaogo.zui.dialog.ConfirmDialogView;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;
import com.yuanbaogo.zui.dialog.model.PactBean;
import com.yuanbaogo.zui.view.TitleBar;
import org.greenrobot.eventbus.EventBus;

/**
 * @Description: 零钱
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 5:29 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.DIB_ACTIVITY)
public class DibActivity extends MvpBaseActivityImpl<DibContract.View, DibPresenter>
        implements View.OnClickListener, DibContract.View {

    /**
     * 自定义HeadView
     */
    TitleBar mineAssetsDibHeadView;

    /**
     * 提示弹窗
     */
    AssetsDialogView assetsDialogView;

    /**
     * 提现
     */
    TextView mineAssetsDibTvWithdraw;

    /**
     * 零钱
     */
    TextView mineAssetsDibTvCurrent;

    /**
     * 可用金额
     */
    double dib = 0;

    TextView mineAssetsDibTvFreeze;

    /**
     * 冻结金额
     */
    long frozen = 0;

    AssetsDetailFragment assetsDetailFragment;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_dib;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mineAssetsDibHeadView = findViewById(R.id.mine_assets_dib_head_view);
        mineAssetsDibTvWithdraw = findViewById(R.id.mine_assets_dib_tv_withdraw);
        mineAssetsDibTvCurrent = findViewById(R.id.mine_assets_dib_tv_current);
        mineAssetsDibTvFreeze = findViewById(R.id.mine_assets_dib_tv_freeze);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mineAssetsDibHeadView.setRightTextOnClickListener(this);
        mineAssetsDibTvWithdraw.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取当前可用金额
        mPresenter.getDiffTypeWallet(1, UserStore.getYbCode());
        //获取已冻结不可用金额
        mPresenter.getDiffTypeWallet(2, UserStore.getYbCode());
        if (assetsDetailFragment != null) {
            EventBus.getDefault().post(MessageWrap.getInstance("5"));
        }
    }

    /**
     * 设置明细
     */
    private void initRecycler() {
        String[] mTitles = {"零钱收支明细"};
        assetsDetailFragment = new AssetsDetailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mine_assets_dib_fl, assetsDetailFragment.getInstance(5, mTitles));
        transaction.commit();
    }

    Spannable string;

    /**
     * 设置当前可用金额
     *
     * @param bean
     */
    @Override
    public void setDiffTypeWallet(String bean) {
        dib = Double.parseDouble(bean);
        string = new SpannableString("￥" + bean);
        initDiffTypeWallet();
    }

    /**
     * 设置零钱数据
     */
    @Override
    public void initDiffTypeWallet() {
        if (string != null) {
            string.setSpan(new AbsoluteSizeSpan(35), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mineAssetsDibTvCurrent.setText(string);
            if (dib >= 100) {
                mineAssetsDibTvWithdraw.setBackgroundResource(R.drawable.bg_ffffff_radius16);
            } else {
                mineAssetsDibTvWithdraw.setBackgroundResource(R.drawable.bg_b5eaec_radius16);
            }
        }
    }

    /**
     * 设置已冻结金额
     *
     * @param bean
     */
    @Override
    public void setFreezeAmount(String bean) {
        mineAssetsDibTvFreeze.setText("￥ " + bean);
    }

    /**
     * 去提现
     */
    @Override
    public void toWithdrawal() {
        RouterHelper.toWithdraw(dib);
    }

    /**
     * 去绑定银行卡
     */
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_bar_tv_right) {//帮助说明
            assetsDialogView = new AssetsDialogView();
            assetsDialogView.setCancelable(false);
            assetsDialogView.show(DibActivity.this.getSupportFragmentManager(), "bring_goods");
            PactBean pactBean = new PactBean()
                    .setTvTitles("帮助说明")
                    .setContentFileName("dib.txt")
                    .setTvDetermines("我已知晓");
            assetsDialogView.setPact(pactBean);
        } else if (id == R.id.mine_assets_dib_tv_withdraw) {//提现
            if (dib >= 100) {//TODO 大于100能提现
                mineAssetsDibTvWithdraw.setEnabled(true);
                mPresenter.canWithdrawal();
            } else {
                mineAssetsDibTvWithdraw.setEnabled(false);
            }
        }
    }

}