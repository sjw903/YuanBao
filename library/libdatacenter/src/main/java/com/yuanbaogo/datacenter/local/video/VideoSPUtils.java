package com.yuanbaogo.datacenter.local.video;

import com.yuanbaogo.datacenter.local.SharePreferenceConfigImpl;
import com.yuanbaogo.libbase.config.ApplicationConfigHelper;

public class VideoSPUtils {

    public static final String FILE_NAME = "yuanbaogo_video";

    public static final String LIVECOVERURL = "live_cover_url";
    public static final String LIVECOVERNAME = "live_cover_name";


    public static void saveLiveCoverUrl(String liveCoverUrl,String liveCoverName) {
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).loadConfig(FILE_NAME);
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).setString(LIVECOVERURL, liveCoverUrl);
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).setString(LIVECOVERNAME, liveCoverName);
    }

    public static String getLiveCoverUrl() {
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).loadConfig(FILE_NAME);
        String url = SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).
                getString(LIVECOVERURL, "");

        return url;
    }

    public static String getLiveCoverName() {
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).loadConfig(FILE_NAME);
        String name = SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).
                getString(LIVECOVERNAME, "");

        return name;
    }
}
