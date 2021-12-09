package com.yuanbaogo.libbase.baseutil;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.yuanbaogo.libbase.config.ApplicationConfigHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class LogUtil {
    private static Boolean LOG_SWITCH = ApplicationConfigHelper.getLogSwitch();
    private static Boolean LOG_WRITE_TO_FILE = false;
    private static char LOG_TYPE = 'v';
    private static String LOG_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static int SDCARD_LOG_FILE_SAVE_DAYS;
    private static String LOG_FILE_NAME;
    private static String LOG_FILE_SUFFIX;
    private static SimpleDateFormat LogSdf;
    private static SimpleDateFormat logfile;
    public static String customTagPrefix;

    static {
        SDCARD_LOG_FILE_SAVE_DAYS = 1;
        LOG_FILE_NAME = "yuanbao";
        LOG_FILE_SUFFIX = ".com.yuanbao.datacenter.log";
        LogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logfile = new SimpleDateFormat("yyyyMMdd");
        customTagPrefix = "";
    }

    public LogUtil() {
    }

    public static void w(String tag, Object msg) {
        log(tag, msg.toString(), 'w');
    }

    public static void e(String tag, Object msg) {
        log(tag, msg.toString(), 'e');
    }

    public static void d(String tag, Object msg) {
        log(tag, msg.toString(), 'd');
    }

    public static void i(String tag, Object msg) {
        log(tag, msg.toString(), 'i');
    }

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), 'v');
    }

    public static void w(String tag, String text) {
        log(tag, text, 'w');
    }

    public static void e(String tag, String text) {
        log(tag, text, 'e');
    }

    public static void d(String tag, String text) {
        log(tag, text, 'd');
    }

    public static void i(String tag, String text) {
        log(tag, text, 'i');
    }

    public static void v(String tag, String text) {
        log(tag, text, 'v');
    }

    private static void log(String tag, String msg, char level) {
        if (!TextUtils.isEmpty(msg)) {
            if (LOG_SWITCH) {
                if ('e' == level && ('e' == LOG_TYPE || 'v' == LOG_TYPE)) {
                    Log.e(tag, "LogUtil " + msg);
                } else if ('w' != level || 'w' != LOG_TYPE && 'v' != LOG_TYPE) {
                    if ('d' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
                        Log.d(tag, "LogUtil " + msg);
                    } else if ('i' != level || 'd' != LOG_TYPE && 'v' != LOG_TYPE) {
                        Log.v(tag, "LogUtil " + msg);
                    } else {
                        Log.i(tag, "LogUtil " + msg);
                    }
                } else {
                    Log.w(tag, "LogUtil " + msg);
                }

                if (LOG_WRITE_TO_FILE) {
                    delFile();
                    writeLogtoFile(String.valueOf(level), tag, msg);
                }
            }
        }
    }

    private static void writeLogtoFile(String logtype, String tag, String text) {
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        String needWriteMessage = LogSdf.format(nowtime) + "    " + logtype + "    " + tag + "    " + text;
        File file = new File(LOG_PATH_SDCARD_DIR, LOG_FILE_NAME + needWriteFiel + LOG_FILE_SUFFIX);

        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException var9) {
            var9.printStackTrace();
        }
    }

    public static void delFile() {
        String needDelFiel = logfile.format(getDateBefore());
        File file = new File(LOG_PATH_SDCARD_DIR, LOG_FILE_NAME + needDelFiel + LOG_FILE_SUFFIX);
        if (file.exists()) {
            file.delete();
        }
    }

    @SuppressLint("WrongConstant")
    private static Date getDateBefore() {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(5, now.get(5) - SDCARD_LOG_FILE_SAVE_DAYS);
        return now.getTime();
    }

    public static void logBigData(String tag, String msg) {
        if (LOG_SWITCH) {
            int LOG_MAXLENGTH = 2000;
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;

            for(int i = 0; i < 100; ++i) {
                if (strLength <= end) {
                    Log.e(tag + "_yuanbao_" + i, msg.substring(start, strLength));
                    break;
                }

                Log.e(tag + "_yuanbao_" + i, msg.substring(start, end));
                start = end;
                end += LOG_MAXLENGTH;
            }

        }
    }

    public static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }


}
