package com.yuanbaogo.homevideo.live.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.homevideo.live.home.adapter.LiveVideoAdapter;
import com.yuanbaogo.homevideo.live.home.adapter.SpacesItemDecoration;
import com.yuanbaogo.homevideo.live.home.contract.LiveContract;
import com.yuanbaogo.homevideo.live.home.model.LiveRecommendBean;
import com.yuanbaogo.homevideo.live.home.presenter.LivePresenter;
import com.yuanbaogo.homevideo.live.pull.view.PullMainActivity;
import com.yuanbaogo.libbase.baseutil.LogUtil;
import com.yuanbaogo.video.R;

import java.util.List;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/6 14:57
 */
public class LiveFragment extends MvpBaseFragmentImpl<LiveContract.View, LivePresenter>
        implements LiveContract.View, OnRefreshListener, OnLoadMoreListener, View.OnClickListener {

    private LivePresenter livePresenter = new LivePresenter();
    SmartRefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    LinearLayout mVideoNoData;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private LiveVideoAdapter mAdapter;
    private LinearLayout ll_no_network;
    private TextView tv_reload;
    private int page = 1;

    @Override
    protected LivePresenter createPresenter(Bundle savedInstanceState) {
        return livePresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.video_fragment_live_recommend;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mVideoNoData = (LinearLayout) findViewById(R.id.ll_video_no_data);
        ll_no_network = (LinearLayout) findViewById(R.id.ll_no_network);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        tv_reload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_reload) {
            setLl_no_network();
        }
    }

    private void setLl_no_network() {
        if (isNetworkAvailable()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            ll_no_network.setVisibility(View.GONE);
            mPresenter.getLivingRecommend(page);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            ll_no_network.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter = new LiveVideoAdapter(getViewContext());
        mRecyclerView.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(getActivity(), 10);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(null);
        mAdapter.setOnItemClickListener(new LiveVideoAdapter.OnItemClickListener() {
            @Override
            public void onItmClick(LiveRecommendBean.RowsBean bean) {
                if (!UserStore.isLogin()) {
                    RouterHelper.toLogin();
                    return;
                }
                Intent intent = new Intent(mContext, PullMainActivity.class);
                intent.putExtra("playerUrl", bean.getPlayerUrl());
                intent.putExtra("RoomId", bean.getAvChatRoomId());
                intent.putExtra("portraitUrl", bean.getPortraitUrl());
                intent.putExtra("followerYbCode", bean.getYbCode());
                startActivity(intent);
            }
        });
//        setLl_no_network();

        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.getLivingRecommend(page);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        mPresenter.getLivingRecommend(page);
    }

    public void loadFinish(boolean isSuccess) {
        mRefreshLayout.finishRefresh(isSuccess);
        mRefreshLayout.finishLoadMore(isSuccess);
    }

    /**
     * 完成加载并标记没有更多数据
     */
    @Override
    public void finishLoadMoreWithNoMoreData() {
        mRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void showEmptyView() {
        mVideoNoData.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        loadFinish(true);
    }

    @Override
    public void setListData(List<LiveRecommendBean.RowsBean> list) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mVideoNoData.setVisibility(View.GONE);
        mAdapter.setData(list);
        mRefreshLayout.finishRefresh(true);
        mRefreshLayout.finishLoadMore(true);
    }

    @Override
    public void addListData(List<LiveRecommendBean.RowsBean> list) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mVideoNoData.setVisibility(View.GONE);
        mAdapter.addData(list);
        mRefreshLayout.finishRefresh(true);
        mRefreshLayout.finishLoadMore(true);
    }

    /**
     * 初始化是否完成 true 完成  false 未完成
     */
    boolean isPrepared = false;

    /**
     * 外部界面显示 false   外部界面不显示 true
     */
    boolean isHiddenVideo;

    /**
     * 推荐界面显示 true 推荐界面显示 false
     */
    private boolean isVisible = false;

    /**
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHiddenVideo = hidden;
    }

    //viewpager切换 不走 onPause onResume方法
    // 使用此方法暂停播放视频
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.e("onHiddenChanged", isVisibleToUser + "");
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {//初始化没有完成false 或 界面不显示false
            return;
        }
        setLl_no_network();
    }

}
