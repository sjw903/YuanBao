package com.yuanbaogo.shop.joingroup.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.utils.DateUtils;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libshare.ShareUtil;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.libshare.model.ShareParamete;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.joingroup.model.ShopInfoBean;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/1/21 6:19 PM
 * @Modifier:
 * @Modify:
 */
public class LuckyFightShareFragment extends DialogsFragment
        implements View.OnClickListener, ShareUtil.OnCallShare {

    View mView;

    RelativeLayout dialogShareViewRlShareSave;

    TextView dialogShareViewTvCancel;

    TextView itemShareViewTvWechat;

    TextView itemShareViewTvWechatCircle;

    ShareBean shareBean;

    ShopInfoBean shopInfoBean;

    TextView dialogLuckyFightShareViewTvTime;

    ImageView dialogLuckyFightShareViewImgLogo;

    TextView dialogLuckyFightShareViewTvTitle;

    TextView dialogLuckyFightShareViewTvPrice;

    ImageView dialogShareViewImgShareList;

    RelativeLayout dialogLuckyFightShareViewRlInfo;

    RelativeLayout dialogShareViewRlTime;

    RelativeLayout dialogShareViewRlShare;

    LinearLayout dialogShareViewLlShare;

    ImageView dialogLuckyFightShareViewImgQR;

    RelativeLayout dialogShareViewRlShareSaveTop;

    RelativeLayout dialogLuckyFightShareViewRlQR;

    TextView dialogLuckyFightShareViewTvQR;

    /**
     * 1 列表分享   2 商品详情分享
     */
    int type = 0;

    public LuckyFightShareFragment(ShareBean shareBean, ShopInfoBean shopInfoBean) {
        this.shareBean = shareBean;
        this.shopInfoBean = shopInfoBean;
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_lucky_fight_share_view;
    }

    @Override
    protected void intViews(View view) {
        this.mView = view;
        dialogShareViewRlShareSave = view.findViewById(R.id.dialog_share_view_rl_share_save);
        dialogShareViewTvCancel = mView.findViewById(R.id.dialog_share_view_tv_cancel);
        itemShareViewTvWechat = mView.findViewById(R.id.item_share_view_tv_wechat);
        itemShareViewTvWechatCircle = mView.findViewById(R.id.item_share_view_tv_wechat_circle);
        dialogLuckyFightShareViewTvTime = mView.findViewById(R.id.dialog_lucky_fight_share_view_tv_time);
        dialogLuckyFightShareViewImgLogo = mView.findViewById(R.id.dialog_lucky_fight_share_view_img_logo);
        dialogLuckyFightShareViewTvTitle = mView.findViewById(R.id.dialog_lucky_fight_share_view_tv_title);
        dialogLuckyFightShareViewTvPrice = mView.findViewById(R.id.dialog_lucky_fight_share_view_tv_price);
        dialogShareViewImgShareList = mView.findViewById(R.id.dialog_share_view_img_share_list);
        dialogLuckyFightShareViewRlInfo = mView.findViewById(R.id.dialog_lucky_fight_share_view_rl_info);
        dialogShareViewRlTime = mView.findViewById(R.id.dialog_share_view_rl_time);
        dialogShareViewRlShare = mView.findViewById(R.id.dialog_share_view_rl_share);
        dialogShareViewLlShare = mView.findViewById(R.id.dialog_share_view_ll_share);
        dialogLuckyFightShareViewImgQR = mView.findViewById(R.id.dialog_lucky_fight_share_view_img_qr);
        dialogShareViewRlShareSaveTop = mView.findViewById(R.id.dialog_share_view_rl_share_save_top);
        dialogLuckyFightShareViewRlQR = mView.findViewById(R.id.dialog_lucky_fight_share_view_rl_qr);
        dialogLuckyFightShareViewTvQR = mView.findViewById(R.id.dialog_lucky_fight_share_view_tv_qr);
        startUpAnimation(mView);
    }

    @Override
    protected void setTexts() {
        //获取屏幕宽高
        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：1080px）
        int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高（像素，如：2292px）

        //获取底部分享宽高
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        dialogShareViewRlShare.measure(w, h);
        int bottomWidth = dialogShareViewRlShare.getMeasuredWidth();
        int bottomHeight = dialogShareViewRlShare.getMeasuredHeight();

        /*
         *设置商品详情高度 宽度
         */
        ViewGroup.MarginLayoutParams topParams = (ViewGroup.MarginLayoutParams)
                dialogShareViewRlShareSaveTop.getLayoutParams();
        topParams.width = screenWidth;
        topParams.height = screenHeight - bottomHeight;
        topParams.topMargin = 100;
        topParams.bottomMargin = 100;
        topParams.leftMargin = 80;
        topParams.rightMargin = 80;
        dialogShareViewRlShareSaveTop.setLayoutParams(topParams);

        //获取底部分享图宽高
        int w2 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h2 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        dialogShareViewRlShareSaveTop.measure(w2, h2);
        int bottomWidth2 = dialogShareViewRlShareSaveTop.getMeasuredWidth();
        int bottomHeight2 = dialogShareViewRlShareSaveTop.getMeasuredHeight();

        //高度1:2的比例
        int heightScale = bottomHeight2 / 2;

        /*
         * 动态设置分享图展示位置
         * 适配高度  宽度用高度的比例进行设置
         */
        ViewGroup.MarginLayoutParams infoTopParams = (ViewGroup.MarginLayoutParams)
                dialogShareViewRlShareSave.getLayoutParams();
        if (screenHeight <= 1920) {
            infoTopParams.width = bottomHeight2 / 3;//1:3
        } else {
            infoTopParams.width = heightScale;
        }
        infoTopParams.height = bottomHeight2;
        dialogShareViewRlShareSave.setLayoutParams(infoTopParams);

        /*
         * 动态设置商品详情展示位置
         */
        ViewGroup.MarginLayoutParams infoParams = (ViewGroup.MarginLayoutParams)
                dialogLuckyFightShareViewRlInfo.getLayoutParams();
        infoParams.width = heightScale - 40;
        dialogLuckyFightShareViewRlInfo.setLayoutParams(infoParams);

        /*
         * 动态设置商品详情LOGO展示位置
         */
        ViewGroup.MarginLayoutParams logoParams = (ViewGroup.MarginLayoutParams)
                dialogLuckyFightShareViewImgLogo.getLayoutParams();
        if (screenHeight <= 1920) {
            logoParams.width = heightScale / 4 - 100;
            logoParams.height = heightScale / 4 - 100;
        } else {
            logoParams.width = heightScale / 2 - 100;
            logoParams.height = heightScale / 2 - 100;
        }
        dialogLuckyFightShareViewImgLogo.setLayoutParams(logoParams);

        if (screenHeight <= 1920) {
            //宽度1:8的比例
            int widthScale = bottomHeight2 / 10;

            /*
             * 动态设置抽奖时间位置
             */
            ViewGroup.MarginLayoutParams timeParams = (ViewGroup.MarginLayoutParams)
                    dialogShareViewRlTime.getLayoutParams();
            timeParams.width = widthScale;
            timeParams.height = widthScale / 2;
            dialogShareViewRlTime.setLayoutParams(timeParams);
            dialogLuckyFightShareViewTvTime.setTextSize(getActivity().getResources().getDimension(R.dimen.margin_3));

            /*
             * 动态设置二维码整体位置
             */
            ViewGroup.MarginLayoutParams qrParams = (ViewGroup.MarginLayoutParams)
                    dialogLuckyFightShareViewRlQR.getLayoutParams();
            qrParams.width = widthScale + 40;
            qrParams.height = widthScale + 40;
            dialogLuckyFightShareViewRlQR.setLayoutParams(qrParams);

            /*
             * 动态设置二维码位置
             */
            ViewGroup.MarginLayoutParams qrImgParams = (ViewGroup.MarginLayoutParams)
                    dialogLuckyFightShareViewImgQR.getLayoutParams();
            qrImgParams.width = widthScale;
            qrImgParams.height = widthScale;
            dialogLuckyFightShareViewImgQR.setLayoutParams(qrImgParams);
            dialogLuckyFightShareViewTvQR.setTextSize(getActivity().getResources().getDimension(R.dimen.margin_3));
        }

        /*
         * 动态设置列表分享展示位置
         */
        ViewGroup.MarginLayoutParams iconParams = (ViewGroup.MarginLayoutParams)
                dialogShareViewImgShareList.getLayoutParams();
        if (screenHeight <= 1920) {
            iconParams.width = bottomHeight2 / 3;
            iconParams.height = bottomHeight2 / 3;
        } else {
            iconParams.width = heightScale - 220;
            iconParams.height = heightScale - 220;
        }
        dialogShareViewImgShareList.setLayoutParams(iconParams);

        if (shopInfoBean.getCoverImages() != null
                && shopInfoBean.getGoodsName() != null
                && shopInfoBean.getGroupGoodsPrice() != null
                && shopInfoBean.getLotteryTime() != null) {
            type = 2;
            dialogLuckyFightShareViewRlInfo.setVisibility(View.VISIBLE);
            dialogShareViewRlTime.setVisibility(View.VISIBLE);
            dialogLuckyFightShareViewTvTime.setText("抽奖时间：\n" + DateUtils.getDateDayHourMinute(shopInfoBean.getLotteryTime()) + "");
            Glide.with(getActivity()).load(shopInfoBean.getCoverImages()).into(dialogLuckyFightShareViewImgLogo);
            dialogLuckyFightShareViewTvTitle.setText(shopInfoBean.getGoodsName());
            dialogLuckyFightShareViewTvPrice.setText("¥" + shopInfoBean.getGroupGoodsPrice() / 100);
            dialogShareViewImgShareList.setVisibility(View.GONE);
        } else {
            type = 1;
            dialogShareViewImgShareList.setVisibility(View.VISIBLE);
            dialogShareViewImgShareList.setBackground(getResources().getDrawable(shopInfoBean.getImagesList()));
            dialogLuckyFightShareViewRlInfo.setVisibility(View.GONE);
            dialogShareViewRlTime.setVisibility(View.GONE);
        }

        if (type == 1) {
            getWXcode(ShareParamete.WEIXIN_GOODS_LIST_PATH
                            .substring(0, ShareParamete.WEIXIN_GOODS_LIST_PATH.indexOf("?")),
                    "i=" + UserStore.getInviteCode());
        } else if (type == 2) {
            getWXcode(ShareParamete.WEIXIN_GOODS_PATH
                            .substring(0, ShareParamete.WEIXIN_GOODS_PATH.indexOf("?")),
                    "i=" + UserStore.getInviteCode() + "&k=" + shopInfoBean.getId());
        }

    }

    @Override
    protected void setOnClicks() {
        dialogShareViewTvCancel.setOnClickListener(this);
        itemShareViewTvWechat.setOnClickListener(this);
        itemShareViewTvWechatCircle.setOnClickListener(this);
    }

    @Override
    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_share_view_tv_cancel) {
            startDownAnimation(mView);
        } else if (id == R.id.item_share_view_tv_wechat) {
            saveCode(dialogShareViewRlShareSave);
            ShareBean shareBean = new ShareBean();
            if (type == 1) {
                shareBean.setUmMinUrl(ShareParamete.WEIXIN_GOODS_LIST_PATH
                        .replace("{inviteCode}", UserStore.getInviteCode()))
                        .setUmMinPath(ShareParamete.WEIXIN_GOODS_LIST_PATH
                                .replace("{inviteCode}", UserStore.getInviteCode()))
                        .setUmMinUserName(ShareParamete.WEIXIN_APPLETS_ORIGINAL_ID)
                        .setUmImgMipmap(R.mipmap.icon_lucky_fight_list_share)
                        .setTitle("邀请好友一起赚钱");
            } else if (type == 2) {
                shareBean.setUmMinUrl(ShareParamete.WEIXIN_GOODS_PATH
                        .replace("{inviteCode}", UserStore.getInviteCode())
                        .replace("{id}", shopInfoBean.getId()))
                        .setUmMinPath(ShareParamete.WEIXIN_GOODS_PATH
                                .replace("{inviteCode}", UserStore.getInviteCode())
                                .replace("{id}", shopInfoBean.getId()))
                        .setUmMinUserName(ShareParamete.WEIXIN_APPLETS_ORIGINAL_ID)
                        .setUmImgUrl(shopInfoBean.getCoverImages())
                        .setTitle("邀请好友一起赚钱");
            }
            ShareUtil.getShareUtils()
                    .shareUtilss(getActivity())
                    .setOnCallShare(this)
                    .setUmMin(ShareUtil.getShareUtils().umMin(shareBean), ShareParamete.WEIXIN);
        } else if (id == R.id.item_share_view_tv_wechat_circle) {
            saveCode(dialogShareViewRlShareSave);
            ShareUtil
                    .getShareUtils()
                    .shareUtils(getActivity())
                    .setOnCallShare(this)
                    .setUMImage(ShareUtil.getShareUtils().umImage(shareBean), ShareParamete.WEIXIN_CIRCLE);
        }
    }

    /**
     * 二维码保存本地
     */
    public void saveCode(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        //view的内容以图片的方式保存下来
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        shareBean.setUmImgBitmap(bitmap);
    }

    @Override
    public void onStartShares() {
    }

    @Override
    public void onResultShares() {

    }

    @Override
    public void onErrorShares() {
    }

    @Override
    public void onCancelShares() {

    }

    Bitmap bitmap;

    /**
     * 获取小程序邀请二维码
     *
     * @param page  扫码进入的小程序页面路径
     * @param scene 链接参数，32个可见字符,示例值(a=1)
     */
    public void getWXcode(String page, String scene) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);//TODO HG page=小程序路径  需要上线后修改
        params.put("scene", scene);
        NetWork.getInstance()
                .post(getContext(),
                        ChildUrl.GET_WXCODE,
                        params,
                        new RequestSateListener<String>() {
                            @Override
                            public void onSuccess(int code, String bean) {
                                if (code == 200 && getView() != null) {
                                    byte[] decode = Base64.decode(bean.split(",")[1], Base64.DEFAULT);
                                    bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                                    dialogLuckyFightShareViewImgQR.setImageBitmap(bitmap);
                                }
                            }

                            @Override
                            public void onFailure(Throwable var1) {
                                if (getView() != null) {

                                }
                            }
                        });
    }

}
