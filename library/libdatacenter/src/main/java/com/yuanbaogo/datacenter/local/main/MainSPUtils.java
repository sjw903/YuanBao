package com.yuanbaogo.datacenter.local.main;

import com.yuanbaogo.datacenter.local.SharePreferenceConfigImpl;
import com.yuanbaogo.libbase.config.ApplicationConfigHelper;

public class MainSPUtils {

    public static final String FILE_NAME = "yuanbaogo_main";

    public static final String PACT = "yuanbao_pact";


    public static void savePact(boolean initPact) {
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).loadConfig(FILE_NAME);
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).setBoolean(PACT, initPact);
    }

    public static boolean getPact() {
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).loadConfig(FILE_NAME);
        boolean b = SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).getBoolean(PACT, true);

        return b;
    }
}
