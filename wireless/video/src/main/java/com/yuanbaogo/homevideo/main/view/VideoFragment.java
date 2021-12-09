package com.yuanbaogo.homevideo.main.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.live.home.view.LiveFragment;
import com.yuanbaogo.homevideo.main.bean.VideoEventModel;
import com.yuanbaogo.homevideo.main.contract.VideoContract;
import com.yuanbaogo.homevideo.main.presenter.VideoPresenter;
import com.yuanbaogo.video.videochoose.TCPicturePickerActivity;
import com.yuanbaogo.video.videochoose.TCVideoPickerActivity;
import com.yuanbaogo.video.videorecord.TCVideoRecordActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/1/21 9:39 PM
 * @Modifier:
 * @Modify:
 */
public class VideoFragment extends MvpBaseFragmentImpl<VideoContract.View, VideoPresenter>
        implements VideoContract.View, OnTabSelectListener, View.OnClickListener, ViewPager.OnPageChangeListener {

    private Context mContext;
    private SlidingTabLayout tl_video;
    private ImageView mIvVideoUpload;
    private ViewPager vp_video;
    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"推荐", "关注", "直播"};
    private PopupWindow uploadPop;
    private VideoPresenter videoPresenter = new VideoPresenter();

    //推荐
    RecommendVideoFragment recommendVideoFragment;
    //关注
    FollowVideoFragment followVideoFragment;
    //直播
    LiveFragment liveFragment;

    @Override
    protected VideoPresenter createPresenter(Bundle savedInstanceState) {
        return videoPresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.fragment_video;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mContext = getActivity();
        tl_video = (SlidingTabLayout) findViewById(R.id.tl_video);
        vp_video = (ViewPager) findViewById(R.id.vp_video);
        mIvVideoUpload = (ImageView) findViewById(R.id.iv_video_upload);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        tl_video.setOnTabSelectListener(this);
        mIvVideoUpload.setOnClickListener(this);
        vp_video.setOnPageChangeListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        recommendVideoFragment = new RecommendVideoFragment();
        followVideoFragment = new FollowVideoFragment();
        liveFragment = new LiveFragment();
        mFragments.clear();
        mFragments.add(recommendVideoFragment);
//        mFragments.add(new LocalVideoFragment());
        mFragments.add(followVideoFragment);
        mFragments.add(liveFragment);
        vp_video.setOffscreenPageLimit(2);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());

        vp_video.setAdapter(mAdapter);
        tl_video.setViewPager(vp_video, mTitles);

    }

    @Override
    public void onTabSelect(int position) {
        if (position == 0) {//推荐
//            recommendVideoFragment.mIsPalying = true;
//            recommendVideoFragment.resumePlay();
        } else if (position == 1) {//关注
//            followVideoFragment.mIsPalying = true;
//            followVideoFragment.resumePlay();
        } else if (position == 2) {//直播
        }
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_video_upload) {//右上角
            if (!UserStore.isLogin()) {
                RouterHelper.toLogin();
                return;
            }
            showUploadPop();
        } else if (id == R.id.rl_go_to_photo) {//传相片
            startActivity(new Intent(mContext, TCPicturePickerActivity.class));
            uploadPop.dismiss();
        } else if (id == R.id.rl_shoot_video) {//拍视频
            startActivity(new Intent(mContext, TCVideoRecordActivity.class));
            uploadPop.dismiss();
        } else if (id == R.id.rl_upload_video) {//传视频
            startActivity(new Intent(mContext, TCVideoPickerActivity.class));
            uploadPop.dismiss();
        } else if (id == R.id.rl_open_live) {//开启直播
//            startActivity(new Intent(mContext, CameraPushMainActivity.class));
//            startActivity(new Intent(mContext, TActivity.class));
//            startActivity(new Intent(mContext, CameraPushEntranceActivity.class));
//            startActivity(new Intent(mContext, PersonalAuthActivity.class));
//            startActivity(new Intent(mContext, ShortVideoPlayActivity.class));

//            startActivity(new Intent(mContext, PushMainActivity.class));
//            startActivity(new Intent(mContext, PullMainActivity.class));
            mPresenter.isOpenLivingRoom();
            uploadPop.dismiss();
        }
    }

    /**
     * 初始化popWindow
     */
    @SuppressLint("RtlHardcoded")
    private void showUploadPop() {
        //设置contentView
        View uploadView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_video, null);
        uploadPop = new PopupWindow(uploadView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        uploadPop.setContentView(uploadView);
        uploadPop.setBackgroundDrawable(new BitmapDrawable());
        //设置各个控件的点击响应
        RelativeLayout rl_go_to_photo = uploadView.findViewById(R.id.rl_go_to_photo);
        RelativeLayout rl_shoot_video = uploadView.findViewById(R.id.rl_shoot_video);
        RelativeLayout rl_open_live = uploadView.findViewById(R.id.rl_open_live);
        RelativeLayout rl_upload_video = uploadView.findViewById(R.id.rl_upload_video);
        rl_go_to_photo.setOnClickListener(this);
        rl_shoot_video.setOnClickListener(this);
        rl_open_live.setOnClickListener(this);
        rl_upload_video.setOnClickListener(this);
        //显示PopupWindow
        int[] location = new int[2];
        mIvVideoUpload.getLocationOnScreen(location);
        uploadPop.setAnimationStyle(R.style.popAnimRightToLeft);
        uploadPop.showAsDropDown(mIvVideoUpload, 0, -mIvVideoUpload.getHeight(), Gravity.RIGHT);
        setPopOutBackground(0.3f);
        uploadPop.setOnDismissListener(() -> {
            setPopOutBackground(1f);
        });
    }

    /**
     * 给popWindow外部设置背景
     *
     * @param value
     */
    public void setPopOutBackground(float value) {
        //设置空白的背景色
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = value;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(VideoEventModel event) {
        tl_video.setVisibility(event.isVisiable() ? View.VISIBLE : View.INVISIBLE);
        mIvVideoUpload.setVisibility(event.isVisiable() ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 判断元宝视频页面是否显示  true：不显示  false：显示
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        recommendVideoFragment.onHiddenChanged(hidden);
        followVideoFragment.onHiddenChanged(hidden);
        liveFragment.onHiddenChanged(hidden);
    }
}
