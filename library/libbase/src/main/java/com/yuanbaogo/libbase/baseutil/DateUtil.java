package com.yuanbaogo.libbase.baseutil;

import android.text.format.Time;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil {
    public static String DATE_FORMAT_OG = "yyyyMMdd";
    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String TIME_FORMAT_SPRIT = "yyyy/MM/dd HH:mm:ss";
    public static String YEAR_MOUTH = "yyyy年MM月";
    public static String YEAR_MOUTH_DAY = "yyyy年MM月dd日 HH:mm";
    public static String MOUTH_DAY_HH_MM = "MM月dd日 HH:mm";
    public static String MOUTH_DAY = "MM月dd日";
    public static String DAY = "dd日";
    public static String HOUR_MIN = "HH:mm";
    public static String HH_MM_SS = "HH:mm:ss";
    public static String DATE_FORMAT_DOT = "yyyy.MM.dd";
    public static String DATE_FORMAT_SPRIT = "yyyy/MM/dd";

    public static long string2Long(String time, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(time).getTime();
        } catch (ParseException var3) {
            var3.printStackTrace();
            Log.v("util", "TimeStrToLong():" + var3);
            return 0L;
        }
    }

    public static String date2Str(Date date, String format) {
        if (null == date) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
    }

    public static Date str2Date(String time, String format) {
        if (StringUtil.isNull(time)) {
            return null;
        } else {
            Date date = null;

            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                date = sdf.parse(time);
            } catch (ParseException var4) {
                var4.printStackTrace();
            }

            return date;
        }
    }

    public static String long2String(long time, String format) {
        if (time > 0L) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = new Date(time);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    public static String long2String(long time) {
        if (time > 0L) {
            SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MOUTH_DAY);
            SimpleDateFormat sdfMD = new SimpleDateFormat(MOUTH_DAY_HH_MM);
            SimpleDateFormat sdfHM = new SimpleDateFormat(HOUR_MIN);
            Date date = new Date(time);
            Date nowDate = new Date();
            String dateStr = "";
            long divDay = getDaysBetween(date, nowDate);
            if (0L == divDay) {
                dateStr = "今天 " + sdfHM.format(date);
            } else if (1L == divDay) {
                dateStr = "昨天 " + sdfHM.format(date);
            } else if (date.getYear() == nowDate.getYear()) {
                dateStr = sdfMD.format(date);
            } else {
                dateStr = sdf.format(date);
            }

            return dateStr;
        } else {
            return "";
        }
    }

    public static String changeFormatDate(String dateStr, String format, String format2) {
        String str2 = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        SimpleDateFormat sdf2 = new SimpleDateFormat(format2);

        try {
            Date date = sdf.parse(dateStr);
            str2 = sdf2.format(date);
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return str2;
    }

    public static Date changeFormatToDate(String dateStr, String format, String format2) {
        String str2 = "";
        Date toDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        SimpleDateFormat sdf2 = new SimpleDateFormat(format2);

        try {
            Date date = sdf.parse(dateStr);
            str2 = sdf2.format(date);
            toDate = sdf2.parse(str2);
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

        return toDate;
    }

    public static int differentDaysByMillisecond(String startDate, String endDate) {
        int days = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date dateS = format.parse(startDate);
            Date dateE = format.parse(endDate);
            days = (int)((dateE.getTime() - dateS.getTime()) / 86400000L);
        } catch (ParseException var6) {
            var6.printStackTrace();
        }

        return days;
    }

    public static Date getMonthFirstDay(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();

        try {
            Date date = format.parse(dateStr);
            cal.setTime(date);
            cal.set(5, cal.getMinimum(5));
            cal.getTime();
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        return cal.getTime();
    }

    public static Date getNextMonthFirstDay(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();

        try {
            Date date = format.parse(dateStr);
            cal.setTime(date);
            cal.set(5, cal.getMinimum(5));
            cal.add(2, 1);
            cal.getTime();
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        return cal.getTime();
    }

    public static int getNowYear() {
        Time time = new Time();
        time.setToNow();
        int year = time.year;
        return year;
    }

    public static int getNowHour() {
        Time time = new Time();
        time.setToNow();
        int hour = time.hour;
        return hour;
    }

    public static int getNowMonth() {
        Time time = new Time();
        time.setToNow();
        int month = time.month;
        return month;
    }

    public static int getNowDay() {
        Time time = new Time();
        time.setToNow();
        int monthDay = time.monthDay;
        return monthDay;
    }

    public static String getNowDate() {
        Time time = new Time();
        time.setToNow();
        int year = time.year;
        int month = time.month + 1;
        int day = time.monthDay;
        String date = String.format("%d-%02d-%02d", year, month, day);
        return date;
    }

    public static String getNowTime() {
        Time time = new Time();
        time.setToNow();
        int hour = time.hour;
        int min = time.minute;
        int sec = time.second;
        String st = String.format("%d:%02d:%02d", hour, min, sec);
        return st;
    }

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(c.getTime());
    }

    public static String getCurrentTime(String format) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(c.getTime());
    }

    public static long getTimeLength(String begin, String end) {
        long time1 = string2Long(begin, TIME_FORMAT);
        long time2 = string2Long(end, TIME_FORMAT);
        return time2 - time1;
    }

    public static long getTimeLength(String begin, String end, String format) {
        long time1 = string2Long(begin, format);
        long time2 = string2Long(end, format);
        return time2 - time1;
    }

    public static Date getDateBefore(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(5, now.get(5) - day);
        return now.getTime();
    }

    public static Date getDateAfter(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(5, now.get(5) + day);
        return now.getTime();
    }

    public static long getDaysBetween(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(11, 0);
        fromCalendar.set(12, 0);
        fromCalendar.set(13, 0);
        fromCalendar.set(14, 0);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(11, 0);
        toCalendar.set(12, 0);
        toCalendar.set(13, 0);
        toCalendar.set(14, 0);
        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / 86400000L;
    }

    public static List<Date> getDateFromStartAndEnd(Calendar startDay, Calendar endDay) {
        List<Date> datas = new ArrayList();
        if (startDay.compareTo(endDay) >= 0) {
            return datas;
        } else {
            Calendar currentPrintDay = startDay;

            while(true) {
                currentPrintDay.add(5, 1);
                if (currentPrintDay.compareTo(endDay) == 0) {
                    return datas;
                }

                datas.add(currentPrintDay.getTime());
            }
        }
    }

    public static boolean isLessThanToday(Date date) {
        if (date == null) {
            return false;
        } else {
            Date nowDate = new Date();
            nowDate = wholePointDate(nowDate);
            date = wholePointDate(date);
            return nowDate.compareTo(date) == 1;
        }
    }

    private static Date wholePointDate(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        if (gc.get(11) == 0 && gc.get(12) == 0 && gc.get(13) == 0) {
            return new Date(date.getTime());
        } else {
            Date date2 = new Date(date.getTime() - (long)(gc.get(11) * 60 * 60 * 1000) - (long)(gc.get(12) * 60 * 1000) - (long)(gc.get(13) * 1000) - 86400000L);
            return date2;
        }
    }

    public static boolean equalsIngoreHMS(Date first, Date second) {
        Calendar firstC = GregorianCalendar.getInstance();
        firstC.setTime(first);
        firstC.set(11, 0);
        firstC.set(12, 0);
        firstC.set(13, 0);
        firstC.set(14, 0);
        Calendar secondC = GregorianCalendar.getInstance();
        secondC.setTime(second);
        secondC.set(11, 0);
        secondC.set(12, 0);
        secondC.set(13, 0);
        secondC.set(14, 0);
        return firstC.equals(secondC);
    }

    /**
     * 由过去的某一时间,计算距离当前的时间
     */
    public static String CalculateTime(String time) {
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        String msg = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 指定时间格式
        Date setTime = null; // 指定时间
        try {
            setTime = sdf.parse(time); // 将字符串转换为指定的时间格式
        } catch (ParseException e) {

            e.printStackTrace();
        }
        long reset = setTime.getTime(); // 获取指定时间的毫秒数
        long dateDiff = nowTime - reset;


        if (dateDiff < 0) {
            msg = "输入的时间不对";
        } else {
            long dateTemp1 = dateDiff / 1000; // 秒
            long dateTemp2 = dateTemp1 / 60; // 分钟
            long dateTemp3 = dateTemp2 / 60; // 小时
            long dateTemp4 = dateTemp3 / 24; // 天数
            long dateTemp5 = dateTemp4 / 30; // 月数
            long dateTemp6 = dateTemp5 / 12; // 年数
            if (dateTemp6 > 0) {
                msg = dateTemp6 + "年前";
            } else if (dateTemp5 > 0) {
                msg = dateTemp5 + "个月前";
            } else if (dateTemp4 > 0) {
                msg = dateTemp4 + "天前";
            } else if (dateTemp3 > 0) {
                msg = dateTemp3 + "小时前";
            } else if (dateTemp2 > 0) {
                msg = dateTemp2 + "分钟前";
            } else if (dateTemp1 > 0) {
                msg = "刚刚";
            }
        }
        return msg;
    }

    /**
     * 由过去的某一时间,计算距离当前的时间
     */
    public static String dateCalculateTime(String time) {
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        String msg = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 指定时间格式
        Date setTime = null; // 指定时间
        try {
            setTime = sdf.parse(time); // 将字符串转换为指定的时间格式
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long reset = setTime.getTime(); // 获取指定时间的毫秒数
        long diff = nowTime - reset;

        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数long diff;
        if (diff > 0) {
            String day = diff / nd +"";//计算差多少天
            String hour = diff % nd / nh +"";//计算差多少小时
            String min = diff % nd % nh / nm +"";//计算差多少分钟
            String sec = diff % nd % nh % nm / ns +"";//计算差多少秒//输出结果

            msg = hour + ":" + min + ":" + sec;

        }
        return msg;
    }
}
