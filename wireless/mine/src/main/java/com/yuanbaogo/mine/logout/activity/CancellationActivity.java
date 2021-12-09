package com.yuanbaogo.mine.logout.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.libbase.view.BaseActivity;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.logout.dialog.MyDialog;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.toast.ToastView;

import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: 注销界面
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/8/21 2:56 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.CANCELLATION_ACTIVITY)
public class CancellationActivity extends BaseActivity implements View.OnClickListener {

    HeadView loginCancellaHeadView;

    private Button loginCancellaButConfirmLogout;

    private TextView loginCancellaTvContent;

    private TextView loginCancellaTvChoose;

    private final String Encoding = "utf-8"; // 文件编码

    String mReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_cancellation);
        findbyIdView();
        text();
        onClick();
    }

    protected void findbyIdView() {
        loginCancellaHeadView = findViewById(R.id.login_cancella_head_view);
        loginCancellaButConfirmLogout = findViewById(R.id.login_cancella_but_confirm_logout);
        loginCancellaTvContent = findViewById(R.id.login_cancella_tv_content);
        loginCancellaTvChoose = findViewById(R.id.login_cancella_tv_choose);
    }

    protected void text() {
        initHeadView();
        loginCancellaTvContent.setText(ReadFromAssets("cancellation.txt"));
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setImgRight(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("注销");
        loginCancellaHeadView.setHead(headBean);
    }

    protected void onClick() {
        loginCancellaTvChoose.setOnClickListener(this);
        loginCancellaButConfirmLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.login_cancella_but_confirm_logout) {
            if (!TextUtils.isEmpty(mReason)) {
                ToastView.showToast(CancellationActivity.this, 0, "正在注销账号，请稍后");
            }
        } else if (id == R.id.login_cancella_tv_choose) {
            initChooseDialog();
        }
    }

    private void initChooseDialog() {
        MyDialog myDialog = new MyDialog(CancellationActivity.this);
        myDialog.setOnClickLoginInvalid(new MyDialog.OnClickLoginInvalid() {
            @Override
            public void setDefineOnclick() {
                for (MyDialog.ItemBean bean : myDialog.list) {
                    if (bean.isChoose()) {
                        mReason = bean.getTitle();
                        loginCancellaTvChoose.setText(mReason);
                    }
                }
                loginCancellaButConfirmLogout.setBackground(getResources().getDrawable(R.drawable.shape_gradient_bg_but_line_orrange));
                loginCancellaButConfirmLogout.setTextColor(getResources().getColor(R.color.colorE03030));
                myDialog.dismiss();
            }

            @Override
            public void setCancelOnclick() {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    /**
     * 读取Assets目录下的文件
     *
     * @return
     */
    private String ReadFromAssets(String fileName) {
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