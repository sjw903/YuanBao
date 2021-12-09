package com.yuanbaogo.commonlib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具类
 */
public class DateUtils {

    public static SimpleDateFormat yyyyMMdd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static SimpleDateFormat yyyyMMdd_HH_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static SimpleDateFormat MM_dd = new SimpleDateFormat("MM-dd", Locale.getDefault());
    public static SimpleDateFormat HH_mm = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static SimpleDateFormat MMdd_HH_mm = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());

    /**
     * 获取格林威治时间(1970年至今的秒数)
     */
    public static long getGMTime1() {
        yyyyMMdd_HH_mm_ss.setTimeZone(TimeZone.getTimeZone("Etc/Greenwich"));
        String format = yyyyMMdd_HH_mm_ss.format(new Date());
        Date gmDate = null;
        try {
            gmDate = yyyyMMdd_HH_mm_ss.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return gmDate.getTime() / 1000;
    }

    /**
     * 获取年月日
     *
     * @return
     */
    public static String getYMD() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取格林威治时间 即1970年至今的秒数
     */
    public static long getGMTime2() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 获取时间HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        String time = null;
        String date = yyyyMMdd_HH_mm_ss.format(new Date());
        //"\\s"以空格截断
        String[] split = date.split("\\s");
        if (split.length > 1) {
            time = split[1];
        }
        return time;
    }

    /**
     * 获取当前时间的年月日时分秒
     *
     * @return
     */
    public static String current() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        return year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分" + second + "秒";
    }

    /**
     * 得到昨天的日期
     *
     * @return
     */
    public static String getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return yyyyMMdd_HH_mm_ss.format(calendar.getTime());
    }

    /**
     * 得到今天的日期
     *
     * @return
     */
    public static String getTodayDate() {
        return yyyyMMdd_HH_mm_ss.format(new Date());
    }

    /**
     * 得到明天的日期
     *
     * @return
     */
    public static String getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return yyyyMMdd_HH_mm_ss.format(calendar.getTime());
    }

    /**
     * 时间转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr(long timeStamp) {
        return yyyyMMdd_HH_mm_ss.format(timeStamp * 1000);
    }

    /**
     * 时间转化为时间格式
     *
     * @param dateString 毫秒
     * @return
     */
    public static String timeStampToStrs(long dateString) {
        String times = yyyyMMdd_HH_mm_ss.format(new Date(dateString));
        return times;
    }

    /**
     * 时间转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToYyyyMmDdHhSs(long timeStamp) {
        return yyyyMMdd_HH_mm.format(timeStamp * 1000);
    }

    /**
     * 时间转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr1(long timeStamp) {
        return yyyyMMdd_HH_mm_ss.format(timeStamp * 1000);
    }

    /**
     * 时间转化为时间(几点)
     *
     * @param time
     * @return
     */
    public static String timeStampToTime(long time) {
        return yyyyMMdd_HH_mm_ss.format(time * 1000);
    }

    /**
     * 将日期格式转化为时间(秒数)
     *
     * @param time
     * @return
     */
    public static long getStringToDate(String time) {
        Date date = new Date();
        try {
            date = yyyyMMdd_HH_mm.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    /**
     * 将日期格式转化为时间(秒数)
     *
     * @param time
     * @return
     */
    public static long getString2Date(String time) {
        Date date = new Date();
        try {
            date = yyyyMMdd.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    /**
     * 判断是否大于当前时间
     *
     * @param time
     * @return
     */
    public static boolean judgeCurrTime(String time) {
        Date date;
        try {
            date = yyyyMMdd_HH_mm.parse(time);
            long t = date.getTime();
            long round = System.currentTimeMillis();
            return t - round > 0;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * 判断是否大于当前时间
     *
     * @param time
     * @return
     */
    public static boolean judgeCurrTime(long time) {
        long round = System.currentTimeMillis();
        if (time - round > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较后面的时间是否大于前面的时间
     *
     * @param
     * @return
     */
    public static boolean judgeTime2Time(String time1, String time2) {
        try {
            //转化为时间
            Date date1 = yyyyMMdd_HH_mm.parse(time1);
            Date date2 = yyyyMMdd_HH_mm.parse(time2);
            //获取秒数作比较
            long l1 = date1.getTime() / 1000;
            long l2 = date2.getTime() / 1000;
            return l2 - l1 > 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 得到日期 yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String formatDate(long time) {
        String date = yyyyMMdd.format(time * 1000);
        return date;
    }

    /**
     * 得到日期 yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String formatDate2(long time) {
        String date = yyyyMMdd.format(time);
        return date;
    }

    /**
     * 得到时间 yyyy-MM-dd HH:mm:ss
     *
     * @param timeStamp
     * @return
     */
    public static String getTime(long timeStamp) {
        return yyyyMMdd_HH_mm_ss.format(timeStamp);
    }

    /**
     * 得到时间 yyyy-MM-dd HH:mm
     *
     * @param timeStamp
     * @return
     */
    public static String getTimeNoSs(long timeStamp) {
        return yyyyMMdd_HH_mm.format(timeStamp);
    }

    /**
     * 将一个时间转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;
        return time / 60 + "";
    }

    /**
     * 获得当前时间差
     *
     * @param timeStamp
     * @return
     */
    public static int nowCurrentTime(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = timeStamp - curTime;
        return (int) time;
    }


    /**
     * 获取当前的时 -->flag==true
     * 获取当前的分 -->flag==false
     *
     * @return
     */
    public static String nowCurrentPoint(long time, boolean flag) {
        long hour = 1000 * 60 * 60;
        long minute = 1000 * 60;
        if (flag) {
            return (time / hour) + "";
        } else {
            long remain = time % hour;
            return (remain / minute) + "";
        }
    }

    /**
     * 获取当前的月日
     *
     * @return
     */
    public static String getDateMonthDay(long time) {
        return MM_dd.format(time);
    }

    /**
     * 获取当前的时分
     *
     * @return
     */
    public static String getDateHourMinute(long time) {
        return HH_mm.format(time);
    }

    /**
     * 获取当前的日时分
     *
     * @return
     */
    public static String getDateDayHourMinute(long time) {
        return MMdd_HH_mm.format(time);
    }

    /**
     * 将标准时间格式HH:mm:ss化为当前的时间差值
     *
     * @param str
     * @return
     */
    public static String StandardFormatStr(String str) {
        try {
            Date d = yyyyMMdd_HH_mm_ss.parse(str);
            long timeStamp = d.getTime();

            long curTime = System.currentTimeMillis() / (long) 1000;
            long time = curTime - timeStamp / 1000;
            return time / 60 + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将一个时间转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }


    /**
     * 日期变量转成对应的星期字符串
     *
     * @param date
     * @return
     */

    public static final int WEEKDAYS = 7;
    //星期字符数组
    public static String[] WEEK = {"周日", "周一", "周二", "周三",
            "周四", "周五", "周六"};

    public static String DateToWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }
        return WEEK[dayIndex - 1];
    }

    private static final long SEVEN_DAY = 7 * 24 * 60 * 60 * 1000;

    /**
     * 还剩多少时间
     *
     * @param date /
     * @return 多少天多少小时多少分钟
     */
    public static String remainTime(long date) {
        long end = date + SEVEN_DAY;
        long current = end - System.currentTimeMillis();
        return "还剩" + timeToString(current);
    }

    public static String timeToString(long milliseconds) {
        final long s = 1000;
        final long m = 60 * s;
        final long h = 60 * m;
        final long d = 24 * h;

        long days = milliseconds / d;
        long hours = (milliseconds % d) / h;
        long minutes = (milliseconds % h) / m;

        StringBuilder stringBuilder = new StringBuilder();
        if (days > 0) {
            stringBuilder.append(days).append("天");
        }
        if (hours > 0) {
            stringBuilder.append(hours).append("时");
        }
        if (minutes > 0) {
            stringBuilder.append(minutes).append("分");
        }
        return stringBuilder.toString();
    }

}
