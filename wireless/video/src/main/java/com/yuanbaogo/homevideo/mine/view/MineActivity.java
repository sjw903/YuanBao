package com.yuanbaogo.homevideo.mine.view;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.live.pull.model.FollowBean;
import com.yuanbaogo.homevideo.live.push.model.CoverImgBean;
import com.yuanbaogo.homevideo.mine.contract.MineContract;
import com.yuanbaogo.homevideo.mine.model.PersonalInfoBean;
import com.yuanbaogo.homevideo.mine.presenter.MinePresenter;
import com.yuanbaogo.homevideo.mine.view.widget.FullViewPager;
import com.yuanbaogo.homevideo.mine.view.widget.ScaleScrollView;
import com.yuanbaogo.homevideo.mine.view.widget.TitleLayout;
import com.yuanbaogo.homevideo.shortvideo.report.model.ReportRequestModel;
import com.yuanbaogo.homevideo.shortvideo.report.view.ReportContentPopupWindow;
import com.yuanbaogo.homevideo.shortvideo.report.view.ReportSelectPopupWindow;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.video.videorecord.TCVideoRecordActivity;
import com.yuanbaogo.zui.dialog.ConfirmDialogView;
import com.yuanbaogo.zui.dialog.MineFunctDialogView;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 好友中心
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/7/21 4:28 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.Mine_Activity)
public class MineActivity extends MvpBaseActivityImpl<MineContract.View, MinePresenter>
        implements MineContract.View,
        ScaleScrollView.OnScrollChangeListener,
        View.OnClickListener, OnCallDialog, MineFunctDialogView.ReportOnCall {

    private TabLayout mTabLayout1, mTabLayout2;
    private TitleLayout titleLayout;
    private int colorPrimary;
    private ArgbEvaluator evaluator;
    private View statusView;
    private AppCompatTextView mLiveNumTV;
    private AppCompatTextView mLiveTV;
    private AppCompatImageView finish;
    private AppCompatImageView menu;
    private AppCompatImageView subscribe;
    private AppCompatTextView mTvFansNum;
    private AppCompatTextView mTvFans;
    private AppCompatTextView mTvFollowNum;
    private AppCompatTextView mTvFollow;

    //举报弹框
    private ReportSelectPopupWindow mReportSelectPopupWindow;
    private ReportRequestModel mReportRequestModel;

    //    @Autowired(name = YBRouter.MineActivityParams.ybCode)
    private String ybCode;

    /**
     * 用户信息Model
     */
    PersonalInfoBean personalInfoBean;

    /**
     * 背景图控件
     */
    AppCompatImageView banner;

    /**
     * 头像
     */
    CircleImageView circleImageView;

    /**
     * 昵称
     */
    AppCompatTextView username;

    /**
     * 个性签名
     */
    AppCompatTextView user_autograph;

    /**
     * 地址
     */
    AppCompatTextView tv_address;

    /**
     * 关注
     */
    AppCompatTextView tv_follow_num;

    /**
     * 粉丝
     */
    AppCompatTextView tv_fans_num;

    /**
     * 直播
     */
    AppCompatTextView tv_live_num;

    /**
     * 作品  点赞
     */
    FullViewPager viewPager;

    /**
     * 0 默认-自己编辑资料  1 关注 2 取消关注
     */
    int followState = 0;

    /**
     * 关注，取消关注
     */
    FollowBean followBean;

    ScaleScrollView scrollView;

    /**
     * 判断目前在哪个fragment
     */
    int pos = 0;

    private String[] tabs = {"作品", "点赞"};

    private List<Fragment> tabFragmentList = new ArrayList<>();

    private WorkFragment fragment_works_mine;

    private LikeFragment fragment_like_mine;

    private String portraitUrl;
    private String address;
    private String nickName;
    private String signature;

    RelativeLayout rlMineReleaseVideo;
    Button butMineReleaseVideo;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_mine;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        colorPrimary = ContextCompat.getColor(this, R.color.colorPrimary);
        //设置状态栏和导航栏
        statusView = findViewById(R.id.statusView);
        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        statusView.setLayoutParams(lp);
        statusView.setBackgroundColor(Color.TRANSPARENT);
        statusView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setNavigationBarColor(colorPrimary);

        banner = findViewById(R.id.banner);
        scrollView = findViewById(R.id.scrollView);
        scrollView.setTargetView(banner);
        scrollView.setOnScrollChangeListener(this);

        circleImageView = findViewById(R.id.avatar);
        username = findViewById(R.id.username);
        user_autograph = findViewById(R.id.user_autograph);
        tv_address = findViewById(R.id.tv_address);
        tv_follow_num = findViewById(R.id.tv_follow_num);
        tv_fans_num = findViewById(R.id.tv_fans_num);
        tv_live_num = findViewById(R.id.tv_live_num);

        mTabLayout1 = findViewById(R.id.tab1);
        mTabLayout2 = findViewById(R.id.tab2);
        titleLayout = findViewById(R.id.title_layout);

        viewPager = findViewById(R.id.viewpager);

        mLiveNumTV = findViewById(R.id.tv_live_num);
        mLiveTV = findViewById(R.id.tv_live);
        finish = findViewById(R.id.finish);
        menu = findViewById(R.id.menu);
        subscribe = findViewById(R.id.subscribe);
        mTvFansNum = (AppCompatTextView) findViewById(R.id.tv_fans_num);
        mTvFans = (AppCompatTextView) findViewById(R.id.tv_fans);
        mTvFollowNum = (AppCompatTextView) findViewById(R.id.tv_follow_num);
        mTvFollow = (AppCompatTextView) findViewById(R.id.tv_follow);
        rlMineReleaseVideo = findViewById(R.id.rl_mine_release_video);
        butMineReleaseVideo = findViewById(R.id.but_mine_release_video);

    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mLiveNumTV.setOnClickListener(this);
        finish.setOnClickListener(this);
        subscribe.setOnClickListener(this);
        menu.setOnClickListener(this);
        mLiveTV.setOnClickListener(this);
        butMineReleaseVideo.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getPersonalInfo(ybCode);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ybCode = getIntent().getStringExtra("ybCode");
        if (ybCode.equals(UserStore.getYbCode())) {
            //自己的个人主页
            subscribe.setImageDrawable(getResources().getDrawable(R.mipmap.icon_edit_data));
            menu.setVisibility(View.GONE);
            mLiveNumTV.setVisibility(View.VISIBLE);
            mLiveTV.setVisibility(View.VISIBLE);
            mTvFollowNum.setOnClickListener(this);
            mTvFollow.setOnClickListener(this);
            mTvFans.setOnClickListener(this);
            mTvFansNum.setOnClickListener(this);
            banner.setOnClickListener(this);
        } else {
            //其他人的个人主页
            subscribe.setImageDrawable(getResources().getDrawable(R.mipmap.icon_follow));
            menu.setVisibility(View.VISIBLE);
            mLiveNumTV.setVisibility(View.GONE);
            mLiveTV.setVisibility(View.GONE);
        }

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                pos = position;
                if (pos == 0) {
                    if (mWorks == 0) {
                        rlMineReleaseVideo.setVisibility(View.VISIBLE);
                    } else {
                        rlMineReleaseVideo.setVisibility(View.GONE);
                    }
                } else if (pos == 1) {
                    rlMineReleaseVideo.setVisibility(View.GONE);
                }
            }
        });

        initTab();

        mPresenter.getPersonalInfo(ybCode);
    }

    long mWorks = 0;

    private void initTab() {
        //请求查看个人中心的粉丝数量，关注数量，作品数量
        mTabLayout1.addTab(mTabLayout1.newTab().setText(tabs[0]));
        mTabLayout1.addTab(mTabLayout1.newTab().setText(tabs[1]));
        mTabLayout2.addTab(mTabLayout2.newTab().setText(tabs[0]));
        mTabLayout2.addTab(mTabLayout2.newTab().setText(tabs[1]));

        Bundle bundle = new Bundle();
        bundle.putString("ybCode", ybCode);
        fragment_works_mine = new WorkFragment();
        fragment_like_mine = new LikeFragment();
        fragment_works_mine.setArguments(bundle);
        fragment_like_mine.setArguments(bundle);
        tabFragmentList.add(fragment_works_mine);
        tabFragmentList.add(fragment_like_mine);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return tabFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return tabFragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        });
        mTabLayout1.setupWithViewPager(viewPager);
        mTabLayout2.setupWithViewPager(viewPager);
    }

    /**
     * 设置用户信息
     *
     * @param bean
     */
    @Override
    public void setPersonalInfo(PersonalInfoBean bean) {
        personalInfoBean = bean;
        initPersonalInfo();
    }

    /**
     * 显示用户信息
     */
    @Override
    public void initPersonalInfo() {
        if (personalInfoBean != null) {
            if (personalInfoBean.getUser() != null) {
                Glide.with(MineActivity.this).load(personalInfoBean.getUser().getBackgroundPictureUrl()).into(banner);
                Glide.with(MineActivity.this).load(personalInfoBean.getUser().getPortraitUrl()).into(circleImageView);
                username.setText(personalInfoBean.getUser().getNickName());
                if (!TextUtils.isEmpty(personalInfoBean.getUser().getSignature())) {
                    user_autograph.setText(personalInfoBean.getUser().getSignature());
                } else {
                    user_autograph.setText("这个人很懒，什么也没有留下");
                }
                if (!TextUtils.isEmpty(personalInfoBean.getUser().getAddress())) {
                    tv_address.setVisibility(View.VISIBLE);
                    tv_address.setText(personalInfoBean.getUser().getAddress());
                } else {
                    tv_address.setVisibility(View.GONE);
                }
                address = personalInfoBean.getUser().getAddress();
                nickName = personalInfoBean.getUser().getNickName();
                signature = personalInfoBean.getUser().getSignature();
                portraitUrl = personalInfoBean.getUser().getPortraitUrl();
            }
            if (personalInfoBean.getStatisticsInfo() != null) {
                tv_follow_num.setText(personalInfoBean.getStatisticsInfo().getFollow() + "");
                tv_fans_num.setText(personalInfoBean.getStatisticsInfo().getFans() + "");
                tv_live_num.setText(personalInfoBean.getStatisticsInfo().getSessions()
                        + personalInfoBean.getStatisticsInfo().getSessionsSell() + "");

                mTabLayout1.getTabAt(0).setText("作品 " + personalInfoBean.getStatisticsInfo().getWorks());
                mTabLayout1.getTabAt(1).setText("点赞 " + personalInfoBean.getStatisticsInfo().getLikeCount());

                mTabLayout2.getTabAt(0).setText("作品 " + personalInfoBean.getStatisticsInfo().getWorks());
                mTabLayout2.getTabAt(1).setText("点赞 " + personalInfoBean.getStatisticsInfo().getLikeCount());

                mWorks = personalInfoBean.getStatisticsInfo().getWorks();
                if (pos == 0) {
                    if (mWorks == 0) {
                        rlMineReleaseVideo.setVisibility(View.VISIBLE);
                    } else {
                        rlMineReleaseVideo.setVisibility(View.GONE);
                    }
                }
            }
            if (personalInfoBean.getUserRelation() != null) {
                if (ybCode.equals(UserStore.getYbCode())) {
                    followState = 0;
                    subscribe.setImageDrawable(getResources().getDrawable(R.mipmap.icon_edit_data));
                } else {
                    if (personalInfoBean.getUserRelation().getValue() == 0) {
                        followState = 1;
                        subscribe.setImageDrawable(getResources().getDrawable(R.mipmap.icon_follow));
                    } else if (personalInfoBean.getUserRelation().getValue() == 1) {
                        followState = 2;
                        subscribe.setImageDrawable(getResources().getDrawable(R.mipmap.icon_followed));
                    } else if (personalInfoBean.getUserRelation().getValue() == 2) {
                        followState = 1;
                        subscribe.setImageDrawable(getResources().getDrawable(R.mipmap.icon_return_follow));
                    } else if (personalInfoBean.getUserRelation().getValue() == 3) {
                        followState = 2;
                        subscribe.setImageDrawable(getResources().getDrawable(R.mipmap.icon_mutual_attention));
                    }
                }
            }
        }
    }

    @Override
    public void setFollow(FollowBean bean) {
        followBean = bean;
        initFollow();
        mPresenter.getPersonalInfo(ybCode);
    }

    @Override
    public void initFollow() {
        if (followBean != null) {
            if (personalInfoBean.getUserRelation().getValue() == 0) {
                followState = 1;
                subscribe.setImageDrawable(getResources().getDrawable(R.mipmap.icon_follow));
            } else if (personalInfoBean.getUserRelation().getValue() == 1) {
                followState = 2;
                subscribe.setImageDrawable(getResources().getDrawable(R.mipmap.icon_followed));
            } else if (personalInfoBean.getUserRelation().getValue() == 2) {
                followState = 1;
                subscribe.setImageDrawable(getResources().getDrawable(R.mipmap.icon_return_follow));
            } else if (personalInfoBean.getUserRelation().getValue() == 3) {
                followState = 2;
                subscribe.setImageDrawable(getResources().getDrawable(R.mipmap.icon_mutual_attention));
            }
        }
    }

    //举报成功
    @Override
    public void onReportSubmitSuccess() {
        mReportSelectPopupWindow.dismiss();
        ToastView.showToast("已收到举报\n我们会尽快核实后处理");
    }

    //举报失败
    @Override
    public void onReportSubmitFail() {

    }

    //举报图片上传失败
    @Override
    public void onUploadPicFail() {

    }

    //举报图片上传成功
    @Override
    public void onUploadPicSuccess(CoverImgBean coverImgBean) {
        mPresenter.reportSubmit(mReportRequestModel);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int x, int y, int ox, int oy) {
        if (null != mTabLayout1 && null != mTabLayout2 && null != titleLayout && null != statusView) {
            int distance = mTabLayout1.getTop() - titleLayout.getHeight() - statusView.getHeight();
            float ratio = v.getScaleY() * 1f / distance;
            if (distance <= v.getScrollY()) {
                ratio = 1;
                if (mTabLayout2.getVisibility() != View.VISIBLE) {
                    mTabLayout2.setVisibility(View.VISIBLE);
                    statusView.setBackgroundColor(colorPrimary);
                }
            } else {
                if (mTabLayout2.getVisibility() == View.VISIBLE) {
                    mTabLayout2.setVisibility(View.INVISIBLE);
                    statusView.setBackgroundColor(Color.TRANSPARENT);
                }
            }
            if (null == evaluator) {
                evaluator = new ArgbEvaluator();
            }
            titleLayout.setBackgroundColor((int) evaluator.evaluate(ratio, Color.TRANSPARENT, colorPrimary));
            titleLayout.setTitleColor((int) evaluator.evaluate(ratio, Color.TRANSPARENT, Color.BLACK));
            if (personalInfoBean != null) {
                titleLayout.setTitle(personalInfoBean.getUser().getNickName());
            } else {
                titleLayout.setTitle("");
            }
        }

        View contentView = scrollView.getChildAt(0);
        //判断是否滑到的底部
        if (contentView != null
                && contentView.getMeasuredHeight() == (scrollView.getScrollY() + scrollView.getHeight())) {
            if (pos == 0) {
                fragment_works_mine.loadMore();
            } else if (pos == 1) {
                fragment_like_mine.loadMore();
            }
        }

    }

    private int getStatusBarHeight() {
        int height = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = getResources().getDimensionPixelSize(resId);
        }
        return height;
    }

    ConfirmDialogView confirmDialogView;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.finish) {
            finish();
        } else if (id == R.id.tv_live_num || id == R.id.tv_live) {
            //我的直播
            RouterHelper.toLiveAmountActivity();
        } else if (id == R.id.subscribe) {
            if (ybCode.equals(UserStore.getYbCode())) {
                if (personalInfoBean != null) {
                    RouterHelper.toEditMine(address, nickName, signature, portraitUrl);
                }
            } else {
                if (followState == 1) {
                    mPresenter.getFollow(ybCode, followState);
                } else if (followState == 2) {
                    confirmDialogView = new ConfirmDialogView("取消",
                            "确定不再关注TA了吗？");
                    confirmDialogView.show(getSupportFragmentManager(), "search_history");
                    confirmDialogView.setOnCallDialog(this);
                }
            }
        } else if (id == R.id.menu) {
            MineFunctDialogView confirmDialogView = new MineFunctDialogView();
            confirmDialogView.setOnCall(this);
            confirmDialogView.show(getSupportFragmentManager(), "search_history");
        } else if (id == R.id.subscribe) {
//            RouterHelper.toEditMine();
        } else if (id == R.id.tv_fans_num || id == R.id.tv_fans) {
            //我的粉丝
            RouterHelper.toMyFansActivity();
        } else if (id == R.id.tv_follow_num || id == R.id.tv_follow) {
            //我的关注
            RouterHelper.toMyFollowActivity();
        } else if (id == R.id.banner) {
            if (personalInfoBean == null
                    && personalInfoBean.getUser() == null
                    && personalInfoBean.getUser().getBackgroundPictureUrl() == null) {
                return;
            }
            //查看背景图
            RouterHelper.toPreviewPictureActivity(personalInfoBean.getUser().getBackgroundPictureUrl());
        } else if (id == R.id.but_mine_release_video) {
            //发布作品
            startActivity(new Intent(this, TCVideoRecordActivity.class));
        }
    }

    @Override
    public void onCallDetermine() {
        mPresenter.getFollow(ybCode, followState);
        confirmDialogView.dismiss();
    }

    @Override
    public void onClickReport() {
//        ToastView.showToast("举报");
        mReportSelectPopupWindow = new ReportSelectPopupWindow(this, "举报用户", new ReportContentPopupWindow.SubmitCallback() {
            @Override
            public void submit(ReportRequestModel reportRequestModel, List<LocalMedia> localMedia) {
                mReportRequestModel = reportRequestModel;
                mReportRequestModel.setAuthorId(personalInfoBean.getUser().getYbCode());
                if (localMedia != null & localMedia.size() > 0) {
                    mPresenter.uploadPic(localMedia);
                } else {
                    mPresenter.reportSubmit(mReportRequestModel);
                }

                // mPresenter.reportSubmit(reportRequestModel);
            }
        });
        mReportSelectPopupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

    }
}