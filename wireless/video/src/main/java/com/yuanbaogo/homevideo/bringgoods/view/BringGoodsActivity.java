package com.yuanbaogo.homevideo.bringgoods.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.bringgoods.adapter.BringGoodsAdapter;
import com.yuanbaogo.homevideo.bringgoods.contract.BringGoodsContract;
import com.yuanbaogo.homevideo.bringgoods.model.LiveGoodsListBean;
import com.yuanbaogo.homevideo.bringgoods.presenter.BringGoodsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 主播带货
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/12/21 5:02 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.BRING_GOODS_ACTIVITY)
public class BringGoodsActivity extends MvpBaseActivityImpl<BringGoodsContract.View, BringGoodsPresenter> implements BringGoodsContract.View,
        View.OnClickListener {

    RecyclerView mineBringGoodsRecycler;

    BringGoodsAdapter bringGoodsAdapter;

    /**
     * 选择模式 多选或者单选:  true 多选  false 单选
     */
    private boolean mSelectMode = true;

    Button mineBringGoodsBut;

    TextView tv_hint;
    TextView tv_key;
    ImageView iv_delete;
    ImageView lib_head_img_right;
    LinearLayout ll_key;
    LinearLayout ll_search_bar;
    LinearLayout search_no_commodity_ll;

    String mSearchKey = "";

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.video_activity_bring_goods;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {

        mineBringGoodsRecycler = findViewById(R.id.bring_goods_recycler);
        mineBringGoodsBut = findViewById(R.id.bring_goods_but);
        lib_head_img_right = findViewById(R.id.lib_head_img_right);

        tv_hint = findViewById(R.id.tv_hint);
        ll_search_bar = findViewById(R.id.ll_search_bar);
        tv_key = findViewById(R.id.tv_key);
        ll_key = findViewById(R.id.ll_key);
        iv_delete = findViewById(R.id.iv_delete);
        search_no_commodity_ll = findViewById(R.id.search_no_commodity_ll);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

        ll_search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterHelper.toShopSearchResult(BringGoodsActivity.this,
                        new SearchMerchandiseBean().setTag(TagParameteBean.liveBringGoods)
                                .setEsGoodsName(mSearchKey).setFinsh(true), 100);
            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchKey = "";
                setSearchBar();
            }
        });
        bringGoodsAdapter.setOnClickListener(new BringGoodsAdapter.OnClickListener() {
            @Override
            public void onCheckedChanged() {
                mPresenter.verification();
            }

            @Override
            public void onSingleCheckedChanged() {
                mPresenter.verification();
            }

            @Override
            public void onItemClick(LiveGoodsListBean bean) {
                RouterHelper.toShopProductDetails(bean.getId(), TagParameteBean.normal, null, null,true);
            }
        });

        mineBringGoodsBut.setOnClickListener(this);
        lib_head_img_right.setOnClickListener(this);
    }

    private void setSearchBar() {
        if (TextUtils.isEmpty(mSearchKey)) {
            ll_key.setVisibility(View.GONE);
            tv_hint.setVisibility(View.VISIBLE);
        } else {
            ll_key.setVisibility(View.VISIBLE);
            tv_key.setText(mSearchKey);
            tv_hint.setVisibility(View.GONE);
        }
        mPresenter.getGoodsList(mSearchKey);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mSelectMode = getIntent().getBooleanExtra("selectMode",true);
        initShopLRecycler();
        mPresenter.getGoodsList(mSearchKey);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bring_goods_but) {
            //需要传递的数据
            setResult(RESULT_OK, new Intent().putStringArrayListExtra("checkgoods", (ArrayList<String>) mPresenter.getGoodsListId()));
            finish();
        } else if (id == R.id.lib_head_img_right) {
            finish();
        }
    }

    private void initShopLRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BringGoodsActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mineBringGoodsRecycler.setLayoutManager(linearLayoutManager);
        bringGoodsAdapter = new BringGoodsAdapter(
                BringGoodsActivity.this, mSelectMode);
        mineBringGoodsRecycler.setAdapter(bringGoodsAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null) {
                Bundle extras = data.getExtras();
                SearchMerchandiseBean mSearchBean = (SearchMerchandiseBean) extras.get("searchBean");
                mSearchKey = mSearchBean.getEsGoodsName();
                setSearchBar();
            }
        }
    }

    @Override
    public void setDataList(List<LiveGoodsListBean> list) {
        if (!list.isEmpty()){
            search_no_commodity_ll.setVisibility(View.GONE);
            mineBringGoodsRecycler.setVisibility(View.VISIBLE);
            mineBringGoodsBut.setVisibility(View.VISIBLE);
            bringGoodsAdapter.setDataList(list);
        }else {
            search_no_commodity_ll.setVisibility(View.VISIBLE);
            mineBringGoodsRecycler.setVisibility(View.GONE);
            mineBringGoodsBut.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSearchBarStatus(Boolean isCan) {
        if (isCan) {
            mineBringGoodsBut.setBackground(getResources().getDrawable(R.drawable.selector_bg_but_login));
            mineBringGoodsBut.setEnabled(true);
        } else {
            mineBringGoodsBut.setBackground(getResources().getDrawable(R.drawable.shape_gradient_bg_but_alpha_50));
            mineBringGoodsBut.setEnabled(false);
        }
    }
}