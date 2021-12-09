package com.yuanbaogo.commonlib.web;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.yuanbaogo.commonlib.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommonWebFragment extends Fragment implements IClientSetup {
    private View mContentView;
    private AgentWeb mAgentWeb;
    private List<MiddlewareWebClientBase> mWebClients = new ArrayList();
    private List<MiddlewareWebChromeBase> mChromeClients = new ArrayList();

    public CommonWebFragment() {
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mContentView = inflater.inflate(R.layout.fragment_common_web, container, false);
        return this.mContentView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initWebView();
    }

    public AgentWeb getAgentWeb() {
        return this.mAgentWeb;
    }

    public WebView getWebView() {
        return this.mAgentWeb != null ? this.mAgentWeb.getWebCreator().getWebView() : null;
    }

    private void initWebView() {
        Bundle args = this.getArguments();
        if (args != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -1);
            ViewGroup parent = (ViewGroup)this.mContentView.findViewById(R.id.web_container);
            int progressColor = args.getInt("progress_color", -1);
            AgentWeb.CommonBuilder builder = AgentWeb.with(this).setAgentWebParent(parent, 0, params)
                    .useDefaultIndicator(progressColor != -1 ? progressColor : Color.parseColor("#f09940"));
            Iterator var6 = this.mWebClients.iterator();

            while(var6.hasNext()) {
                MiddlewareWebClientBase client = (MiddlewareWebClientBase)var6.next();
                builder.useMiddlewareWebClient(client);
            }

            var6 = this.mChromeClients.iterator();

            while(var6.hasNext()) {
                MiddlewareWebChromeBase client = (MiddlewareWebChromeBase)var6.next();
                builder.useMiddlewareWebChrome(client);
            }

            this.mAgentWeb = builder.createAgentWeb().ready().go(args.getString("url"));
        }
    }


    public void addCustomWebClient(MiddlewareWebClientBase webClient) {
        this.mWebClients.add(webClient);
    }

    public void addCustomChromeClient(MiddlewareWebChromeBase chromeClient) {
        this.mChromeClients.add(chromeClient);
    }


}
