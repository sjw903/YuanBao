package com.yuanbaogo.homevideo.mine.adapter;

import android.content.Context;

import com.yuanbaogo.homevideo.mine.model.TabItemModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * 功能：
 * 时间 ： 2021/8/24:.
 * 作者：11190
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<TabItemModel> models;
    private FragmentManager fm;
    private Context context;

    public FragmentAdapter(Context context, @NonNull FragmentManager fm, List<TabItemModel> models) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.models = models;
        this.fm = fm;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        TabItemModel model = models.get(position);
        Fragment fragment = fm.getFragmentFactory().instantiate(context.getClassLoader(), model.getClazz());
        fragment.setArguments(model.getArgs());
        return fragment;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return models.get(position).getTitle();
    }
}
