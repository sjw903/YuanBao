package com.yuanbaogo.mine.live.view;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.live.adapter.LiveFragmentAdapter;
import com.yuanbaogo.mine.live.contract.LiveAmountContract;
import com.yuanbaogo.mine.live.presenter.LiveAmountPresenter;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.view.TitleBar;

import java.util.ArrayList;

@Route(path = YBRouter.Live_Amount_Activity)
public class LiveAmountActivity extends MvpBaseActivityImpl<LiveAmountContract.View, LiveAmountPresenter>implements View.OnClickListener {

    private TitleBar mLiveLlTitleBar;
    private SlidingTabLayout tlLive;
    private ViewPager vpLive;
    private LiveAmountFragment fragment;

    private final String[] mTabLayoutTitles = {"全部", "本周","本月"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_live_amount;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mLiveLlTitleBar = findViewById(R.id.live_title_bar);
        tlLive = findViewById(R.id.tl_live);
        vpLive = findViewById(R.id.live_view_pager);

    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mLiveLlTitleBar.setTitleOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mFragments.clear();
        mFragments.add(LiveAmountFragment.newInstance("all"));
        mFragments.add(LiveAmountFragment.newInstance("week"));
        mFragments.add(LiveAmountFragment.newInstance("month"));
        MyPagerAdapter fragmentAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpLive.setOffscreenPageLimit(2);
        vpLive.setAdapter(fragmentAdapter);
        tlLive.setViewPager(vpLive, mTabLayoutTitles);

    }

    @Override
    public void onClick(View view) {

    }

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
            return mTabLayoutTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}