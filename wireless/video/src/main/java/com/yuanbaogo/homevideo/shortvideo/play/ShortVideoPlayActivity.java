package com.yuanbaogo.homevideo.shortvideo.play;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.main.view.RecommendVideoFragment;
import com.yuanbaogo.libbase.view.BaseActivity;
import com.yuanbaogo.router.YBRouter;

import java.util.List;

@Route(path = YBRouter.SHORTVIDEOPLAY)
public class ShortVideoPlayActivity extends BaseActivity {

    private List<RecommendVideoBean.RowsBean> mVideoList;
    private ImageView video_back_iv;
    private int startPosition;
    private String sourcetype;
    private int type;
    private RecommendVideoFragment recommendVideoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_video_play);

        mVideoList = getIntent().getParcelableArrayListExtra("shortvideolist");
        startPosition = getIntent().getIntExtra("startPosition", -1);
        sourcetype = getIntent().getStringExtra("sourcetype");
        type = getIntent().getIntExtra("type", 0);

        video_back_iv = findViewById(R.id.video_back_iv);

        video_back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setStatusBar();

        // 检查活动是否使用布局版本
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {
            // 但是，如果我们正在从以前的状态恢复，我们不需要做其他事请应当return;
            // 可能会有重叠的片段
            if (savedInstanceState != null) {
                return;
            }

            // 在Activity里创建新的Fragment
            recommendVideoFragment = RecommendVideoFragment.newInstance(mVideoList, startPosition, sourcetype, type);

            // 如果Activity是想将Intent传递给Fragment，调用getExtras()作为参数传递给Fragment

            // 添加一个fragment给'fragment_container'的FrameLayout

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, recommendVideoFragment).commit();


        }
    }

    /**
     * 设置透明状态栏
     */
    protected void setStatusBar() {
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));//设置状态栏颜色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色

    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        recommendVideoFragment.pausePlay();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
