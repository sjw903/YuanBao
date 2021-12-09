package com.yuanbaogo.zui.dialog;

import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.model.PactBean;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;

import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.io.InputStream;

public class AssetsDialogView extends DialogsFragment implements View.OnClickListener {

    TextView dialogAssetsViewTvTitle;

    TextView dialogAssetsViewTvContent;

    TextView dialogAssetsViewTvDetermine;

    private final String Encoding = "utf-8"; // 文件编码
    private String fileName = "integral.txt"; // 文件名

    private static final int MSG_SET_CONTENT = 0x11;//设置提示内容文字

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SET_CONTENT:
                    getPact((PactBean) msg.obj);
                    break;
            }
        }
    };


    public void getPact(PactBean pactBean) {

        dialogAssetsViewTvTitle.setText(pactBean.getTvTitles());

        dialogAssetsViewTvDetermine.setText(pactBean.getTvDetermines());

        fileName = pactBean.getContentFileName();
        dialogAssetsViewTvContent.setText(ReadFromAssets());
    }

    /**
     * 设置提示内容
     *
     * @param pactBean 内容
     * @return 返回弹窗本身
     */
    public AssetsDialogView setPact(PactBean pactBean) {
        Message msg = mHandler.obtainMessage();
        msg.what = MSG_SET_CONTENT;
        msg.obj = pactBean;
        mHandler.sendMessage(msg);
        return this;
    }


    @Override
    public int getLayout() {
        return R.layout.dialog_assets_view;
    }

    @Override
    protected void intViews(View view) {
        dialogAssetsViewTvTitle = view.findViewById(R.id.dialog_assets_view_tv_title);
        dialogAssetsViewTvContent = view.findViewById(R.id.dialog_assets_view_tv_content);
        dialogAssetsViewTvDetermine = view.findViewById(R.id.dialog_assets_view_tv_determine);
    }

    @Override
    protected void setTexts() {

    }

    @Override
    protected void setOnClicks() {
        dialogAssetsViewTvDetermine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_assets_view_tv_determine) {
            dismiss();
        }
    }

//    @Override
//    public int getHeight() {
//        return 1000;
//    }
//
//    public int getWidth() {
//        return 800;
//    }

    public int getGravity() {
        return Gravity.CENTER;
    }

    /**
     * 读取Assets目录下的文件
     *
     * @return
     */
    private String ReadFromAssets() {
        String retStr = "";
        try {
            // 打开文件到流
            InputStream inStream = getResources().getAssets().open(fileName);
            // 获取长度
            int fileLen = inStream.available();
            // 放入缓冲区
            byte[] buffer = new byte[fileLen];
            inStream.read(buffer);
            // 转码到文本
            retStr = EncodingUtils.getString(buffer, Encoding);
            //关闭
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retStr;
    }

}
