package com.yuanbaogo.commonlib.web;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.yuanbaogo.commonlib.R;
import com.yuanbaogo.router.YBRouter;

@Route(path = YBRouter.WEB_ACTIVITY)
public class CommonWebActivity extends FragmentActivity {
    private static final String TAG = CommonWebActivity.class.getSimpleName();
    protected CommonWebFragment mFragment;
    protected View mRlTitle;
    protected TextView mTvTitle;
    protected ImageView mIvBack;
    protected ImageView mIvClose;
    protected ImageView mIvRight1;
    protected ImageView mIvRight2;
    protected MiddlewareWebChromeBase mChromeClient = new MiddlewareWebChromeBase() {
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title) && CommonWebActivity.this.mTvTitle != null) {
                CommonWebActivity.this.mTvTitle.setText(title);
            }

        }
    };
    protected MiddlewareWebClientBase mWebClient = new MiddlewareWebClientBase() {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (CommonWebActivity.this.mFragment.getWebView().canGoBack()) {
                CommonWebActivity.this.mIvClose.setVisibility(View.VISIBLE);
            } else {
                CommonWebActivity.this.mIvClose.setVisibility(View.GONE);
            }

        }
    };

    public CommonWebActivity() {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_common_web);
        this.initViews();
        this.initFragment();
    }

    private void initViews() {
        this.mRlTitle = this.findViewById(R.id.rl_title);
        this.mTvTitle = (TextView)this.findViewById(R.id.tv_title);
        this.mIvBack = (ImageView)this.findViewById(R.id.iv_back);
        this.mIvClose = (ImageView)this.findViewById(R.id.iv_close);
        this.mIvRight1 = (ImageView)this.findViewById(R.id.iv_right1);
        this.mIvRight2 = (ImageView)this.findViewById(R.id.iv_right2);
        this.mIvBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!CommonWebActivity.this.goBack()) {
                    CommonWebActivity.this.finish();
                }

            }
        });
        this.mIvClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CommonWebActivity.this.finish();
            }
        });
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("title_background")) {
                this.mRlTitle.setBackgroundColor(bundle.getInt("title_background"));
            }

            if (bundle.containsKey("title_color")) {
                this.mTvTitle.setTextColor(bundle.getInt("title_color"));
            }

            if (bundle.containsKey("title_size")) {
                this.mTvTitle.setTextSize((float)bundle.getInt("title_size"));
            }

            if (bundle.containsKey("back_drawable")) {
                this.mIvBack.setImageResource(bundle.getInt("back_drawable"));
            }

            if (bundle.containsKey("close_drawable")) {
                this.mIvClose.setImageResource(bundle.getInt("close_drawable"));
            }

        }
    }

    private void initFragment() {
        this.mFragment = new CommonWebFragment();
        this.setupClients(this.mFragment);
        this.mFragment.setArguments(this.getIntent().getExtras());
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, this.mFragment);
        transaction.commit();
    }

    protected WebView getWebView() {
        return this.mFragment.getWebView();
    }

    protected AgentWeb getAgentWeb() {
        return this.mFragment.getAgentWeb();
    }

    public void onBackPressed() {
        if (!this.goBack()) {
            super.onBackPressed();
        }
    }

    private boolean goBack() {
        if (this.mFragment != null) {
            WebView webView = this.mFragment.getWebView();
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }

        return false;
    }

    protected void setupTitleButtonRight1(int resId, View.OnClickListener listener) {
        this.setupTitleButtonRight1(this.getResources().getDrawable(resId), listener);
    }

    protected void setupTitleButtonRight2(int resId, View.OnClickListener listener) {
        this.setupTitleButtonRight2(this.getResources().getDrawable(resId), listener);
    }

    protected void setupTitleButtonRight1(Drawable drawable, View.OnClickListener listener) {
        if (this.mIvRight1 != null) {
            this.mIvRight1.setVisibility(View.VISIBLE);
            this.mIvRight1.setImageDrawable(drawable);
            this.mIvRight1.setOnClickListener(listener);
        }

    }

    protected void setupTitleButtonRight2(Drawable drawable, View.OnClickListener listener) {
        if (this.mIvRight2 != null) {
            this.mIvRight2.setVisibility(View.VISIBLE);
            this.mIvRight2.setImageDrawable(drawable);
            this.mIvRight2.setOnClickListener(listener);
        }

    }

    @CallSuper
    protected void setupClients(IClientSetup setup) {
        setup.addCustomWebClient(this.mWebClient);
        setup.addCustomChromeClient(this.mChromeClient);
    }
}
