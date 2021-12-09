package com.yuanbaogo.libbase.baseutil;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Locale;

/**
 * 获取手机配置
 * HG 2020/12/16
 */
public class SystemUtil {


    private static final String TAG = "SystemUtil";

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

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
