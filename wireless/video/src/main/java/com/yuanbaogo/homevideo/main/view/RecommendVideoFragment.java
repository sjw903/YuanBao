package com.yuanbaogo.homevideo.main.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.utils.TextUtils;
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
import com.yuanbaogo.homevideo.main.bean.VideoEventModel;
import com.yuanbaogo.homevideo.main.contract.RecommendVideoContract;
import com.yuanbaogo.homevideo.main.presenter.RecommendVideoPresenter;
import com.yuanbaogo.homevideo.shortvideo.comment.ShortVideoCommentDialog;
import com.yuanbaogo.homevideo.shortvideo.comment.model.CommentListBean;
import com.yuanbaogo.homevideo.shortvideo.report.model.ReportRequestModel;
import com.yuanbaogo.homevideo.shortvideo.report.view.ReportContentPopupWindow;
import com.yuanbaogo.homevideo.shortvideo.report.view.ReportSelectPopupWindow;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.video.play.PlayerInfo;
import com.yuanbaogo.video.widget.VideoShoppingCardLayout;
import com.yuanbaogo.zui.dialog.SelectConfirmDialogView;
import com.yuanbaogo.zui.dialog.ShareDialogView;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.VideoRefreshHeader;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * @author lhx
 * @description: ????????????
 * @date : 2021/7/6 13:36
 */
public class RecommendVideoFragment extends MvpBaseFragmentImpl<RecommendVideoContract.View, RecommendVideoPresenter>
        implements RecommendVideoContract.View, ITXVodPlayListener, TelephonyUtil.OnTelephoneListener,
        ShareDialogView.IDeleteVideoListener, ShareDialogView.ReportVideoListener, ShareDialogView.UninterestedVideoListener,
        VideoRefreshHeader.OnHeadStateChangedListener {

    private RecommendVideoPresenter recommendVideoPresenter = new RecommendVideoPresenter();
    //    private ImageView iv_video_no_data;
    private LinearLayout ll_no_network;
    private TextView tv_reload;

    private VerticalViewPager mVerticalViewPager;

    private SmartRefreshLayout mRefreshLayout;
    private MyPagerAdapter mPagerAdapter;
    private TXCloudVideoView mTXCloudVideoView;
    private ImageView mIvCover;
    private ProgressBar mProgressBar;
    private ImageView mIvPause;
    private ImageView iv_progress_img;
    // ?????????id ?????????????????? ?????????????????? ??????URL??? ??????URL
    private List<RecommendVideoBean.RowsBean> mTCLiveInfoList = new ArrayList<>();
    private int mCurrentPosition = 0;
    private int mStartPosition = -1;
    /**
     * SDK?????????????????????
     */
    private TXVodPlayer mTXVodPlayer;

    private ShareDialogView mDelShareDialogView;
    private ShareDialogView mShareDialogView;

    private int page = 1;

    private boolean isFirst = true;//???????????????

    private boolean isPause = false;//????????????
    private boolean isLike = false;//????????????
    //    private int likeCount;//????????????
    private String deleteVodId;//??????????????????ID
    private String unconcernVodId;//?????????????????????ID
    private ReportSelectPopupWindow mReportSelectPopupWindow;

    //????????????
    private ReportRequestModel mReportRequestModel;

    VideoRefreshHeader zi_refresh_head;

    private PowerManager mPowerManager;//?????? a???????????? b??????????????????????????????
    public boolean mIsPalying;//????????????????????????

    public static final String SOURCETYPE_EXTERNAL = "external";
    public static final String SOURCETYPE_LIKE = "like";
    public static final String SOURCETYPE_WORK = "work";
    private String mSourceType = "";

    private int type;

    public static RecommendVideoFragment newInstance(List<RecommendVideoBean.RowsBean> videoList, int startPosition, String sourcetype, int type) {
        RecommendVideoFragment fragment = new RecommendVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("videolist", (ArrayList<? extends Parcelable>) videoList);
        bundle.putInt("startPosition", startPosition);
        bundle.putString("sourcetype", sourcetype);
        bundle.putInt("type", type);
        //fragment???????????????????????????Bundle??????
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected RecommendVideoPresenter createPresenter(Bundle savedInstanceState) {
        return recommendVideoPresenter;
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.fragment_recommend_video;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {

        if (getArguments() != null) {
            mTCLiveInfoList = getArguments().getParcelableArrayList("videolist");
            mStartPosition = getArguments().getInt("startPosition");
            mSourceType = getArguments().getString("sourcetype");
            type = getArguments().getInt("type");
        }

        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_video);

        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.player_cloud_view);
        mIvCover = (ImageView) findViewById(R.id.player_iv_cover);
        mVerticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_view_pager);
        iv_progress_img = (ImageView) findViewById(R.id.iv_progress_img);
        zi_refresh_head = (VideoRefreshHeader) findViewById(R.id.zi_refresh_head);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_h);

        mPowerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);

        Glide.with(mContext).asGif()
                .load(R.drawable.video_loading_progress)
                .into(iv_progress_img);

        ll_no_network = (LinearLayout) findViewById(R.id.ll_no_network);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
//        iv_video_no_data = (ImageView) findViewById(R.id.iv_video_no_data);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        //???????????????????????????????????????
        if (mSourceType.equals(SOURCETYPE_LIKE) || mSourceType.equals(SOURCETYPE_WORK) || mSourceType.equals(SOURCETYPE_EXTERNAL)) {
            mRefreshLayout.setEnableRefresh(false);
        } else {
            mRefreshLayout.setEnableRefresh(true);
        }

        mVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mSourceType.equals(SOURCETYPE_LIKE) || mSourceType.equals(SOURCETYPE_WORK)) {
                    return;
                }
            }

            @Override
            public void onPageSelected(int position) {
                isFirst = false;
                mCurrentPosition = position;

                View v = mVerticalViewPager.findViewById(position);
                if (v != null) {
                    VideoShoppingCardLayout zri_videoshoppingcard = v.findViewById(R.id.zri_videoshoppingcard);
                    RecommendVideoBean.RowsBean bean = mTCLiveInfoList.get(mCurrentPosition);
                    zri_videoshoppingcard.timedStart(bean);
                }
                mRefreshLayout.setEnableRefresh(false);
                if (position == 0 && !mSourceType.equals(SOURCETYPE_EXTERNAL)) {
                    mRefreshLayout.setEnableRefresh(true);
                }
                if (mSourceType.equals(SOURCETYPE_LIKE) || mSourceType.equals(SOURCETYPE_WORK)) {
                    mRefreshLayout.setEnableRefresh(false);
                }
                if (mIvPause != null) {
                    if (mIvPause.getVisibility() == View.VISIBLE) {
                        mIvPause.setVisibility(View.GONE);
                        isPause = false;
                    }
                }
                // ??????????????????????????????????????????????????????seek???0
                TXLog.d(TAG, "??????????????????????????????????????????mTXVodPlayer = " + mTXVodPlayer);
                if (mTXVodPlayer != null) {
                    mTXVodPlayer.seek(0);
                    mTXVodPlayer.pause();
                }

                if (mTCLiveInfoList.size() >= 10 && position >= mTCLiveInfoList.size() - 3) {
                    if (!mSourceType.equals(SOURCETYPE_EXTERNAL)) {
                        page++;
                        mPresenter.getRecommendVideoList(page + "", "10");
                    }
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
                    mProgressBar.setProgress(0);
                }
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        //????????????
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            page = 1;
            mPresenter.getRecommendVideoList(page + "", "10");
        });
        zi_refresh_head.setChangedListener(this);
        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLl_no_network();
            }
        });
    }

    private void setLl_no_network() {
        if (isNetworkAvailable()) {
            if (!mSourceType.equals(SOURCETYPE_EXTERNAL)) {
                mPresenter.getRecommendVideoList(page + "", "10");
                iv_progress_img.setVisibility(View.VISIBLE);
            }
            mRefreshLayout.setVisibility(View.VISIBLE);
            ll_no_network.setVisibility(View.GONE);
//            iv_video_no_data.setVisibility(View.GONE);
        } else {
            mRefreshLayout.setVisibility(View.GONE);
            ll_no_network.setVisibility(View.VISIBLE);
//            iv_video_no_data.setVisibility(View.GONE);
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
        shareBean2.setNotInterested(mSourceType.equals(SOURCETYPE_LIKE) || mSourceType.equals(SOURCETYPE_WORK) && type == 1 ? false : true);
        shareBean2.setDelete(false);
        mShareDialogView = new ShareDialogView(shareBean2);
        mShareDialogView.setReportVideoListener(this);
        mShareDialogView.setUninterestedVideoListener(this);

        if (mStartPosition > 0) {
            mVerticalViewPager.setCurrentItem(mStartPosition);
        }
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onVideoListSuccess(RecommendVideoBean bean) {
        mRefreshLayout.finishRefresh(true/*,false*/);//??????false??????????????????
        if (bean != null && bean.getRows() != null && bean.getRows().size() > 0) {
            if (page == 1) {
                mTCLiveInfoList = bean.getRows();
            } else {
                mTCLiveInfoList.addAll(bean.getRows());
            }
            mPagerAdapter.notifyDataSetChanged();
        } else {
            if (page == 1) {
                mRefreshLayout.setVisibility(View.GONE);
                ll_no_network.setVisibility(View.GONE);
//            iv_video_no_data.setVisibility(View.VISIBLE);
            } else {
//                ToastView.showToast("????????????????????????");
            }
        }
        iv_progress_img.setVisibility(View.GONE);
    }

    @Override
    public void onVideoListFail() {
        mRefreshLayout.finishRefresh(true/*,false*/);//??????false??????????????????
        if (page == 1) {
            mRefreshLayout.setVisibility(View.GONE);
            ll_no_network.setVisibility(View.GONE);
//        iv_video_no_data.setVisibility(View.VISIBLE);
        }
        iv_progress_img.setVisibility(View.GONE);
    }

    /**
     * ??????????????????
     */
    @Override
    public void onClickLikeSuccess(String likeState, int position) {
        isLike = false;
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
        if (mTCLiveInfoList.size() == 0) {
            getActivity().finish();
        }
    }

    //?????????????????????????????????
    @Override
    public void onUninterestedVideo() {
        mPresenter.unconcernVideo(unconcernVodId);
        mShareDialogView.dismiss();
        mTCLiveInfoList.remove(mCurrentPosition);
        mPagerAdapter.notifyDataSetChanged();
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

    class MyPagerAdapter extends PagerAdapter {

        ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();

        protected PlayerInfo instantiatePlayerInfo(int position) {
            TXCLog.d(TAG, "instantiatePlayerInfo " + position);

            PlayerInfo playerInfo = new PlayerInfo();
            TXVodPlayer vodPlayer = new TXVodPlayer(mContext);
            vodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
            //FIXBUG:FULL_SCREEN ?????????????????????ADJUST_RESOLUTION??????
            vodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
            vodPlayer.setVodListener(RecommendVideoFragment.this);
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

            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_player_content, null);
            view.setId(position);
            view.setTag(position); // Add tag

            VideoShoppingCardLayout zri_videoshoppingcard = view.findViewById(R.id.zri_videoshoppingcard);
            if (videoInfo != null) {
                zri_videoshoppingcard.setGoodsCardBean(videoInfo);
                if (isFirst) {
                    zri_videoshoppingcard.timedStart(videoInfo);
                }
                ImageView iv_video_add_friend = (ImageView) view.findViewById(R.id.iv_video_add_friend);
                if (videoInfo.getFollowState() == 0) {
                    iv_video_add_friend.setVisibility(View.VISIBLE);
                    iv_video_add_friend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!UserStore.isLogin()) {
                                RouterHelper.toLogin();
                                return;
                            }
                            mPresenter.toFollowFans(videoInfo.getYbCode());
                            iv_video_add_friend.setVisibility(View.GONE);
                        }
                    });
                } else {
                    iv_video_add_friend.setVisibility(View.GONE);
                }

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
                ImageView mIvLike = view.findViewById(R.id.iv_video_like);
                TextView mTvLike = view.findViewById(R.id.tv_video_like);
                if (videoInfo.getHasLiked() == 0) {
                    mIvLike.setImageResource(R.mipmap.icon_video_like_no);
                } else {
                    mIvLike.setImageResource(R.mipmap.icon_video_like);
                }
                mTvLike.setText(videoInfo.getLikeCount() + "");
                mIvLike.setOnClickListener(v -> {
                    if (UserStore.isLogin()) {
                        if (!isLike) {
                            isLike = true;
                            if (videoInfo.getHasLiked() == 0) {
                                mIvLike.setImageResource(R.mipmap.icon_video_like);
                                videoInfo.setLikeCount(videoInfo.getLikeCount() + 1);
                                mPresenter.clickLike("1", videoInfo.getVideoId(), position);
                            } else {
                                mIvLike.setImageResource(R.mipmap.icon_video_like_no);
                                videoInfo.setLikeCount(videoInfo.getLikeCount() - 1);
                                mPresenter.clickLike("0", videoInfo.getVideoId(), position);
                            }
                        }
                        mTvLike.setText(videoInfo.getLikeCount() + "");
                    } else {
                        RouterHelper.toLogin();
                    }
                });
                mIvComment.setOnClickListener(v -> {
                    shortVideoCommentDialog = new ShortVideoCommentDialog(getViewContext(), videoInfo.getVideoId());
                    shortVideoCommentDialog.setiDeleteDialog(new ShortVideoCommentDialog.IDeleteDialog() {
                        @Override
                        public void onDeleteItem(CommentListBean.RowsBean bean, LinearLayout secondViewGroup) {
                            SelectConfirmDialogView selectConfirmDialogView =
                                    new SelectConfirmDialogView();
                            selectConfirmDialogView.setOnListener(new SelectConfirmDialogView.OnListener() {
                                @Override
                                public void onConfirm() {
                                    shortVideoCommentDialog.deleteOneComment(bean, secondViewGroup);
                                }

                                @Override
                                public void onCancel() {
                                    selectConfirmDialogView.dismiss();
                                }
                            });
                            selectConfirmDialogView.show(getChildFragmentManager(), "delete_comment");
                        }
                    });
                    shortVideoCommentDialog.setOnItemClickListener(new ShortVideoCommentDialog.OnItemClickListener() {
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
                    shortVideoCommentDialog.show();
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
                            mIsPalying = true;
                            playerInfo.vodPlayer.pause();
                            ivPause.setVisibility(View.VISIBLE);
                        } else {
                            mIsPalying = false;
                            playerInfo.vodPlayer.resume();
                            ivPause.setVisibility(View.GONE);
                        }
                        isPause = !isPause;
                    }
                });
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
        }
    }

    //??????????????????
    public void resumePlay() {
        if (mPowerManager.isScreenOn()) {//????????????  ?????????????????????
//            if (mIsPalying) {
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
//            }
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
    public void pausePlay() {
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
        if (shortVideoCommentDialog != null && shortVideoCommentDialog.mKeyBoardListener != null) {
            shortVideoCommentDialog.mKeyBoardListener.setOnSoftKeyBoardChangeListener(null);
            shortVideoCommentDialog.mKeyBoardListener = null;
        }
    }

    public void stopPlay(boolean clearLastFrame) {
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
                mIsPalying = true;
                mTXVodPlayer.resume();
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null && playerInfo.isBegin) {
                mIvCover.setVisibility(View.GONE);
                TXCLog.i(TAG, "onPlayEvent, event begin, cover remove");
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null && playerInfo.isBegin) {
                // ??????
                int currentTime = (int) playerInfo.vodPlayer.getCurrentPlaybackTime();//????????????????????????  ?????????
                if (currentTime != 0) {
                    int duration = (int) playerInfo.vodPlayer.getDuration();//???????????????  ?????????
                    float total = (float) currentTime / duration * 100;
                    mProgressBar.setProgress((int) total);
                }
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

    private ShortVideoCommentDialog shortVideoCommentDialog;

    //??????????????????
    private void showSheetDialog(String id) {
//        bottomSheetDialog = new ShortVideoCommentDialog(getViewContext(), id);
//        bottomSheetDialog.show();
    }

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
     * ????????????????????? true ??????  false ?????????
     */
    boolean isPrepared = false;

    /**
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHiddenVideo = hidden;
        if (!isHiddenVideo && isVisible) {
            resumePlay();
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
        }
    }

}
