package com.yuanbaogo.homevideo.shortvideo.report.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.WindowManager;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.SelectPicPopupWindow;

import java.util.Arrays;
import java.util.List;


/**
 * 底下弹出的pop
 */
public class ReportSelectPopupWindow extends SelectPicPopupWindow {
    private List<String> datas;
    ReportContentPopupWindow mReportContentPopupWindow;
    ReportContentPopupWindow.SubmitCallback mSubmitCallback;
    private String reportTitle;

    @Override
    protected int initHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int initWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }


    //含默认值构造方法
    public ReportSelectPopupWindow(Activity activity,String reportTitle, ReportContentPopupWindow.SubmitCallback submitCallback) {
        this(activity,reportTitle, activity.getResources().getStringArray(R.array.report_list), submitCallback);
    }

    public ReportSelectPopupWindow(Activity activity,String reportTitle, String[] datas, ReportContentPopupWindow.SubmitCallback submitCallback) {
        this(activity,reportTitle, Arrays.asList(datas), submitCallback);
    }

    public ReportSelectPopupWindow(Activity activity,String reportTitle, List<String> datas, ReportContentPopupWindow.SubmitCallback submitCallback) {
        super(activity,reportTitle, null, datas);
        this.datas = datas;
        this.reportTitle = reportTitle;
        mSubmitCallback = submitCallback;
    }

    @Override
    protected void initView() {
        super.initView();
        setItemClickListener((parent, view, position, id) -> {
            dismiss();
            mReportContentPopupWindow = new ReportContentPopupWindow(mContext,reportTitle, datas.get(position), mSubmitCallback);
            mReportContentPopupWindow.showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        });
    }
}