package com.yuanbaogo.homevideo.live.personal.view;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.live.personal.model.ViewerListBean;
import com.yuanbaogo.homevideo.live.pull.adapter.ViewerListAdapter;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.zui.dialog.PopupWindowWithMask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewerListPop extends PopupWindowWithMask implements OnRefreshListener, OnLoadMoreListener {

    private View mContentView;
    private OnClickListener mListener;
    Activity mActivity;
    String mRoomid;
    RefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;

    ViewerListAdapter mAdapter;
    List<ViewerListBean> mList;


    public interface OnClickListener {
        void OnClick(int payChanelType);
    }

    public void setOnPayClickListener(OnClickListener listener) {
        this.mListener = listener;
    }

    public ViewerListPop(Activity activity, String roomid) {
        super(activity, true);
        this.mActivity = activity;
        this.mRoomid = roomid;
        initView();
    }

    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.pop_viewer_list, null, false);
        return mContentView;
    }

    @Override
    protected int initHeight() {
        int screenHeight = ScreenUtils.getScreenHeight(context);
        return (int) (screenHeight * 0.6);
    }

    @Override
    protected int initWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    private void initView() {

        mRefreshLayout = (RefreshLayout) mContentView.findViewById(R.id.refreshLayout);
        mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.recyclerView);

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ViewerListAdapter(mActivity);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ViewerListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos, String ybcode) {
                follow(ybcode, pos);
            }
        });

        loadData();

    }


    protected int mPage = 1;
    int size = 20;

    public void loadList(List<ViewerListBean.RowsBean> list) {
        loadFinish(true);
        if (list == null) return;
        if (mPage == 1) {
            mAdapter.setData(list);
        } else {
            mAdapter.addData(list);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void loadFailure() {
        loadFinish(false);
    }

    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage = 1;
        loadData();
    }

    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        loadData();
    }

    private void loadData() {
        Map<String, Object> params = new HashMap<>();
        params.put("avChatRoomId", mRoomid);
        params.put("page", mPage);
        params.put("size", size);

        NetWork.getInstance().post(mActivity,
                ChildUrl.listcharm,
                params,
                new RequestSateListener<ViewerListBean>() {
                    @Override
                    public void onSuccess(int code, ViewerListBean bean) {

                        if (bean.getRows() == null || bean.getRows().size() == 0) {
                            if (mPage == 1) {
                                loadFailure();
                            } else {
                                mRefreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        } else {
                            loadList(bean.getRows());
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        loadFailure();
                    }
                });
    }

    private void loadFail() {
        if (mPage > 1) mPage--;
        else mPage = 1;
    }

    protected void loadFinish(boolean successful) {
        mRefreshLayout.finishRefresh(successful);
        mRefreshLayout.finishLoadMore(successful);
        if (!successful) loadFail();
    }


    public void follow(String followerYbCode, int pos) {
        Map<String, Object> params = new HashMap<>();
        params.put("businessId", mRoomid);
        params.put("followerYbCode", followerYbCode);
        params.put("state", "1");
        params.put("type", "1");

        NetWork.getInstance().post(mActivity,
                ChildUrl.follow,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String str) {
                        mAdapter.notifyItemChanged(pos);
                        loadData();
                    }

                    @Override
                    public void onFailure(Throwable var1) {

                    }
                });
    }

}

