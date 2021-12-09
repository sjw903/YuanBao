package com.yuanbaogo.mine.dib.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.dib.contract.WithdrawSuccessContract;
import com.yuanbaogo.mine.dib.presenter.WithdrawSuccessPresenter;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.progress.VerticalProgress;

/**
 * @Description: 零钱提现成功
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/28/21 11:47 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.WITHDRAW_SUCCESS_ACTIVITY)
public class WithdrawSuccessActivity extends MvpBaseActivityImpl<WithdrawSuccessContract.View, WithdrawSuccessPresenter>
        implements View.OnClickListener {

    HeadView mineAssetsDibWithdrawSuccessHeadView;

    VerticalProgress mineAssetsDibWithdrawSuccessProgress;

    Button mineAssetsDibWithdrawButFinish;

    ImageView mineAssetsDibWithdrawSuccessImgSuccess;

    String price;

    TextView mineAssetsDibWithdrawSuccessTvPrice;

    TextView mineAssetsDibWithdrawSuccessTvTypeName;

    String bankName;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_withdraw_success;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mineAssetsDibWithdrawSuccessHeadView = findViewById(R.id.mine_assets_dib_withdraw_success_head_view);
        mineAssetsDibWithdrawSuccessProgress = findViewById(R.id.mine_assets_dib_withdraw_success_progress);
        mineAssetsDibWithdrawButFinish = findViewById(R.id.mine_assets_dib_withdraw_but_finish);
        mineAssetsDibWithdrawSuccessImgSuccess = findViewById(R.id.mine_assets_dib_withdraw_success_img_success);
        mineAssetsDibWithdrawSuccessTvPrice = findViewById(R.id.mine_assets_dib_withdraw_success_tv_price);
        mineAssetsDibWithdrawSuccessTvTypeName = findViewById(R.id.mine_assets_dib_withdraw_success_tv_type_name);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        price = getIntent().getStringExtra("price");
        bankName = getIntent().getStringExtra("bankName");
        mineAssetsDibWithdrawSuccessTvPrice.setText("￥" + price);
        mineAssetsDibWithdrawSuccessTvTypeName.setText(bankName);
        mineAssetsDibWithdrawButFinish.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        mineAssetsDibWithdrawSuccessProgress.setProgress(50);
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_search_delete_item)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("零钱提现")
                .setImgRight(false);
        mineAssetsDibWithdrawSuccessHeadView.setHead(headBean);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mine_assets_dib_withdraw_but_finish) {
            finish();
        }
    }
}