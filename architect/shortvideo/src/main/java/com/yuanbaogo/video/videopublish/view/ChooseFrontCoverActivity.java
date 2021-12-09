package com.yuanbaogo.video.videopublish.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.basic.ITitleBarLayout;
import com.tencent.qcloud.ugckit.component.TitleBarLayout;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoEditer;
import com.tencent.ugc.TXVideoInfoReader;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.libbase.baseutil.ImageUtils;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.videopublish.adapter.CoverImageAdapter;
import com.yuanbaogo.video.videopublish.contract.ChooseCoverContract;
import com.yuanbaogo.video.videopublish.presenter.ChooseCoverPresenter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseFrontCoverActivity extends MvpBaseActivityImpl<ChooseCoverContract.View, ChooseCoverPresenter>
        implements ChooseCoverContract.View, OnCallRecyclerItem {

    //15张帧图片
    private final Integer AVERAGE_COUNT = 15;
    private TitleBarLayout mTitleBarLayout;
    private RecyclerView mRlCover;
    private String mInVideoPath;//视频地址
    private String mCoverPath;//缩略图地址
    private List<Bitmap> mCoverBitmaps;
    private CoverImageAdapter mCoverImageAdapter;
    private ImageView mIvCorver;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        setTheme(R.style.EditerActivityTheme);
        return R.layout.activity_choose_front_cover;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mTitleBarLayout = findViewById(R.id.titleBar_layout);
        mRlCover = findViewById(R.id.rv_choose_cover);
        mIvCorver = findViewById(R.id.iv_corver);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mTitleBarLayout.setOnBackClickListener(v -> {
            finish();
        });
        mTitleBarLayout.setOnRightClickListener(v -> {
            mTitleBarLayout.setEnabled(false);
            new Thread(() -> {
                //保存图片到本地
                ImageUtils.save(mCoverBitmaps.get(mCoverImageAdapter.getChioceposition()), mCoverPath, Bitmap.CompressFormat.PNG);
                runOnUiThread(() -> {
                    if (isActive()) {
                        {
                            Intent intent = new Intent(ChooseFrontCoverActivity.this, VideoPublishActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(UGCKitConstants.VIDEO_COVERPATH, mCoverPath);
                            intent.putExtras(bundle);
                            setResult(200, intent);
                            finish();
                        }
                    }
                });
            }).start();
        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mInVideoPath = getIntent().getStringExtra(UGCKitConstants.VIDEO_PATH);
        mCoverPath = getIntent().getStringExtra(UGCKitConstants.VIDEO_COVERPATH);
        mTitleBarLayout.setTitle(getString(R.string.video_chioce_cover), ITitleBarLayout.POSITION.MIDDLE);
        mTitleBarLayout.setLeftIcon(R.mipmap.icon_back);
        mTitleBarLayout.getMiddleTitle().setTextColor(getColor(R.color.black));
        mTitleBarLayout.setTitle(getString(R.string.new_phone_btn), ITitleBarLayout.POSITION.RIGHT);
        mCoverBitmaps = new ArrayList<>();
        if (!mInVideoPath.isEmpty()) {
            TXVideoEditConstants.TXVideoInfo info = TXVideoInfoReader.getInstance().getVideoFileInfo(mInVideoPath);
            TXVideoEditer txVideoEditer = new TXVideoEditer(ChooseFrontCoverActivity.this);
            txVideoEditer.setVideoPath(mInVideoPath);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mIvCorver.getLayoutParams();

            if (info.width > info.height){
                lp.leftMargin = 0;
                lp.rightMargin = 0;
                lp.bottomMargin = 615;
                lp.topMargin = 546;
                mIvCorver.setLayoutParams(lp);
            }else {
                lp.leftMargin = 180;
                lp.rightMargin = 180;
                lp.bottomMargin = 255;
                lp.topMargin = 180;
                mIvCorver.setLayoutParams(lp);
            }

            //获取15帧图片
            txVideoEditer.getThumbnail(AVERAGE_COUNT, info.width, info.height, true, new TXVideoEditer.TXThumbnailListener() {
                @Override
                public void onThumbnail(int index, long l, Bitmap bitmap) {
                    if (bitmap != null) {
                        if (index == 0) {//默认显示第一张缩略图
                            runOnUiThread(() -> {
                                if (isActive()) {
                                    {
                                        mIvCorver.setImageBitmap(bitmap);
                                    }
                                }
                            });
                        }
                        mCoverBitmaps.add(bitmap);
                        if (index == AVERAGE_COUNT - 1) {
                            //缩略图获取完成  加载列表适配器
                            runOnUiThread(() -> {
                                if (isActive()) {
                                    initAdapter();
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private void initAdapter() {
        mCoverImageAdapter = new CoverImageAdapter(this, mCoverBitmaps, R.layout.adapter_cover_image);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRlCover.setLayoutManager(layoutManager);
        mCoverImageAdapter.setCallRecyclerItem(this);
        mRlCover.setAdapter(mCoverImageAdapter);

    }

    //recycle点击回调 更换大图
    @Override
    public void onCallItem(View view, int postion) {
        if (mCoverBitmaps != null && mCoverBitmaps.size() > postion) {
            mIvCorver.setImageBitmap(mCoverBitmaps.get(postion));
        }
    }

}