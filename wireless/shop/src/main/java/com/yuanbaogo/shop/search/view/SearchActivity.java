package com.yuanbaogo.shop.search.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentTransaction;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.datacenter.local.search.SearchSPUtils;
import com.yuanbaogo.libbase.baseutil.KeyBoardUtils;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.main.presenter.ShopPresenter;
import com.yuanbaogo.shop.search.contract.SearchContract;
import com.yuanbaogo.zui.edittext.EditTextOnEditor;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.search.SearchHistoryFragment;
import com.yuanbaogo.zui.search.call.OnCallHistory;

/**
 * @Description: 搜索页面
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/6/21 7:08 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.SHOP_SEARCH_ACTIVITY)
public class SearchActivity extends MvpBaseActivityImpl<SearchContract.View, ShopPresenter>
        implements SearchContract.View,
        EditTextOnEditor.OnEditor,
        OnCallHistory,
        View.OnClickListener {

    /**
     * 自定义Head
     */
    HeadView shopSearchHeadView;

    /**
     * 搜索框
     */
    EditText libHeadEditSearch;

    /**
     * 搜索内容
     */
    @Autowired(name = YBRouter.ShopSearchActivityParams.searchMerchandiseBean)
    SearchMerchandiseBean mSearchBean;

    /**
     * 搜索内容
     */
    String mSearch;

    /**
     * 显示历史搜索
     */
    FrameLayout shopSearchHistory;

    /**
     * 搜索历史记录
     */
    SearchHistoryFragment searchHistoryFragment;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopSearchHeadView = findViewById(R.id.shop_search_head_view);
        libHeadEditSearch = shopSearchHeadView.getLibHeadEditSearch();
        shopSearchHistory = findViewById(R.id.shop_search_history);

        //自动弹出软键盘
        libHeadEditSearch.postDelayed(new Runnable() {
            @Override
            public void run() {
                libHeadEditSearch.requestFocus();
                InputMethodManager manager = ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE));
                if (manager != null) manager.showSoftInput(libHeadEditSearch, 0);
            }
        }, 800);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        shopSearchHeadView.getLibHeadTvRight().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lib_head_tv_right) {
            if (!TextUtils.isEmpty(libHeadEditSearch.getText().toString())) {
                initSaveOrSearch(libHeadEditSearch.getText().toString());
            } else {
                initSaveOrSearch(mSearchBean.getEsGoodsName());
            }
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        initHeadView();

        initSearchHistory();

        initEditSearch();
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
                .setRlSearchBg(R.drawable.shape_gradient_bg_gray_50)
                .setTvSearch(false)
                .setEditSearch(true)
                .setImgRight(false);
        shopSearchHeadView.setHead(headBean);
        shopSearchHeadView.getLibHeadTvRight().setText("搜索");
        shopSearchHeadView.getLibHeadTvRight().setVisibility(View.VISIBLE);
    }

    /**
     * 设置历史搜索
     */
    private void initSearchHistory() {
        searchHistoryFragment = new SearchHistoryFragment();
        searchHistoryFragment.limitLineCount = 2;
        searchHistoryFragment.spName = SearchSPUtils.SEARCH_HISTORY_SHOP;
        searchHistoryFragment.setOnCallHistory(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.shop_search_history, searchHistoryFragment);
        transaction.commit();
    }

    /**
     * 搜索输入框
     */
    private void initEditSearch() {
        libHeadEditSearch.setHint(mSearchBean.getEsGoodsName());
        mSearch = mSearchBean.getEsGoodsName();
        EditTextOnEditor.getOnEditor()
                .setEditTextOnEditor(libHeadEditSearch)
                .setOnEditor(this)
                .startEditor();
    }

    @Override
    public void onEditor(TextView textView, int actionId, KeyEvent keyEvent, String searchKey) {
        initSaveOrSearch(searchKey);
    }

    /**
     * 进行搜索
     *
     * @param searchKey
     */
    private void initSaveOrSearch(String searchKey) {
        if (KeyBoardUtils.isSoftShowing(SearchActivity.this)) {
            KeyBoardUtils.hintKeyboard(SearchActivity.this);
        }
        if (!TextUtils.isEmpty(searchKey)) {
            //搜索
            mSearch = searchKey;
            if (!TextUtils.isEmpty(mSearch)) {
                SearchSPUtils.getInstance(SearchActivity.this)
                        .save(searchHistoryFragment.spName, mSearch);
            }
            if (searchHistoryFragment != null) {
                searchHistoryFragment.initHistory(1);
            }
            mSearchBean.setEsGoodsName(mSearch);
            if (mSearchBean.isFinsh()) {
                RouterHelper.toShopProductDetailsListResult(this, mSearchBean);
                return;
            }
            RouterHelper.toShopProductDetailsList(mSearchBean);
        }
    }

    @Override
    public void onNoEditor(TextView textView, int actionId, KeyEvent keyEvent, String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            initSaveOrSearch(mSearchBean.getEsGoodsName());
        }
    }

    /**
     * 历史记录点击搜索
     *
     * @param view
     * @param search
     */
    @Override
    public void onCallHistoryItem(View view, String search) {
        //点击了
        libHeadEditSearch.setText(search);
        libHeadEditSearch.setSelection(search.length());//光标在最后
        initSaveOrSearch(search);
    }

}