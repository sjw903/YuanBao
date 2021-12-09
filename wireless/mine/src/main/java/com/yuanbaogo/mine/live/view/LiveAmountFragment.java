package com.yuanbaogo.mine.live.view;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;
import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.live.adapter.LiveAmountItemAdapter;
import com.yuanbaogo.mine.live.contract.LiveAmountFragmentContract;
import com.yuanbaogo.mine.live.presenter.LiveAmountFragmentPresenter;
import com.yuanbaogo.mine.live.model.LiveAmountItem;

import java.util.ArrayList;
import java.util.List;

public class LiveAmountFragment extends MvpBaseFragmentImpl<LiveAmountFragmentContract.View, LiveAmountFragmentPresenter> implements OnRefreshListener, OnLoadMoreListener, LiveAmountFragmentContract.View {

    private RecyclerView mLiveListRv;
    private LinearLayout noLiveLl;
    private LiveAmountItemAdapter liveAmountItemAdapter;
    private List<LiveAmountItem> mLiveAmountItems = new ArrayList<>();
    private SmartRefreshLayout mLiveListSl;
    private static final String TYPE = "type";
    private String type;
    protected int mPage = DEFAULT_ONE;

    public static LiveAmountFragment newInstance(String type) {
        LiveAmountFragment fragment = new LiveAmountFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected LiveAmountFragmentPresenter createPresenter(Bundle savedInstanceState) {
        return new LiveAmountFragmentPresenter();
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_fragment_live_amount;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mLiveListRv = (RecyclerView) findViewById(R.id.live_list_rv);
        mLiveListSl = (SmartRefreshLayout) findViewById(R.id.live_list_sl);
        noLiveLl = (LinearLayout) findViewById(R.id.no_live_ll);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mLiveListSl.setOnLoadMoreListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
        }
        liveAmountItemAdapter = new LiveAmountItemAdapter(getContext(), mLiveAmountItems);
        mLiveListRv.setLayoutManager(new LinearLayoutManager(mContext));
        mLiveListRv.setAdapter(liveAmountItemAdapter);
        mPresenter.getMyLiveList(mPage, type);
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        //        Log.e()
//
//        if (isVisibleToUser) {
//            if (isFirstVisible) {
//                isFirstVisible = false;
//            } else {
//                mPresenter.getMyLiveList(mPage, type);
//            }
//        }
//
//    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        mPresenter.getMyLiveList(mPage, type);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage = DEFAULT_ONE;
        mLiveAmountItems.clear();
        mPresenter.getMyLiveList(mPage, type);
    }

    @Override
    public void showLiveAmount(List<LiveAmountItem> liveAmountItems) {
        mLiveListSl.setVisibility(View.VISIBLE);
        noLiveLl.setVisibility(View.GONE);
//        mLiveListSl.finishRefresh();
        mLiveListSl.finishLoadMore();
        if (liveAmountItems.size()< LOAD_ROWS * mPage){
            mLiveListSl.finishLoadMoreWithNoMoreData();
        }
        mLiveAmountItems.addAll(liveAmountItems);
        liveAmountItemAdapter.notifyDataSetChanged();

    }

    @Override
    public void loadFail(Throwable throwable) {
        if (mPage > 1) {
            mPage--;
        }
        mLiveListSl.setVisibility(View.GONE);
        noLiveLl.setVisibility(View.VISIBLE);
    }
}
