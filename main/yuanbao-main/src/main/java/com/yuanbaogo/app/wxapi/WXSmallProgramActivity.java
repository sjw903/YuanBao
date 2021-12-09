package com.yuanbaogo.app.wxapi;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanbaogo.app.R;
import com.yuanbaogo.commonlib.base.BaseActivityImpl;
import com.yuanbaogo.router.YBRouter;

/**
 * 首页小程序展示
 *
 * @author steven
 */
@Route(path = YBRouter.EVENT_ACTIVITY)
public class WXSmallProgramActivity extends BaseActivityImpl {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        setStatusBar();
        initView();
    }
    /**
     * 设置透明状态栏
     */
    protected void setStatusBar() {
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));//设置状态栏颜色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色

    }
    private void initView() {
        ImageView eventImage = findViewById(R.id.img_event);
        ImageView closeImage = findViewById(R.id.img_close);

        eventImage.setOnClickListener(v -> {
            goSmallProgram();
            finish();
        });
        closeImage.setOnClickListener(v -> {
            finish();
        });
    }

    private void goSmallProgram() {
        String appId = "wxd930ea5d5a258f4f"; // 填移动应用(App)的 AppId，非小程序的 AppID
        IWXAPI api = WXAPIFactory.createWXAPI(this, appId);

        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "gh_d43f693ca31f"; // 填小程序原始id
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        api.sendReq(req);
    }
}