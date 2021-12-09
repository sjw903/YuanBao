package com.yuanbaogo.mine.collection;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.collection.adapter.CollectionRecyclerAdapter;
import com.yuanbaogo.mine.collection.model.CollectionItem;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.COLLECTION_ACTIVITY)
public class CollectionActivity extends MvpBaseActivityImpl<CollectionContract.View, CollectionPresenter>
        implements CollectionContract.View, View.OnClickListener, OnRefreshListener, OnLoadMoreListener,
        CompoundButton.OnCheckedChangeListener, CollectionRecyclerAdapter.OnRefreshCollection {

    private TitleBar mCollectionTitleBar;
    private SmartRefreshLayout mCollectionSmartRefresh;
    private RecyclerView mCollectionRv;
    private ImageView mCollectionIvTop;
    private ImageView mCollectionIvCar;
    private RelativeLayout mCollectionLlDelete;
    private CheckBox mCollectionCbAll;
    private TextView mCollectionTvDelete;
    private ImageView mCollectionIvEmpty;

    private List<CollectionItem> mCollectionItems = new ArrayList<>();
    private int mPage = DEFAULT_ONE;
    private CollectionRecyclerAdapter mAdapter;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_collection;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mCollectionTitleBar = findViewById(R.id.collection_title_bar);
        mCollectionSmartRefresh = findViewById(R.id.collection_smart_refresh);
        mCollectionRv = findViewById(R.id.collection_rv);
        mCollectionIvTop = findViewById(R.id.collection_iv_top);
        mCollectionIvCar = findViewById(R.id.collection_iv_car);
        mCollectionLlDelete = findViewById(R.id.collection_ll_delete);
        mCollectionCbAll = findViewById(R.id.collection_cb_all);
        mCollectionTvDelete = findViewById(R.id.collection_tv_delete);
        mCollectionIvEmpty = findViewById(R.id.collection_iv_empty);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mCollectionTitleBar.setRightTextOnClickListener(this);
        mCollectionSmartRefresh.setOnRefreshListener(this);
        mCollectionSmartRefresh.setOnLoadMoreListener(this);
        mCollectionIvCar.setOnClickListener(this);
        mCollectionIvTop.setOnClickListener(this);
        mCollectionCbAll.setOnCheckedChangeListener(this);
        mCollectionTvDelete.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mAdapter = new CollectionRecyclerAdapter(this, mCollectionItems);
        mCollectionRv.setAdapter(mAdapter);
        mAdapter.setOnRefreshCollection(this);
        clear();
        loadData();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.title_bar_tv_right) {
            // 管理按钮
            if (mAdapter.getFlag() == CollectionRecyclerAdapter.NORMAL) {
                deSelectAll();
                mAdapter.setFlag(CollectionRecyclerAdapter.SELECTED);
                mCollectionLlDelete.setVisibility(View.VISIBLE);
                mCollectionTitleBar.setRightText("完成");
                mCollectionTitleBar.setRightTextColor(getColor(R.color.wing_selected_title));
            } else {
                deSelectAll();
                mAdapter.setFlag(CollectionRecyclerAdapter.NORMAL);
                mCollectionLlDelete.setVisibility(View.GONE);
                mCollectionTitleBar.setRightText("管理");
                mCollectionTitleBar.setRightTextColor(getColor(R.color.wing_sub_title));
            }
        } else if (id == R.id.collection_iv_car) {
            // 购物车
            RouterHelper.toShopCart();
        } else if (id == R.id.collection_iv_top) {
            // 回到顶部
            mCollectionRv.smoothScrollToPosition(0);
        } else if (id == R.id.collection_tv_delete) {
            // 删除选中商品
            mPresenter.deleteFavorites(mAdapter.mSelectedItems);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage = DEFAULT_ONE;
        clear();
        loadData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        loadData();
    }

    private void loadData() {
        mPresenter.getCollectionList(mPage);
    }

    private void clear() {
        if (mCollectionItems.size() <= 0) {
            return;
        }
        mCollectionItems.clear();
    }

    private void loadFail() {
        if (mPage > 1) mPage--;
        else mPage = DEFAULT_ONE;
    }

    protected void loadFinish(boolean successful) {
        mCollectionSmartRefresh.finishRefresh(successful);
        mCollectionSmartRefresh.finishLoadMore(successful);
        if (!successful) loadFail();
    }

    @Override
    public void showCollectionList(List<CollectionItem> collectionItems) {
        loadFinish(true);
        showEmpty(collectionItems == null);
        if (collectionItems == null) return;
        mCollectionItems.addAll(collectionItems);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 是否显示缺省页面
     *
     * @param isShowEmpty true 显示  false 不显示
     */
    protected void showEmpty(boolean isShowEmpty) {
        if (isShowEmpty) {
            mCollectionIvEmpty.setVisibility(View.VISIBLE);
            mCollectionSmartRefresh.setVisibility(View.GONE);
        } else {
            mCollectionIvEmpty.setVisibility(View.GONE);
            mCollectionSmartRefresh.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadFail(Throwable throwable) {
        loadFinish(false);
    }

    @Override
    public void moveShopCart(boolean result) {
        if (result) {
            ToastView.showToast("加入购物车成功");
        } else {
            ToastView.showToast("加入购物车失败");
        }
    }

    @Override
    public void deleteFavorites(boolean result) {
        if (result) {
            List<CollectionItem> collectionItems = new ArrayList<>();
            for (int i = 0; i < mCollectionItems.size(); i++) {
                if (mAdapter.mSelectedItems.contains(mCollectionItems.get(i).getId())) {
                    collectionItems.add(mCollectionItems.get(i));
                }
            }
            mCollectionItems.removeAll(collectionItems);
            mAdapter.notifyDataSetChanged();
            if (isSelectAll) {
                mCollectionLlDelete.setVisibility(View.GONE);
                mCollectionTitleBar.setRightText("管理");
                mCollectionTitleBar.setRightTextColor(getColor(R.color.wing_sub_title));
                showEmpty(true);
            }
            ToastView.showToast("删除成功");
        } else {
            ToastView.showToast("删除失败");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isSelectAll = isChecked;
        if (isChecked) {
            selectAll();
            mCollectionCbAll.setTextColor(getColor(R.color.wing_selected_title));
        } else {
            deSelectAll();
            mCollectionCbAll.setTextColor(getColor(R.color.wing_sub_title_hint));
        }
    }

    /**
     * 是否全选
     */
    boolean isSelectAll;

    private void selectAll() {
        mAdapter.mSelectedItems.clear();
        for (int i = 0, count = mAdapter.getItemCount(); i < count; i++) {
            mAdapter.mSelectedItems.add(mAdapter.getId(i));
        }
        initBottomDeleteLayout(true);
    }

    private void deSelectAll() {
        mAdapter.mSelectedItems.clear();
        initBottomDeleteLayout(true);
    }

    @Override
    public void onRefresh() {
        initBottomDeleteLayout(false);
    }

    private void initBottomDeleteLayout(boolean refresh) {
        mCollectionTvDelete.setEnabled(mAdapter.mSelectedItems.size() > 0);
        mCollectionTvDelete.setText("删除 ( " + mAdapter.mSelectedItems.size() + " )");
        if (refresh) {
            mAdapter.notifyDataSetChanged();
        }
        mCollectionCbAll.setChecked(mAdapter.mSelectedItems.size() == mCollectionItems.size());
    }

}