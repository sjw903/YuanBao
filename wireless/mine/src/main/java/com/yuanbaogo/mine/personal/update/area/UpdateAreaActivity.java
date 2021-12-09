package com.yuanbaogo.mine.personal.update.area;

import android.os.Bundle;

import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;

public class UpdateAreaActivity  extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> {

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_update_area;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {

    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

}