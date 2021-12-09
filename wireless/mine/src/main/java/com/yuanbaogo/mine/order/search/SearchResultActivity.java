package com.yuanbaogo.mine.order.search;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.cancel.CancelOrderFragment;
import com.yuanbaogo.mine.order.finish.FinishOrderFragment;
import com.yuanbaogo.mine.order.pay.PayOrderFragment2;
import com.yuanbaogo.mine.order.receipt.ReceiptOrderFragment;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;
import com.yuanbaogo.router.YBRouter;

@Route(path = YBRouter.SEARCH_RESULT_ACTIVITY)
public class SearchResultActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> implements View.OnClickListener {

    private static final String TAG = "SearchResultActivity";
    @Autowired(name = YBRouter.SearchResultActivityParams.keyword)
    String keyword = "";
    @Autowired(name = YBRouter.OrderActivityParams.orderType)
    int orderType;
    private LinearLayout mSearchLlTitleBar;
    private ImageButton mSearchResultIbBack;
    private TextView mSearchTvSearch;
    private ImageButton mSearchResultIbMessage;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_search_result;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mSearchLlTitleBar = findViewById(R.id.search_ll_title_bar);
        mSearchResultIbBack = findViewById(R.id.search_result_ib_back);
        mSearchTvSearch = findViewById(R.id.search_tv_search);
        mSearchResultIbMessage = findViewById(R.id.search_result_ib_message);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mSearchResultIbBack.setOnClickListener(this);
        mSearchResultIbMessage.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.search_result_fl, getSearchFragment());
        transaction.commit();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        mSearchLlTitleBar.setPadding(0, statusBarHeight, 0, 0);
        mSearchTvSearch.setText(keyword);
    }

    private Fragment getSearchFragment() {
        switch (orderType) {
            case 1:
                return PayOrderFragment2.newInstance(keyword);
            case 2:
                return ReceiptOrderFragment.newInstance(keyword);
            case 3:
                return FinishOrderFragment.newInstance(keyword);
            default:
                return CancelOrderFragment.newInstance(keyword);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.search_result_ib_back) {
            finish();
        } else if (id == R.id.search_result_ib_message) {
            ToastUtil.showToast("消息");
        }
    }

}