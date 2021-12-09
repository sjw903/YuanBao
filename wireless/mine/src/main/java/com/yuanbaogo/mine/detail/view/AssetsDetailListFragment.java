package com.yuanbaogo.mine.detail.view;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.detail.adapter.IntegralDetailAdapter;
import com.yuanbaogo.mine.detail.contract.AssetsDetailListContract;
import com.yuanbaogo.mine.detail.model.FindLogBean;
import com.yuanbaogo.mine.detail.presenter.AssetsDetailListPresenter;
import com.yuanbaogo.mine.integral.model.MessageWrap;
import com.yuanbaogo.mine.integral.model.YBIntegralBean;
import com.yuanbaogo.zui.toast.ToastView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @Description: 收支明细列表
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/28/21 4:06 PM
 * @Modifier:
 * @Modify:
 */
public class AssetsDetailListFragment extends MvpBaseFragmentImpl<AssetsDetailListContract.View, AssetsDetailListPresenter>
        implements AssetsDetailListContract.View, OnRefreshListener, OnLoadMoreListener {

    /**
     * 1 五百专区
     * 2 五千专区
     * 3 五万专区
     * 4 元宝积分
     * 5 零钱
     */
    int type;

    /**
     * 标题
     */
    private String mTitle;

    /**
     * 刷新 加载
     */
    SmartRefreshLayout mineFragmentAssetsDetailOneSwipe;

    /**
     * 实体类Model
     */
    YBIntegralBean ybIntegralBean = new YBIntegralBean();

    /**
     * 明细列表
     */
    RecyclerView mineAssetsDetailRecycler;

    /**
     * 明细适配器
     */
    IntegralDetailAdapter integralRecyclerAdapter;

    /**
     * 没有数据
     */
    RelativeLayout mineAssetsDetailRlNoData;

    /**
     * 明细Presenter
     */
    AssetsDetailListPresenter assetsDetailListPresenter = new AssetsDetailListPresenter();

    /**
     * 页数
     */
    int pageNum = 1;

    /**
     * 一页多少条
     */
    int size = 10;

    /**
     * 总页数
     */
    int totalPage = 0;

    public static AssetsDetailListFragment getInstance(String title, int type) {
        AssetsDetailListFragment fragment = new AssetsDetailListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected AssetsDetailListPresenter createPresenter(Bundle savedInstanceState) {
        return assetsDetailListPresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_fragment_assets_detail_one;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mineFragmentAssetsDetailOneSwipe = (SmartRefreshLayout) findViewById(R.id.mine_fragment_assets_detail_one_swipe);
        mineAssetsDetailRlNoData = (RelativeLayout) findViewById(R.id.mine_assets_detail_rl_no_data);
        mineAssetsDetailRecycler = (RecyclerView) findViewById(R.id.mine_assets_detail_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mineAssetsDetailRecycler.setLayoutManager(linearLayoutManager);
        type = getArguments().getInt("type");
        mTitle = getArguments().getString("title");
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mineFragmentAssetsDetailOneSwipe.setOnRefreshListener(this);
        mineFragmentAssetsDetailOneSwipe.setOnLoadMoreListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initData();
    }

    /**
     * 请求接口
     */
    private void initData() {
        if (type == 1 || type == 2 || type == 3) {
            FindLogBean findLogBean = new FindLogBean().setAreaType(type)
                    .setPageReq(new FindLogBean.FindLogReqBean().setPage(pageNum).setSize(size));
            if (mTitle.equals("余额收支明细")) {
                mPresenter.getFindLog(ChildUrl.FIND_ACCOUNT_LOG_LIST, findLogBean, UserStore.getYbCode());
            } else if (mTitle.equals("元宝券收支明细")) {
                mPresenter.getFindLog(ChildUrl.FIND_COUPON_LOG_LIST, findLogBean, UserStore.getYbCode());
            }
        } else if (type == 4) {
            mPresenter.getCoinPointLog(ChildUrl.GET_COIN_POINT_LOG, pageNum, size, UserStore.getYbCode());
        } else if (type == 5) {
            mPresenter.getCoinPointLog(ChildUrl.GET_SMALL_CHANGE_LOG, pageNum, size, UserStore.getYbCode());
        }
    }

    /**
     * 设置查询元宝积分log数据
     *
     * @param bean
     */
    @Override
    public void setCoinPointLog(YBIntegralBean bean) {
        loadFinish(true);
        if (bean != null) {
            ybIntegralBean = bean;
            if (ybIntegralBean.getTotal() % size == 0) {
                totalPage = ybIntegralBean.getTotal() / size;
            } else {
                totalPage = (ybIntegralBean.getTotal() / size) + 1;
            }
            initCoinPointLog();
        } else {
            mineAssetsDetailRecycler.setVisibility(View.GONE);
            mineAssetsDetailRlNoData.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示查询元宝积分log数据
     */
    @Override
    public void initCoinPointLog() {
        initRecycler();
    }

    /**
     * 显示收支明细
     */
    private void initRecycler() {
        if (ybIntegralBean.getRows() == null) {
            mineAssetsDetailRecycler.setVisibility(View.GONE);
            mineAssetsDetailRlNoData.setVisibility(View.VISIBLE);
            return;
        }
        if (pageNum == 1) {
            if (ybIntegralBean.getRows().size() == 0) {
                mineAssetsDetailRecycler.setVisibility(View.GONE);
                mineAssetsDetailRlNoData.setVisibility(View.VISIBLE);
                return;
            }
            integralRecyclerAdapter = new IntegralDetailAdapter(
                    getContext(), ybIntegralBean.getRows(), type,
                    R.layout.mine_item_assets_integral_detail);
            mineAssetsDetailRecycler.setAdapter(integralRecyclerAdapter);
        } else {
            if (ybIntegralBean.getRows().size() == 0) {
                return;
            }
            integralRecyclerAdapter.addAll(ybIntegralBean.getRows());
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        initData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (pageNum == totalPage) {
            ToastView.showToast(getActivity(), "暂无更多数据");
            loadFinish(true);
            return;
        }
        pageNum++;
        initData();
    }

    protected void loadFinish(boolean successful) {
        mineFragmentAssetsDetailOneSwipe.finishRefresh(successful);
        mineFragmentAssetsDetailOneSwipe.finishLoadMore(successful);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageWrap message) {
        type = Integer.parseInt(message.message);
        initData();
    }

}