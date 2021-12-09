package com.yuanbaogo.homevideo.mine.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.main.view.RecommendVideoFragment;
import com.yuanbaogo.homevideo.mine.adapter.VideoAdapter;
import com.yuanbaogo.homevideo.mine.contract.WorkContract;
import com.yuanbaogo.homevideo.mine.model.VodListBean;
import com.yuanbaogo.homevideo.mine.presenter.WorkPresenter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 点赞视频列表
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/8/21 6:24 PM
 * @Modifier:
 * @Modify:
 */
public class LikeFragment extends MvpBaseFragmentImpl<WorkContract.View, WorkPresenter>
        implements WorkContract.View, OnCallRecyclerItem {

    public static final String YBCODE = "ybCode";

    private String ybCode;

    private VideoAdapter mVideoAdapter;

    private RecyclerView mRecyclerView;

    RecommendVideoBean vodListBean;

    private String state = "refresh";

    private int pageNum = 1;

    private int pageSize = 40;

    ArrayList<RecommendVideoBean.RowsBean> listDTOList = new ArrayList<>();

    RelativeLayout videoRlNoData;

    TextView videoTvNoData;

    @Override
    protected WorkPresenter createPresenter(Bundle savedInstanceState) {
        return new WorkPresenter();
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.fragment_work;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        if (getArguments() != null) {
            ybCode = getArguments().getString(YBCODE);
        }
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        setFresh();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        videoRlNoData = (RelativeLayout) findViewById(R.id.video_rl_no_data);
        videoTvNoData = (TextView) findViewById(R.id.video_tv_no_data);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        refresh();
    }

    public void loadMore() {
        if (listDTOList.size() % pageSize == 0) {
            state = "loadMore";
            setFresh();
        } else {
            ToastView.showToast("暂无更多数据");
        }
    }

    public void refresh() {
        state = "refresh";
        setFresh();
    }

    private void setFresh() {
        if (state.equals("loadMore")) {
            pageNum++;
            mPresenter.getLikeVodList(pageNum, pageSize, "", ybCode);
        } else {
            pageNum = 1;
            mPresenter.getLikeVodList(pageNum, pageSize, "", ybCode);
        }
    }

    @Override
    public void setVodList(RecommendVideoBean bean) {
        vodListBean = bean;
        initVodList();
    }

    @Override
    public void initVodList() {
        if (vodListBean != null && vodListBean.getRows() != null) {
            if (state.equals("loadMore")) {//上推显示更多
                listDTOList.addAll(vodListBean.getRows());
                mVideoAdapter.addData(listDTOList);
            } else if (state.equals("refresh")) {
                if (vodListBean.getRows().size() > 0) {
                    listDTOList.clear();
                    //列表
                    videoRlNoData.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    //隐藏列表
                    mRecyclerView.setVisibility(View.GONE);
                    videoRlNoData.setVisibility(View.VISIBLE);
                    videoTvNoData.setText("您还没有进行过点赞哦");
                    Drawable drawable = getResources().getDrawable(com.yuanbaogo.zui.R.mipmap.icon_dianzan_no_data);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    videoTvNoData.setCompoundDrawables(null, drawable, null, null);
                }
                listDTOList.addAll(vodListBean.getRows());
                mVideoAdapter = new VideoAdapter(getActivity(), listDTOList, R.layout.item_video);
                mVideoAdapter.setOnCallBack(this);
                mRecyclerView.setAdapter(mVideoAdapter);
            }
        }
    }

    @Override
    public void onCallItem(View view, int postion) {
        RouterHelper.toShortVideoPlayActivity(listDTOList, postion, RecommendVideoFragment.SOURCETYPE_LIKE,ybCode.equals(UserStore.getYbCode()) ? 1 : 2);
    }
}