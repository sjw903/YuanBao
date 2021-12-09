package com.yuanbaogo.video.search.view;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.search.contract.VideoSearchContract;
import com.yuanbaogo.video.search.presenter.VideoSearchPresenter;

/**
 * @author lhx
 * @description: 视频搜索结果
 * @date : 2021/7/13 15:35
 */
public class VideoSearchFragment extends MvpBaseFragmentImpl<VideoSearchContract.View, VideoSearchPresenter>
    implements VideoSearchContract.View{

    SwipeRefreshLayout mSrlVideoSearch;

    RecyclerView mRvVideoSearch;

    ImageView mIvVideoSearchNo;

    VideoSearchPresenter videoSearchPresenter = new VideoSearchPresenter();

    public void setTitle(String title) {

    }

    @Override
    protected VideoSearchPresenter createPresenter(Bundle savedInstanceState) {
        return videoSearchPresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.fragment_video_search;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mSrlVideoSearch = (SwipeRefreshLayout) findViewById(R.id.srl_video_search);
        mRvVideoSearch = (RecyclerView) findViewById(R.id.rv_video_search);
        mIvVideoSearchNo = (ImageView) findViewById(R.id.iv_video_search_no);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
}
