package com.yuanbaogo.commonlib.web;

import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;

public interface IClientSetup {
    void addCustomWebClient(MiddlewareWebClientBase var1);

    void addCustomChromeClient(MiddlewareWebChromeBase var1);
}
