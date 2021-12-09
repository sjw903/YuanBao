package com.yuanbaogo.video.videopublish.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.module.upload.TXUGCPublish;
import com.tencent.qcloud.ugckit.module.upload.TXUGCPublishTypeDef;
import com.tencent.qcloud.ugckit.utils.LogReport;
import com.tencent.qcloud.ugckit.utils.TCUserMgr;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.utils.fragment.ChangeParms;
import com.yuanbaogo.libbase.config.ApplicationConfigHelper;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.videopublish.contract.VideoPublishContract;
import com.yuanbaogo.video.videopublish.model.SignatureBean;
import com.yuanbaogo.video.videopublish.presenter.VideoPublishPresenter;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.progress.GradientRoundProgress;
import com.yuanbaogo.zui.toast.ToastView;

import java.io.File;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author lhx
 * @description: 发布视频
 * @date : 2021/7/9 10:25
 */
public class VideoPublishActivity extends MvpBaseActivityImpl<VideoPublishContract.View, VideoPublishPresenter>
        implements VideoPublishContract.View, View.OnClickListener, TXUGCPublishTypeDef.ITXVideoPublishListener {

    private ImageView iv_video_publish_gauss_cover, iv_video_publish_cover;
    private TextView tv_video_publish_choose_cover, tv_publish, tv_sell_goods, tv_count;
    private EditText et_video_content;
    private RelativeLayout mRlChooseCover;
    private RelativeLayout mRlProgressState;
    private HeadView ziHvVideoPublish;
    private GradientRoundProgress ziGrp;
    private String mVideoPath = null;//视频地址
    private String mCoverPath = null;//封面地址
    private String mCosSignature;//签名
    private boolean mIsUpLoadSuccess;//是否上传成功

    private LinearLayout ll_video_release;
    @Nullable
    private TXUGCPublish mVideoPublish = null;
    @NonNull
    private Handler mHandler = new Handler();

    public static final int REQUEST_CODE_GOODLIST = 0X00035;
    private List<String> goodsList;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_video_publish;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mVideoPath = getIntent().getStringExtra(UGCKitConstants.VIDEO_PATH);
        mCoverPath = getIntent().getStringExtra(UGCKitConstants.VIDEO_COVERPATH);
        iv_video_publish_gauss_cover = findViewById(R.id.iv_video_publish_gauss_cover);
        iv_video_publish_cover = findViewById(R.id.iv_video_publish_cover);
        tv_video_publish_choose_cover = findViewById(R.id.tv_video_publish_choose_cover);
        mRlChooseCover = findViewById(R.id.rl_choose_cover);
        mRlProgressState = findViewById(R.id.rl_progress_state);
        ziGrp = findViewById(R.id.ziGrp);
        tv_publish = findViewById(R.id.tv_publish);
        tv_sell_goods = findViewById(R.id.tv_sell_goods);
        tv_count = findViewById(R.id.tv_count);
        et_video_content = findViewById(R.id.et_video_content);
        ziHvVideoPublish = findViewById(R.id.zi_hv_video_publish_title);
        ll_video_release = findViewById(R.id.ll_video_release);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        tv_publish.setOnClickListener(this);
        tv_sell_goods.setOnClickListener(this);
        mRlChooseCover.setOnClickListener(this);
        ziHvVideoPublish.getLibHeadImgLeft().setOnClickListener(this);
        et_video_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_count.setText(s.length() + "/20");
            }
        });

        ll_video_release.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setImgRight(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("发布");
        ziHvVideoPublish.setHead(headBean);
        Uri coverPathUri = Uri.fromFile(new File(mCoverPath));
        setCover(coverPathUri);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lib_head_img_left) {
            finish();
        } else if (v.getId() == R.id.tv_sell_goods) {
            RouterHelper.toBringGoods(this, REQUEST_CODE_GOODLIST, false);
        } else if (v.getId() == R.id.tv_publish) {
            if (TextUtils.isEmpty(et_video_content.getText().toString())) {
                ToastView.showToast(this, R.mipmap.icon_video_upload_tip, "请填写视频标题");
                return;
            }
            mPresenter.getSignature();
            tv_publish.setEnabled(false);
        } else if (v.getId() == R.id.rl_choose_cover) {
            Intent intent = new Intent(this, ChooseFrontCoverActivity.class);
            intent.putExtra(UGCKitConstants.VIDEO_PATH, mVideoPath);
            intent.putExtra(UGCKitConstants.VIDEO_COVERPATH, mCoverPath);
            startActivityForResult(intent, 100);
        } else if (v.getId() == R.id.ll_video_release) {
            //点击除EditText之外的地方隐藏键盘
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 200) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    mCoverPath = bundle.getString(UGCKitConstants.VIDEO_COVERPATH);
                    Uri coverPathUri = Uri.fromFile(new File(mCoverPath));
                    setCover(coverPathUri);
                }
            }
        } else if (requestCode == REQUEST_CODE_GOODLIST) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                goodsList = bundle.getStringArrayList("checkgoods");
            }
        }
    }

    private void setCover(Uri coverPathUri) {
        Glide.with(this)
                .load(coverPathUri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)//解决加载同一地址为不同图片时总是显示第一次图片的问题
                .skipMemoryCache(true)
                .into(iv_video_publish_cover);
        Glide.with(this)
                .asBitmap()
                .load(coverPathUri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transform(new BlurTransformation(50, 1))
                .into(iv_video_publish_gauss_cover);
    }

    /**
     * Step2:开始发布视频（子线程）
     */
    private void startPublish() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mVideoPublish == null) {
                    mVideoPublish = new TXUGCPublish(ApplicationConfigHelper.mApplication, TCUserMgr.getInstance().getUserId());
                }
                /**
                 * 设置视频发布监听器
                 */
                mVideoPublish.setListener(VideoPublishActivity.this);

                TXUGCPublishTypeDef.TXPublishParam param = new TXUGCPublishTypeDef.TXPublishParam();
                param.signature = mCosSignature;
                param.videoPath = mVideoPath;
                param.coverPath = mCoverPath;
                int publishCode = mVideoPublish.publishVideo(param);
//                if (publishCode != 0) {
//                    mTVPublish.setText("发布失败，错误码：" + publishCode);
//                }
            }
        });
    }

    /**
     * 视频发布进度
     *
     * @param uploadBytes
     * @param totalBytes
     */
    @Override
    public void onPublishProgress(long uploadBytes, long totalBytes) {
        int progress = (int) (uploadBytes * 100 / totalBytes);
        mRlProgressState.setVisibility(View.VISIBLE);
        ziGrp.setProgress(progress);
    }

    /**
     * 视频发布结果回调<p/>
     * 当视频发布成功后，发布到点播系统，此时就可以在视频列表看到"已发布的视频"
     *
     * @param publishResult
     */
    @Override
    public void onPublishComplete(@NonNull TXUGCPublishTypeDef.TXPublishResult publishResult) {

        /**
         * ELK数据上报：视频发布到点播系统
         */
        LogReport.getInstance().reportPublishVideo(publishResult);

        if (publishResult.retCode == TXUGCPublishTypeDef.PUBLISH_RESULT_OK) {
            String goodid = "";
            if (goodsList != null && goodsList.size() > 0) {
                goodid = goodsList.get(0);
            }
            mPresenter.uploadVideo("", publishResult.coverURL, "", "",
                    et_video_content.getText().toString(), publishResult.videoId, publishResult.videoURL, goodid);
        }
    }

    @Override
    public void getSignatureSuccess(SignatureBean signature) {
        mCosSignature = signature.getSignature();
        startPublish();
    }

    @Override
    public void uploadVideoSuccess(String msg) {
        mIsUpLoadSuccess = true;
        ChangeParms.sChangeFragment.changge(1);
        RouterHelper.toMain();
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        File file = new File(mVideoPath);
        if (file.exists()) {
            file.delete();
        }
        if (!TextUtils.isEmpty(mCoverPath)) {
            file = new File(mCoverPath);
            if (file.exists()) {
                file.delete();
            }
        }
        if (mIsUpLoadSuccess) {
            ToastView.showToast(R.string.upload_success);
        }
    }


}
