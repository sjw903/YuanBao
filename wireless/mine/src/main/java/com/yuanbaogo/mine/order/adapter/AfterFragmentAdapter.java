package com.yuanbaogo.mine.order.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.yuanbaogo.mine.order.after.apply.RefundApplyFragment;
import com.yuanbaogo.mine.order.after.process.RefundProcessFragment;
import com.yuanbaogo.mine.order.after.record.RefundRecordFragment;


public class AfterFragmentAdapter extends FragmentStateAdapter {

    public AfterFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return RefundApplyFragment.newInstance();
            case 1:
                return RefundProcessFragment.newInstance();
            default:
                return RefundRecordFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}


