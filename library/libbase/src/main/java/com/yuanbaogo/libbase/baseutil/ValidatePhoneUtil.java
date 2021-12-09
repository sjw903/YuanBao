package com.yuanbaogo.libbase.baseutil;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.android.arouter.utils.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 手机号验证
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/8/21 2:50 PM
 * @Modifier:
 * @Modify:
 */
public class ValidatePhoneUtil {

    //判断手机号码的正则表达式
    // 之前写的是[0-9]{0,11}，这样写的话表示0到11位数都可以，无法满足手机号的校验，修改为[0-9]{11}
    private static final String MOBILE_NUM_REGEX = "[0-9]{11}";
//    private static final String MOBILE_NUM_REGEX = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(16[0-9])|(19[0-9]))\\d{8}$";

    /**
     * 验证一个号码是不是手机号，用正则判断的
     *
     * @param mobileNumber
     */
    public static boolean validateMobileNumber(String mobileNumber) {
        if (mobileNumber == null || TextUtils.isEmpty(mobileNumber)) return false;
        Pattern p = Pattern.compile(MOBILE_NUM_REGEX);
        Matcher m = p.matcher(mobileNumber);
        return m.matches();
    }

    /**
     * 规则：至少包含大小写字母及数字中的两种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
//        String regex = "^[a-zA-Z0-9-.!@#$%^&*()+?><]+$";
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }

    /**
     *
     * 验证码是否符合4
     *
     * @param checkCodes 验证码
     * @return 返回值
     */
    public static boolean isCheckCode(String checkCodes) {
        if (checkCodes == null || TextUtils.isEmpty(checkCodes)) return false;
        Pattern p = Pattern
                .compile("^\\d{6}$");
        Matcher m = p.matcher(checkCodes);
        return m.matches();
    }

    /**
     * 软键盘隐藏
     */
    public static void hideShowKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //得到InputMethodManager的实例
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 软键盘显示
     */
    public static void showSoftInput(Context context, final View view) {
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.requestFocus();  //获得焦点
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//弹出小键盘
            }
        }, 0);
    }

}
