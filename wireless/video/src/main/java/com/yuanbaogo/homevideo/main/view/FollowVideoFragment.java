package com.yuanbaogo.homevideo.main.view;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.qcloud.ugckit.utils.LogReport;
import com.tencent.qcloud.ugckit.utils.TelephonyUtil;
import com.tencent.qcloud.ugckit.utils.ToastUtil;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yuanbaogo.commonlib.base.MvpBaseFragmentImpl;
import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.live.push.model.CoverImgBean;
import com.yuanbaogo.homevideo.main.adapter.VideoFollowListAdapter;
import com.yuanbaogo.homevideo.main.bean.VideoEventModel;
import com.yuanbaogo.homevideo.main.bean.VideoLiveFollowListBean;
import com.yuanbaogo.homevideo.main.contract.FollowVideoContract;
import com.yuanbaogo.homevideo.main.presenter.FollowVideoPresenter;
import com.yuanbaogo.homevideo.shortvideo.comment.ShortVideoCommentPop;
import com.yuanbaogo.homevideo.shortvideo.report.model.ReportRequestModel;
import com.yuanbaogo.homevideo.shortvideo.report.view.ReportContentPopupWindow;
import com.yuanbaogo.homevideo.shortvideo.report.view.ReportSelectPopupWindow;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.video.play.PlayerInfo;
import com.yuanbaogo.zui.dialog.ShareDialogView;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.RecommendVideoRefreshHeader;
import com.yuanbaogo.zui.view.VideoRefreshHeader;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * @author lhx
 * @description: ??????
 * @date : 2021/7/6 14:56
 */
public class FollowVideoFragment extends MvpBaseFragmentImpl<FollowVideoContract.View, FollowVideoPresenter>
        implements FollowVideoContract.View, ITXVodPlayListener, TelephonyUtil.OnTelephoneListener,
        ShareDialogView.IDeleteVideoListener, ShareDialogView.ReportVideoListener, ShareDialogView.UninterestedVideoListener,
        RecommendVideoRefreshHeader.OnHeadStateChangedListener, VideoRefreshHeader.OnHeadStateChangedListener {

    private FollowVideoPresenter recommendVideoPresenter = new FollowVideoPresenter();

    private int requstcode = 1111;

    private LinearLayout ll_no_network;
    private TextView tv_reload;

    private LinearLayout ll_no_login;
    private LinearLayout ll_no_data;
    private TextView tv_login;

    private VerticalViewPager mVerticalViewPager;

    private SmartRefreshLayout mRefreshLayout;
    private MyPagerAdapter mPagerAdapter;
    private TXCloudVideoView mTXCloudVideoView;
    private ImageView mIvCover;
    private ImageView mIvPause;
    private ImageView iv_progress_img;
    // ?????????id ?????????????????? ?????????????????? ??????URL??? ??????URL
    private List<RecommendVideoBean.RowsBean> mTCLiveInfoList = new ArrayList<>();
    private int mCurrentPosition = 0;
    /**
     * SDK?????????????????????
     */
    private TXVodPlayer mTXVodPlayer;

    private ShareDialogView mDelShareDialogView;
    private ShareDialogView mShareDialogView;

    private int page = 1;

    private boolean isPause = false;//????????????
    private boolean isLike = false;//????????????
    private int likeCount;//????????????
    private String deleteVodId;//??????????????????ID
    private String unconcernVodId;//?????????????????????ID
    private ReportSelectPopupWindow mReportSelectPopupWindow;

    //????????????
    private ReportRequestModel mReportRequestModel;

    private PowerManager mPowerManager;//?????? a???????????? b??????????????????????????????
    public boolean mIsPalying;//????????????????????????


    private VideoFollowListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private TextView tv_open_live_view;
    private LinearLayout ll_live_view;
    private LinearLayout ll_live_view_mask;

    VideoRefreshHeader zi_refresh_head;

    @Override
    protected FollowVideoPresenter createPresenter(Bundle savedInstanceState) {
        return recommendVideoPresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.fragment_follow_recommend_video;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {

        if (getArguments() != null) {
            mTCLiveInfoList = getArguments().getParcelableArrayList("videolist");
        }

        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_video);
        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.player_cloud_view);
        mIvCover = (ImageView) findViewById(R.id.player_iv_cover);
        mVerticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_view_pager);
        iv_progress_img = (ImageView) findViewById(R.id.iv_progress_img);

        zi_refresh_head = (VideoRefreshHeader) findViewById(R.id.zi_refresh_head);

        mPowerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);

        Glide.with(mContext).asGif()
                .load(R.drawable.video_loading_progress)
                .into(iv_progress_img);

        ll_no_network = (LinearLayout) findViewById(R.id.ll_no_network);
        tv_reload = (TextView) findViewById(R.id.tv_reload);

        ll_no_login = (LinearLayout) findViewById(R.id.ll_no_login);
        tv_login = (TextView) findViewById(R.id.tv_login);

        ll_no_data = (LinearLayout) findViewById(R.id.ll_no_data);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_live_video_follow);
        tv_open_live_view = (TextView) findViewById(R.id.tv_open_live_view);
        ll_live_view = (LinearLayout) findViewById(R.id.ll_live_view);
        ll_live_view_mask = (LinearLayout) findViewById(R.id.ll_live_view_mask);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

        mVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                View v = mVerticalViewPager.findViewById(position);

                mCurrentPosition = position;

                mRefreshLayout.setEnableRefresh(false);
                if (position == 0) {
                    mRefreshLayout.setEnableRefresh(true);
                }

                if (mIvPause.getVisibility() == View.VISIBLE) {
                    mIvPause.setVisibility(View.GONE);
                    isPause = false;
                }
                // ??????????????????????????????????????????????????????seek???0
                TXLog.d(TAG, "??????????????????????????????????????????mTXVodPlayer = " + mTXVodPlayer);
                if (mTXVodPlayer != null) {
                    mTXVodPlayer.seek(0);
                    mTXVodPlayer.pause();
                }

                if (mTCLiveInfoList.size() >= 10 && position >= mTCLiveInfoList.size() - 3) {
                    page++;
                    mPresenter.getRecommendVideoList(page, "10");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        mVerticalViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
//                Log.e("yf", "mVerticalViewPager, transformPage pisition = " + position + " mCurrentPosition" + mCurrentPosition);
                if (position != 0) {
                    return;
                }

                ViewGroup viewGroup = (ViewGroup) page;
                mIvCover = (ImageView) viewGroup.findViewById(R.id.player_iv_cover);
                mTXCloudVideoView = (TXCloudVideoView) viewGroup.findViewById(R.id.player_cloud_view);
                mIvPause = (ImageView) viewGroup.findViewById(R.id.iv_video_play);

                PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(mCurrentPosition);
                if (playerInfo != null) {
                    if (mIvPause.getVisibility() == View.GONE) {
                        playerInfo.vodPlayer.resume();
                    }
                    mTXVodPlayer = playerInfo.vodPlayer;
                }
            }
        });
        //????????????
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            page = 1;
            mPresenter.getRecommendVideoList(page, "10");
        });

        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLl_no_network();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterHelper.toLogin(FollowVideoFragment.this, requstcode);
            }
        });

        zi_refresh_head.setChangedListener(this);

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BroadcastProtocol.BROAD_LOGIN_SUCCESS);
//        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void setLl_no_network() {
        if (isNetworkAvailable()) {
            if (!UserStore.isLogin()) {
                ll_no_login.setVisibility(View.VISIBLE);
                iv_progress_img.setVisibility(View.GONE);
            } else {
                mPresenter.getRecommendVideoList(page, "10");
                mPresenter.getLiveVideoList();
                mRefreshLayout.setVisibility(View.VISIBLE);
                iv_progress_img.setVisibility(View.VISIBLE);
                ll_no_network.setVisibility(View.GONE);
                ll_no_data.setVisibility(View.GONE);
                ll_no_login.setVisibility(View.GONE);
            }
        } else {
            mRefreshLayout.setVisibility(View.GONE);
            ll_no_network.setVisibility(View.VISIBLE);
            ll_no_data.setVisibility(View.GONE);
            iv_progress_img.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
//        setLl_no_network();
//        mVerticalViewPager.setOffscreenPageLimit(2);
        mPagerAdapter = new MyPagerAdapter();
        mVerticalViewPager.setAdapter(mPagerAdapter);
        TelephonyUtil.getInstance().setOnTelephoneListener(this);
        TelephonyUtil.getInstance().initPhoneListener();

        //?????????????????????
        ShareBean shareBean = new ShareBean();
        shareBean.setFriend(false);
        shareBean.setCommunity(false);
        shareBean.setWeixin(false);
        shareBean.setWeixinCircle(false);
        shareBean.setReport(false);
        shareBean.setNotInterested(false);
        shareBean.setDelete(true);
        mDelShareDialogView = new ShareDialogView(shareBean);
        mDelShareDialogView.setDeleteVideoListener(this);

        ShareBean shareBean2 = new ShareBean();
        shareBean2.setFriend(false);
        shareBean2.setCommunity(false);
        shareBean2.setWeixin(false);
        shareBean2.setWeixinCircle(false);
        shareBean2.setReport(true);
        shareBean2.setNotInterested(false);
        shareBean2.setDelete(false);
        mShareDialogView = new ShareDialogView(shareBean2);
        mShareDialogView.setReportVideoListener(this);
        mShareDialogView.setUninterestedVideoListener(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new VideoFollowListAdapter(mContext);
        mAdapter.setOnItemClickListener(new VideoFollowListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickClick(String url) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);

        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onVideoListSuccess(RecommendVideoBean bean) {
        mRefreshLayout.finishRefresh(true/*,false*/);//??????false??????????????????
        if (bean != null && bean.getRows() != null && bean.getRows().size() > 0) {
            mVerticalViewPager.setVisibility(View.VISIBLE);
            if (page == 1) {
                ll_no_network.setVisibility(View.GONE);
                ll_no_data.setVisibility(View.GONE);
                mTCLiveInfoList = bean.getRows();
            } else {
                mTCLiveInfoList.addAll(bean.getRows());
            }
            mPagerAdapter.notifyDataSetChanged();
        } else {
            if (page == 1) {
                mVerticalViewPager.setVisibility(View.GONE);
                ll_no_network.setVisibility(View.GONE);
                ll_no_data.setVisibility(View.VISIBLE);
            }
        }
        iv_progress_img.setVisibility(View.GONE);
    }

    @Override
    public void onLiveListSuccess(VideoLiveFollowListBean bean) {

        if (bean != null && bean.getTotal() > 0) {
            ll_live_view_mask.setVisibility(View.VISIBLE);
            ll_live_view.setVisibility(View.VISIBLE);
            tv_open_live_view.setText(bean.getTotal() + "?????????");
            tv_open_live_view.setVisibility(View.GONE);
            mAdapter.setData(bean.getList());
            tv_open_live_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll_live_view.setVisibility(View.VISIBLE);
                    ll_live_view_mask.setVisibility(View.VISIBLE);
                    tv_open_live_view.setVisibility(View.GONE);
                }
            });
            ll_live_view_mask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_open_live_view.setVisibility(View.VISIBLE);
                    ll_live_view.setVisibility(View.GONE);
                    ll_live_view_mask.setVisibility(View.GONE);
                }
            });
        } else {
            ll_live_view_mask.setVisibility(View.GONE);
            ll_live_view.setVisibility(View.GONE);
            tv_open_live_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onVideoListFail() {
        mRefreshLayout.finishRefresh(true/*,false*/);//??????false??????????????????
        if (page == 1) {
            ll_no_network.setVisibility(View.GONE);
            mVerticalViewPager.setVisibility(View.GONE);
            ll_no_data.setVisibility(View.VISIBLE);
        }
        iv_progress_img.setVisibility(View.GONE);
    }

    @Override
    public void onClickLikeSuccess(String likeState, int position) {
        mTCLiveInfoList.get(position).setHasLiked(Integer.parseInt(likeState));
    }

    @Override
    public void onDeleteVideoSuccess() {
        ToastView.showToast("????????????");
    }

    @Override
    public void onUnconcernVideoSuccess() {
        ToastView.showToast("???????????????????????????????????????????????????");
    }

    //????????????
    @Override
    public void onReportSubmitSuccess() {
        mReportSelectPopupWindow.dismiss();
        mTCLiveInfoList.remove(mCurrentPosition);
        ToastView.showToast("???????????????\n??????????????????????????????");
//        mVerticalViewPager.setCurrentItem(mCurrentPosition + 1);
        mPagerAdapter.notifyDataSetChanged();
    }

    //????????????
    @Override
    public void onReportSubmitFail() {

    }

    //????????????????????????
    @Override
    public void onUploadPicSuccess(CoverImgBean coverImgBean) {
        mPresenter.reportSubmit(mReportRequestModel);
    }

    //????????????????????????
    @Override
    public void onUploadPicFail() {

    }

    //???????????????????????????
    @Override
    public void onReportVideo() {
        mShareDialogView.dismiss();
        mReportSelectPopupWindow = new ReportSelectPopupWindow(getActivity(), "????????????", new ReportContentPopupWindow.SubmitCallback() {
            @Override
            public void submit(ReportRequestModel reportRequestModel, List<LocalMedia> localMedia) {
                mReportRequestModel = reportRequestModel;
                mReportRequestModel.setAuthorId(mTCLiveInfoList.get(0).getYbCode());
                mReportRequestModel.setBizId(mTCLiveInfoList.get(0).getVideoId());
                mReportRequestModel.setBizTitle(mTCLiveInfoList.get(0).getTitle());
                if (localMedia != null & localMedia.size() > 0) {
                    mPresenter.uploadPic(localMedia);
                } else {
                    mPresenter.reportSubmit(mReportRequestModel);
                }

                // mPresenter.reportSubmit(reportRequestModel);
            }
        });
        mReportSelectPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    //???????????????????????????
    @Override
    public void onDeleteVideo() {
        mPresenter.deleteVideo(deleteVodId);
        mDelShareDialogView.dismiss();
        mTCLiveInfoList.remove(mCurrentPosition);
//        mVerticalViewPager.setCurrentItem(mCurrentPosition + 1);
        mPagerAdapter.notifyDataSetChanged();
    }

    //?????????????????????????????????
    @Override
    public void onUninterestedVideo() {
        mPresenter.unconcernVideo(unconcernVodId);
        mShareDialogView.dismiss();
        mTCLiveInfoList.remove(mCurrentPosition);
        mPagerAdapter.notifyDataSetChanged();
    }

    class MyPagerAdapter extends PagerAdapter {

        ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();

        protected PlayerInfo instantiatePlayerInfo(int position) {
            TXCLog.d(TAG, "instantiatePlayerInfo " + position);

            PlayerInfo playerInfo = new PlayerInfo();
            TXVodPlayer vodPlayer = new TXVodPlayer(mContext);
            vodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
            //FIXBUG:FULL_SCREEN ?????????????????????ADJUST_RESOLUTION??????
            vodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
            vodPlayer.setVodListener(FollowVideoFragment.this);
            TXVodPlayConfig config = new TXVodPlayConfig();

            File sdcardDir = mContext.getExternalFilesDir(null);
            if (sdcardDir != null) {
                config.setCacheFolderPath(sdcardDir.getAbsolutePath() + "/txcache");
            }
            config.setMaxCacheItems(5);
            vodPlayer.setConfig(config);
            vodPlayer.setAutoPlay(false);

            RecommendVideoBean.RowsBean tcLiveInfo = mTCLiveInfoList.get(position);
            playerInfo.playURL = tcLiveInfo.getVideoUrl();
            playerInfo.vodPlayer = vodPlayer;
            playerInfo.pos = position;
            playerInfoList.add(playerInfo);

            return playerInfo;
        }

        protected void destroyPlayerInfo(int position) {
            while (true) {
                PlayerInfo playerInfo = findPlayerInfo(position);
                if (playerInfo == null) {
                    break;
                }
                playerInfo.vodPlayer.stopPlay(true);
                playerInfoList.remove(playerInfo);

                TXCLog.d(TAG, "destroyPlayerInfo " + position);
            }
        }

        public PlayerInfo findPlayerInfo(int position) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.pos == position) {
                    return playerInfo;
                }
            }
            return null;
        }

        public PlayerInfo findPlayerInfo(TXVodPlayer player) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.vodPlayer == player) {
                    return playerInfo;
                }
            }
            return null;
        }

        public void onDestroy() {
            for (PlayerInfo playerInfo : playerInfoList) {
                playerInfo.vodPlayer.stopPlay(true);
            }
            playerInfoList.clear();
        }

        @Override
        public int getCount() {
            return mTCLiveInfoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            RecommendVideoBean.RowsBean videoInfo = mTCLiveInfoList.get(position);

            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_player_content_follow, null);
            view.setId(position);
            view.setTag(position); // Add tag

            // ??????
            ImageView coverImageView = (ImageView) view.findViewById(R.id.player_iv_cover);
            Glide.with(mContext).load(videoInfo.getCoverUrl()).error(R.drawable.bg).into(coverImageView);
            coverImageView.setVisibility(View.GONE);
            // ??????
            CircleImageView ivAvatar = (CircleImageView) view.findViewById(R.id.player_civ_avatar);
            Glide.with(mContext).load(videoInfo.getPortrait()).error(R.mipmap.icon_video_default_head).into(ivAvatar);
            ivAvatar.setOnClickListener(v -> {
                if (!UserStore.isLogin()) {
                    RouterHelper.toLogin();
                    return;
                }
                /**
                 * ??????????????????
                 */
                RouterHelper.toMineActivity(mTCLiveInfoList.get(position).getYbCode());
            });
            // ??????
            TextView tvName = (TextView) view.findViewById(R.id.player_tv_publisher_name);
            tvName.setText("@" + videoInfo.getUserNickName());
            TextView mTvDescribe = view.findViewById(R.id.tv_video_describe);
            mTvDescribe.setText(videoInfo.getTitle());
            //??????
            ImageView mIvComment = view.findViewById(R.id.iv_comment);
            TextView mTvComment = view.findViewById(R.id.tv_video_comment);
            mTvComment.setText(videoInfo.getCommentCount() + "");

            //??????
            likeCount = videoInfo.getLikeCount();
            ImageView mIvLike = view.findViewById(R.id.iv_video_like);
            TextView mTvLike = view.findViewById(R.id.tv_video_like);
            if (videoInfo.getHasLiked() == 0) {
                mIvLike.setImageResource(R.mipmap.icon_video_like_no);
                isLike = false;
            } else {
                mIvLike.setImageResource(R.mipmap.icon_video_like);
                isLike = true;
            }
            mTvLike.setText(videoInfo.getLikeCount() + "");
            mIvLike.setOnClickListener(v -> {
                if (UserStore.isLogin()) {
                    if (videoInfo.getHasLiked() == 0) {
                        mIvLike.setImageResource(R.mipmap.icon_video_like);
                        videoInfo.setLikeCount(videoInfo.getLikeCount() + 1);
                        mPresenter.clickLike("1", videoInfo.getVideoId(), position);
                    } else {
                        mIvLike.setImageResource(R.mipmap.icon_video_like_no);
                        videoInfo.setLikeCount(videoInfo.getLikeCount() - 1);
                        mPresenter.clickLike("0", videoInfo.getVideoId(), position);
                    }
                    mTvLike.setText(videoInfo.getLikeCount() + "");
                    isLike = !isLike;
                } else {
                    RouterHelper.toLogin();
                }
            });
            mIvComment.setOnClickListener(v -> {
                mPopupWindow = new ShortVideoCommentPop(getViewContext(), videoInfo.getVideoId());
                mPopupWindow.setOnItemClickListener(new ShortVideoCommentPop.OnItemClickListener() {
                    @Override
                    public void onDeleteSuccess() {
                        videoInfo.setCommentCount(videoInfo.getCommentCount() - 1);
                        mTvComment.setText(videoInfo.getCommentCount() + "");
                    }

                    @Override
                    public void onReplySuccess() {
                        videoInfo.setCommentCount(videoInfo.getCommentCount() + 1);
                        mTvComment.setText(videoInfo.getCommentCount() + "");
                    }
                });
                mPopupWindow.show();
            });

            //????????????????????????
            LinearLayout mLlShare = view.findViewById(R.id.ll_video_share);

            mLlShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (videoInfo.getYbCode().equals(UserStore.getYbCode())) {
                        mDelShareDialogView.show(getChildFragmentManager(), "share");
                        deleteVodId = videoInfo.getVideoId();
                    } else {
                        mShareDialogView.show(getChildFragmentManager(), "share");
                        unconcernVodId = videoInfo.getVideoId();
                    }
                }

            });

            TextView tvStatus = (TextView) view.findViewById(R.id.tx_video_review_status);
//            if (videoInfo.review_status == TCVideoInfo.REVIEW_STATUS_NOT_REVIEW) {
//                tvStatus.setVisibility(View.VISIBLE);
//                tvStatus.setText(R.string.video_not_review);
//            } else if (videoInfo.review_status == TCVideoInfo.REVIEW_STATUS_PORN) {
//                tvStatus.setVisibility(View.VISIBLE);
//                tvStatus.setText(R.string.video_porn);
//            } else if (videoInfo.review_status == TCVideoInfo.REVIEW_STATUS_NORMAL) {
//                tvStatus.setVisibility(View.GONE);
//            }

            ImageView ivPause = (ImageView) view.findViewById(R.id.iv_video_play);

            // ?????????player
            TXCloudVideoView playView = (TXCloudVideoView) view.findViewById(R.id.player_cloud_view);
            PlayerInfo playerInfo = instantiatePlayerInfo(position);
            playerInfo.playerView = playView;
            playerInfo.vodPlayer.setPlayerView(playView);
            playerInfo.vodPlayer.setRenderMode(0);
            playerInfo.vodPlayer.startPlay(playerInfo.playURL);

            playView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIvPause = ivPause;
                    if (!isPause) {
                        playerInfo.vodPlayer.pause();
                        ivPause.setVisibility(View.VISIBLE);
                    } else {
                        playerInfo.vodPlayer.resume();
                        ivPause.setVisibility(View.GONE);
                    }
                    isPause = !isPause;
                }
            });
            if (isVisible) {
                playerInfo.vodPlayer.pause();
            } else {
                playerInfo.vodPlayer.resume();
            }
            container.addView(view);
            return view;
        }

        @Override
        public int getItemPosition(Object object) {
            View view = (View) object;
            int currentPage = mCurrentPosition; // Get current page index
            if (currentPage == (Integer) view.getTag()) {
                return POSITION_NONE; //???????????????????????????notifychange
            } else {
                return POSITION_UNCHANGED;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            TXCLog.i(TAG, "MyPagerAdapter destroyItem, position = " + position);

            destroyPlayerInfo(position);

            container.removeView((View) object);
        }
    }

    private void restartPlay() {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.resume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHiddenVideo && isVisible) {
            resumePlay();
            if (ll_no_login.getVisibility() == View.VISIBLE) {
                setLl_no_network();
            }
        }
    }

    //??????????????????
    public void resumePlay() {
        if (mPowerManager.isScreenOn()) {//????????????  ?????????????????????
            if (mIsPalying) {
                if (mTXCloudVideoView != null) {
                    mTXCloudVideoView.onResume();
                }
                if (mTXVodPlayer != null) {
                    if (mIvPause.getVisibility() == View.VISIBLE) {
                        mIvPause.setVisibility(View.GONE);
                        isPause = false;
                    }
                    mTXVodPlayer.resume();
                }
            }
            return;
        }
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onResume();
        }
        if (mTXVodPlayer != null) {
            if (mIvPause.getVisibility() == View.VISIBLE) {
                mIvPause.setVisibility(View.GONE);
                isPause = false;
            }
            mTXVodPlayer.resume();
        }

    }

    //????????????
    private void pausePlay() {
        if (mTXVodPlayer != null) {
            mIsPalying = mTXVodPlayer.isPlaying();
        }
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onPause();
        }
        if (mTXVodPlayer != null) {
            mTXVodPlayer.pause();
        }
    }

    //??????activity???????????????setUserVisibleHint
    // ????????????????????????
    @Override
    public void onPause() {
        super.onPause();
        pausePlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onDestroy();
            mTXCloudVideoView = null;
        }

        mPagerAdapter.onDestroy();
        stopPlay(true);
        mTXVodPlayer = null;

        TelephonyUtil.getInstance().uninitPhoneListener();
//        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    protected void stopPlay(boolean clearLastFrame) {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.stopPlay(clearLastFrame);
        }
    }

    @Override
    public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
            int width = param.getInt(TXLiveConstants.EVT_PARAM1);
            int height = param.getInt(TXLiveConstants.EVT_PARAM2);
            //FIXBUG:????????????????????????????????????????????????
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            restartPlay();
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {// ??????I????????????????????????
            mIvCover.setVisibility(View.GONE);
            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null) {
                playerInfo.isBegin = true;
            }
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event I FRAME, player = " + player);
                mIvCover.setVisibility(View.GONE);

                LogReport.getInstance().reportVodPlaySucc(event);

                int result = mTXVodPlayer.getWidth() / 3;
                int w = mTXVodPlayer.getWidth() / result;
                int h = mTXVodPlayer.getHeight() / result;
                mTXVodPlayer.setRenderMode(h <= 4 ? TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION : TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
            }
        } else if (event == TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED) {
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);
                mTXVodPlayer.resume();
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null && playerInfo.isBegin) {
                mIvCover.setVisibility(View.GONE);
                TXCLog.i(TAG, "onPlayEvent, event begin, cover remove");
            }
        } else if (event < 0) {
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);

                LogReport.getInstance().reportVodPlayFail(event);
            }
            ToastUtil.toastShortMessage("event:" + event);
        }
    }

    @Override
    public void onNetStatus(TXVodPlayer player, Bundle status) {

    }

    @Override
    public void onRinging() {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.setMute(true);
        }
    }

    @Override
    public void onOffhook() {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.setMute(true);
        }
    }

    @Override
    public void onIdle() {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.setMute(false);
        }
    }

    private ShortVideoCommentPop mPopupWindow;

    //??????????????????
    private void showSheetDialog(String id) {
//        bottomSheetDialog = new ShortVideoCommentDialog(getViewContext(), id);
//        bottomSheetDialog.show();
    }

    //????????????  ?????????????????????
    @Override
    public void refreshPullDownListener() {
        EventBus.getDefault().post(new VideoEventModel(false));
    }

    //??????????????????  ?????????????????????
    @Override
    public void refreshFinishListener() {
        EventBus.getDefault().post(new VideoEventModel(true));
    }

    /**
     * ????????????????????? true ??????  false ?????????
     */
    boolean isPrepared = false;

    /**
     * ?????????????????? false   ????????????????????? true
     */
    boolean isHiddenVideo;

    /**
     * ?????????????????? true ?????????????????? false
     */
    private boolean isVisible = false;

    /**
     * ??????????????????????????????????????????
     */
    boolean isFirstShow = true;

    /**
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHiddenVideo = hidden;
        if (!isHiddenVideo && isVisible) {
            resumePlay();
            if (ll_no_login.getVisibility() == View.VISIBLE) {
                setLl_no_network();
            }
        } else {
            pausePlay();
        }
    }

    //viewpager?????? ?????? onPause onResume??????
    // ?????????????????????????????????
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            pausePlay();
        }
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {//?????????????????????false ??? ???????????????false
            return;
        }
        if (!isHiddenVideo && isFirstShow) {
            setLl_no_network();
            isFirstShow = false;
        } else {
            resumePlay();
            if (ll_no_login.getVisibility() == View.VISIBLE) {
                setLl_no_network();
            }
        }
    }
//
//    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(BroadcastProtocol.BROAD_LOGIN_SUCCESS)) {
//                setLl_no_network();
//            }
//        }
//    };

}
