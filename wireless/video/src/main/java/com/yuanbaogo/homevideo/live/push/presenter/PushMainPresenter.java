package com.yuanbaogo.homevideo.live.push.presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.datacenter.local.video.VideoSPUtils;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.remote.model.UpLoadFileBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.homevideo.bringgoods.view.AnchorGoodsListPop;
import com.yuanbaogo.homevideo.live.push.contract.PushMainContract;
import com.yuanbaogo.homevideo.live.push.model.CartGoodsListBean;
import com.yuanbaogo.homevideo.live.push.model.CoverImgBean;
import com.yuanbaogo.homevideo.live.push.model.CreateLiveBean;
import com.yuanbaogo.homevideo.live.push.model.ExplainGoodsBean;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.zui.dialog.CommonRemindPop;
import com.yuanbaogo.zui.toast.ToastView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushMainPresenter extends MvpBasePersenter<PushMainContract.View> implements PushMainContract.Presenter {

    private int mInviteTime = 0;//验证码间隔时间
    private int what_change = 1;
    private String coverImgName = "";
    private String liveId = "";
    private AnchorGoodsListPop mPopupWindow;
    List<String> mGoodsList = new ArrayList<>();

    /*
     * 直播间封面
     *//*
    LIVE_ROOM_COVER(1,"live_room_cover","live/room/cover/"),
    *//*
     * 短视频封面
     *//*
    VOD_COVER(2,"vod_cover","vod/cover"),
    *//*
     * 举报
     *//*
    REPORT_VOD(3,"report_vod","report/vod/"),
    *//*
     * 用户头像
     *//*
    USER_AVATAR(4,"user_avatar","user/avatar/"),
    *//*
     * 圈子
     *//*
    QUNZI(5,"qunzi","qunzi/");*/

    @Override
    public void uploadPic(String path, File file, String mimeType) {
        UpLoadFileBean bean = new UpLoadFileBean();
        List<UpLoadFileBean> fileList = new ArrayList();
        bean.setFileName(file.getName());
        bean.setMediaType(mimeType);
        bean.setPath(path);
        bean.setFileKey("file");
        fileList.add(bean);
        Map<String, String> params = new HashMap<>();
        params.put("type", "1");

        NetWork.getInstance().upLoadFile(getContext(), ChildUrl.upload, params, fileList, new RequestSateListener<CoverImgBean>() {
            @Override
            public void onSuccess(int code, CoverImgBean bean) {
                if (bean != null) {
                    coverImgName = bean.getFileName();
                    getView().setCoverImg(bean.getThumbUrl());
                    if (!TextUtils.isEmpty(bean.getThumbUrl())) {
                        VideoSPUtils.saveLiveCoverUrl(bean.getThumbUrl(), coverImgName);
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {
            }
        });
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (isActive()) {
                getView().setLiveTitleNum(s.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public TextWatcher getTextWatcher() {
        return mTextWatcher;
    }

    public void createLive(String title) {
//        if (TextUtils.isEmpty(UserStore.getToken())) {
//            RouterHelper.toLogin();
//            return;
//        }

        if (TextUtils.isEmpty(coverImgName)) {
            ToastView.showToast(getContext(), "请添加直播间封面");
            return;
        }

        if (TextUtils.isEmpty(title)) {
            ToastView.showToast(getContext(), "请填写直播间标题");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("goods", mGoodsList);
        params.put("livePictureId", coverImgName);
        params.put("title", title);

        NetWork.getInstance().post(getContext(), ChildUrl.createLive, params, new RequestSateListener<CreateLiveBean>() {
            @Override
            public void onSuccess(int code, CreateLiveBean bean) {

                if (isActive()) {
                    getView().startPush(bean.getPushUrl());
                }
                if (isActive()) {
                    getView().startLive(bean);
                }
                mInviteTime = 3;
                if (handler != null) {
                    handler.sendEmptyMessageDelayed(what_change, 1000);
                }
                liveId = bean.getAvChatRoomId();
            }

            @Override
            public void onFailure(Throwable e) {
//                ToastView.showToast(getContext(), "获取信息失败");
            }
        },true);
    }

    public LayoutTransition getTransition() {
        LayoutTransition transition = new LayoutTransition();
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
        ObjectAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(null, new PropertyValuesHolder[]{scaleX, scaleY})
                .setDuration(transition.getDuration(LayoutTransition.APPEARING));
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ObjectAnimator objectAnimator = (ObjectAnimator) animation;
                View view = (View) objectAnimator.getTarget();
                view.setPivotX(0f);
                view.setPivotY(view.getMeasuredHeight());
            }
        });
        transition.setAnimator(LayoutTransition.APPEARING, valueAnimator);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(null, "alpha", 0, 1).
                setDuration(LayoutTransition.DISAPPEARING);
        transition.setAnimator(LayoutTransition.DISAPPEARING, objectAnimator);
        return transition;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (mInviteTime > 0) {
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(what_change, 1000);
                    }
                }
                if (isActive()) {
                    getView().changeTimer(mInviteTime);
                }
                mInviteTime--;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacksAndMessages((Object) null);
    }

    public void setGoodsList(List<String> list) {
        mGoodsList = list;
    }

    public void toCartGoods() {
        if (mPopupWindow == null) {
            mPopupWindow = new AnchorGoodsListPop((Activity) getContext());
            mPopupWindow.setOutside(false);
            mPopupWindow.setOnClickListener(new AnchorGoodsListPop.OnClickListener() {
                @Override
                public void onExplainClick(int pos, String liveGoodsId) {
                    explainLiveGoods(null, liveGoodsId);
                }

                @Override
                public void onGoodsClick(CartGoodsListBean bean) {
                    //商品SpuID  直播  商品批次  直播ID号
                    RouterHelper.toShopProductDetails(bean.getShoppingId(),
                            TagParameteBean.liveBringGoods, bean.getLot(), liveId, true);
                }

                @Override
                public void onSubmitShoppingCartGoods(List<String> list) {
                    explainLiveGoods(list, "");
                }
            });
        }
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(((Activity) getContext()).getWindow().getDecorView(),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        getCartGoodsList();
    }

    //    在直播间内看购物车的所有商品
    private void getCartGoodsList() {
        NetWork.getInstance().get(getContext(),
                ChildUrl.cartgoodslist.replace("{liveId}", liveId),
                null,
                new RequestSateListener<List<CartGoodsListBean>>() {
                    @Override
                    public void onSuccess(int code, List<CartGoodsListBean> list) {
                        if (mPopupWindow != null) {
                            mPopupWindow.setData(list);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                    }
                });
    }

    //讲解直播间商品,对购物车商品的上移,下移,讲解,删除等操作
    private void explainLiveGoods(List<String> list, String liveGoodsId) {
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingId", liveGoodsId);
        params.put("livingRoomId", liveId);
        params.put("goodsIds", list);
//        params.put("livingRoomId", "3006120210825150656");

        NetWork.getInstance().post(getContext(),
                ChildUrl.updatelivegoodssort,
                params,
                new RequestSateListener<ExplainGoodsBean>() {
                    @Override
                    public void onSuccess(int code, ExplainGoodsBean bean) {
                        if (list == null) {
                            getCartGoodsList();//回显
                            getLiveGoodsCard(liveGoodsId);
//                        mPopupWindow.setExplainStaus(pos);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                    }
                });
    }

    //获取讲解直播间商品
    private void getLiveGoodsCard(String liveGoodsId) {
        NetWork.getInstance().get(getContext(),
                ChildUrl.explainlivegoods.replace("{livingRoomId}", liveId)
//                ChildUrl.explainlivegoods.replace("{livingRoomId}", "3006120210825150656")
                        .replace("{liveGoodsId}", liveGoodsId),
                null,
                new RequestSateListener<ExplainGoodsBean>() {
                    @Override
                    public void onSuccess(int code, ExplainGoodsBean bean) {
                        if (isActive()) {
                            getView().setExplainCard(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                    }
                });
    }

    CommonRemindPop pop;

    public void closeLiving(String avChatRoomId, String closeType) {

        pop = new CommonRemindPop(getContext(), "一大批观众正在赶来，确定要关闭直播？", "等等他们", "我要关闭",
                new CommonRemindPop.OnClickListener() {
                    @Override
                    public void onLeftClick() {
                        pop.dismiss();
                    }

                    @Override
                    public void onRightClick() {
                        pop.dismiss();
                        Map<String, Object> params = new HashMap<>();
                        params.put("avChatRoomId", avChatRoomId);
                        params.put("closeType", closeType);

                        NetWork.getInstance().post(getContext(),
                                ChildUrl.closeliving,
                                params,
                                new RequestSateListener<ExplainGoodsBean>() {
                                    @Override
                                    public void onSuccess(int code, ExplainGoodsBean bean) {
                                        if (isActive()) {
                                            getView().destroy();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Throwable var1) {
                                        if (isActive()) {
                                            getView().destroy();
                                        }
                                    }
                                });
                    }
                });
        pop.showAtLocation(((Activity) getContext()).getWindow().getDecorView(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    public void setCoverImgName(String coverImgName) {
        this.coverImgName = coverImgName;
    }
}
