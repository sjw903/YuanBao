package com.yuanbaogo.commonlib.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * @Description: Fragment 切换
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/9/21 1:27 PM
 * @Modifier:
 * @Modify:
 */
public class FragmentUtils {

    private FragmentManager fragmentManager;
    private int id;

    public FragmentUtils(FragmentManager fragmentManager, int id) {
        this.fragmentManager = fragmentManager;
        this.id = id;
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    public void addFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment, tag);
        fragmentTransaction.commit();
    }

    public void setListFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment1 : fragments) {
            fragmentTransaction.hide(fragment1);
        }
        if (!fragments.contains(fragment)) {
            fragmentTransaction.add(id, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();//TODO HG fragmentTransaction.commit();
    }

    public void setListFragments(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment1 : fragments) {
            fragmentTransaction.hide(fragment1);
        }
        if (!fragments.contains(fragment)) {
            fragmentTransaction.add(id, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

}
