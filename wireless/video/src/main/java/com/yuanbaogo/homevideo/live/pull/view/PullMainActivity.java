package com.yuanbaogo.homevideo.live.pull.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.util.Pools;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.entity.LocalMedia;
import com.permissionx.guolindev.PermissionX;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.V2TXLivePlayer;
import com.tencent.live2.V2TXLivePlayerObserver;
import com.tencent.live2.impl.V2TXLivePlayerImpl;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.live.personal.EndingStoryActivity;
import com.yuanbaogo.homevideo.live.personal.view.ViewerListPop;
import com.yuanbaogo.homevideo.live.pull.LiveHelper;
import com.yuanbaogo.homevideo.live.pull.contract.PullMainContract;
import com.yuanbaogo.homevideo.live.pull.model.EnterLiveBean;
import com.yuanbaogo.homevideo.live.pull.model.GroupChangeInfoBean;
import com.yuanbaogo.homevideo.live.pull.presenter.PullMainPresenter;
import com.yuanbaogo.homevideo.live.pull.weight.LoveLayout;
import com.yuanbaogo.homevideo.live.push.model.CoverImgBean;
import com.yuanbaogo.homevideo.live.push.model.ReciveExplainGoodsBean;
import com.yuanbaogo.homevideo.shortvideo.comment.view.InputTextMsgDialog;
import com.yuanbaogo.homevideo.shortvideo.report.model.ReportRequestModel;
import com.yuanbaogo.homevideo.shortvideo.report.view.ReportContentPopupWindow;
import com.yuanbaogo.homevideo.shortvideo.report.view.ReportSelectPopupWindow;
import com.yuanbaogo.im.contract.IMContract;
import com.yuanbaogo.im.group.GroupManagerImpl;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.live.push.Constants;
import com.yuanbaogo.zui.dialog.ShareDialogView;
import com.yuanbaogo.zui.dialog.StatusShipPop;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.PileAvertView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tencent.live2.V2TXLiveCode.V2TXLIVE_ERROR_INVALID_PARAMETER;
import static com.tencent.live2.V2TXLiveCode.V2TXLIVE_OK;


/**
 * 摄像头推流入口页面，主要用于生成推流地址
 */
public class PullMainActivity extends MvpBaseActivityImpl<PullMainContract.View, PullMainPresenter> implements PullMainContract.View,
        View.OnClickListener, ShareDialogView.ReportVideoListener {

    private ViewerListPop mPopupWindow;
    private CircleImageView circle_iamge;
    private ImageView iv_followed;
    private ImageView iv_like;
    private RelativeLayout root;

    private ImageView iv_anchor_back;
    private ImageView iv_shopping_cart;
    private ImageView iv_shopping_set;
    private ImageView iv_timer;
    private ImageView iv_explain_card;
    private ImageView iv_live_introducing_back;
    private TextView tv_explain_card_name;
    private TextView tv_explain_card_price;
    private TextView tv_to_shopping;
    private TextView tv_anchor_viewer_num;
    private TextView tv_anchor_name;
    private TextView tv_anchor_fans;
    private TextView tv_anchor_charm;

    private TextView et_input_message;
    private PileAvertView pa_anchor_viewer_head;
    private LinearLayout llContainer;
    private LinearLayout ll_explain_card;
    private LinearLayout ll_follow;
    private LoveLayout ll_love;
    private ImageView live_introducing_iv;

    private InputTextMsgDialog inputTextMsgDialog;

    //举报弹窗
    private ShareDialogView mShareDialogView;
    private ReciveExplainGoodsBean mReciveExplainGoodsBean;
    private ReportSelectPopupWindow mReportSelectPopupWindow;
    private ReportRequestModel mReportRequestModel;

    private Pools.SimplePool<TextView> viewPool = new Pools.SimplePool<>(6);
    private String playerUrl;
    private String roomId;

    private V2TXLiveDef.V2TXLiveFillMode mRenderMode = V2TXLiveDef.V2TXLiveFillMode.V2TXLiveFillModeFill;    //Player 当前渲染模式
    private V2TXLiveDef.V2TXLiveRotation mRenderRotation = V2TXLiveDef.V2TXLiveRotation.V2TXLiveRotation0;         //Player 当前渲染角度


    String mFans = "0";
    String mVisitors = "0";
    String visitorsSum = "0";
    String mCharm = "0";

    String fansNew = "0";
    String commissionAmount = "0";
    String totalAmount = "0";
    String orderCount = "0";

    String liveStandardContent = "";
    private String portraitUrl;
    private String title;
    private String anchorNickName;
    private String createDateTime;
    private String audienceNickName;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_puller_main;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        circle_iamge = findViewById(R.id.circle_iamge);
        ll_love = findViewById(R.id.ll_love);
        iv_like = findViewById(R.id.iv_like);
        root = findViewById(R.id.root);

        iv_anchor_back = findViewById(R.id.iv_anchor_back);
        tv_anchor_name = findViewById(R.id.tv_anchor_name);
        tv_anchor_fans = findViewById(R.id.tv_anchor_fans);
        tv_anchor_charm = findViewById(R.id.tv_anchor_charm);
        tv_anchor_viewer_num = findViewById(R.id.tv_anchor_viewer_num);
        pa_anchor_viewer_head = findViewById(R.id.pa_anchor_viewer_head);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        iv_shopping_set = (ImageView) findViewById(R.id.iv_shopping_set);
        iv_shopping_cart = (ImageView) findViewById(R.id.iv_shopping_cart);

        ll_explain_card = findViewById(R.id.ll_explain_card);
        iv_explain_card = findViewById(R.id.iv_explain_card);
        iv_live_introducing_back = findViewById(R.id.iv_live_introducing_back);
        tv_explain_card_name = findViewById(R.id.tv_explain_card_name);
        tv_explain_card_price = findViewById(R.id.tv_explain_card_price);
        tv_to_shopping = findViewById(R.id.tv_to_shopping);

        et_input_message = (TextView) findViewById(R.id.et_input_message);
        iv_followed = (ImageView) findViewById(R.id.iv_followed);
        ll_follow = (LinearLayout) findViewById(R.id.ll_follow);
        live_introducing_iv = (ImageView) findViewById(R.id.live_introducing_iv);

        playerUrl = getIntent().getStringExtra("playerUrl");
        portraitUrl = getIntent().getStringExtra("portraitUrl");

        roomId = getIntent().getStringExtra("RoomId");
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        iv_live_introducing_back.setOnClickListener(this);
        tv_to_shopping.setOnClickListener(this);
        ll_follow.setOnClickListener(this);

        iv_shopping_set.setOnClickListener(this);

        iv_shopping_cart.setOnClickListener(this);
        iv_like.setOnClickListener(this);
        iv_anchor_back.setOnClickListener(this);
        pa_anchor_viewer_head.setOnClickListener(this);
        et_input_message.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initPlayView();
        PermissionX.init(PullMainActivity.this)
                .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .setDialogTintColor(Color.parseColor("#008577"), Color.parseColor("#83e8dd"))
                .onExplainRequestReason((scope, deniedList) -> {
                    String message = "需要您同意以下权限才能正常使用";
                    scope.showRequestReasonDialog(deniedList, message, "确定", "取消");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        // 初始化完成之后自动播放
                        mPresenter.getInto(roomId);
                        startPlay();
                    } else {
                        ToastView.showToast("请先同意相关权限");
                        finish();
                    }
                });

        llContainer.setLayoutTransition(mPresenter.getTransition());
        int screenHeight = ScreenUtils.getScreenHeight(this);
        ViewGroup.LayoutParams lp = llContainer.getLayoutParams();
        lp.height = (int) (screenHeight * 0.4);
        llContainer.setLayoutParams(lp);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IMContract.IM_RECEIVERESTCUSTOMDATA);
        intentFilter.addAction(IMContract.IM_GROUPINFOCHANGED);
        intentFilter.addAction(IMContract.IM_MEMBERENTER);
        registerReceiver(mBroadcastReceiver, intentFilter);


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

    }

    private void initPlayView() {
        mVideoView = (TXCloudVideoView) findViewById(R.id.liveplayer_video_view);
        mVideoView.showLog(false);
        mLivePlayer = new V2TXLivePlayerImpl(this);
    }

    private V2TXLivePlayer mLivePlayer;
    public TXCloudVideoView mVideoView;
    public boolean mFrontCamera = true;
//    private boolean mIsPlaying = false;

    private void startPlay() {

        new GroupManagerImpl(this).joinGroup(roomId);

        int code = LiveHelper.checkPlayURL(playerUrl);
        if (code != Constants.PLAY_STATUS_SUCCESS) {
//            mIsPlaying = false;
        } else {
            mLivePlayer.setRenderView(mVideoView);
            mLivePlayer.setObserver(new MyPlayerObserver());
            mLivePlayer.setRenderRotation(mRenderRotation);
            mLivePlayer.setRenderFillMode(mRenderMode);

            /**
             * result返回值：
             * 0 V2TXLIVE_OK; -2 V2TXLIVE_ERROR_INVALID_PARAMETER; -3 V2TXLIVE_ERROR_REFUSED;
             */
            code = mLivePlayer.startPlay(playerUrl);
//            mIsPlaying = code == 0;

        }
        //处理UI相关操作
        onPlayStart(code);
    }

    public void setFollowState(boolean status) {
        if (status) {
            iv_followed.setVisibility(View.VISIBLE);
            ll_follow.setVisibility(View.GONE);
        } else {
            iv_followed.setVisibility(View.GONE);
            ll_follow.setVisibility(View.VISIBLE);
        }
    }

    public void onPlayStart(int code) {
        switch (code) {
            case V2TXLIVE_OK:
//                startLoadingAnimation();
                break;
            case V2TXLIVE_ERROR_INVALID_PARAMETER:
//                Toast.makeText(mContext, R.string.liveplayer_warning_res_url_invalid, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        if (code != V2TXLIVE_OK) {
            finish();
        } else {
        }
    }

    @Override
    public void setLive(EnterLiveBean bean) {

        title = bean.getTitle();
        mFans = bean.getFans();
        mCharm = bean.getCharm();
        mVisitors = bean.getVisitors();
        anchorNickName = bean.getAnchorNickName();
        audienceNickName = bean.getAudienceNickName();
        createDateTime = bean.getCreateDateTime();

        if(!"0".equals(bean.getLiveState())){
            EndingStoryActivity.toEndingStory(PullMainActivity.this, 0,
                    portraitUrl, anchorNickName, mCharm, mVisitors,
                    fansNew, orderCount, totalAmount, commissionAmount, createDateTime);
            close();
        }

        if("0".equals(bean.getSellGoods())){
            iv_shopping_cart.setVisibility(View.VISIBLE);
        }else{
            iv_shopping_cart.setVisibility(View.GONE);
        }

        Glide.with(this).load(portraitUrl).into(circle_iamge);

        if (bean.getAttentionType().equals("0") || bean.getAttentionType().equals("2")) { //没有关注
            setFollowState(false);
        } else if (bean.getAttentionType().equals("1") || bean.getAttentionType().equals("3")) {//已关注
            setFollowState(true);
        }

        tv_anchor_name.setText(anchorNickName);
        tv_anchor_viewer_num.setText(bean.getVisitors());
        tv_anchor_fans.setText("粉丝数：" + bean.getFans());
        tv_anchor_charm.setText("魅力值 " + bean.getCharm());

        V2TIMManager.getInstance().addSimpleMsgListener(listener);
        if (TextUtils.isEmpty(bean.getLiveStandardContent())) {
            liveStandardContent = this.getResources().getString(R.string.live_telecast);
        } else {
            liveStandardContent = bean.getLiveStandardContent();
        }

        addMessage("", liveStandardContent, 1, "");
    }

    public void setViewerHead(List<String> urls) {
        if (mVisitors.equals("0")) {
            pa_anchor_viewer_head.setVisibility(View.GONE);
        } else {
            pa_anchor_viewer_head.setVisibility(View.VISIBLE);
            pa_anchor_viewer_head.setAvertImages(urls);
        }
    }

    MLiveRoomSimpleMsgListener listener = new MLiveRoomSimpleMsgListener();

    class MLiveRoomSimpleMsgListener extends V2TIMSimpleMsgListener {
        @Override
        public void onRecvGroupTextMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, String text) {
            try {
                Message msg = new Message();
                msg.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("name", sender.getNickName());
                bundle.putString("message", text);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String name0 = msg.getData().getString("name");
                    String headurl = msg.getData().getString("headurl");
                    addMessage(name0, "", 2, headurl);
                    break;
                case 1:
                    String name = msg.getData().getString("name");
                    String message = msg.getData().getString("message");
                    addMessage(name, message, 3, "");
                    break;
                case 2:
                    String code = msg.getData().getString("code");
                    String data = msg.getData().getString("data");
                    String audience = msg.getData().getString("audience");
                    if (code.equals("0")) { //0：魅力值排序变更
//                        List<String> list = JSON.parseArray(data, String.class);
                        String[] datas = data.split(",");
                        List<String> list = new ArrayList();
                        for (int i = 0; i < datas.length; i++) {
                            list.add(datas[i]);
                        }
                        setViewerHead(list);
                    } else if (code.equals("1")) { //1：讲解商品变更
                        ReciveExplainGoodsBean bean = JSON.parseObject(data, ReciveExplainGoodsBean.class);
                        setExplainCard(bean);
                    } else if (code.equals("2")) {//2：主播关闭直播间（直播结束）
                        EndingStoryActivity.toEndingStory(PullMainActivity.this, 0,
                                portraitUrl, anchorNickName, mCharm, visitorsSum,
                                fansNew, orderCount, totalAmount, commissionAmount, createDateTime);
                        close();
                    } else if (code.equals("4")) {// 4：管理员关闭直播间（禁播）
                        mHandler.sendEmptyMessageDelayed(4, 8000);
                        StatusShipPop pop = new StatusShipPop(PullMainActivity.this, getResources().getDrawable(R.mipmap.auth_error), audience);
                        pop.setOnItemClickListener(new StatusShipPop.OnItemClickListener() {
                            @Override
                            public void onClickBack() {
                                destroy();
                            }
                        });
                        pop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                    } else if (code.equals("3")) {//3: 异常关闭直播间（直接关闭app或者手机没电等
                    }
                    break;
                case 3:
                    String charm = msg.getData().getString("charm");
                    String visitors = msg.getData().getString("visitors");
                    String fans = msg.getData().getString("fans");

                    fansNew = msg.getData().getString("fansNew");
                    commissionAmount = msg.getData().getString("commissionAmount");
                    totalAmount = msg.getData().getString("totalAmount");
                    orderCount = msg.getData().getString("orderCount");
                    visitorsSum = msg.getData().getString("visitorsSum");

                    if (!(mVisitors.equals(visitors))) {
                        mVisitors = visitors;
                        tv_anchor_viewer_num.setText(visitors);
                    }
                    if (!(mFans.equals(fans))) {
                        mFans = fans;
                        tv_anchor_fans.setText("粉丝数：" + fans);
                    }
                    if (!(mCharm.equals(charm))) {
                        mCharm = charm;
                        tv_anchor_charm.setText("魅力值 " + charm);
                    }
                    break;
                case 4:
                    destroy();
                    break;
            }
        }
    };


    private void refreshHead(String info) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GroupChangeInfoBean bean = JSON.parseObject(info, GroupChangeInfoBean.class);
                    Message msg = new Message();
                    msg.what = 3;
                    Bundle bundle = new Bundle();
                    bundle.putString("charm", bean.getCharm());
                    bundle.putString("visitors", bean.getVisitors());
                    bundle.putString("fans", bean.getFans());

                    bundle.putString("fansNew", bean.getFansNew());
                    bundle.putString("commissionAmount", bean.getCommissionAmount());
                    bundle.putString("totalAmount", bean.getTotalAmount());
                    bundle.putString("orderCount", bean.getOrderCount());
                    bundle.putString("visitorsSum", bean.getVisitorsSum());
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                String groupID = intent.getStringExtra("groupID");

                if (groupID.equals(roomId)) {
                    if (action.equals(IMContract.IM_RECEIVERESTCUSTOMDATA)) {
                        String code = intent.getStringExtra("code");
                        String data = intent.getStringExtra("data");
                        String audience = intent.getStringExtra("audience");
                        Message msg = new Message();
                        msg.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("code", code);
                        bundle.putString("data", data);
                        bundle.putString("audience", audience);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
//                        Log.e("network", "===im==onReceiveRESTCustomData====" + code);
//                        Log.e("network", "===im==onReceiveRESTCustomData==getData==" + data);
//                        Log.e("network", "===im==onReceiveRESTCustomData==audience==" + audience);
                    } else if (action.equals(IMContract.IM_MEMBERENTER)) {

                        String memberEnterName = intent.getStringExtra("memberEnterName");
                        String memberEnterHeadurl = intent.getStringExtra("memberEnterHeadurl");

                        Message msg = new Message();
                        msg.what = 0;
                        Bundle bundle = new Bundle();
                        bundle.putString("name", memberEnterName);
                        bundle.putString("headurl", memberEnterHeadurl);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);

                    } else if (action.equals(IMContract.IM_GROUPINFOCHANGED)) {
                        String info = intent.getStringExtra("GroupChangeInfo");
                        refreshHead(info);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    private class MyPlayerObserver extends V2TXLivePlayerObserver {
        @Override
        public void onWarning(V2TXLivePlayer player, int code, String msg, Bundle extraInfo) {
        }

        @Override
        public void onError(V2TXLivePlayer player, int code, String msg, Bundle extraInfo) {
        }

        @Override
        public void onSnapshotComplete(V2TXLivePlayer v2TXLivePlayer, Bitmap bitmap) {
        }

        //直播播放器视频状态变化通知
        @Override
        public void onVideoPlayStatusUpdate(V2TXLivePlayer player, V2TXLiveDef.V2TXLivePlayStatus status, V2TXLiveDef.V2TXLiveStatusChangeReason reason, Bundle bundle) {
            Log.i("network", "[Player] onVideoPlayStatusUpdate: player-" + player + ", status-" + status + ", reason-" + reason);
            switch (status) {
                case V2TXLivePlayStatusLoading:
//                    startLoadingAnimation();
                    break;
                case V2TXLivePlayStatusPlaying:
//                    stopLoadingAnimation();
                    break;
                case V2TXLivePlayStatusStopped:

                default:
                    break;
            }
        }

        // 直播播放器音频状态变化通知
        @Override
        public void onAudioPlayStatusUpdate(V2TXLivePlayer player, V2TXLiveDef.V2TXLivePlayStatus status, V2TXLiveDef.V2TXLiveStatusChangeReason reason, Bundle bundle) {
            Log.e("network", "[Player] onAudioPlayStatusUpdate: player-" + player + ", status-" + status + ", reason-" + reason);
            switch (status) {
                case V2TXLivePlayStatusLoading:
//                    startLoadingAnimation();
                    break;
                case V2TXLivePlayStatusPlaying:
//                    stopLoadingAnimation();
                    break;
                case V2TXLivePlayStatusStopped:

                default:
                    break;
            }
        }

        @Override
        public void onPlayoutVolumeUpdate(V2TXLivePlayer player, int volume) {
//            Log.i(TAG, "onPlayoutVolumeUpdate: player-" + player +  ", volume-" + volume);
        }
    }


    private void addMessage(String name, String message, int type, String headurl) {
        if (llContainer.getChildCount() == 6) {
            llContainer.removeViewAt(0);
        }
        View messageView = obtainTextView();
        llContainer.addView(messageView);
        LinearLayout ll_live_message = messageView.findViewById(R.id.ll_live_message);
        TextView tv_live_message = messageView.findViewById(R.id.tv_live_message);

        LinearLayout ll_live_viewer = messageView.findViewById(R.id.ll_live_viewer);
        CircleImageView circle_iamge_head = messageView.findViewById(R.id.circle_iamge_head);
        TextView tv_live_viewer = messageView.findViewById(R.id.tv_live_viewer);

        if (type == 1) {
            ll_live_viewer.setVisibility(View.GONE);
            ll_live_message.setVisibility(View.VISIBLE);
            tv_live_message.setText(message);
            tv_live_message.setTextColor(getResources().getColor(R.color.colorFFE082));
        } else if (type == 2) {
            ll_live_viewer.setVisibility(View.VISIBLE);
            ll_live_message.setVisibility(View.GONE);
            Glide.with(this).load(headurl)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(circle_iamge_head);
            tv_live_viewer.setText(name + " 进入了直播间");
        } else if (type == 3) {
            ll_live_viewer.setVisibility(View.GONE);
            ll_live_message.setVisibility(View.VISIBLE);
            tv_live_message.setText(getClickableSpan(name + ": ", message));
        }

        int screenWidth = ScreenUtils.getScreenWidth(this);
        ViewGroup.LayoutParams lp = ll_live_message.getLayoutParams();
        lp.width = (int) (screenWidth * 0.65);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ll_live_message.setLayoutParams(lp);
    }

    private SpannableString getClickableSpan(String pretxt, String message) {
        SpannableString spannableString = new SpannableString(pretxt + message);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorFFBB82)), 0, pretxt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private View obtainTextView() {
        View view = viewPool.acquire();
        if (view == null) {
            view = LayoutInflater.from(this).inflate(R.layout.video_live_item_message, null);
        }
        return view;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_anchor_back) {
            mPresenter.getOut(roomId);
            close();
        } else if (id == R.id.iv_shopping_cart) {
            mPresenter.toCartGoods();
        } else if (id == R.id.iv_live_introducing_back) {
            ll_explain_card.setVisibility(View.GONE);
        } else if (id == R.id.ll_follow) {
            mPresenter.followState();
        } else if (id == R.id.iv_shopping_set) {
            mShareDialogView.show(getSupportFragmentManager(), "share");
        } else if (id == R.id.et_input_message) {
            initInputTextMsgDialog(root);
//        } else if (id == R.id.iv_followed) {
//            mPresenter.followState();
        } else if (id == R.id.iv_like) {
            mPresenter.clickCharm();
            ll_love.addChristmas(PullMainActivity.this);
        } else if (id == R.id.pa_anchor_viewer_head) {
            mPopupWindow = new ViewerListPop(this, roomId);
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    //点击弹窗的举报回调
    @Override
    public void onReportVideo() {
        mShareDialogView.dismiss();
        mReportSelectPopupWindow = new ReportSelectPopupWindow(this,"举报直播", new ReportContentPopupWindow.SubmitCallback() {
            @Override
            public void submit(ReportRequestModel reportRequestModel, List<LocalMedia> localMedia) {
                mReportRequestModel = reportRequestModel;
                mReportRequestModel.setAuthorId(mPresenter.getYbCode());
                mReportRequestModel.setBizId(roomId);
                mReportRequestModel.setBizTitle(title);
                if (localMedia != null & localMedia.size() > 0) {
                    mPresenter.uploadPic(localMedia);
                } else {
                    mPresenter.reportSubmit(mReportRequestModel);
                }
            }
        });
        mReportSelectPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    //举报图片上传成功
    @Override
    public void onUploadPicSuccess(CoverImgBean coverImgBean) {
        mPresenter.reportSubmit(mReportRequestModel);
    }

    //举报图片上传失败
    @Override
    public void onUploadPicFail() {
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

    public void setExplainCard(ReciveExplainGoodsBean bean) {
        mReciveExplainGoodsBean = bean;
        if (ll_explain_card.getVisibility() == View.GONE) {
            ll_explain_card.setVisibility(View.VISIBLE);
        }
        tv_explain_card_name.setText(bean.getGoodsTitle());
        tv_explain_card_price.setText("￥" + bean.getGoodsPrice()/100);
        Glide.with(this).load(bean.getGoodsUrl()).into(iv_explain_card);
        Glide.with(this).load(R.drawable.icon_shop_recommend_live_diverge_view_second4).into(live_introducing_iv);
        ll_explain_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //商品SpuID  直播  商品批次  直播ID号
                RouterHelper.toShopProductDetails(bean.getLiveGoods(),
                        TagParameteBean.liveBringGoods, bean.getProductBatch(), roomId,true);
            }
        });
        tv_to_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getSku(bean.getLiveGoods(),bean.getProductBatch(),bean.getGoodsPrice(),bean.getGoodsUrl());
            }
        });
    }

    @Override
    public void onBackPressed() {
        mPresenter.getOut(roomId);
        destroy();
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        mHandler.removeCallbacksAndMessages((Object) null);
    }


    public void close() {
        destroy();
        finish();
    }

    private void destroy() {
        V2TIMManager.getInstance().removeSimpleMsgListener(listener);
        GroupManagerImpl.quitGroup(roomId);
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay();
            mLivePlayer.setObserver(null);
            mLivePlayer = null;
        }
        if (mVideoView != null) {
            mVideoView.onDestroy();
            mVideoView = null;
        }
    }

    private void initInputTextMsgDialog(View view) {
        dismissInputDialog();
//        if (view != null) {
//            this.offsetY = view.getTop();
//            scrollLocation(offsetY);
//        }
        if (inputTextMsgDialog == null) {
            inputTextMsgDialog = new InputTextMsgDialog(this, R.style.dialog);
            inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
                    if (!UserStore.isLogin()) {
                        RouterHelper.toLogin();
                        return;
                    }
                    if (!TextUtils.isEmpty(roomId) && !TextUtils.isEmpty(msg)) {
                        GroupManagerImpl.sendGroupMessage(msg, roomId, new GroupManagerImpl.Callback() {

                            @Override
                            public void onError(int code, String errInfo) {
                            }

                            @Override
                            public void onSuccess(String nickName, String text, String groupID) {
                                addMessage(audienceNickName, text, 3, "");
                            }
                        });
                    }
                }

                @Override
                public void dismiss() {

                }
            });
            inputTextMsgDialog.setHint("说点什么好听的...");
            inputTextMsgDialog.setMaxNumber(50);
        }
        showInputTextMsgDialog();
    }

    private void dismissInputDialog() {
        if (inputTextMsgDialog != null) {
            if (inputTextMsgDialog.isShowing()) inputTextMsgDialog.dismiss();
            inputTextMsgDialog.cancel();
            inputTextMsgDialog = null;
        }
    }

    private void showInputTextMsgDialog() {
        inputTextMsgDialog.show();
    }



}