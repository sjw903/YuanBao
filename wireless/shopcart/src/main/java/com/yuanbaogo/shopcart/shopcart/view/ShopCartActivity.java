package com.yuanbaogo.shopcart.shopcart.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.shopcart.R;
import com.yuanbaogo.shopcart.main.view.ShopCartFragment;
import com.yuanbaogo.shopcart.shopcart.contract.ShopCartAContract;
import com.yuanbaogo.shopcart.shopcart.presenter.ShopCartAPresenter;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/3/21 9:42 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.SHOP_CART_ACTIVITY)
public class ShopCartActivity extends MvpBaseActivityImpl<ShopCartAContract.View, ShopCartAPresenter> {

    FrameLayout shopCartFl;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_shop_cart;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        shopCartFl = findViewById(R.id.shop_cart_fl);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initFragmet();
    }

    private void initFragmet() {
        ShopCartFragment shopCartFragment = new ShopCartFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.shop_cart_fl, shopCartFragment.getInstance(2));
        transaction.commit();
    }

}