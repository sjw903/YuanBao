package com.yuanbaogo.video.search.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.datacenter.local.search.SearchSPUtils;
import com.yuanbaogo.libbase.baseutil.KeyBoardUtils;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.search.adapter.VideoSearchHotAdapter;
import com.yuanbaogo.video.search.contract.VideoAndUserSearchContract;
import com.yuanbaogo.video.search.presenter.VideoAndUserSearchPresenter;
import com.yuanbaogo.zui.edittext.EditTextOnEditor;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.search.SearchHistoryFragment;
import com.yuanbaogo.zui.search.call.OnCallHistory;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;

/**
 * @author lhx
 * @description: 短视频搜索页面
 * @date : 2021/7/13 14:23
 */
public class VideoAndUserSearchActivity extends MvpBaseActivityImpl<VideoAndUserSearchContract.View, VideoAndUserSearchPresenter>
        implements VideoAndUserSearchContract.View, OnCallHistory, EditTextOnEditor.OnEditor, TextWatcher, OnTabSelectListener {

    private HeadView ziHeadView;
    private EditText etVideoAndUser;
    private FrameLayout fl_search_history;
    private SearchHistoryFragment searchHistoryFragment;
    private LinearLayout ll_hot;
    private RecyclerView rv_video_hot;
    private SlidingTabLayout tl_video_user_search;
    private ViewPager vp_video_user_search;
    private VideoSearchHotAdapter videoSearchHotAdapter;
    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"视频", "用户"};
    private String mSearch;
    private VideoSearchFragment videoSearchFragment;
    private UserSearchFragment userSearchFragment;

    /**
     * 设置历史搜索
     */
    private void initSearchHistory() {
        searchHistoryFragment = new SearchHistoryFragment();
        searchHistoryFragment.limitLineCount = 2;
        searchHistoryFragment.spName = SearchSPUtils.SEARCH_HISTORY_VIDEO;
        searchHistoryFragment.setOnCallHistory(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_search_history, searchHistoryFragment);//默认显示第一个Frament
        transaction.commit();
    }

    /**
     * 标题栏
     */
    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(true)
                .setRlSearchBg(R.drawable.shape_bg_f1f5f6_18)
                .setTvSearch(false)
                .setEditSearch(true)
                .setImgRight(false);
        ziHeadView.setHead(headBean);
    }

    /**
     * 搜索输入框
     */
    private void initEditSearch() {
        EditTextOnEditor onEditor = EditTextOnEditor.getOnEditor();
        onEditor.setEditTextOnEditor(etVideoAndUser);
        onEditor.setOnEditor(this);
        onEditor.startEditor();
    }

    /**
     * 历史记录点击搜索
     * @param view
     * @param search
     */
    @Override
    public void onCallHistoryItem(View view, String search) {
        //点击了
        etVideoAndUser.setText(search);
        etVideoAndUser.setSelection(search.length());//光标在最后
        initSaveOrSearch(search);
    }

    /**
     * 进行搜索
     *
     * @param searchKey
     */
    private void initSaveOrSearch(String searchKey) {
        if (KeyBoardUtils.isSoftShowing(this)) {
            KeyBoardUtils.hintKeyboard(this);
        }
        if (!TextUtils.isEmpty(searchKey)) {
            //搜索
            mSearch = etVideoAndUser.getText().toString();
            if (!TextUtils.isEmpty(mSearch)) {
                SearchSPUtils.getInstance(this)
                        .save(SearchSPUtils.SEARCH_HISTORY_VIDEO, etVideoAndUser.getText().toString());
            }
            if (searchHistoryFragment != null) {
                searchHistoryFragment.initHistory(1);
            }
            tl_video_user_search.setVisibility(View.VISIBLE);
            vp_video_user_search.setVisibility(View.VISIBLE);
            fl_search_history.setVisibility(View.GONE);
            ll_hot.setVisibility(View.GONE);
            viewShowOrHidde(searchKey);
        }
    }

    public void viewShowOrHidde(String title) {
        if (KeyBoardUtils.isSoftShowing(this)) {
            KeyBoardUtils.hintKeyboard(this);
        }
        if (tabState == 0) {//视频
            videoSearchFragment.setTitle(title);
        } else if (tabState == 1) {//用户
            userSearchFragment.setScreen(title);
        }
    }

    @Override
    public void onEditor(TextView textView, int actionId, KeyEvent keyEvent, String searchKey) {
        initSaveOrSearch(searchKey);
    }

    @Override
    public void onNoEditor(TextView textView, int actionId, KeyEvent keyEvent, String searchKey) {
        ToastView.showToast(this, 0, "请输入搜索内容");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length() == 0) {//输入框值被删除
            fl_search_history.setVisibility(View.VISIBLE);
            ll_hot.setVisibility(View.VISIBLE);
            tl_video_user_search.setVisibility(View.GONE);
            vp_video_user_search.setVisibility(View.GONE);
        }
    }

    private int tabState;

    /**
     * 点击tab标签
     * @param position
     */
    @Override
    public void onTabSelect(int position) {
        tabState = position;
    }

    /**
     * 再次点击tab标签
     * @param position
     */
    @Override
    public void onTabReselect(int position) {

    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_video_and_user_search;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        ziHeadView = findViewById(R.id.video_head_view);
        etVideoAndUser = ziHeadView.getLibHeadEditSearch();
        fl_search_history = findViewById(R.id.fl_search_history);
        ll_hot = findViewById(R.id.ll_hot);
        rv_video_hot = findViewById(R.id.rv_video_hot);
        tl_video_user_search = findViewById(R.id.tl_video_user_search);
        vp_video_user_search = findViewById(R.id.vp_video_user_search);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        etVideoAndUser.addTextChangedListener(this);
        tl_video_user_search.setOnTabSelectListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        initEditSearch();
        initSearchHistory();
        videoSearchFragment = new VideoSearchFragment();
        userSearchFragment = new UserSearchFragment();
        mFragments.add(videoSearchFragment);
        mFragments.add(userSearchFragment);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp_video_user_search.setAdapter(mAdapter);
        tl_video_user_search.setViewPager(vp_video_user_search, mTitles);
    }

    /**
     * TabLayout适配器
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
