package com.yuanbaogo.video.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yuanbaogo.commonlib.bean.RecommendVideoBean;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.TagParameteBean;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.video.R;
import com.yuanbaogo.zui.head.call.OnCallHeadBack;

public class VideoShoppingCardLayout extends RelativeLayout {

    private TextView tv_video_goods_describe;
    private TextView tv_video_goods_sku;
    private TextView tv_video_goods_price;
    private TextView tv_video_goods_buy;
    private TextView tv_video_goods_link;

    private Context mContext;
    private RelativeLayout rl_goods_card;
    private ImageView iv_video_close;
    private ImageView iv_video_goods;
    //    private boolean showGoodsCard = false;


    public void setOnCallHeadBack(OnCallHeadBack onCallHeadBack) {
//        this.onCallHeadBack = onCallHeadBack;
    }


    public VideoShoppingCardLayout(Context context) {
        super(context);
        initView(context);
    }

    public VideoShoppingCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VideoShoppingCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private int mInviteTime = 5;//验证码间隔时间
    private int what_change = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == what_change) {
                mInviteTime--;
                if (mInviteTime > 0) {
                    if (mHandler != null) {
                        mHandler.sendEmptyMessageDelayed(what_change, 1000);
                    }
                } else {
                    changeGoodsCard(true);
                }
            }
        }
    };

    private void changeGoodsCard(boolean showCard) {
        if (showCard) {
            rl_goods_card.setVisibility(View.VISIBLE);
            tv_video_goods_link.setVisibility(View.GONE);
        } else {
            rl_goods_card.setVisibility(View.GONE);
            tv_video_goods_link.setVisibility(View.VISIBLE);
        }
    }

    public void timedStart(RecommendVideoBean.RowsBean videoInfo) {
        if (videoInfo != null) {
            if (videoInfo.getGoodsInfoVO() != null && videoInfo.getGoodsInfoVO().size() > 0) {
                rl_goods_card.setVisibility(View.GONE);
                tv_video_goods_link.setVisibility(View.VISIBLE);
                mInviteTime = 5;
                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(what_change, 1000);
                }
            } else {
                tv_video_goods_link.setVisibility(View.GONE);
                rl_goods_card.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void initView(Context context) {

        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.viewgroup_video_shoppingcard, this);

        rl_goods_card = (RelativeLayout) view.findViewById(R.id.rl_goods_card);
        iv_video_close = (ImageView) view.findViewById(R.id.iv_video_close);
        iv_video_goods = (ImageView) view.findViewById(R.id.iv_video_goods);
        tv_video_goods_describe = (TextView) view.findViewById(R.id.tv_video_goods_describe);
        tv_video_goods_sku = (TextView) view.findViewById(R.id.tv_video_goods_sku);
        tv_video_goods_price = (TextView) view.findViewById(R.id.tv_video_goods_price);
        tv_video_goods_buy = (TextView) view.findViewById(R.id.tv_video_goods_buy);

        tv_video_goods_link = (TextView) view.findViewById(R.id.tv_video_goods_link);

        iv_video_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_goods_card.setVisibility(View.GONE);
            }
        });
    }


    public void setGoodsCardBean(RecommendVideoBean.RowsBean videoInfo) {

        if (videoInfo.getGoodsInfoVO() != null && videoInfo.getGoodsInfoVO().size() > 0) {
            RecommendVideoBean.RowsBean.GoodsInfoVOBean goodsInfo = videoInfo.getGoodsInfoVO().get(0);
            if (goodsInfo != null) {
                tv_video_goods_link.setText(videoInfo.getGoodsInfoVO().get(0).getGoodsName());
                //Fragment You cannot start a load for a destroyed activity 切换账号登录再回来 todo
                Glide.with(mContext)
                        .load(goodsInfo.getGoodsPicture())
                        .apply(new RequestOptions()
                                .transforms(new CenterCrop(), new RoundedCorners(15)
                                ))
                        .into(iv_video_goods);
                tv_video_goods_describe.setText(goodsInfo.getGoodsName());
                tv_video_goods_sku.setText(goodsInfo.getSpecificationName());
                tv_video_goods_price.setText(getResources().getString(R.string.money) + goodsInfo.getGoodsMoney() / 100);
                tv_video_goods_buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //防暴力点击
                        if (ClickUtils.isFastClick()) {
                            return;
                        }
                        if (TextUtils.isEmpty(UserStore.getToken())) {
                            RouterHelper.toLogin();
                            return;
                        } else {
//                                mPresenter.getSku(goodsInfo.getId(), goodsInfo.getLot(), goodsInfo.getGoodsMoney(), goodsInfo.getGoodsPicture(), videoInfo.getVideoId());
                            RouterHelper.toShopProductDetails(goodsInfo.getId(),
                                    TagParameteBean.videoBringGoods, goodsInfo.getLot(), videoInfo.getVideoId(),true);
                        }
                    }
                });
                rl_goods_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //防暴力点击
                        if (ClickUtils.isFastClick()) {
                            return;
                        }
                        RouterHelper.toShopProductDetails(goodsInfo.getId(),
                                TagParameteBean.videoBringGoods, goodsInfo.getLot(), videoInfo.getVideoId(),true);
                    }
                });
                tv_video_goods_link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //防暴力点击
                        if (ClickUtils.isFastClick()) {
                            return;
                        }
                        RouterHelper.toShopProductDetails(goodsInfo.getId(),
                                TagParameteBean.videoBringGoods, goodsInfo.getLot(), videoInfo.getVideoId(),true);
                    }
                });
            } else {
                tv_video_goods_link.setVisibility(View.GONE);
                rl_goods_card.setVisibility(View.GONE);
            }
        } else {
            tv_video_goods_link.setVisibility(View.GONE);
            rl_goods_card.setVisibility(View.GONE);
        }


    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        Log.e("yf", "======onAttachedToWindow====== " + s);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        Log.e("yf", "======onDetachedFromWindow====== " + s);
    }

    @Override
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
//        Log.e("yf", "======onWindowVisibilityChanged====== " + s + " *##* " + i);

    }


}
