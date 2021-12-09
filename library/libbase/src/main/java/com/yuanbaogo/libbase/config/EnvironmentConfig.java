package com.yuanbaogo.libbase.config;

public class EnvironmentConfig {
    
    public static final int ENVIRONMENT_DEV_TEST = 1; //开发
    public static final int ENVIRONMENT_TEST = 2; //测试
    public static final int ENVIRONMENT_QUASI = 3; //预生产
    public static final int ENVIRONMENT_PRODUCTION = 4; //生产


    private static int sEnvironment = 1;

    public EnvironmentConfig() {
    }

    public static int getsEnvironment() {
        return sEnvironment;
    }

    public static void setsEnvironment(int sEnvironment) {
        EnvironmentConfig.sEnvironment = sEnvironment;
    }


}
