package com.yuanbaogo.libbase.baseutil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CheckInputUtils {

    // 过滤特殊字符
    public static String StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& ;*（）——+|{}【】‘；：\"”“’。，、？]";

        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static boolean CheckName(String str) { // 调用位置：修改用戶名
        String regex = "^[a-zA-Z0-9_-\u4E00-\u9FA5]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.matches();

    }

    public static boolean InputName(String str) { // 添加用户，用户名不允许为中文
        String regex = "^[^\u4e00-\u9fa5]{0,}$";// 非中文
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.matches();

    }

    public static boolean CheckEmail(String str) { // 调用位置：注册、修改email
        String regex = "^[A-Za-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    public static boolean CheckSpecAsc(String str) { // 调用位置：修改设备名称
        String regex = "^[\\s\\w\\u4e00-\\u9fa5，。,.?？！!@#^&*()-_=+-—]+[^/]{0,32}$";// {}[]:;|'<>,.?/\"
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.matches();
        // return true;
    }

    public static boolean CheckIdea(String str) { // 调用位置：意见反馈
        String regex = "^[\\w\\u4e00-\\u9fa5，。,.?？！!]+$";// {}[]:;|'<>,.?/\"
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.matches();

    }

    public static boolean CheckLetterAndNum(String str) {
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    public static boolean CheckPassword(String str) {
        String regex = "^[a-zA-Z0-9`~!@#$%^&*()-=+_]+$";// {}[]:;|'<>,.?/\"
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    public static boolean CheckNum(String str) {
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    public static boolean CheckIP(String str) {
        String regex = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    public static boolean checkDeviceId(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        if (str.length() != 22) {
            return false;
        }
        return true;
    }

    // 判断字符串中是否包含中文
    public static boolean checkChinese(String str) {
        String string = null;
        try {
            string = URLDecoder.decode(str, "UTF-8"); // 为避免出现乱码时可能是其他字符而可以通过校验的情况
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String regex = ".*?[\u4E00-\u9FFF]+.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * @author tanyadong
     * @Title: isEmoji
     * @Description: 判断是否有表情
     * @date 2017/3/23 10:55
     */
    public static boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }
}
