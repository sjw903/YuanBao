package com.yuanbaogo.zui.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.banner.adapter.MultipleTypesAdapter;
import com.yuanbaogo.zui.banner.bean.DataBean;
import com.yuanbaogo.zui.banner.indicator.NumIndicator;
import com.yuanbaogo.zui.banner.viewholder.Video2Holder;

import java.util.List;

/**
 * @Description: 自定义轮播图
 * @Params: 依赖 https://codechina.csdn.net/mirrors/youth5201314/banner?utm_source=csdn_github_accelerator
 * @Problem:
 * @Author: HG
 * @Date: 7/2/21 10:09 AM
 * @Modifier:
 * @Modify:
 */
public class BannerView extends LinearLayout {

    Context context;

    /**
     * 父控件
     */
    LinearLayout libLlBannerView;

    /**
     * 轮播图
     */
    Banner libBanner;

    /**
     * 原生播放器
     */
    VideoView videoView;

    /**
     * 轮播数据
     */
    List<DataBean> dataBeans;

    /**
     * 默认允许自动轮播
     */
    boolean isAutoLoop = true;

    /**
     * 默认指示器不显示
     */
    boolean isAttachToBanner = false;

    /**
     * 设置轮播图高度
     */
    int height = 230;

    int indicatorMarginBottom = 0;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        intView();
    }

    /**
     * 初始化
     */
    private void intView() {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_view, this);
        libLlBannerView = view.findViewById(R.id.lib_ll_banner_view);
        libBanner = view.findViewById(R.id.lib_banner);
    }

    /**
     * 设置轮播图高度
     *
     * @param height 是否显示指示器  true 显示  false 不显示
     * @return
     */
    public BannerView setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * 是否允许自动轮播
     *
     * @param isAutoLoop ture 允许，false 不允许
     * @return
     */
    public BannerView isAutoLoop(boolean isAutoLoop) {
        this.isAutoLoop = isAutoLoop;
        return this;
    }

    /**
     * 指示器
     *
     * @param isAttachToBanner 是否显示指示器  true 显示  false 不显示
     * @return
     */
    public BannerView isAttachToBanner(boolean isAttachToBanner) {
        this.isAttachToBanner = isAttachToBanner;
        return this;
    }

    /**
     * 设置指示器距离底部的距离
     *
     * @param indicatorMarginBottom
     * @return
     */
    public BannerView setIndicatorMarginBottom(int indicatorMarginBottom) {
        this.indicatorMarginBottom = indicatorMarginBottom;
        return this;
    }

    /**
     * 轮播数据
     *
     * @param dataBeans
     */
    public void setData(List<DataBean> dataBeans) {
        this.dataBeans = dataBeans;
        initBanner();
    }

    /**
     * 加载轮播图
     */
    private void initBanner() {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) libBanner.getLayoutParams();
        linearParams.height = height;
        libBanner.setLayoutParams(linearParams);
        libBanner
                .addBannerLifecycleObserver(new AppCompatActivity())////添加生命周期观察者
                .setAdapter(new MultipleTypesAdapter(context, dataBeans))//设置banner的适配器
                .isAutoLoop(isAutoLoop)//是否允许自动轮播
                .setIndicator(new NumIndicator(context), isAttachToBanner)//自定义轮播指示器  是否显示布局上
                .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)//指示器显示位置
                .setIndicatorMargins(new IndicatorConfig.Margins(0, 0, 0, indicatorMarginBottom))
                .setLoopTime(10000)//设置轮播间隔时间
                .addOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        stopVideo(position);
                    }

                    @Override
                    public void onPageSelected(int position) {
                        stopVideo(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                })
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        if (onCallBanner != null) {
                            onCallBanner.onCallBannerItem(position);
                        }
                    }
                });
    }

    /**
     * 停止视频
     *
     * @param position
     */
    private void stopVideo(int position) {
        if (videoView == null) {
            RecyclerView.ViewHolder viewHolder = libBanner.getAdapter().getViewHolder();
            if (viewHolder instanceof Video2Holder) {
                Video2Holder holder = (Video2Holder) viewHolder;
                videoView = holder.videoView;
                if (position != 3) {
                    videoView.start();
                }
            }
        } else {
            if (position != 3) {
                videoView.start();
            }
        }
    }

    OnCallBanner onCallBanner;

    public void setOnCallBanner(OnCallBanner onCallBanner) {
        this.onCallBanner = onCallBanner;
    }

    public interface OnCallBanner {
        void onCallBannerItem(int position);
    }

}
