package com.yuanbaogo.mine.myfollow.view;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;
import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
import com.yuanbaogo.mine.myfollow.contract.MyFollowContract;
import com.yuanbaogo.mine.myfollow.presenter.MyFollowPresenter;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.dialog.ConfirmDialogView;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.My_Follow_Activity)
public class MyFollowActivity extends MvpBaseActivityImpl<MyFollowContract.View, MyFollowPresenter> implements OnLoadMoreListener, MyFollowContract.View , View.OnClickListener, MyFansFollowItemAdapter.FollowStateOnCall {

    private EditText mMyFollowSearchEt;
    private TextView mMyFollowSearchTv;
    private RecyclerView mMyFollowListRv;
    private SmartRefreshLayout mMyFollowListSl;
    private TextView mFollowSearchResultTv;

    private MyFansFollowItemAdapter myFansFollowItemAdapter;
    private List<MyFansFollowItem> mMyFansFollowItems = new ArrayList<>();
    protected int mPage = DEFAULT_ONE;
    private String keyword = "";
    private int position;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_my_follow;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mMyFollowSearchEt = findViewById(R.id.my_follow_search_et);
        mMyFollowSearchTv = findViewById(R.id.my_follow_search_tv);
        mMyFollowListRv = findViewById(R.id.my_follow_list_rv);
        mMyFollowListSl = findViewById(R.id.my_follow_list_sl);
        mFollowSearchResultTv = findViewById(R.id.follow_search_result_tv);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mMyFollowListSl.setOnLoadMoreListener(this);
        mMyFollowSearchTv.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getMyFollowList(keyword,mPage);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        myFansFollowItemAdapter = new MyFansFollowItemAdapter(this, mMyFansFollowItems);
        mMyFollowListRv.setLayoutManager(new LinearLayoutManager(this));
        myFansFollowItemAdapter.setOnCall(this);
        mMyFollowListRv.setAdapter(myFansFollowItemAdapter);
        mPresenter.getMyFollowList(keyword,mPage);

        mMyFollowSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mMyFollowSearchEt.getText().toString().length()>0){
                    mMyFollowSearchTv.setVisibility(View.VISIBLE);
                }else {
                    mMyFollowSearchTv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        keyword = mMyFollowSearchEt.getText().toString();
        mPage++;
        mPresenter.getMyFollowList(keyword,mPage);
    }

    //获取关注列表成功
    @Override
    public void showMyFollowList(List<MyFansFollowItem> myFansFollowItems) {
        mFollowSearchResultTv.setVisibility(View.GONE);
        mMyFollowListSl.setVisibility(View.VISIBLE);
        mMyFollowListSl.finishLoadMore();
        if (myFansFollowItems.size()< LOAD_ROWS * mPage){
            mMyFollowListSl.finishLoadMoreWithNoMoreData();
        }
        if (!mMyFansFollowItems.isEmpty()){
            mMyFansFollowItems.clear();
        }
        mMyFansFollowItems.addAll(myFansFollowItems);
        myFansFollowItemAdapter.notifyDataSetChanged();
    }

    //关注/取关成功
    @Override
    public void followFans(FollowBean followBeans) {
        mMyFansFollowItems.get(position).setFansState(followBeans.getState().toString());
        myFansFollowItemAdapter.notifyDataSetChanged();
    }

    //获取关注列表失败
    @SuppressLint("SetTextI18n")
    @Override
    public void loadFail(Throwable throwable) {
        if (mMyFollowSearchEt.getText().toString().length() > 0){
            mFollowSearchResultTv.setVisibility(View.VISIBLE);
            mMyFollowListSl.setVisibility(View.GONE);
            mFollowSearchResultTv.setText("没有找到“" + mMyFollowSearchEt.getText().toString() + "”相关结果");
        }else {
//            if (!mMyFansFollowItems.isEmpty()){
                mMyFansFollowItems.clear();
//            }
            myFansFollowItemAdapter.notifyDataSetChanged();
            mFollowSearchResultTv.setVisibility(View.GONE);
            mMyFollowListSl.setVisibility(View.VISIBLE);
        }
        if (mPage > 1) {
            mPage--;
        }
    }

    //关注/取关失败
    @Override
    public void followFansFail(Throwable throwable) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.my_follow_search_tv){//关键字搜索列表
            keyword = mMyFollowSearchEt.getText().toString();
            mPresenter.getMyFollowList(keyword,mPage);
        }
    }

    @Override
    public void onClickImg(String fansState, String followerYbCode, int position) {
        this.position = position;
        if (fansState.equals("0")) {//0：陌生人，显示关注
            mPresenter.getFollowFans("1", followerYbCode);//点击实现关注对方
        }else if (fansState.equals("1") || fansState.equals("3")){//1：关注/相互关注，显示已关注/相互关注
            //弹窗再次确认不在关注
            ConfirmDialogView confirmDialogView = new ConfirmDialogView("取消",
                    "确定不再关注TA了吗？");
            confirmDialogView.show(getSupportFragmentManager(), "search_history");
            confirmDialogView.setOnCallDialog(new OnCallDialog() {
                @Override
                public void onCallDetermine() {
                    mPresenter.getFollowFans("2", followerYbCode);//点击取关
                    confirmDialogView.dismiss();
                }
            });
        }
    }
}