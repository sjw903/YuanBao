package com.yuanbaogo.libbase.config;

import android.app.Application;

public class ApplicationConfigHelper {

    public static Application mApplication;

    public static int versionCode;

    public static boolean getLogSwitch(){
        if (EnvironmentConfig.ENVIRONMENT_DEV_TEST == EnvironmentConfig.getsEnvironment()) {
            return true;
        } else if (EnvironmentConfig.ENVIRONMENT_TEST == EnvironmentConfig.getsEnvironment()) {
            return true;
        } else if (EnvironmentConfig.ENVIRONMENT_QUASI == EnvironmentConfig.getsEnvironment()) {
            return true;
        } else if (EnvironmentConfig.ENVIRONMENT_PRODUCTION == EnvironmentConfig.getsEnvironment()) {
            return true;
        } else {
            return false;
        }
    }


}
