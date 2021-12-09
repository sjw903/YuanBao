package com.yuanbaogo.datacenter.url.http;

import com.yuanbaogo.libbase.config.EnvironmentConfig;

public class RootUrl {

    public static String WEB_ROOT = "https://api.yuanbaogo.com";


    static {
        updateWeb();
    }

    public static void updateWeb() {
        if (EnvironmentConfig.ENVIRONMENT_DEV_TEST == EnvironmentConfig.getsEnvironment()) {
            // 开发环境
            WEB_ROOT = "http://dev.yb.com";
        } else if (EnvironmentConfig.ENVIRONMENT_TEST == EnvironmentConfig.getsEnvironment()) {
            // 测试环境
            WEB_ROOT = "http://test.yb.com";
        } else if (EnvironmentConfig.ENVIRONMENT_QUASI == EnvironmentConfig.getsEnvironment()) {
            // 准生产环境
            WEB_ROOT = "https://api.yuanbaogo.com";
        } else if (EnvironmentConfig.ENVIRONMENT_PRODUCTION == EnvironmentConfig.getsEnvironment()) {
            // 正式环境
            WEB_ROOT = "https://api.yuanbaogo.com";
        } else {
            // 正式环境
            WEB_ROOT = "https://api.yuanbaogo.com";
        }
    }

}
