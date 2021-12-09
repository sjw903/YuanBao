package com.yuanbaogo.login.jiyan;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.geetest.sdk.GT3ConfigBean;
import com.geetest.sdk.GT3ErrorBean;
import com.geetest.sdk.GT3GeetestUtils;
import com.geetest.sdk.GT3Listener;
import com.geetest.sdk.GT3LoadImageView;
import com.geetest.sdk.utils.GT3ServiceNode;
import com.yuanbaogo.libbase.baseutil.LogUtil;
import com.yuanbaogo.login.R;

import org.json.JSONObject;

/**
 * 2021年5月28日15:20:47
 * 极验
 * guwei
 **/
public class JiYan {
    Context context;
    GT3GeetestUtils gt3GeetestUtils;
    private GT3ConfigBean gt3ConfigBean;
    private JiYanListener jiYanListener;

    public JiYan(Context context) {
        this.context = context;
        // 请在oncreate方法里初始化以获取足够手势数据来保证第一轮验证成功率
        gt3GeetestUtils = new GT3GeetestUtils(context);
        // 配置 GT3ConfigBean 文件, 也可在调用 startCustomFlow 方法前处理
        gt3ConfigBean = new GT3ConfigBean();
        // 设置验证模式, 1: bind, 2: unbind
        gt3ConfigBean.setPattern(1);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // 设置回退键是否 DismissDialog, 默认 Dismiss
        gt3ConfigBean.setUnCanceledOnTouchKeyCodeBack(false);
        // 设置点击灰色区域是否 DismissDialog, 默认 Dismiss
        gt3ConfigBean.setCanceledOnTouchOutside(false);
        // 设置语言, 如果为 null 则使用 Activity 默认语言
        gt3ConfigBean.setLang(null);
        // 设置加载 WebView 超时时间, 单位 ms, 默认10000, 仅且 WebView 加载静态文件超时，不包括之前的 https 请求
        gt3ConfigBean.setTimeout(Integer.parseInt( Constants.TIMEOUT_DEFAULT));
        // 设置 WebView 内部 H5 页面请求超时, 单位 ms, 默认 10000
        gt3ConfigBean.setWebviewTimeout(Integer.parseInt(Constants.TIMEOUT_DEFAULT));
        // 设置验证服务集群节点, 默认为中国节点, 使用其他节点需要相应配置, 否则无法使用验证
        gt3ConfigBean.setGt3ServiceNode(GT3ServiceNode.NODE_CHINA);
        // 设置自定义 LoadingView
        GT3LoadImageView gt3LoadImageView = new GT3LoadImageView(context);
        gt3LoadImageView.setIconRes(R.drawable.loading_test);
        gt3LoadImageView.setLoadViewWidth(48); // 单位dp
        gt3LoadImageView.setLoadViewHeight(48); // 单位dp
        gt3ConfigBean.setLoadImageView(gt3LoadImageView);
        // 设置回调监听
        gt3ConfigBean.setListener(new GT3Listener() {

            /**
             * 验证码加载完成
             * @param duration 加载时间和版本等信息，为json格式
             */
            @Override
            public void onDialogReady(String duration) {

            }

            /**
             * 图形验证结果回调
             * @param code 1为正常 0为失败
             */
            @Override
            public void onReceiveCaptchaCode(int code) {

            }

            /**
             * 自定义api2回调
             * @param result，api2请求上传参数
             */
            @Override
            public void onDialogResult(String result) {
                // 开启自定义api2逻辑
                if (jiYanListener != null) {
                    jiYanListener.jiyanSuccess(result);
                }
            }

            /**
             * 统计信息，参考接入文档
             * @param result
             */
            @Override
            public void onStatistics(String result) {

            }

            /**
             * 验证码被关闭
             * @param num 1 点击验证码的关闭按钮来关闭验证码, 2 点击屏幕关闭验证码, 3 点击返回键关闭验证码
             */
            @Override
            public void onClosed(int num) {

            }

            /**
             * 验证成功回调
             * @param result
             */
            @Override
            public void onSuccess(String result) {

            }

            /**
             * 验证失败回调
             * @param errorBean 版本号，错误码，错误描述等信息
             */
            @Override
            public void onFailed(GT3ErrorBean errorBean) {
                if (jiYanListener != null) {
                    jiYanListener.jiyanFailed();
                }
            }

            /**
             * 自定义api1回调
             */
            @Override
            public void onButtonClick() {
                if (jiYanListener != null) {
                    jiYanListener.jiyanButtonClick();
                }
            }
        });
        gt3GeetestUtils.init(gt3ConfigBean);
    }

    public void setJiYanListener(JiYanListener jiYanListener) {
        this.jiYanListener = jiYanListener;
    }
    public void startJY() {
        // 开启验证
        gt3GeetestUtils.startCustomFlow();
    }

    public void continueJY(JSONObject params) {
        // SDK可识别格式为
        // {"success":1,"challenge":"06fbb267def3c3c9530d62aa2d56d018","gt":"019924a82c70bb123aae90d483087f94","new_captcha":true}
        // TODO 设置返回api1数据，即使为 null 也要设置，SDK内部已处理
        try {
//            if (params != null && params.getInt("success") == 1) {
                gt3ConfigBean.setApi1Json(params);

                // 继续验证
                gt3GeetestUtils.getGeetest();
//            }
        } catch (Exception e) {
            LogUtil.e("极验异常", e.getMessage() + "");
        }
    }

    public void showSuccessDialog(boolean isShow) {
        if (isShow) {
            gt3GeetestUtils.showSuccessDialog();
        } else {
            gt3GeetestUtils.showFailedDialog();
        }
    }

    public void destroy() {
        if (gt3GeetestUtils != null) {
            gt3GeetestUtils.destory();
        }
    }

    public void dismiss(){
        if (gt3GeetestUtils != null) {
            gt3GeetestUtils.dismissGeetestDialog();
        }
    }

    public interface JiYanListener {
        void jiyanButtonClick();

        void jiyanSuccess(String result);

        void jiyanFailed();
    }

}
