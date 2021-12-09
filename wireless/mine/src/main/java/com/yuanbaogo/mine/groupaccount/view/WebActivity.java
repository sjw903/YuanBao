package com.yuanbaogo.mine.groupaccount.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.libshare.ShareUtil;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.libshare.model.ShareParamete;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.groupaccount.contract.WebContract;
import com.yuanbaogo.mine.groupaccount.model.WebBean;
import com.yuanbaogo.mine.groupaccount.presenter.WebPresenter;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.dialog.GenerateMitoDialogView;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.toast.ToastView;

import org.json.JSONException;

import java.util.HashMap;

/**
 * @Description: 充值权益
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/13/21 9:47 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.WEB_JS_ACTIVITY)
public class WebActivity extends MvpBaseActivityImpl<WebContract.View, WebPresenter>
        implements ShareUtil.OnCallShare, WebContract.View {

    HeadView webHeadView;

    /**
     * 链接
     */
    @Autowired(name = YBRouter.WebActivityParams.url)
    String url;

    /**
     * 是否显示HeadView
     */
    @Autowired(name = YBRouter.WebActivityParams.isShow)
    boolean isShow;

    TextView tvTitle;

    WebView webView;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_web;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        webHeadView = findViewById(R.id.web_head_view);
        webView = findViewById(R.id.web_view);
        tvTitle = webHeadView.getLibHeadTvTitle();
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if (url.startsWith(WebUrls.inviUser.substring(0, WebUrls.inviUser.indexOf("?")))) {
            mPresenter.getWXcode(ShareParamete.WEIXIN_INVITE_PATH
                            .substring(0, ShareParamete.WEIXIN_INVITE_PATH.indexOf("?")),
                    "i=" + UserStore.getInviteCode());
        }

        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);

        initHeadView();
        initWebViews();
    }


    private void initWebViews() {
        WebChromeClient webClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                tvTitle.setText(s);
            }
        };
        webView.setWebChromeClient(webClient);
        if (url != null && !url.equals("")) {
            webView.loadUrl(url);
        }
        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);
        // 设置缓存路径
        // webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);

        webSettings.setSupportMultipleWindows(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);

        webSettings.setDomStorageEnabled(true);//节点缓存？？
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "android");
        //在User Agent中添加" device/android"作为标记
        webSettings.setUserAgentString(webSettings.getUserAgentString() + WebUrls.device);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("weixin://wap/pay?")) {//微信
                    try {
                        goToPay(url);
                    } catch (Exception e) {
                        ToastView.showToast("未安装微信的客户端");
                    }
                    return true;
                } else if (url.startsWith("amapuri://route")) {//高德
                    try {
                        goToPay(url);
                    } catch (Exception e) {
                        ToastView.showToast("未安装高德的客户端");
                    }
                    return true;
                } else if (parseScheme(url)) {//支付宝
                    try {
                        goToPay(url);
                    } catch (Exception e) {
                        ToastView.showToast("未安装支付宝的客户端");
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();// 接受所有网站的证书
            }

        });
    }

    public boolean parseScheme(String url) {
        if (url.startsWith("alipays:") || url.startsWith("alipay")) {
            return true;
        } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                && (url.startsWith("alipays:") || url.startsWith("alipay"))) {
            return true;
        } else {
            return false;
        }
    }

    public void goToPay(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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

    //解决开启定位失败问题
    private class MyWbChromeClient extends WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(
                String s,
                GeolocationPermissionsCallback geolocationPermissionsCallback) {
            geolocationPermissionsCallback.invoke(s, true, false);
            super.onGeolocationPermissionsShowPrompt(s, geolocationPermissionsCallback);
        }
    }

    private void initHeadView() {
        if (isShow) {
            webHeadView.setVisibility(View.VISIBLE);
        } else {
            webHeadView.setVisibility(View.GONE);
        }
        HeadBean headBean = new HeadBean()
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("")
                .setImgRight(false);
        webHeadView.setHead(headBean);
    }

    Bitmap bitmap;

    @Override
    public void setWXcode(String bean) {
        byte[] decode = Base64.decode(bean.split(",")[1], Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    @Override
    public void initWXcode() {
        finish();
    }

    public class MyJavaScriptInterface {

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void executeClientAction(String string) throws JSONException {
            WebBean webBean = GsonUtil.GsonToBean(string, WebBean.class);
            if (webBean.getType().equals("closeWindow")) {
                finish();
            } else if (webBean.getType().equals("topUpTicket")) {
                if (webBean.getParams().getPrice().equals("500")) {
                    RouterHelper.toRechargeNow(1);
                } else if (webBean.getParams().getPrice().equals("5000")) {
                    RouterHelper.toRechargeNow(2);
                } else if (webBean.getParams().getPrice().equals("50000")) {
                    RouterHelper.toRechargeNow(3);
                }
            } else if (webBean.getType().equals("wxShare")) {
                ShareBean shareBean = new ShareBean()
                        .setUmMinUrl(ShareParamete.WEIXIN_INVITE_PATH.replace("{inviteCode}",
                                UserStore.getInviteCode()))
                        .setUmMinPath(ShareParamete.WEIXIN_INVITE_PATH.replace("{inviteCode}",
                                UserStore.getInviteCode()))
                        .setUmMinUserName(ShareParamete.WEIXIN_APPLETS_ORIGINAL_ID)
                        .setUmImgMipmap(R.mipmap.icon_invite_friends_bg)
                        .setTitle("邀请好友一起赚钱");
                ShareUtil.getShareUtils()
                        .shareUtilss(WebActivity.this)
                        .setOnCallShare(WebActivity.this)
                        .setUmMin(ShareUtil.getShareUtils().umMin(shareBean), ShareParamete.WEIXIN);
            } else if (webBean.getType().equals("createImage")) {
                ShareBean shareBean = new ShareBean()
                        .setTitle("我要分享图片到好友")
                        .setWxcode(bitmap);//请求接口拿到的
                GenerateMitoDialogView generateMitoDialogView = new GenerateMitoDialogView(shareBean);
                generateMitoDialogView.show(getSupportFragmentManager(), "generate_mito");
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放资源
        webView.destroy();
        webView = null;
    }

}