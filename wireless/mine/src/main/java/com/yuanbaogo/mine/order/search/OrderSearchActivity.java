package com.yuanbaogo.mine.order.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.luck.picture.lib.tools.SPUtils;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;
import com.yuanbaogo.zui.view.FlowLayout;

import java.util.HashSet;
import java.util.Set;

@Route(path = YBRouter.ORDER_SEARCH_ACTIVITY)
public class OrderSearchActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> implements View.OnClickListener {

    private static final String ORDER_SEARCH = "order_search";
    private LinearLayout mSearchLlTitleBar;
    private ImageButton mSearchIbBack;
    private EditText mSearchEtSearch;
    private TextView mSearchTvSearch;
    private ImageView mSearchIvDelete;
    private FlowLayout mSearchFlowLayout;
    private Set<String> mSearchKeyword;
    private SPUtils mSpUtils;
    @Autowired(name = YBRouter.OrderActivityParams.orderType)
    int orderType;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_order_search;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mSearchLlTitleBar = findViewById(R.id.search_ll_title_bar);
        mSearchIbBack = findViewById(R.id.search_ib_back);
        mSearchEtSearch = findViewById(R.id.search_et_search);
        mSearchTvSearch = findViewById(R.id.search_tv_search);
        mSearchIvDelete = findViewById(R.id.search_iv_delete);
        mSearchFlowLayout = findViewById(R.id.search_flow_layout);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mSearchIbBack.setOnClickListener(this);
        mSearchTvSearch.setOnClickListener(this);
        mSearchIvDelete.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        mSearchLlTitleBar.setPadding(0, statusBarHeight, 0, 0);
        mSpUtils = SPUtils.getInstance();
        mSearchKeyword = mSpUtils.getStringSet(ORDER_SEARCH, new HashSet<>());
        for (String next : mSearchKeyword) {
            View view = LayoutInflater.from(this).inflate(R.layout.mine_layout_search_item, null);
            TextView textView = view.findViewById(R.id.search_item_tv_keyword);
            textView.setText(next);
            textView.setOnClickListener(v -> {
                RouterHelper.toSearchResult(next,orderType);
                finish();
            });
            mSearchFlowLayout.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.search_ib_back) {
            finish();
        } else if (id == R.id.search_tv_search) {
            String search = mSearchEtSearch.getText().toString().trim();
            if (TextUtils.isEmpty(search)) {
                ToastUtil.showToast(R.string.search_toast);
                return;
            }
            mSearchKeyword.add(search);
            mSpUtils.put(ORDER_SEARCH, mSearchKeyword);
            RouterHelper.toSearchResult(search,orderType);
            finish();
        } else if (id == R.id.search_iv_delete) {
            mSpUtils.put(ORDER_SEARCH, new HashSet<>());
            mSearchFlowLayout.removeAllViews();
        }
    }

}