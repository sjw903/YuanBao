package com.yuanbaogo.homevideo.live.pull.presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.remote.model.UpLoadFileBean;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.homevideo.bringgoods.view.ViewerGoodsListPop;
import com.yuanbaogo.homevideo.live.pull.contract.PullMainContract;
import com.yuanbaogo.homevideo.live.pull.model.EnterLiveBean;
import com.yuanbaogo.homevideo.live.pull.model.FollowBean;
import com.yuanbaogo.homevideo.live.pull.model.FollowStateBean;
import com.yuanbaogo.homevideo.live.push.model.CartGoodsListBean;
import com.yuanbaogo.homevideo.live.push.model.CoverImgBean;
import com.yuanbaogo.homevideo.shortvideo.report.model.ReportRequestModel;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.zui.dialog.SortDialogView;
import com.yuanbaogo.zui.dialog.model.SkuBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PullMainPresenter extends MvpBasePersenter<PullMainContract.View> implements PullMainContract.Presenter {

    String roomId;
    String followerYbCode;
    private ViewerGoodsListPop mPopupWindow;

    @Override
    public void getSku(String spuId, String mLot, long price, String url) {
        Map<String, Object> params = new HashMap<>();
        NetWork.getInstance().get(getContext(),
                ChildUrl.SKU.replace("{spuId}", spuId),
                params,
                new RequestSateListener<SkuBean>() {
                    @Override
                    public void onSuccess(int code, SkuBean skuBean) {
                        if (code == 200 && skuBean != null) {
                            skuBean.setPrice(price)//价格
                                    .setImgUrl(url)//图片
                                    .setStock(0);//库存
                            //直播    skubean  商品批次   直播ID
                            SortDialogView sortDialogView = new SortDialogView(TagParameteBean.liveBringGoods, skuBean, mLot, roomId);
                            sortDialogView.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "shop_info");
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                    }
                });
    }

    public String getYbCode() {
        return followerYbCode;
    }

    public void toCartGoods() {
        if (mPopupWindow == null) {
            mPopupWindow = new ViewerGoodsListPop((Activity) getContext());
            mPopupWindow.setOnClickListener(new ViewerGoodsListPop.OnClickListener() {
                @Override
                public void onBuyClick(CartGoodsListBean bean) {
                    getSku(bean.getShoppingId(), bean.getProductBatch(), bean.getGoodsMoney(), bean.getGoodsPicture());
                }

                @Override
                public void onItemClick(CartGoodsListBean bean) {
                    //商品SpuID  直播  商品批次  直播ID号
                    RouterHelper.toShopProductDetails(bean.getShoppingId(),
                            TagParameteBean.liveBringGoods, bean.getLot(), roomId, true);
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
                ChildUrl.cartgoodslist.replace("{liveId}", roomId),
//                ChildUrl.cartgoodslist.replace("{liveId}", "3006120210825150656"),
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

    @Override
    protected void onIntent(Intent intent) {
        super.onIntent(intent);
        followerYbCode = intent.getStringExtra("followerYbCode");
    }

    @Override
    public void getInto(String avChatRoomId) {
        roomId = avChatRoomId;
        Map<String, Object> params = new HashMap<>();
        params.put("avChatRoomId", avChatRoomId);

        NetWork.getInstance().post(getContext(),
                ChildUrl.getinto,
                params,
                new RequestSateListener<EnterLiveBean>() {
                    @Override
                    public void onSuccess(int code, EnterLiveBean bean) {
//                        followerYbCode = bean.getYbCode();
                        if (isActive()) {
                            getView().setLive(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                    }
                });
    }

    @Override
    public void getOut(String avChatRoomId) {
        Map<String, Object> params = new HashMap<>();
        params.put("avChatRoomId", avChatRoomId);

        NetWork.getInstance().post(getContext(),
                ChildUrl.getout,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String str) {
                        if (isActive()) {
                            getView().close();
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (isActive()) {
                            getView().close();
                        }
                    }
                });
    }

    @Override
    public void addCharm(String avChatRoomId, int charmValue) {
        Map<String, Object> params = new HashMap<>();
        params.put("avChatRoomId", avChatRoomId);
        params.put("charmValue", charmValue);

        NetWork.getInstance().post(getContext(),
                ChildUrl.addCharm,
                params,
                new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int code, String str) {
                    }

                    @Override
                    public void onFailure(Throwable var1) {

                    }
                });
    }

    @Override
    public void uploadPic(List<LocalMedia> localMedias) {
        if (localMedias == null) {
            return;
        }
        List<UpLoadFileBean> fileList = new ArrayList();
        for (int i = 0; i < localMedias.size(); i++) {
            LocalMedia localMedia = localMedias.get(i);
            UpLoadFileBean bean = new UpLoadFileBean();
            String path = localMedia.isCompressed() ? localMedia.getCompressPath() : localMedia.getPath();
            File file = new File(path);
            bean.setFileName(file.getName());
            bean.setMediaType(localMedia.getMimeType());
            bean.setPath(path);
            bean.setFileKey("file");
            fileList.add(bean);
        }

        Map<String, String> params = new HashMap<>();
        params.put("type", "3");
        NetWork.getInstance().upLoadFile(getContext(), ChildUrl.upload, params, fileList, new RequestSateListener<CoverImgBean>() {
            @Override
            public void onSuccess(int code, CoverImgBean bean) {

                if (isActive())
                    getView().onUploadPicSuccess(bean);
            }

            @Override
            public void onFailure(Throwable e) {
                getView().onUploadPicFail();
            }
        });
    }

    @Override
    public void reportSubmit(ReportRequestModel requestModel) {
        Map<String, Object> params = new HashMap<>();
        params.put("authorId", requestModel.getAuthorId());
//        params.put("authorNickName", requestModel.getAuthorNickName());
        params.put("bizId", requestModel.getBizId());
        params.put("bizTitle", requestModel.getBizTitle());
        params.put("content", requestModel.getContent());
        params.put("screenshots", requestModel.getScreenshots());
//        params.put("description", requestModel.getDescription());
//        params.put("lookerId", requestModel.getLookerId());
        params.put("tagName", requestModel.getTagName());
        NetWork.getInstance().post(getContext(), ChildUrl.reportVodSubmit.replace("{type}", "vod")
                , params, new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int var1, String var2) {
                        if (isActive())
                            getView().onReportSubmitSuccess();
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (isActive())
                            getView().onReportSubmitFail();
                    }
                },false);
    }

    @Override
    public void follow(String businessId, String followerYbCode, int state) {
        Map<String, Object> params = new HashMap<>();
        params.put("businessId", businessId);
        params.put("followerYbCode", followerYbCode);
        params.put("state", state);
        params.put("type", "1");

        NetWork.getInstance().post(getContext(),
                ChildUrl.follow,
                params,
                new RequestSateListener<FollowBean>() {
                    @Override
                    public void onSuccess(int code, FollowBean bean) {
                        if (bean.getState().equals("0") || bean.getState().equals("2")) { //没有关注
                            getView().setFollowState(false);
                        } else if (bean.getState().equals("1") || bean.getState().equals("3")) {//已关注
                            if (isActive())
                                getView().setFollowState(true);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {

                    }
                });
    }

    @Override
    public void followState() {
        Map<String, Object> params = new HashMap<>();
        params.put("followerYbCode", followerYbCode);

        NetWork.getInstance().post(getContext(),
                ChildUrl.myFollowState,
                params,
                new RequestSateListener<FollowStateBean>() {
                    @Override
                    public void onSuccess(int code, FollowStateBean bean) {
                        if (bean.getState() == 0 || bean.getState() == 2) { //没有关注
                            follow(roomId, followerYbCode, 1);
                        } else if (bean.getState() == 1 || bean.getState() == 3) {//已关注
                            follow(roomId, followerYbCode, 2);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {

                    }
                });
    }

    private int count = 0;
    private long firstTime = 0;
    private Timer delayTimer;
    private TimerTask task;
    private long interval = 2000;

    @Override
    public void clickCharm() {
        long secondTime = System.currentTimeMillis();
        // 判断每次点击的事件间隔是否符合连击的有效范围
        // 不符合时，有可能是连击的开始，否则就仅仅是单击
        if (secondTime - firstTime <= interval) {
            ++count;
        } else {
            count = 1;
        }
        // 延迟，用于判断用户的点击操作是否结束
        delay();
        firstTime = secondTime;
    }

    // 延迟时间是连击的时间间隔有效范围
    private void delay() {
        if (task != null)
            task.cancel();

        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                // message.what = 0;
                handler.sendMessage(message);
            }
        };
        delayTimer = new Timer();
        delayTimer.schedule(task, interval);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            if (count == 1) {
//                tvCount.setText("单击事件");
//            } else if (count > 1) {
//                tvCount.setText("连续点击事件，共点击了 " + count + " 次");
//            }
            if (!TextUtils.isEmpty(roomId)) {
                addCharm(roomId, count);
            }
            delayTimer.cancel();
            count = 0;
            super.handleMessage(msg);
        }
    };

}
