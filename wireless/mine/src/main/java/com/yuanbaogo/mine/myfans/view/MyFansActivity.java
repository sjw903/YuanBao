package com.yuanbaogo.mine.myfans.view;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;
import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.myfans.adapter.MyFansFollowItemAdapter;
import com.yuanbaogo.mine.myfans.contract.MyFansContract;
import com.yuanbaogo.mine.myfans.model.FollowBean;
import com.yuanbaogo.mine.myfans.model.MyFansFollowItem;
import com.yuanbaogo.mine.myfans.presenter.MyFansPresenter;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.dialog.ConfirmDialogView;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.My_Fans_Activity)
public class MyFansActivity extends MvpBaseActivityImpl<MyFansContract.View, MyFansPresenter> implements OnLoadMoreListener, MyFansContract.View, MyFansFollowItemAdapter.FollowStateOnCall {
    private RecyclerView mMyFansListRv;
    private MyFansFollowItemAdapter myFansFollowItemAdapter;
    private List<MyFansFollowItem> mMyFansFollowItems = new ArrayList<>();
    private SmartRefreshLayout mMyFansListSl;
    protected int mPage = DEFAULT_ONE;
    private int position;


    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_my_fans;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mMyFansListRv = findViewById(R.id.my_fans_list_rv);
        mMyFansListSl = findViewById(R.id.my_fans_list_sl);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mMyFansListSl.setOnLoadMoreListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getMyFansList(mPage);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        myFansFollowItemAdapter = new MyFansFollowItemAdapter(this, mMyFansFollowItems);
        mMyFansListRv.setLayoutManager(new LinearLayoutManager(this));
        myFansFollowItemAdapter.setOnCall(this);
        mMyFansListRv.setAdapter(myFansFollowItemAdapter);
        mPresenter.getMyFansList(mPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        mPresenter.getMyFansList(mPage);
    }

    //获取粉丝列表成功
    @Override
    public void showMyFansList(List<MyFansFollowItem> myFansFollowItems) {
        mMyFansListSl.finishLoadMore();
        if (myFansFollowItems.size()< LOAD_ROWS * mPage){
            mMyFansListSl.finishLoadMoreWithNoMoreData();
        }
        if (!mMyFansFollowItems.isEmpty()){
            mMyFansFollowItems.clear();
        }
        mMyFansFollowItems.addAll(myFansFollowItems);
        myFansFollowItemAdapter.notifyDataSetChanged();
    }

    //关注、取消关注成功
    @Override
    public void followFans(FollowBean followBeans) {
        mMyFansFollowItems.get(position).setFansState(followBeans.getState().toString());
        myFansFollowItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(Throwable throwable) {
        if (mPage > 1) {
            mPage--;
        }
    }

    @Override
    public void onClickImg(String fansState, String followerYbCode, int position) {
        this.position = position;
        if (fansState.equals("2")) {//2:被关注，显示回粉,点击实现回粉，关注对方
            mPresenter.getFollowFans("1", followerYbCode);//关注状态：1.关注，2.取消关注
        } else if (fansState.equals("3")) {//3：相互关注，显示相互关注,点击显示弹框，是否取关

            //弹窗再次确认不在关注
            ConfirmDialogView confirmDialogView = new ConfirmDialogView("取消",
                    "确定不再关注TA了吗？");
            confirmDialogView.show(getSupportFragmentManager(), "search_history");
            confirmDialogView.setOnCallDialog(new OnCallDialog() {
                @Override
                public void onCallDetermine() {
                    mPresenter.getFollowFans("2", followerYbCode);
                    confirmDialogView.dismiss();
                }
            });
        }

    }
}