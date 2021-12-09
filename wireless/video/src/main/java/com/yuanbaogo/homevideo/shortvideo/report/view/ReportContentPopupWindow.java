package com.yuanbaogo.homevideo.shortvideo.report.view;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.yuanbaogo.homevideo.shortvideo.report.model.ReportRequestModel;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.PopupWindowWithMask;
import com.yuanbaogo.zui.picture.GlideEngine;
import com.yuanbaogo.zui.picture.SelectPictureAdapter;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;


/**
 * 举报内容填写弹窗
 */
public class ReportContentPopupWindow extends PopupWindowWithMask implements SelectPictureAdapter.PictureResultCallback, SelectPictureAdapter.OnClickPreview {
    private static final String TAG = "ReportContentPopupWindow";
    private View mContentView;
    private static final int MAX_PICTURE = 4;//相片最大张数
    private TextView mTvReportType;
    private EditText mEtReport;
    private RecyclerView mRvReprotImage;
    private String mReportType;
    private List<LocalMedia> mLocalMediaList = new ArrayList<>();
    private SelectPictureAdapter mAdapter;
    private ImageView mIvClose;
    private SubmitCallback mSubmitCallback;
    private Button mBtReportSubmit;
    private ReportRequestModel reportRequestModel;
    private TextView mTvTextNum;
    private String reportTitle;
    private TextView report_title_tv;

    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.pop_bottom_report_content, null, false);
        return mContentView;
    }


    @Override
    protected int initHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int initWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }


    public ReportContentPopupWindow(Activity activity,String reportTitle, String reportType, SubmitCallback submitCallback) {
        super(activity, true);
        this.mReportType = reportType;
        this.reportTitle = reportTitle;
        this.mSubmitCallback = submitCallback;
        setAnimationStyle(R.style.pop_anim_bottom);
        initView();
        initData();

    }

    private void initData() {
        setFocusable(true);
        setOutsideTouchable(false);
        setContentView(mContentView);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //给PopupWindow的window窗口设置软键盘展示属性
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mTvReportType.setText(mReportType);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mAdapter = new SelectPictureAdapter(context, mLocalMediaList);
        mAdapter.setMaxPicture(MAX_PICTURE);
        mAdapter.setPictureResultCallback(this);
        mRvReprotImage.setLayoutManager(layoutManager);
        mAdapter.setOnClickPreview(this);
        mRvReprotImage.setAdapter(mAdapter);
    }

    private void initView() {
        report_title_tv = mContentView.findViewById(R.id.report_title_tv);
        mTvReportType = mContentView.findViewById(R.id.tv_report_type);
        mEtReport = mContentView.findViewById(R.id.et_report);
        mRvReprotImage = mContentView.findViewById(R.id.rv_reprot_image);
        mIvClose = mContentView.findViewById(R.id.iv_close);
        mBtReportSubmit = mContentView.findViewById(R.id.bt_report_submit);
        mTvTextNum = mContentView.findViewById(R.id.tv_text_num);
        report_title_tv.setText(reportTitle);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mContentView.setOnTouchListener((v, event) -> true);
        mIvClose.setOnClickListener(v -> {
            dismiss();
        });
        mBtReportSubmit.setOnClickListener(v -> {
//            if(mEtReport.getText().toString().isEmpty()){
//                ToastView.showToast("举报内容不能为空");
//                return;
//            }
            reportRequestModel = new ReportRequestModel();
            reportRequestModel.setContent(mEtReport.getText().toString());
            reportRequestModel.setTagName(mReportType);
            mSubmitCallback.submit(reportRequestModel, mLocalMediaList);
            this.dismiss();
        });
        mEtReport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null){
                    if (s.length() > 100) {
                        ToastView.showToast("已达到最大字数限制");
                        mEtReport.setText(s.toString().substring(0, 100));
                        mEtReport.setSelection(100);
                    }else {
                        mTvTextNum.setText(s.length()+"");
                    }
                }else {
                    mTvTextNum.setText(0+"");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onChioceResult() {
        if ((context == null)) {
            return;
        }
        PictureSelector.create((Activity) context).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(MAX_PICTURE - mAdapter.listSize()) // 最大选择个数
//                .selectionData(mLocalMediaList)//是否传入已选图片
                .isCompress(true)
                .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
//                        mLocalMediaList.clear();
                        mLocalMediaList.addAll(result);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }

    @Override
    public void onPreview(int position, List<LocalMedia> result) {
        PictureSelector.create((Activity) context)
                .themeStyle(R.style.picture_default_style) // xml设置主题
                .setPictureStyle(getDefaultStyle())// 动态自定义相册主题
                //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isNotPreviewDownload(false)// 预览图片长按是否可以下载
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .openExternalPreview(position, result);
    }

    public interface SubmitCallback {
        void submit(ReportRequestModel reportRequestModel, List<LocalMedia> mLocalMediaList);
    }

    private PictureParameterStyle getDefaultStyle() {
        // 相册主题
        PictureParameterStyle mPictureParameterStyle = new PictureParameterStyle();
        // 是否改变状态栏字体颜色(黑白切换)
        mPictureParameterStyle.isChangeStatusBarFontColor = false;
        // 相册状态栏背景色
        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#393a3e");
        // 相册列表标题栏背景色
        mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#393a3e");
        // 相册父容器背景色
        mPictureParameterStyle.pictureContainerBackgroundColor = ContextCompat.getColor(context, R.color.color424242);
        // 相册返回箭头
        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.picture_icon_back;
        // 标题栏字体颜色
        mPictureParameterStyle.pictureTitleTextColor = ContextCompat.getColor(context, R.color.picture_color_white);
        return mPictureParameterStyle;
    }


}