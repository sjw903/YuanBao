package com.yuanbaogo.datacenter.local.user;

import android.text.TextUtils;

import com.yuanbaogo.datacenter.local.SharePreferenceConfigImpl;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.libbase.config.ApplicationConfigHelper;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/12/21 1:39 PM
 * @Modifier:
 * @Modify:
 */
public class UserStore {

    public static final String FILE_NAME = "yuanbaogo_user";
    public static String TOKEN = "";

    /**
     * 保存用户信息
     */
    public static void saveUser(UserInfo userInfo) {
        TOKEN = userInfo.getToken();
        String userStr = GsonUtil.GsonString(userInfo);
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).loadConfigCRYPTKEY(FILE_NAME);
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).setString("userinfo", userStr);
    }

    /**
     * 获取用户信息
     */
    public static UserInfo getUser() {
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).loadConfigCRYPTKEY(FILE_NAME);
        String userStr = SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).
                getString("userinfo", "");
        if (TextUtils.isEmpty(userStr)) {
            return null;
        }
        UserInfo userInfo = GsonUtil.GsonToBean(userStr, UserInfo.class);
        return userInfo;
    }

    /**
     * 清除用户信息
     */
    public static void cleanUser() {
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).loadConfigCRYPTKEY(FILE_NAME);
        SharePreferenceConfigImpl.getInstance(ApplicationConfigHelper.mApplication).clear();
        TOKEN = "";
    }

    public static String getToken() {
        if (TextUtils.isEmpty(TOKEN)) {
            UserInfo usrinfo = getUser();
            return usrinfo == null ? "" : usrinfo.getToken();
        } else {
            return TOKEN;
        }
    }

    public static String getYbCode() {
        UserInfo user = UserStore.getUser();
        if (user != null) {
            return user.getUserId();
        } else {
            return "";
        }
    }

    public static String getUserPhone() {
        UserInfo user = UserStore.getUser();
        if (user != null) {
            return user.getPhone();
        } else {
            return "";
        }
    }

    public static String getUserName() {
        UserInfo user = UserStore.getUser();
        if (user != null) {
            return user.getNickName();
        } else {
            return "";
        }
    }

    /**
     * 获取加密的手机号
     *
     * @return /
     */
    public static String getEncryptionPhone() {
        UserInfo user = UserStore.getUser();
        if (user != null) {
            String userPhone = user.getPhone();
            return userPhone.substring(0, 3) + "****" + userPhone.substring(7);
        } else {
            return "";
        }
    }

    public static String getImSign() {
        UserInfo user = UserStore.getUser();
        if (user != null) {
            return user.getImSign();
        } else {
            return "";
        }
    }

    public static void setImSign(String usersig) {
        UserInfo user = UserStore.getUser();
        if (user != null) {
            user.setImSign(usersig);
        }
        saveUser(user);
    }

    public static String getInviteCode() {
        UserInfo user = UserStore.getUser();
        if (user != null) {
            return user.getInvitationCode();
        } else {
            return "";
        }
    }

    /**
     * 判断用户是否登录
     *
     * @return true 为登录  false 为未登录
     */
    public static boolean isLogin() {
        UserInfo user = UserStore.getUser();
        return user != null;
    }

}
