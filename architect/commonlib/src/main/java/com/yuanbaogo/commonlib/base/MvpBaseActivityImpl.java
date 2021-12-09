package com.yuanbaogo.commonlib.base;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OSUtils;
import com.yuanbaogo.libbase.basemvp.IBaseView;
import com.yuanbaogo.libbase.basemvp.MvpBaseActivity;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.libbase.view.BaseActivity;
import com.yuanbaogo.network.HttpUtil;


/**
 * @author yangf
 * @version 1.0
 */
public abstract class MvpBaseActivityImpl<V extends IBaseView, P extends MvpBasePersenter> extends MvpBaseActivity<V, P> implements IBaseView {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 所有子类都将继承这些相同的属性,请在设置界面之后设置
//        ImmersionBar.with(this)
//                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
//                .flymeOSStatusBarFontColor(R.color.color424242)  //修改flyme OS状态栏字体颜色
//                .init();
        setStatusBar();
        // 设置强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    public void onResume() {
        super.onResume();
        // 非必加
        // 如果你的app可以横竖屏切换，适配了华为emui3系列系统手机，并且navigationBarWithEMUI3Enable为true，
        // 请在onResume方法里添加这句代码（同时满足这三个条件才需要加上代码哦：1、横竖屏可以切换；2、华为emui3系列系统手机；3、navigationBarWithEMUI3Enable为true）
        // 否则请忽略
        if (OSUtils.isEMUI3_x()) {
            ImmersionBar.with(this).init();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpUtil.cancel(this);
        ToastUtil.cancel();
        // 必须调用该方法，防止内存泄漏
        ImmersionBar.destroy(this, null);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 非必加
        // 如果你的app可以横竖屏切换，适配了4.4或者华为emui3.1系统手机，并且navigationBarWithKitkatEnable为true，
        // 请务必在onConfigurationChanged方法里添加如下代码（同时满足这三个条件才需要加上代码哦：1、横竖屏可以切换；2、android4.4或者华为emui3.1系统手机；3、navigationBarWithKitkatEnable为true）
        // 否则请忽略
        ImmersionBar.with(this).init();
    }

    /**
     * 设置透明状态栏
     */
    protected void setStatusBar() {
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));//设置状态栏颜色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色

    }

}
