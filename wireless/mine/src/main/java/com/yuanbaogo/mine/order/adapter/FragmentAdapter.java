package com.yuanbaogo.mine.order.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.yuanbaogo.mine.order.all.AllOrderFragment;
import com.yuanbaogo.mine.order.cancel.CancelOrderFragment;
import com.yuanbaogo.mine.order.finish.FinishOrderFragment;
import com.yuanbaogo.mine.order.pay.PayOrderFragment2;
import com.yuanbaogo.mine.order.receipt.ReceiptOrderFragment;


public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return AllOrderFragment.newInstance();
            case 1:
                return PayOrderFragment2.newInstance();
            case 2:
                return ReceiptOrderFragment.newInstance();
            case 3:
                return FinishOrderFragment.newInstance();
            default:
                return CancelOrderFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}


