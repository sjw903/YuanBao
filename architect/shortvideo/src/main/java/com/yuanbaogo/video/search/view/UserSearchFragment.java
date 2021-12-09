package com.yuanbaogo.video.search.view;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.search.contract.UserSearchContract;
import com.yuanbaogo.video.search.presenter.UserSearchPresenter;

/**
 * @author lhx
 * @description: 用户搜索结果列表
 * @date : 2021/7/13 16:43
 */
public class UserSearchFragment extends MvpBaseFragmentImpl<UserSearchContract.View, UserSearchPresenter>
    implements UserSearchContract.View{

    SwipeRefreshLayout mSrlUserSearch;

    RecyclerView mRvUserSearch;

    ImageView mIvUserSearchNo;

    UserSearchPresenter userSearchPresenter = new UserSearchPresenter();

    @Override
    protected UserSearchPresenter createPresenter(Bundle savedInstanceState) {
        return userSearchPresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.fragment_user_search;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mSrlUserSearch = (SwipeRefreshLayout) findViewById(R.id.srl_user_search);
        mRvUserSearch = (RecyclerView) findViewById(R.id.rv_user_search);
        mIvUserSearchNo = (ImageView) findViewById(R.id.iv_user_search_no);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    public void setScreen(String title) {

    }

}
