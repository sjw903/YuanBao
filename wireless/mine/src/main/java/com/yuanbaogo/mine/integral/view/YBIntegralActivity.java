package com.yuanbaogo.mine.integral.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.yuanbaogo.mine.integral.contract.YBIntegralContract;
import com.yuanbaogo.mine.integral.presenter.YBIntegralPresenter;
import com.yuanbaogo.zui.dialog.AssetsDialogView;
import com.yuanbaogo.zui.dialog.model.PactBean;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.view.TitleBar;

import org.greenrobot.eventbus.EventBus;

/**
 * @Description: 元宝积分
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/10/21 9:23 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.YB_INTEGRAL_ACTIVITY)
public class YBIntegralActivity extends MvpBaseActivityImpl<YBIntegralContract.View, YBIntegralPresenter>
        implements View.OnClickListener, YBIntegralContract.View {

    TitleBar mineAssetsIntegralHeadView;

//    ImageView mineAssetsIntegralImgInstruction;

    TextView mineAssetsIntegralTvRechargeNow;

    TextView mineAssetsIntegralTvCurrentIntegral;

    AssetsDetailFragment assetsDetailFragment;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_yb_integral;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mineAssetsIntegralHeadView = findViewById(R.id.mine_assets_integral_head_view);
//        mineAssetsIntegralImgInstruction = findViewById(R.id.mine_assets_integral_img_instruction);
        mineAssetsIntegralTvRechargeNow = findViewById(R.id.mine_assets_integral_tv_recharge_now);
        mineAssetsIntegralTvCurrentIntegral = findViewById(R.id.mine_assets_integral_tv_current_integral);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
//        mineAssetsIntegralImgInstruction.setOnClickListener(this);
        mineAssetsIntegralHeadView.setRightTextOnClickListener(this);
        mineAssetsIntegralTvRechargeNow.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
//        initHeadView();
        initRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getDiffTypeWallet(0, UserStore.getYbCode());
        if (assetsDetailFragment != null) {
            EventBus.getDefault().post(MessageWrap.getInstance("4"));
        }
    }

//    private void initHeadView() {
//        HeadBean headBean = new HeadBean()
//                .setImgLeft(true)
//                .setImgLeftIcon(R.mipmap.icon_head_back_white)
//                .setRlSearch(false)
//                .setTvCenterTitle(true)
//                .setTvCenterTitles("元宝积分")
//                .setImgRight(false);
//        mineAssetsIntegralHeadView.getLibHeadTvTitle().setTextColor(getResources().getColor(R.color.colorFFFFFF));
//        mineAssetsIntegralHeadView.setHead(headBean);
//    }

    @Override
    public void setDiffTypeWallet(String bean) {
        mineAssetsIntegralTvCurrentIntegral.setText(bean + "");
    }

    @Override
    public void initDiffTypeWallet() {

    }

    private void initRecycler() {
        String[] mTitles = {"元宝积分收支明细"};
        assetsDetailFragment = new AssetsDetailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mine_assets_integral_fl, assetsDetailFragment.getInstance(4, mTitles));
        transaction.commit();
    }

    AssetsDialogView assetsDialogView;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_bar_tv_right) {
            assetsDialogView = new AssetsDialogView();
            assetsDialogView.setCancelable(false);
            assetsDialogView.show(YBIntegralActivity.this.getSupportFragmentManager(), "bring_goods");
            PactBean pactBean = new PactBean()
                    .setTvTitles("帮助说明")
                    .setContentFileName("integral.txt")
                    .setTvDetermines("我已知晓");
            assetsDialogView.setPact(pactBean);
        } else if (id == R.id.mine_assets_integral_tv_recharge_now) {
            RouterHelper.toIntegralRecharge();
        }
    }

}