package com.yuanbaogo.homevideo.main.view;

import android.os.Bundle;

import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.video.R;
import com.yuanbaogo.homevideo.main.contract.LocalVideoContract;
import com.yuanbaogo.homevideo.main.presenter.LocalVideoPresenter;

/**
 * @author lhx
 * @description: 同城推荐短视频页
 * @date : 2021/7/6 14:55
 */
public class LocalVideoFragment extends MvpBaseFragmentImpl<LocalVideoContract.View, LocalVideoPresenter>
    implements LocalVideoContract.View{

    private LocalVideoPresenter localVideoPresenter = new LocalVideoPresenter();

    @Override
    protected LocalVideoPresenter createPresenter(Bundle savedInstanceState) {
        return localVideoPresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.fragment_recommend_video;
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
