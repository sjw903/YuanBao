package com.yuanbaogo.app.view;

import static com.yuanbaogo.router.YBRouter.EVENT_ACTIVITY;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yuanbaogo.app.R;
import com.yuanbaogo.app.contract.MainContract;
import com.yuanbaogo.app.parms.GlobalParms;
import com.yuanbaogo.app.presenter.MainPresenter;
import com.yuanbaogo.app.wxapi.WXSmallProgramActivity;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.utils.FragmentUtils;
import com.yuanbaogo.commonlib.utils.fragment.ChangeFragment;
import com.yuanbaogo.commonlib.utils.fragment.ChangeParms;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.find.FindFragment;
import com.yuanbaogo.homevideo.main.view.VideoFragment;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.login.login.view.LoginActivity;
import com.yuanbaogo.mine.mine.view.MineFragment;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.shop.main.view.ShopFragment;
import com.yuanbaogo.shopcart.main.view.ShopCartFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhx
 * @description:
 * @date : 2021/7/1 16:01
 */
@Route(path = YBRouter.MAIN_ACTIVITY)
public class MainActivity extends MvpBaseActivityImpl<MainContract.View, MainPresenter>
        implements View.OnClickListener {

    private FragmentManager supportFragmentManager;

    BottomNavigationView mainBnve;

    FrameLayout mainFrame;

    RelativeLayout mainRlLogin;

    Button mainButLogin;

    FragmentUtils fragmentUtils;

    ShopFragment shopFragment;

    VideoFragment videoFragment;

    FindFragment findFragment;

    ShopCartFragment shopCartFragment;

    MineFragment mineFragment;


    private long mFirstTime;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mainBnve = findViewById(R.id.main_bnve);
        mainFrame = findViewById(R.id.main_frame);
        mainRlLogin = findViewById(R.id.main_rl_login);
        mainButLogin = findViewById(R.id.main_but_login);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mainButLogin.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        initFragment();
        initEvent();
        initBnve();


        List ids = new ArrayList<>();
        ids.add(R.id.main_item_shop);
        ids.add(R.id.main_item_video);
        ids.add(R.id.main_item_shop_cart);
        ids.add(R.id.main_item_mine);
        clearToast(mainBnve, ids);
    }

    //??????????????????
    public static void clearToast(BottomNavigationView bottomNavigationView, List ids) {

        ViewGroup bottomNavigationMenuView = (ViewGroup) bottomNavigationView.getChildAt(0);

        //?????????View,????????????????????????

        for (int position = 0; position < ids.size(); position++) {

            bottomNavigationMenuView.getChildAt(position).findViewById((Integer) ids.get(position)).setOnLongClickListener(new View.OnLongClickListener() {

                @Override

                public boolean onLongClick(View v) {

                    return true;

                }
            });

        }
    }

    private void initBnve() {
        mainBnve.setItemIconTintList(null);
        ChangeParms.setFragmentSelected(new ChangeFragment() {
            @Override
            public void changge(int postion) {
                if (postion == 1) {
                    mainBnve.setSelectedItemId(R.id.main_item_video);
                } else if (postion == 0) {
                    mainBnve.setSelectedItemId(R.id.main_item_shop);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserStore.isLogin()) {
            mainRlLogin.setVisibility(View.GONE);//??????????????????
        }
        new Handler().postDelayed(() -> {
            ARouter.getInstance().build(EVENT_ACTIVITY).navigation();
        }, 5000);
    }

    private void checkIsLogin() {
        //????????????
        if (UserStore.isLogin()) {
            mainRlLogin.setVisibility(View.GONE);//??????????????????
        } else {
            mainRlLogin.setVisibility(View.VISIBLE);//??????????????????
        }
    }

    private void initFragment() {
        //?????????????????????
        supportFragmentManager = getSupportFragmentManager();
        //??????Fragment
        shopFragment = new ShopFragment();
        fragmentUtils = new FragmentUtils(supportFragmentManager, R.id.main_frame);
        fragmentUtils.addFragment(shopFragment);
    }

    private void initEvent() {
        mainBnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_item_shop:
                        shopFragment = GlobalParms.getShopFragment();
                        fragmentUtils.setListFragment(shopFragment);
                        checkIsLogin();//??????????????????????????????????????????
                        break;
                    case R.id.main_item_video:
                        videoFragment = GlobalParms.getVideoFragment();
                        fragmentUtils.setListFragment(videoFragment);
                        mainRlLogin.setVisibility(View.GONE);//??????????????????
                        break;
                    case R.id.main_item_shop_cart:
                        shopCartFragment = GlobalParms.getShopCartFragment();
                        fragmentUtils.setListFragment(shopCartFragment.getInstance(1));
                        checkIsLogin();
                        break;
                    case R.id.main_item_mine:
                        mineFragment = GlobalParms.getMineFragment();
                        fragmentUtils.setListFragment(mineFragment);
                        mainRlLogin.setVisibility(View.GONE);//??????????????????
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.main_but_login) {
//            RouterHelper.toLogin();
            startActivity(new Intent(this, LoginActivity.class));

        }
    }


    /**
     * ????????????
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - mFirstTime > 2000) {
                // ????????????????????????????????????800?????????????????????
                ToastUtil.showToast("??????????????????????????????");
                // ??????firstTime
                mFirstTime = secondTime;
                return true;
            } else {
//                BaseApplicon.baseApplicon.AppExit(this);
//                finish();
                System.exit(0);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * ???????????????
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
