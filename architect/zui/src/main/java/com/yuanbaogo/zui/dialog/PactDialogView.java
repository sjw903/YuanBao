package com.yuanbaogo.zui.dialog;

import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;
import com.yuanbaogo.zui.dialog.model.PactBean;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;

import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: 协议弹窗
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/12/21 9:57 AM
 * @Modifier:
 * @Modify:
 */
public class PactDialogView extends DialogsFragment implements View.OnClickListener {

    TextView dialogPpactViewTvTitle;

    ImageView dialogPactViewImgCancel;

    TextView dialogPactViewTvContent;

    View dialogPactViewLine;

    LinearLayout dialogPactViewLlBottom;

    TextView dialogPactViewTvCancel;

    View dialogPactViewLine2;

    TextView dialogPactViewTvDetermine;

    OnCallDialog onCallDialog;

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

        dialogPpactViewTvTitle.setVisibility(pactBean.isTvTitle() ? View.VISIBLE : View.GONE);
        dialogPpactViewTvTitle.setText(pactBean.getTvTitles());

        dialogPactViewImgCancel.setVisibility(pactBean.isImgCancel() ? View.VISIBLE : View.GONE);

        dialogPactViewLine.setVisibility(pactBean.isLine() ? View.VISIBLE : View.GONE);

        dialogPactViewLlBottom.setVisibility(pactBean.isLlBottom() ? View.VISIBLE : View.GONE);

        dialogPactViewTvCancel.setVisibility(pactBean.isCancel() ? View.VISIBLE : View.GONE);
        dialogPactViewLine2.setVisibility(pactBean.isCancel() ? View.VISIBLE : View.GONE);
        dialogPactViewTvCancel.setText(pactBean.getTvCancels());

        dialogPactViewTvDetermine.setVisibility(pactBean.isDetermine() ? View.VISIBLE : View.GONE);
        dialogPactViewTvDetermine.setText(pactBean.getTvDetermines());

        fileName = pactBean.getContentFileName();
        dialogPactViewTvContent.setText(ReadFromAssets());

        if (pactBean.getType() == 1) {
            return;
        }

        String pact = dialogPactViewTvContent.getText().toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(pact);
        //第一个出现的位置
        final int start = pact.indexOf("《");
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //防暴力点击
                if (ClickUtils.isFastClick()) {
                    return;
                }
                //用户服务协议点击事件
                RouterHelper.toWeb(WebUrls.proUser);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文件颜色
                ds.setColor(getResources().getColor(R.color.colorEA5504));
                // 去掉下划线
                ds.setUnderlineText(false);
            }
        }, start, start + 8, 0);
        //最后一个出现的位置
        final int end = pact.lastIndexOf("《");
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //防暴力点击
                if (ClickUtils.isFastClick()) {
                    return;
                }
                //隐私协议点击事件
                RouterHelper.toWeb(WebUrls.proPrivacy);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文件颜色
                ds.setColor(getResources().getColor(R.color.colorEA5504));
                // 去掉下划线
                ds.setUnderlineText(false);
            }
        }, end, end + 8, 0);
        dialogPactViewTvContent.setMovementMethod(LinkMovementMethod.getInstance());
        dialogPactViewTvContent.setText(ssb, TextView.BufferType.SPANNABLE);

    }

    /**
     * 设置提示内容
     *
     * @param pactBean 内容
     * @return 返回弹窗本身
     */
    public PactDialogView setPact(PactBean pactBean) {
        Message msg = mHandler.obtainMessage();
        msg.what = MSG_SET_CONTENT;
        msg.obj = pactBean;
        mHandler.sendMessage(msg);
        return this;
    }

    public void setOnCallDialog(OnCallDialog onCallDialog) {
        this.onCallDialog = onCallDialog;
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_pact_view;
    }

    @Override
    protected void intViews(View view) {
        dialogPpactViewTvTitle = view.findViewById(R.id.dialog_pact_view_tv_title);
        dialogPactViewImgCancel = view.findViewById(R.id.dialog_pact_view_img_cancel);
        dialogPactViewTvContent = view.findViewById(R.id.dialog_pact_view_tv_content);
        dialogPactViewLine = view.findViewById(R.id.dialog_pact_view_line);
        dialogPactViewLlBottom = view.findViewById(R.id.dialog_pact_view_ll_bottom);
        dialogPactViewTvCancel = view.findViewById(R.id.dialog_pact_view_tv_cancel);
        dialogPactViewLine2 = view.findViewById(R.id.dialog_pact_view_line2);
        dialogPactViewTvDetermine = view.findViewById(R.id.dialog_pact_view_tv_determine);
    }

    @Override
    protected void setTexts() {

    }

    @Override
    protected void setOnClicks() {
        dialogPactViewImgCancel.setOnClickListener(this);
        dialogPactViewTvCancel.setOnClickListener(this);
        dialogPactViewTvDetermine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_pact_view_img_cancel || id == R.id.dialog_pact_view_tv_cancel) {
            if (onCallCancel != null) {
                onCallCancel.onCallCancel();
                return;
            }
            dismiss();
        } else if (id == R.id.dialog_pact_view_tv_determine) {
            onCallDialog.onCallDetermine();
        }
    }

    @Override
    public int getHeight() {
        return 1000;
    }

    public int getWidth() {
        return 800;
    }

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

    OnCallCancel onCallCancel;

    public void setOnCallCancel(OnCallCancel onCallCancel) {
        this.onCallCancel = onCallCancel;
    }

    public interface OnCallCancel {
        void onCallCancel();
    }


}