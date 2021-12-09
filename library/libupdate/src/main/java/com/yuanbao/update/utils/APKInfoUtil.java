package com.yuanbao.update.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

public class APKInfoUtil {
    private static final String TAG = "APKInfoUtil";

    /**
     * 获取版本信息
     *
     * @param context /
     * @return 版本信息字符串  eg：3.0.0
     */
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context, context.getPackageName());
        return null == packageInfo ? "" : packageInfo.versionName;
    }

    /**
     * 获取版本号
     *
     * @param context /
     * @return 版本信息字符串  eg：300
     */
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context, context.getPackageName());
        return null == packageInfo ? 0 : packageInfo.versionCode;
    }

    @Nullable
    public static PackageInfo getPackageInfo(Context context, String packageName) {
        if (null == context) {
            Log.e(TAG, "getVersionName :: context = null");
            return null;
        }

        if (TextUtils.isEmpty(packageName)) {
            Log.e(TAG, "getVersionName :: wrong package name = " + packageName);
            return null;
        }

        try {
            return context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getPackageInfo :: " + e.toString());
        }

        return null;
    }
}
