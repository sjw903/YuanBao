package com.yuanbaogo.homevideo.live.push.view;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.util.Pools;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.permissionx.guolindev.PermissionX;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.liteav.demo.beauty.view.BeautyPanel;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.V2TXLivePusher;
import com.tencent.live2.impl.V2TXLivePusherImpl;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.local.video.VideoSPUtils;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.live.personal.EndingStoryActivity;
import com.yuanbaogo.homevideo.live.personal.view.ViewerListPop;
import com.yuanbaogo.homevideo.live.pull.model.GroupChangeInfoBean;
import com.yuanbaogo.homevideo.live.push.contract.PushMainContract;
import com.yuanbaogo.homevideo.live.push.dialog.AnchorSettingPop;
import com.yuanbaogo.homevideo.live.push.listener.LivePusherObserver;
import com.yuanbaogo.homevideo.live.push.model.CreateLiveBean;
import com.yuanbaogo.homevideo.live.push.model.ExplainGoodsBean;
import com.yuanbaogo.homevideo.live.push.presenter.PushMainPresenter;
import com.yuanbaogo.homevideo.shortvideo.comment.view.InputTextMsgDialog;
import com.yuanbaogo.im.contract.IMContract;
import com.yuanbaogo.im.group.GroupManagerImpl;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.libbase.baseutil.ScreenListener;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.zui.dialog.SelectPicPopupWindow;
import com.yuanbaogo.zui.dialog.StatusShipButtonPop;
import com.yuanbaogo.zui.picture.GlideEngine;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.PileAvertView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 摄像头推流入口页面，主要用于生成推流地址
 */
public class PushMainActivity extends MvpBaseActivityImpl<PushMainContract.View, PushMainPresenter> implements PushMainContract.View, View.OnClickListener {

    private EditText et_live_title;
    private TextView tv_live_title_num;
    private TextView tv_live_agreement;
    private TextView tv_live_start;
    private ImageView iv_live_video_back;
    private ImageView iv_live_video_cover;
    private LinearLayout ll_live_beauty;
    private LinearLayout ll_live_overturn;
    private LinearLayout ll_live_selling;
    private RelativeLayout rl_pusher_guide;
    private RelativeLayout rl_anchor_interface;
    private TextView tv_change_cover;
    private BeautyPanel mBeautyPanelView;

    private ImageView iv_anchor_back;
    private ImageView iv_shopping_cart;
    private ImageView circle_anchor_head;
    private ImageView iv_shopping_set;
    private ImageView iv_timer;
    private ImageView iv_explain_card;
    private ImageView iv_live_introducing_back;
    private TextView tv_explain_card_name;
    private TextView tv_explain_card_price;
    private TextView tv_anchor_viewer_num;
    private TextView tv_anchor_name;
    private TextView tv_anchor_fans;
    private TextView tv_anchor_charm;
    private TextView et_input_message;
    private PileAvertView pa_anchor_viewer_head;
    private LinearLayout llContainer;
    private LinearLayout ll_explain_card;
    private Pools.SimplePool<TextView> viewPool = new Pools.SimplePool<>(6);

    public V2TXLivePusher mLivePusher;
    public TXCloudVideoView mPusherView;
    public boolean mFrontCamera = true;
    public boolean mFlashLigh = false;
    public boolean isLiveStart = false;
    private AnchorSettingPop mPopupWindow;
    private ViewerListPop mViewerListPop;

    private String roomId;
    private boolean mIsResume = false;
    private String mSellGoods;  // 1不带货

    private InputTextMsgDialog inputTextMsgDialog;
    private ScreenListener screenListener;
    private ImageView live_introducing_iv;

    String mFans = "0";
    String mVisitors = "0";
    String visitorsSum = "0";
    String mCharm = "0";

    String fansNew = "0";
    String commissionAmount = "0";
    String totalAmount = "0";
    String orderCount = "0";
    private String portraitUrl;
    private String anchorNickName;
    private String createDateTime;

    RelativeLayout pusher_rl;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (null != mBeautyPanelView && mBeautyPanelView.getVisibility() != View.GONE && ev.getRawY() < mBeautyPanelView.getTop()) {
            mBeautyPanelView.setVisibility(View.GONE);
            if (!isLiveStart) {
                rl_pusher_guide.setVisibility(View.VISIBLE);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_live_video_back) {
            finish();
        } else if (id == R.id.iv_live_introducing_back) {
            ll_explain_card.setVisibility(View.GONE);
        } else if (id == R.id.iv_anchor_back) {
            if (isLiveStart)
                mPresenter.closeLiving(roomId, "0");
        } else if (id == R.id.tv_live_agreement) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            RouterHelper.toWebJs(WebUrls.proImclause, true);
        } else if (id == R.id.iv_shopping_cart) {//主播购物车
            mPresenter.toCartGoods();
        } else if (id == R.id.iv_shopping_set) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else if (id == R.id.iv_live_video_cover) {
            selectPhoto();
        } else if (id == R.id.ll_live_beauty) {
            if (mBeautyPanelView.isShown()) {
                mBeautyPanelView.setVisibility(View.GONE);
                rl_pusher_guide.setVisibility(View.VISIBLE);
            } else {
                mBeautyPanelView.setVisibility(View.VISIBLE);
                rl_pusher_guide.setVisibility(View.GONE);
            }
        } else if (id == R.id.ll_live_selling) { //主播带货
            RouterHelper.toBringGoods(this, REQUEST_CODE_GOODLIST, true);
        } else if (id == R.id.tv_live_start) {
            mPresenter.createLive(et_live_title.getText().toString());
        } else if (id == R.id.ll_live_overturn) {
//            // 表明当前是前摄像头
//            if (view.getTag() == null || (Boolean) view.getTag()) {
//                view.setTag(false);
//            } else {
//                view.setTag(true);
//            }
            switchCamera();
        } else if (id == R.id.et_input_message) {
            initInputTextMsgDialog(null);
        } else if (id == R.id.pa_anchor_viewer_head) {
            mViewerListPop = new ViewerListPop(this, roomId);
            mViewerListPop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else if (id == R.id.pusher_rl) {
            //点击除EditText之外的地方隐藏键盘
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_pusher_main;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        iv_live_video_back = findViewById(R.id.iv_live_video_back);
        iv_live_video_cover = findViewById(R.id.iv_live_video_cover);
        tv_change_cover = findViewById(R.id.tv_change_cover);
        et_live_title = findViewById(R.id.et_live_title);
        ll_live_beauty = findViewById(R.id.ll_live_beauty);
        ll_live_overturn = findViewById(R.id.ll_live_overturn);
        ll_live_selling = findViewById(R.id.ll_live_selling);
        tv_live_start = findViewById(R.id.tv_live_start);
        tv_live_agreement = findViewById(R.id.tv_live_agreement);
        tv_live_title_num = findViewById(R.id.tv_live_title_num);
        rl_pusher_guide = findViewById(R.id.rl_pusher_guide);
        rl_anchor_interface = findViewById(R.id.rl_anchor_interface);
        mBeautyPanelView = findViewById(R.id.livepusher_bp_beauty_pannel);
        iv_timer = findViewById(R.id.iv_timer);

        iv_anchor_back = findViewById(R.id.iv_anchor_back);
        tv_anchor_name = findViewById(R.id.tv_anchor_name);
        tv_anchor_fans = findViewById(R.id.tv_anchor_fans);
        tv_anchor_charm = findViewById(R.id.tv_anchor_charm);
        tv_anchor_viewer_num = findViewById(R.id.tv_anchor_viewer_num);
        pa_anchor_viewer_head = findViewById(R.id.pa_anchor_viewer_head);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        iv_shopping_set = (ImageView) findViewById(R.id.iv_shopping_set);
        iv_shopping_cart = (ImageView) findViewById(R.id.iv_shopping_cart);
        circle_anchor_head = (ImageView) findViewById(R.id.circle_anchor_head);

        ll_explain_card = findViewById(R.id.ll_explain_card);
        iv_explain_card = findViewById(R.id.iv_explain_card);
        iv_live_introducing_back = findViewById(R.id.iv_live_introducing_back);
        tv_explain_card_name = findViewById(R.id.tv_explain_card_name);
        tv_explain_card_price = findViewById(R.id.tv_explain_card_price);

        et_input_message = (TextView) findViewById(R.id.et_input_message);
        pusher_rl = findViewById(R.id.pusher_rl);
        live_introducing_iv = (ImageView) findViewById(R.id.live_introducing_iv);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        iv_live_video_back.setOnClickListener(this);
        iv_anchor_back.setOnClickListener(this);
        pa_anchor_viewer_head.setOnClickListener(this);
        iv_live_introducing_back.setOnClickListener(this);
        iv_live_video_cover.setOnClickListener(this);
        ll_live_beauty.setOnClickListener(this);
        ll_live_overturn.setOnClickListener(this);
        ll_live_selling.setOnClickListener(this);
        tv_live_start.setOnClickListener(this);
        tv_live_agreement.setOnClickListener(this);
        et_input_message.setOnClickListener(this);
        pusher_rl.setOnClickListener(this);
        et_live_title.addTextChangedListener(mPresenter.getTextWatcher());
        et_live_title.setFilters(new InputFilter[]{et_live_title.getFilters()[0], mInputFilter});//这里的editText.getFilters()[0]是为了保留上面的xml设置的LengthFilter同时有效
        mBeautyPanelView.setBeautyManager(mLivePusher.getBeautyManager());
        mBeautyPanelView.setOnBeautyListener(new BeautyPanel.OnBeautyListener() {
            @Override
            public boolean onClose() {
                if (!isLiveStart) {
                    rl_pusher_guide.setVisibility(View.VISIBLE);
                }
                mBeautyPanelView.setVisibility(View.GONE);

                return true;
            }
        });

        iv_shopping_cart.setOnClickListener(this);
        iv_shopping_set.setOnClickListener(this);
        mLivePusher.setObserver(new LivePusherObserver(new LivePusherObserver.OnLivePusherListener() {
            @Override
            public void onError(String msg) {
            }

            @Override
            public void onWarning(String msg) {
            }
        }));

        mPopupWindow.setOnPayClickListener(new AnchorSettingPop.OnClickListener() {
            @Override
            public void onBeautifyClick() {
                if (mBeautyPanelView.isShown()) {
                    mBeautyPanelView.setVisibility(View.GONE);
                    rl_pusher_guide.setVisibility(View.VISIBLE);
                } else {
                    mBeautyPanelView.setVisibility(View.VISIBLE);
                    rl_pusher_guide.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCameraClick() {
                switchCamera();
            }

            @Override
            public void onLampClick() {
                turnOnFlashLight();
            }
        });

        screenListener = new ScreenListener(this);
        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
            }

            @Override
            public void onScreenOff() {
                pause();
            }

            @Override
            public void onUserPresent() {
                resume();
            }
        });

    }

//禁止输入回车换行
    InputFilter mInputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            return source.toString().replace("\n", "");
        }
    };


    @Override
    protected void initViews(Bundle savedInstanceState) {
        initPusher();
        PermissionX.init(this)
                .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .setDialogTintColor(Color.parseColor("#008577"), Color.parseColor("#83e8dd"))
                .onExplainRequestReason((scope, deniedList) -> {
                    String message = "需要您同意以下权限才能正常使用";
                    scope.showRequestReasonDialog(deniedList, message, "确定", "取消");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        setPusher();
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

        mPopupWindow = new AnchorSettingPop(PushMainActivity.this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IMContract.IM_RECEIVERESTCUSTOMDATA);
        intentFilter.addAction(IMContract.IM_GROUPINFOCHANGED);
        intentFilter.addAction(IMContract.IM_MEMBERENTER);
        registerReceiver(mBroadcastReceiver, intentFilter);
        String url = VideoSPUtils.getLiveCoverUrl();
        String name = VideoSPUtils.getLiveCoverName();
        if (!TextUtils.isEmpty(url)) {
            setCoverImg(url);
            mPresenter.setCoverImgName(name);
        }
    }

    private void initPusher() {
        initListener();
        mLivePusher = new V2TXLivePusherImpl(this, V2TXLiveDef.V2TXLiveMode.TXLiveMode_RTMP);
        // 设置默认美颜参数， 美颜样式为光滑，美颜等级 5，美白等级 3，红润等级 2
        mLivePusher.getBeautyManager().setBeautyStyle(TXLiveConstants.BEAUTY_STYLE_SMOOTH);
        mLivePusher.getBeautyManager().setBeautyLevel(5);
        mLivePusher.getBeautyManager().setWhitenessLevel(6);
        mLivePusher.getBeautyManager().setRuddyLevel(2);
    }

    private void setPusher() {
        mPusherView = (TXCloudVideoView) findViewById(R.id.pusher_tx_cloud_view);
        mPusherView.setVisibility(View.VISIBLE);
        // 添加播放回调
//        mLivePusher.setObserver(new MyPusherObserver());
        // 设置推流分辨率
//        mLivePusher.setVideoQuality(mVideoResolution, mIsLandscape ?
//                V2TXLiveVideoResolutionModeLandscape : V2TXLiveVideoResolutionModePortrait);

        // 是否开启观众端镜像观看
        mLivePusher.setEncoderMirror(false);

        // 是否打开曝光对焦
        mLivePusher.getDeviceManager().enableCameraAutoFocus(false);

        mLivePusher.getAudioEffectManager().enableVoiceEarMonitor(false);
        // 设置场景
//        setPushScene(mQualityType, mIsEnableAdjustBitrate);


        // 设置本地预览View
        mLivePusher.setRenderView(mPusherView);
        mLivePusher.startCamera(mFrontCamera);
        mLivePusher.startMicrophone();
//        if (!mFrontCamera) mLivePusher.getDeviceManager().switchCamera(mFrontCamera);
        // 发起推流
//        resultCode = mLivePusher.startPush(tRTMPURL.trim());
        mLivePusher.startCamera(true);
    }

    @Override
    public void startPush(String url) {
//启动推流
        int ret = mLivePusher.startPush(url.trim());
        isLiveStart = true;
        new GroupManagerImpl(this).joinGroup(roomId);
    }

    @Override
    public void startLive(CreateLiveBean bean) {
        mFans = bean.getFans();
        mCharm = bean.getCharm();
        mVisitors = bean.getVisitors();

        anchorNickName = bean.getAnchorNickName();
        portraitUrl = bean.getPortraitUrl();
        createDateTime = bean.getCreateDateTime();

        roomId = bean.getAvChatRoomId();
        mSellGoods = bean.getSellGoods();

        rl_pusher_guide.setVisibility(View.GONE);
        iv_timer.setVisibility(View.VISIBLE);
        rl_anchor_interface.setVisibility(View.VISIBLE);

        tv_anchor_name.setText(anchorNickName);
        tv_anchor_viewer_num.setText(bean.getVisitors());
        tv_anchor_fans.setText("粉丝数：" + bean.getFans());
        tv_anchor_charm.setText("魅力值 " + bean.getCharm());

        Glide.with(this).load(portraitUrl).into(circle_anchor_head);

        String liveStandardContent = bean.getLiveStandardContent();

        if (mSellGoods.equals("1")) {
            iv_shopping_cart.setVisibility(View.GONE);
        } else {
            iv_shopping_cart.setVisibility(View.VISIBLE);
        }

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
                        String anchor = intent.getStringExtra("Anchor");
                        Message msg = new Message();
                        msg.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("code", code);
                        bundle.putString("data", data);
                        bundle.putString("anchor", anchor);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);

//                        Log.e("network", "===im==onReceiveRESTCustomData====" + code);
//                        Log.e("network", "===im==onReceiveRESTCustomData==getData==" + data);
//                        Log.e("network", "===im==onReceiveRESTCustomData==Anchor==" + anchor);

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

    @Override
    public void setCoverImg(String thumbUrl) {
        tv_change_cover.setVisibility(View.VISIBLE);
        Glide.with(this).load(thumbUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(iv_live_video_cover);
    }

    @Override
    public void changeTimer(int time) {
        if (time == 3) {
            iv_timer.setImageDrawable(getResources().getDrawable(R.mipmap.icon_three));
        } else if (time == 2) {
            iv_timer.setImageDrawable(getResources().getDrawable(R.mipmap.icon_two));
        } else if (time == 1) {
            iv_timer.setImageDrawable(getResources().getDrawable(R.mipmap.icon_one));
        } else {
            iv_timer.setVisibility(View.GONE);
        }
    }

    @Override
    public void setExplainCard(ExplainGoodsBean bean) {
        ll_explain_card.setVisibility(View.VISIBLE);
        tv_explain_card_name.setText(bean.getGoodsName());
        tv_explain_card_price.setText("￥" + bean.getGoodsMoney() / 100);
        Glide.with(this).load(bean.getGoodsPicture()).into(iv_explain_card);
        Glide.with(this).load(R.drawable.icon_shop_recommend_live_diverge_view_second4).into(live_introducing_iv);
        ll_explain_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //商品SpuID  直播  商品批次  直播ID号
                RouterHelper.toShopProductDetails(bean.getShoppingId(),
                        TagParameteBean.liveBringGoods, bean.getLot(), roomId, true);
            }
        });
    }

    @Override
    public void setLiveTitleNum(int textNum) {
        tv_live_title_num.setText(textNum + "/15");
    }

    private void switchCamera() {
        mFrontCamera = !mFrontCamera;
        mLivePusher.getDeviceManager().switchCamera(mFrontCamera);
    }

    private void turnOnFlashLight() {
        mFlashLigh = !mFlashLigh;
        mLivePusher.getDeviceManager().enableCameraTorch(mFlashLigh);
    }

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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String name0 = msg.getData().getString("name");
                    String headurl = msg.getData().getString("headurl");
                    if (!anchorNickName.equals(name0) && !TextUtils.isEmpty(name0)) {
                        addMessage(name0, "", 2, headurl);
                    }
                    break;
                case 1:
                    String name = msg.getData().getString("name");
                    String message = msg.getData().getString("message");
                    addMessage(name, message, 3, "");
                    break;
                case 2:
                    String code = msg.getData().getString("code");
                    String data = msg.getData().getString("data");
                    String anchor = msg.getData().getString("anchor");
                    if (code.equals("0")) { //0：魅力值排序变更
//                        List<String> list = JSON.parseArray(data, String.class);
                        String[] datas = data.split(",");
                        List<String> list = new ArrayList();
                        for (int i = 0; i < datas.length; i++) {
                            list.add(datas[i]);
                        }
                        setViewerHead(list);
                    } else if (code.equals("4")) { //4：管理员关闭直播间（禁播）
                        StatusShipButtonPop pop = new StatusShipButtonPop(PushMainActivity.this, getResources().getDrawable(com.yuanbaogo.video.R.mipmap.auth_error), anchor, "");
                        pop.setOnItemClickListener(new StatusShipButtonPop.OnItemClickListener() {
                            @Override
                            public void onClickBack() {
                                destroy();
                            }
                        });
                        pop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                    } else if (code.equals("1")) { //讲解商品
                    } else if (code.equals("2")) { //2：主播关闭直播间（直播结束）
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

//                    Log.e("yf","-------fansNew-----------"+fansNew);
//                    Log.e("yf","-------commissionAmount-----------"+commissionAmount);
//                    Log.e("yf","-------totalAmount-----------"+totalAmount);
//                    Log.e("yf","-------visitorsSum-----------"+visitorsSum);
//                    Log.e("yf","-------orderCount-----------"+orderCount);
//                    Log.e("yf","-------fans-----------"+fans);
//                    Log.e("yf","-------mFans-----------"+mFans);
//                    Log.e("yf","-------visitors-----------"+visitors);
//                    Log.e("yf","-------charm-----------"+charm);

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
            }
        }
    };


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

        if (type == 1) { //直播说明
            ll_live_viewer.setVisibility(View.GONE);
            ll_live_message.setVisibility(View.VISIBLE);
            tv_live_message.setText(message);
            tv_live_message.setTextColor(getResources().getColor(R.color.colorFFE082));
        } else if (type == 2) {//进入了直播间
            ll_live_viewer.setVisibility(View.VISIBLE);
            ll_live_message.setVisibility(View.GONE);
            Glide.with(this).load(headurl)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(circle_iamge_head);
            tv_live_viewer.setText(name + " 进入了直播间");
        } else if (type == 3) {//消息
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

    public void destroy() {
        if (mSellGoods.equals("1")) {
            EndingStoryActivity.toEndingStory(this, 1, portraitUrl, anchorNickName, mCharm, visitorsSum, fansNew, orderCount, totalAmount, commissionAmount, createDateTime);
        } else {
            EndingStoryActivity.toEndingStory(this, 2, portraitUrl, anchorNickName, mCharm, visitorsSum, fansNew, orderCount, totalAmount, commissionAmount, createDateTime);
        }
        unregisterReceiver(mBroadcastReceiver);
        if (listener != null) {
            V2TIMManager.getInstance().removeSimpleMsgListener(listener);
        }
        GroupManagerImpl.quitGroup(roomId);
        stopPush();
        mPusherView.onDestroy();
        unInitPhoneListener();
        mHandler = null;
        screenListener.unregisterListener();
        finish();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (isLiveStart) {
                mPresenter.closeLiving(roomId, "0");
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 请求相机
     */
    public static final int REQUEST_CODE_GOODLIST = 0X003;
    private List<String> mPhotoSelectTypes;
    private SelectPicPopupWindow mPhotoSelectPopupWindow;

    private void selectPhoto() {
        mPhotoSelectTypes = new ArrayList<>();
        mPhotoSelectTypes.add("拍照");
        mPhotoSelectTypes.add("相册选择");
        mPhotoSelectPopupWindow = new SelectPicPopupWindow(this, "",
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mPhotoSelectPopupWindow.dismiss();
                        if (position == 0) {  //拍照
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            int reqcode = REQUEST_CODE_GETIMAGE_BYCAMERA;
//                            if (intent.resolveActivity(PushMainActivity.this.getPackageManager()) != null) {
//                                photoFile = FileUtils.createImageFile();
//                                photoFileAbsolutePath = photoFile.getAbsolutePath();
//                                if (photoFile != null) {
//                                    Uri uri = null;
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                        uri = FileProvider.getUriForFile(PushMainActivity.this, "com.yuanbaogo.video.fileprovider", photoFile);
//                                    } else {
//                                        uri = Uri.fromFile(photoFile);
//                                    }
//                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                                }
//                            }
//                            startActivityForResult(intent, reqcode);

                            PictureSelector.create(PushMainActivity.this)
                                    .openCamera(PictureMimeType.ofImage())
                                    .isCompress(true)
                                    .isEnableCrop(true)
                                    .withAspectRatio(1, 1)
                                    .showCropFrame(true)
                                    .showCropGrid(true)
                                    .minimumCompressSize(100)
                                    .loadImageEngine(GlideEngine.createGlideEngine())
                                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                                        @Override
                                        public void onResult(List<LocalMedia> result) {
                                            String path = result.get(0).getCompressPath();
                                            String mimeType = result.get(0).getMimeType();
                                            if (!TextUtils.isEmpty(path)) {
                                                File file = new File(path);
                                                if (file.exists()) {
                                                    mPresenter.uploadPic(path, file, mimeType);
                                                } else {
                                                    ToastUtil.showToast("图片获取失败");
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancel() {
                                        }
                                    });


                        } else if (position == 1) {   //相册
                            PictureSelector.create(PushMainActivity.this).openGallery(PictureMimeType.ofImage())
                                    .maxSelectNum(1) // 最大选择个数
                                    .isCamera(false)
                                    .isCompress(true)
                                    .isEnableCrop(true)
                                    .withAspectRatio(1, 1)
                                    .showCropFrame(true)
                                    .showCropGrid(true)
                                    .minimumCompressSize(100)
                                    .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                                        @Override
                                        public void onResult(List<LocalMedia> result) {
                                            String path = result.get(0).getCompressPath();
                                            String mimeType = result.get(0).getMimeType();
                                            if (!TextUtils.isEmpty(path)) {
                                                File file = new File(path);
                                                if (file.exists()) {
                                                    mPresenter.uploadPic(path, file, mimeType);
                                                } else {
                                                    ToastUtil.showToast("图片获取失败");
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancel() {

                                        }
                                    });
                        }
                    }
                }, mPhotoSelectTypes);
        mPhotoSelectPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_GOODLIST) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    List<String> list = bundle.getStringArrayList("checkgoods");
                    mPresenter.setGoodsList(list);
                }

            }
        }
    }

    private void pause() {

        if (mPusherView != null) {
            mPusherView.onPause();
        }
        mLivePusher.startVirtualCamera(decodeResource(getResources(), R.drawable.livepusher_pause_publish));
        mLivePusher.pauseAudio();
        mIsResume = false;

    }

    /**
     * 获取资源图片
     *
     * @param resources
     * @param id
     * @return
     */
    private Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }

    private void stopPush() {
        // 停止本地预览
        mLivePusher.stopCamera();
        // 移除监听
        mLivePusher.setObserver(null);
        // 停止推流
        mLivePusher.stopPush();
        // 隐藏本地预览的View
        mPusherView.setVisibility(View.GONE);
    }

    private TXPhoneStateListener mPhoneListener;

    private class TXPhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:   //电话等待接听
                case TelephonyManager.CALL_STATE_OFFHOOK:   //电话接听
                    pause();
                    break;
                case TelephonyManager.CALL_STATE_IDLE:      //电话挂机
                    resume();
                    break;
            }
        }
    }

    /**
     * 初始化电话监听、系统是否打开旋转监听
     */
    private void initListener() {
        mPhoneListener = new TXPhoneStateListener();
        TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * 销毁
     */
    private void unInitPhoneListener() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneListener, PhoneStateListener.LISTEN_NONE);
    }

    private void resume() {
        if (mIsResume) {
            return;
        }
        if (mPusherView != null) {
            mPusherView.onResume();
        }
        mLivePusher.stopVirtualCamera();
//        if (mIsMuteAudio) {// audio这里要结合外部设定的 MuteAudio 和 PausePusher 来决定是否静音上行。
//            mLivePusher.pauseAudio();
//        } else {
//            mLivePusher.resumeAudio();
//        }
        mLivePusher.resumeVideo();
        mIsResume = true;
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
                                addMessage(anchorNickName, text, 3, "");
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