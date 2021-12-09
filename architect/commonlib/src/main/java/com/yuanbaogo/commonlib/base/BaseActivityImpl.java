package com.yuanbaogo.commonlib.base;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yuanbaogo.commonlib.R;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.libbase.view.BaseActivity;
import com.yuanbaogo.network.HttpUtil;

public abstract class BaseActivityImpl extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTransparent();
        initARoute();
    }

    private void initARoute() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpUtil.cancel(this);
        ToastUtil.cancel();
    }

    /**
     * 设置透明状态栏
     */
    private void setStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            //设置修改状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏的颜色，和你的APP主题或者标题栏颜色一致就可以了
            window.setStatusBarColor(getResources().getColor(R.color.white));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }
}
