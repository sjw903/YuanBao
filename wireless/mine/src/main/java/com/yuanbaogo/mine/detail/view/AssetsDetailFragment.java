package com.yuanbaogo.mine.detail.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.integral.model.YBIntegralBean;

import java.util.ArrayList;

/**
 * @Description: 收支明细
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/27/21 6:28 PM
 * @Modifier:
 * @Modify:
 */
public class AssetsDetailFragment extends Fragment {

    View mView;

    /**
     * 1 五百专区
     * 2 五千专区
     * 3 五万专区
     * 4 元宝积分
     * 5 零钱
     */
    int type = 1;

    /**
     * 默认显示标题数据
     */
    private String[] mTitles = {"余额收支明细", "元宝券收支明细"};

    /**
     * 刷新 加载控件
     */
    SlidingTabLayout mineAssetsDetailTabTitle;

    /**
     * 切换
     */
    ViewPager mineAssetsDetailVP;

    /**
     * 明细适配器
     */
    private MyPagerAdapter mAdapter;

    /**
     * Fragment集合
     */
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public static AssetsDetailFragment getInstance(int type, String[] mTitles) {
        AssetsDetailFragment fragment = new AssetsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putStringArray("title", mTitles);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.mine_fragment_assets_detail, container, false);
        mineAssetsDetailTabTitle = mView.findViewById(R.id.mine_assets_detail_tab_title);
        mineAssetsDetailVP = mView.findViewById(R.id.mine_assets_detail_vp);
        type = getArguments().getInt("type");
        mTitles = getArguments().getStringArray("title");
        initTabVp();
        return mView;
    }

    private void initTabVp() {
        for (String title : mTitles) {
            mFragments.add(AssetsDetailListFragment.getInstance(title, type));
        }
        mAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        mineAssetsDetailVP.setAdapter(mAdapter);
        mineAssetsDetailTabTitle.setViewPager(mineAssetsDetailVP, mTitles);
        if (mTitles.length < 2) {
            mineAssetsDetailTabTitle.setIndicatorColor(getResources().getColor(R.color.colorFFFFFF));
        } else {
            mineAssetsDetailTabTitle.setIndicatorColor(getResources().getColor(R.color.color212121));
        }
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
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
